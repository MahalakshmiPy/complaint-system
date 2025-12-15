package com.example.complainSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/complaint")
    public String complaintPage() {
        return "complaint";
    }
}
