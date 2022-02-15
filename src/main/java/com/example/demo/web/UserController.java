package com.example.demo.web;

import java.security.Principal;

import com.example.demo.dto.UserDTO;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.services.FinanceRecordService;
import com.example.demo.services.UserService;
import com.example.demo.validations.ResponseErrorValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/updateUserData")
    public ResponseEntity<Object> updateUserData(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal){
        var errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        var user = userService.updateUser(userDTO, principal);
        return ResponseEntity.ok(user);
    }
}
