package me.ks.springdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.ks.springdeveloper.domain.User;
import me.ks.springdeveloper.dto.AddUserRequest;
import me.ks.springdeveloper.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long save(AddUserRequest request){
        return userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build()).getId();
    }
}
