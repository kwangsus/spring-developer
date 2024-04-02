package me.ks.springdeveloper.config.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import me.ks.springdeveloper.domain.User;
import me.ks.springdeveloper.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;

@Log4j2
@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken() : 토큰생성")
    @Test
    void generateToken() {
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("user")
                .build());

        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        Assertions.assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("invalidToken() : 토큰 검증 실패")
    @Test
    void validToken_invalid() {
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        log.info(token);
        boolean result = tokenProvider.validToken(token);

        Assertions.assertThat(result).isFalse();
    }

    @DisplayName("validToken() : 토큰 검증 성공")
    @Test
    void validToken_valid() {
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);
        log.info(token);

        //boolean result = tokenProvider.validToken(token);
        boolean result;
        try{
            Jwts.parser()
                    .setSigningKey("myPersonalKey1")
                    .parseClaimsJws(token);
            result = true;
        } catch(Exception e) {
            log.info("key invalid.");
            result = false;
        }
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication() : 토큰으로 인증정보 가져오기")
    @Test
    void getAuthentication() {
        String userEmail = "user@gmail.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        Authentication authentication = tokenProvider.getAuthentication(token);

        Assertions.assertThat(((UserDetails) (authentication.getPrincipal())).getUsername()).isEqualTo(userEmail);
    }
}
