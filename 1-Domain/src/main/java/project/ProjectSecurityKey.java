package project;

import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ProjectSecurityKey {

    private final String key;

    public ProjectSecurityKey() {
        int length = 128;
        boolean useLetters = true;
        boolean useNumbers = true;
        this.key = RandomStringUtils.random(length, useLetters, useNumbers);
    }
    
    protected String getKey() {
        return this.key;
    }
    
    public String getHashedKey() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(this.key.getBytes(StandardCharsets.UTF_8));
        String hashString = Base64.getEncoder().encodeToString(hash);
        return hashString;
    }
}
