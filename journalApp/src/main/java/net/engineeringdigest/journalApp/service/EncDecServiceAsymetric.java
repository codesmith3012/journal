package net.engineeringdigest.journalApp.service;

import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;


// This is the  service layer that we have made because now we want to use the asymetric encryption and decryption and the private and public key would be generated only once and in pair i have not implemented but you can do it if you like it...
@Service
public class EncDecServiceAsymetric {

    private static final String RSA = "RSA";

    public PrivateKey privateKey ;
    public PublicKey publicKey ;

    // Constructor to initialize keys once
    public EncDecServiceAsymetric() throws NoSuchAlgorithmException {
        init();  // Generate keys once during service initialization
    }
    // used to generate private and public key pair which is then used for asymmetric encryption and decryption
    public void init() throws NoSuchAlgorithmException {
        KeyPair keyPair = generateKeyPair();
        privateKey = keyPair.getPrivate();// generating private key
        publicKey = keyPair.getPublic();// generating public key
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator  = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(4096);
        return keyPairGenerator.generateKeyPair();
    }

    public String encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IllegalBlockSizeException, BadPaddingException {
        init();
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] encryptedValue  = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedValue);

    }

    public String decrypt(String encryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] decryptedValue  = cipher.doFinal(Base64.getMimeDecoder().decode(encryptedData));
        return new String(decryptedValue);

    }
}
