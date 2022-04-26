package de.krayadev.domain.valueobjects;

import lombok.NonNull;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Embeddable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

@Embeddable
public final class ProjectSecurityKey {

    @NonNull
    private final String key;

    public ProjectSecurityKey() {
        int length = 128;
        boolean useLetters = true;
        boolean useNumbers = true;
        this.key = RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public String getKey() throws NoSuchAlgorithmException {
        return this.hashedKey();
    }
    
    public String hashedKey() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(this.key.getBytes(StandardCharsets.UTF_8));
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
        if (o == null || getClass() != o.getClass()) return false;
        ProjectSecurityKey that = (ProjectSecurityKey) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
