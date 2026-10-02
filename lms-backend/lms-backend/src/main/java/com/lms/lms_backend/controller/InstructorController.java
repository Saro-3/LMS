package com.lms.lms_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    @GetMapping("/dashboard")
    public String dashboard() {

        return "Welcome to Instructor Controller";
    }
}
