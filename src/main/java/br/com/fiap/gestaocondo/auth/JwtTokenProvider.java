//package br.com.fiap.gestaocondo.auth;
//
//import io.jsonwebtoken.*;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import java.security.SignatureException;
//import java.util.Date;
//
//@Component
//public class JwtTokenProvider {
//    private final String JWT_SECRET = "sua-chave-secreta";
//    private final long JWT_EXPIRATION = 604800000L;
//
//    public String generateToken(Authentication authentication) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
//        return Jwts.builder()
//                .setSubject(authentication.getName())
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
//                .compact();
//    }
//
//    public String getUsernameFromJWT(String token) {
//        return Jwts.parser()
//                .setSigningKey(JWT_SECRET)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
//            return true;
//        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
//                 UnsupportedJwtException | IllegalArgumentException ex) {
//            return false;
//        }
//    }
//}
