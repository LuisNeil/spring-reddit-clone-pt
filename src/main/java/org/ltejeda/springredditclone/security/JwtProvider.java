package org.ltejeda.springredditclone.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.ltejeda.springredditclone.exceptions.SpringRedditException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;
import static io.jsonwebtoken.Jwts.parserBuilder;

@Service
public class JwtProvider {
    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try{
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream,"123456".toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new SpringRedditException("Exception occurred while loading keystore");
        }
    }
    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder().
                setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try{
            return (PrivateKey) keyStore.getKey("springblog","123456".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            throw new SpringRedditException("Exception occurred while retrieving public key from keystore");
        }
    }

    public boolean validateToken(String jwt){
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
//        Jwts.parserBuilder().setSigningKey(getPublicKey()).build();
        return true;
    }

    private PublicKey getPublicKey(){
        try{
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e){
            throw new SpringRedditException("Exception occurred while " + "retrieving public key from keystore");
        }
    }

    public String  getUsernameFromJwt(String token){
        Claims claims = parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
//        Claims claims1 = parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();

    }
}
