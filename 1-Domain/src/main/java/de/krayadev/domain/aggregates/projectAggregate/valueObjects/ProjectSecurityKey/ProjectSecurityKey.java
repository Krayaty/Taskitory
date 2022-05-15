package de.krayadev.domain.aggregates.projectAggregate.valueObjects.ProjectSecurityKey;

import lombok.NonNull;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Embeddable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

@Embeddable
public final class ProjectSecurityKey{

    @NonNull
    protected final String value;

    public ProjectSecurityKey() {
        int length = 128;
        boolean useLetters = true;
        boolean useNumbers = true;
        this.value = RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public String getValue(){
        try {
            return this.hashedKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected String hashedKey() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(this.value.getBytes(StandardCharsets.UTF_8));
        String hashString = Base64.getEncoder().encodeToString(hash);
        return hashString;
    }

    @Override
    public String toString(){
        try {
            return this.hashedKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (getClass() == o.getClass()){
            ProjectSecurityKey that = (ProjectSecurityKey) o;
            return Objects.equals(value, that.value);

        } else if(o instanceof String){
            return Objects.equals(this.getValue(), o);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
