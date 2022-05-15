package de.krayadev.domain.aggregates.projectAggregate.valueObjects.ProjectSecurityKey;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProjectSecurityKeyTest {

    @Test
    void testGetValue() {
        ProjectSecurityKey key = new ProjectSecurityKey();
        String keyValue = key.value;

        String hashedKeyValue = key.getValue();

        assertNotEquals(keyValue, hashedKeyValue);
    }

    @Test
    void testHashedKey() throws NoSuchAlgorithmException {
        ProjectSecurityKey key = new ProjectSecurityKey();

        for (int i = 0; i < 100; i++) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(key.value.getBytes(StandardCharsets.UTF_8));
            String hashString = Base64.getEncoder().encodeToString(hash);

            assertEquals(hashString, key.hashedKey());
        }
    }

}
