package com.example.demo.web;

import com.example.demo.payload.request.CreateFinanceRecordRequest;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.response.CreateFinanceRecordSuccessResponse;
import com.example.demo.security.JWTTokenProvider;
import com.example.demo.services.FinanceRecordService;
import com.example.demo.services.UserService;
import com.example.demo.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/api/FinanceRecord")
@PreAuthorize("permitAll()")
public class FinanceRecordController {

    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private UserService userService;
    @Autowired
    private FinanceRecordService financeRecordService;

    @PostMapping("/create")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody CreateFinanceRecordRequest createFinanceRecordRequest, BindingResult bindingResult, Principal principal){
        var errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        financeRecordService.createFinanceRecord(createFinanceRecordRequest, principal);
        return ResponseEntity.ok(new CreateFinanceRecordSuccessResponse(true));
    }

    @GetMapping("/getAllByUser")
    public ResponseEntity<Object> getAllFinanceRecordsByUser(Principal principal){

        return ResponseEntity.ok(financeRecordService.getAllFinanceRecordsByUser(principal));
    }

}
