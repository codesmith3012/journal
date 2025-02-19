package net.engineeringdigest.journalApp.utilis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {
    // Your secret key should be a Base64-encoded string of sufficient length.
    // Here, we assume it's already Base64-encoded.
    private String SECRET_KEY = "cYBsjuCpDnH5uoakPkVCZPD7QxcOjeXz19Kv7fc0zQY=";

    private SecretKey getSigningKey() {
        // This returns a SecretKey object based on your SECRET_KEY.
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        // Use the new parserBuilder() method in 0.12.6.
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Updated generateToken method to accept username and roles.
    public String generateToken(String username, String roles) {
        Map<String, Object> claims = new HashMap<>();
        if (roles == null || roles.isEmpty()) {
            log.warn("Roles are either null or empty.");
        } else {
            log.debug("Roles being included in the token: {}", roles);
        }
        claims.put("role", roles);
        log.debug("Returning from generateToken...");
       return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        try {
            log.debug("Entering createToken with claims: {} and username: {}", claims, username);
            Date issuedAt = new Date(System.currentTimeMillis());
            Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60); // 1 hour expiry
            log.debug("Token issued at: {} and expires at: {}", issuedAt, expirationDate);

            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(issuedAt)
                    .setExpiration(expirationDate)
                    // Specify both the key and algorithm explicitly
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            log.error("Error while creating token. Exception: {}", e.getMessage(), e);
            throw new RuntimeException("Error while creating token", e);
        }
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
