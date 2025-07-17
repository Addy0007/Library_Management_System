package com.example.LMS.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class front {
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Welcome to the LMS Application!";
    }
}
