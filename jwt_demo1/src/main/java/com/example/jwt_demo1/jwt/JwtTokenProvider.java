package com.example.jwt_demo1.jwt;
import com.example.jwt_demo1.User.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Date;
@Component
@Slf4j
public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "lemanh";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 60000;



    //taoj jwt tu thong tin user
    public String gennerateToken(User user) {
        Date now = new Date();
        Date expiration_date = new Date(now.getTime() + JWT_EXPIRATION);

        Claims claims = Jwts.claims().setSubject(user.getUsername());
//        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        claims.put("id", user.getId());
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration_date)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }



    public String getUserNameFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            System.out.println(JWT_SECRET);
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
