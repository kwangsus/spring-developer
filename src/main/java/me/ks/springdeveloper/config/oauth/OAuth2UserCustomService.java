package me.ks.springdeveloper.config.oauth;

import lombok.RequiredArgsConstructor;
import me.ks.springdeveloper.domain.User;
import me.ks.springdeveloper.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        saveOrUpdate(oAuth2User);

        return oAuth2User;
    }

    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        User user = userRepository.findByEmail(email)
                .map(e -> e.update(name))
                .orElse(User.builder()
                        .email(email)
                        .password("*PROTECTED*")
                        .nickname(name)
                        .build());

        return userRepository.save(user);

    }

}
