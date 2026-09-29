package com.boticasaludica.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    public static final long ADMIN_EXPIRATION_SECONDS = 300L;
    public static final long CAJERO_EXPIRATION_SECONDS = 600L;

    @Value("${app.jwt.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        String role = extractRoleFromUserDetails(userDetails);
        long expiresIn = expirationSecondsForRole(role);

        Date issuedAt = new Date();
        Date expiration = new Date(
                issuedAt.getTime() + expiresIn * 1000L
        );

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("username", userDetails.getUsername())
                .claim("role", role)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(signingKey())
                .compact();
    }

    public String extractUsername(String token) {
        return claims(token).get("username", String.class);
    }

    public String extractRole(String token) {
        return claims(token).get("role", String.class);
    }

    public Date extractIssuedAt(String token) {
        return claims(token).getIssuedAt();
    }

    public Date extractExpiration(String token) {
        return claims(token).getExpiration();
    }

    public boolean isValid(String token) {
        claims(token);
        return true;
    }

    public long expirationSecondsForRole(String role) {
        return switch (role) {
            case "ADMIN" -> ADMIN_EXPIRATION_SECONDS;
            case "CAJERO" -> CAJERO_EXPIRATION_SECONDS;
            default -> throw new IllegalArgumentException(
                    "Rol no soportado: " + role
            );
        };
    }

    public String extractRoleFromUserDetails(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.replaceFirst("^ROLE_", ""))
                .filter(role -> role.equals("ADMIN") || role.equals("CAJERO"))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario sin rol válido")
                );
    }

    private Claims claims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey signingKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
