package de.krayadev.domain.project;

import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Embeddable
@Getter
public class ProjectSecurityKey {

    @Column(length = 128, columnDefinition = "bpchar(128)", unique = true, updatable = false)
    private final String key;

    public ProjectSecurityKey() {
        int length = 128;
        boolean useLetters = true;
        boolean useNumbers = true;
        this.key = RandomStringUtils.random(length, useLetters, useNumbers);
    }
    
    public String hashedKey() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(this.key.getBytes(StandardCharsets.UTF_8));
        String hashString = Base64.getEncoder().encodeToString(hash);
        return hashString;
    }
}
