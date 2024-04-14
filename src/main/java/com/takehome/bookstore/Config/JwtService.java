package com.takehome.bookstore.Config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "OK9uRHuEgNxtTn9GxW0cc145DKVDan8N3JkCA92h75biOTyihvT22LEnHKOnjurhTKzgzegR92ReU+cjqskIOVwHwrXfXQ06Fh2vPaZXJNFpsS96qE7V6GiqA8GsCyIgEaPIKedNyISMb+CDToCzFHsW7z4egnbPsF5uKGA012Wed0hP2DqPTA1hG91F9bPxC9dor3+rQ5XaTSQUGi5GoNx5dxyzICuyRsAT+Tm5vJhWIpLcwpeifSGvPlVgCDjjdf2ZWecbSpEGJTja73U5WRSqklaNl6h8sFchCfJc56uwYaKaMQ+XaBHN1wEHF2n3dVk9FBTfaZBd64/fd0Fttw8le8FdcFgWUY/2v7VkgU0";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        // This one is for if one does not want to add extraClaims
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}
