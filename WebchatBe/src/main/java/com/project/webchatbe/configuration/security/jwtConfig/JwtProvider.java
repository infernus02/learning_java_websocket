package com.project.webchatbe.configuration.security.jwtConfig;

import com.project.webchatbe.configuration.security.UserDetailsImpl;
import com.project.webchatbe.entity.User;
import com.project.webchatbe.repository.IUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.SECRET_KEY}")
    private String JWT_SECRET;
    @Value("${jwt.JWT_EXPIRATION}")
    private int JWT_EXPIRATION;
    @Autowired
    IUserRepository userRepository;

    public String generateToken(UserDetailsImpl customDetailService){
        return this.generateTokenByUsername(customDetailService.getUsername());
    }

    public String generateTokenByUsername(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        User user = userRepository.findByUsername(username).get();
        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .claim("username", user.getUsername())
                .claim("role", user.getRole().getName())
                .setExpiration(expiryDate)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public String getKeyByValueFromJWT(String key, String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.get(key,String.class);
    }
}
