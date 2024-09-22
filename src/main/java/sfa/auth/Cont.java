package sfa.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sfa.auth.dto.request.JwtRequest;
import sfa.auth.service.UserServices;

@RestController
public class Cont {
    private final UserServices userServices;

    public Cont(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/create")
    public void create(@RequestBody JwtRequest jwtRequest){
        userServices.createUser(jwtRequest);
    }
    @GetMapping("/login")
    public String login(@RequestParam  Long mobile, @RequestParam String password){
        return userServices.loginUser(mobile, password);
    }

}
