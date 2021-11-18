package com.cmpn.tripsdemo.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenService {

    public static User admin;
    public static User user;
    private static final String TOKEN = "auth-token";
    private static final String SECRET =
            "1 Господь — Пастырь мой; я ни в чем не буду нуждаться:\n" +
            "2 Он покоит меня на злачных пажитях и водит меня к водам тихим,\n" +
            "3 подкрепляет душу мою, направляет меня на стези правды ради имени Своего.\n" +
            "4 Если я пойду и долиною смертной тени, не убоюсь зла, потому что Ты со мной; Твой жезл и Твой посох — они успокаивают меня.\n" +
            "5 Ты приготовил предо мною трапезу в виду врагов моих; умастил елеем голову мою; чаша моя преисполнена.\n" +
            "6 Так, благость и милость <Твоя> да сопровождают меня во все дни жизни моей, и я пребуду в доме Господнем многие дни.";

    static {
        admin = new User("admin", "00000", new SimpleGrantedAuthority("ROLE_ADMIN"));
        user = new User("user", "00000", new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String generateToken(org.springframework.security.core.userdetails.User user) {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

        String simpleToken = Jwts.builder()
                .claim("username", user.getUsername())
                .setExpiration(Date.valueOf(LocalDateTime.now().plusDays(5).toLocalDate()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return "Bearer " + simpleToken;
    }

    public User parseToken(String token) {
        byte[] secretBytes = SECRET.getBytes();

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secretBytes)
                .build()
                .parseClaimsJws(token);

        String username = claims.getBody().get("username", String.class);
        boolean isAdmin = "admin".equals(username);

        return isAdmin ? admin : user;
    }

    public boolean verifyToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return token.equals(TOKEN);
    }

    @Document
    public static class User {
        @MongoId
        String id;
        String username;
        @JsonIgnore
        String password;
        Set<GrantedAuthority> authorities;

        public User(String username, String password, SimpleGrantedAuthority... authorities) {
            this.password = password;
            this.username = username;

            this.authorities = new HashSet<>();
            if (authorities != null && authorities.length > 0)
                this.authorities.addAll(Arrays.asList(authorities));
        }

        public User(String id, String username, String password, Set<GrantedAuthority> authorities) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.authorities = authorities;
        }

        protected User() {}

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Set<GrantedAuthority> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(Set<GrantedAuthority> authorities) {
            this.authorities = authorities;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
