package org.ltejeda.springredditclone.service;

import lombok.RequiredArgsConstructor;
import org.ltejeda.springredditclone.exceptions.SpringRedditException;
import org.ltejeda.springredditclone.model.RefreshToken;
import org.ltejeda.springredditclone.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token){
        refreshTokenRepository.findByToken(token).orElseThrow(() -> new SpringRedditException("Invalid refresh token"));
    }
     public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
     }
}
