package com.example.demo.web;

import com.example.demo.payload.request.CreateFinanceMonthPlanRequest;
import com.example.demo.payload.request.CreateFinanceRecordRequest;
import com.example.demo.payload.response.CreateFinanceRecordSuccessResponse;
import com.example.demo.services.FinanceMonthPlanService;
import com.example.demo.services.UserService;
import com.example.demo.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/api/FinanceMonthPlan")
@PreAuthorize("permitAll()")
public class FinanceMonthPlanController {

    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private UserService userService;
    @Autowired
    private FinanceMonthPlanService financeMonthPlanService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateFinanceMonthPlanRequest createFinanceMonthPlanRequest, BindingResult bindingResult, Principal principal){
        var errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        financeMonthPlanService.createFinancePlan(createFinanceMonthPlanRequest, principal);
        return ResponseEntity.ok(new CreateFinanceRecordSuccessResponse(true));
    }

    @GetMapping("/getAllByUser")
    public ResponseEntity<Object> getAllByUser(Principal principal){

        return ResponseEntity.ok(financeMonthPlanService.getAllFinancePlanByUser(principal));
    }

    @GetMapping("/getLastMonthByUser")
    public ResponseEntity<Object> getLastMonthByUser(Principal principal){

        return ResponseEntity.ok(financeMonthPlanService.getAllFinancePlanByUserLastMonth(principal));
    }
}
