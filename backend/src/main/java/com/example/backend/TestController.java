package com.example.backend;

import com.example.backend.client.TimekeeperClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
public class TestController{
TimekeeperClient client = new TimekeeperClient();
    @GetMapping
    public String test(){
        client.getUser(22748L);
        return "Hello world!";
    }
}
