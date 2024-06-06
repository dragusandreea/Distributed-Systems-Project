package sd.project.devicemanagementservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "566D5971337336763979244226452948404D635166546A576E5A723475377721";

    public HttpStatus authorizeByRole(String tokenInput, UUID uid) {
        String token = tokenInput.substring(7);
        String userType = extractUserType(token);
        if(isTokenValid(token)) {
        if(userType.equals("ADMIN")) {
            return HttpStatus.OK;
        } else {
           if(userType.equals("CLIENT")) {
               if(uid != null) {
                   UUID uid2 = extractUUID(token);
                   if(uid.equals(uid2)) {
                       return HttpStatus.OK;
                   }
               }
           }

        }
        }
        return HttpStatus.FORBIDDEN;
    }
    public String extractUserType(String token) {
        final Claims claims = extractAllClaims(token);
        return (String) claims.get("userType");
    }

    public UUID extractUUID(String token) {
        final Claims claims = extractAllClaims(token);
        return UUID.fromString((String) claims.get("id"));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token) {
        return (!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
