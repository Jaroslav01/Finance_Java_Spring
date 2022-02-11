package com.example.demo.web;

import java.security.Principal;

import com.example.demo.services.FinanceRecordService;
import com.example.demo.services.UserService;
import com.example.demo.validations.ResponseErrorValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("/api/user")
@PreAuthorize("permitAll()")
public class UserController {

    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private UserService userService;
    @Autowired
    private FinanceRecordService financeRecordService;

    @GetMapping("/getCurrentUser")
    public ResponseEntity<Object> getCurrentUser(Principal principal){
        var user = userService.getUser(principal);
        user.setPassword("Hiden");
       return ResponseEntity.ok(user);
    }
}
