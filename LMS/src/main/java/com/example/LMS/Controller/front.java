package com.example.LMS.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class front {
    @GetMapping("/")//Register and in this account and also a login controller-> forgot pass and else
    @ResponseBody
    public String home() {
        return "Welcome to the LMS Application!";
    }
}
