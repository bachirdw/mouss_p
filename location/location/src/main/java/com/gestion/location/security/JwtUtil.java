package com.gestion.location.security;

// import java.nio.charset.StandardCharsets;
// import java.security.Key;
// import java.util.Date;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;

// @Component
// public class JwtUtil {
//     private final String jwtSecret = "secret-key";
//     private final long jwtExpirationMs = 86400000; // 24h

//     public String generateToken(UserDetails userDetails) {
//         return Jwts.builder()
//                 .setSubject(userDetails.getUsername()) // email
//                 .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//                 .signWith(getKey())
//                 .compact();
//     }

//     public String getUsernameFromToken(String token) {
//         return Jwts.parserBuilder().setSigningKey(getKey()).build()
//                 .parseClaimsJws(token).getBody().getSubject();
//     }

//     public boolean validateToken(String token, UserDetails userDetails) {
//         return getUsernameFromToken(token).equals(userDetails.getUsername())
//                 && !isTokenExpired(token);
//     }

//     private boolean isTokenExpired(String token) {
//         return Jwts.parserBuilder().setSigningKey(getKey()).build()
//                 .parseClaimsJws(token).getBody().getExpiration().before(new Date());
//     }

//     private Key getKey() {
//         return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
//     }
// }


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "my-super-secret-key-with-32-bytes!!!";

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
