package lk.ama.pos.ama.controller;

import lk.ama.pos.ama.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @GetMapping
    public void healthCheck(){
        System.out.println("User Working");
    }
}
