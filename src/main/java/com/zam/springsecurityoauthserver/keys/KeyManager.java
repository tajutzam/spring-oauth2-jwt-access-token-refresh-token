package com.zam.springsecurityoauthserver.keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Objects;


@Component
public class KeyManager {

    @Autowired
    private Environment environment;

    @Value("${access-token.private}")
    private String accessTokenPrivatePath;

    @Value("${access-token.public}")
    private String accessTokenPublicPath;


    @Value("${refresh-token.private}")
    private String refreshTokenPrivatePath;

    @Value("${refresh-token.public}")
    private String refreshTokenPublicPath;

    private KeyPair _accessTokenKeyPair;
    private KeyPair _refreshTokenKeyPair;


    public KeyPair get_accessTokenKeyPair() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {

        if (Objects.isNull(_accessTokenKeyPair)) {
            _accessTokenKeyPair = getKeyPair(accessTokenPublicPath, accessTokenPrivatePath);
        }

        return _accessTokenKeyPair;
    }

    public KeyPair get_refreshTokenKeyPair() {

        if (Objects.isNull(_refreshTokenKeyPair)) {
            _refreshTokenKeyPair = getKeyPair(refreshTokenPublicPath, refreshTokenPrivatePath);
        }
        return _refreshTokenKeyPair;
    }

    private KeyPair getKeyPair(String publicPath, String privatePath)  {

        KeyPair keyPair = null;

        // TODO: 6/5/23 create file to generate access Token and refresh
        File publicKeyFile = new File(publicPath);
        File privateKeyFile = new File(privatePath);

        if (publicKeyFile.exists() && privateKeyFile.exists()) {

            KeyFactory keyFactory = null;
            try {
                keyFactory = KeyFactory.getInstance("RSA");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            // TODO: 6/5/23 encode public key
            byte[] publicKeyBytes = new byte[0];
            try {
                publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = null;
            try {
                publicKey = keyFactory.generatePublic(publicKeySpec);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
            // TODO: 6/5/23 encode private key
            byte[] privateKeyBytes = new byte[0];
            try {
                privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = null;
            try {
                privateKey = keyFactory.generatePrivate(privateKeySpec);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }

            keyPair = new KeyPair(publicKey, privateKey);
            return keyPair;
        } else {
            if (Arrays.stream(environment.getActiveProfiles()).anyMatch(s -> s.equals("prod"))) {
                throw new RuntimeException("public and private keys doesn't exist");
            }
        }
        File directory = new File("access-refresh-token-keys");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();

            try (FileOutputStream stream = new FileOutputStream(publicPath)) {
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
                stream.write(keySpec.getEncoded());
            }

            try (FileOutputStream stream = new FileOutputStream(privatePath)) {
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
                stream.write(keySpec.getEncoded());
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
        return keyPair;
    }

    public RSAPrivateKey getAccessTokenPrivateKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        return (RSAPrivateKey) get_accessTokenKeyPair().getPrivate();
    }

    public RSAPublicKey getAccessTokenPublicKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        return (RSAPublicKey) get_accessTokenKeyPair().getPublic();
    }


    public RSAPrivateKey getRefreshTokenPrivateKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        return (RSAPrivateKey) get_refreshTokenKeyPair().getPrivate();
    }


    public RSAPublicKey getRefreshTokenPublicKey()  {
        return (RSAPublicKey) get_refreshTokenKeyPair().getPublic();
    }



}
