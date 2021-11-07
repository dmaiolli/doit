package br.com.denys.service;

import br.com.denys.domain.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${doit.jwt.duration}")
    private long duration;

    @Value("${doit.jwt.secret}")
    private String secret;

    public String createToken(Authentication authenticate) {
        User user = (User) authenticate.getPrincipal();

        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + duration);

        return Jwts.builder()
                .setIssuer("DoItAPI")
                .setSubject(user.getId().toString())
                .setIssuedAt(today).
                setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean valid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.valueOf(claims.getSubject());
    }
}
