package org.ltejeda.springredditclone.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String authenticationToken;
    private String username;
    private Instant expiresAt;
    private String refreshToken;
}
