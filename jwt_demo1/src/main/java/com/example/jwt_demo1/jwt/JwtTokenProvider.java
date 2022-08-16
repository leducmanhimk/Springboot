package com.example.jwt_demo1.jwt;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "lemanh";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L;

    //taoj jwt tu thong tin user
    public String gennerateToken(){
        Date now = new Date();
        Date expydate = new Date(now.getTime() + JWT_EXPIRATION);
        Claims claims = Jwts.claims().setSubject(Long.toString(1L));
        claims.put("id", 1);
        claims.put("username", 1);
        claims.put("email", 1);

        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(Long.toString(1L))
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expydate)
                .signWith(SignatureAlgorithm.HS512,JWT_SECRET)
                .compact();
    }
    //laays thong tin user tu jwt
    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
    public boolean validateToken(String authToken){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        }catch (MalformedJwtException ex){
            log.error("invalid JWT token");
        }catch (ExpiredJwtException ex){
            log.error("Expired JWT token");
        }catch (UnsupportedJwtException ex){
            log.error("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
