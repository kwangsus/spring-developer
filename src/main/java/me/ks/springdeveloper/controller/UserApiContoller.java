package me.ks.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ks.springdeveloper.dto.AddUserRequest;
import me.ks.springdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class UserApiContoller {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }

}
