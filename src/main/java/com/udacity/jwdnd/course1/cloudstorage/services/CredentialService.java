package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int saveCredential(CredentialForm credentialForm) {
        if (credentialForm.getId() != null) {
            // Update credentials
            Credential credential = this.credentialMapper.getCredential(credentialForm.getId());
            if (credential != null && (credential.getUserid() == credentialForm.getUserid())) {
                credential.setUrl(credentialForm.getUrl());
                credential.setUsername(credentialForm.getUsername());
                String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), credential.getKey());
                credential.setPassword(encryptedPassword);
                this.credentialMapper.update(credential);

                return 1;
            } else {
                return -1;
            }

        } else {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
            Credential credential = new Credential(
                    null,
                    credentialForm.getUrl(),
                    credentialForm.getUsername(),
                    encodedKey,
                    encryptedPassword,
                    credentialForm.getUserid()
            );

            return this.credentialMapper.insert(credential);
        }
    }

    public Credential getCredential(Integer credentialid) {
        return this.credentialMapper.getCredential(credentialid);
    }

    public Credential[] getAllCredentials(Integer userid) {
        Credential[] credentials = this.credentialMapper.getAllCredentials(userid);

        // Get clear text password
        for (Credential c: credentials) {
            String password = this.encryptionService.decryptValue(c.getPassword(), c.getKey());
            c.setClearPassword(password);
        }
        
        return credentials;
    }

    public void deleteCredential(Integer credentialid) {
        this.credentialMapper.delete(credentialid);
    }
}
