package com.biglobby.utils.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.biglobby.configuration.Constrant.SIGNING_KEY;;

@Component
public class JwtTokenUtil implements Serializable {

    private String doGenerateToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);
        // claims.put("scope", Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("https://www.google.com")
                .setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getUsername());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> ClaimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return ClaimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    public Boolean validate(String token, UserDetails userDetail) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetail.getUsername()) && !isTokenExpired(token));
    }
}
