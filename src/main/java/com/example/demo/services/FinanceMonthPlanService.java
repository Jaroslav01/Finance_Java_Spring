package com.example.demo.services;

import com.example.demo.entity.FinanceMonthPlan;
import com.example.demo.entity.FinanceRecord;
import com.example.demo.entity.User;
import com.example.demo.payload.request.CreateFinanceMonthPlanRequest;
import com.example.demo.payload.request.CreateFinanceRecordRequest;
import com.example.demo.repository.FinanceMonthPlanRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceMonthPlanService {
    public static final Logger LOG = LoggerFactory.getLogger(FinanceMonthPlanService.class);

    private final FinanceMonthPlanRepository financeMonthPlanRepository;
    private final UserRepository userRepository;

    public FinanceMonthPlanService(FinanceMonthPlanRepository financeMonthPlanRepository, UserRepository userRepository) {
        this.financeMonthPlanRepository = financeMonthPlanRepository;
        this.userRepository = userRepository;
    }

    private User getUserByPrincipal(Principal principal){
        var username = principal.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }

    private boolean checkDate(long time){
        Date date = new Date(time);
        Date dateFor2 = new Date();
        Date date2 = new Date(dateFor2.getYear(), dateFor2.getMonth(), 1);

        System.out.println(date);
        System.out.println(date2);

        if (date.compareTo(date2) > 0) return true;
        else return false;
    }

    public FinanceMonthPlan createFinancePlan(CreateFinanceMonthPlanRequest createFinanceMonthPlanRequest, Principal principal){
        var user = getUserByPrincipal(principal);
        return this.createFinancePlan(createFinanceMonthPlanRequest, user);
    }

    public List<FinanceMonthPlan> getAllFinancePlanByUser(Principal principal){
        var user = getUserByPrincipal(principal);
        return this.financeMonthPlanRepository.findAllByUser(user);
    }

    public List<FinanceMonthPlan> getAllFinancePlanByUserLastMonth(Principal principal){
        var user = getUserByPrincipal(principal);

        var financeMonthPlans = financeMonthPlanRepository.findAllByUser(user);
        var financeMonthPlansLastMonth = financeMonthPlans.stream().filter((financeMonthPlan) ->
                this.checkDate(financeMonthPlan.getCreatedDate())
        ).collect(Collectors.toList());
        return financeMonthPlansLastMonth;
    }

    public FinanceMonthPlan createFinancePlan(CreateFinanceMonthPlanRequest createFinanceMonthPlanRequest, User user){
        var financeRecordPlan = new FinanceMonthPlan();
        financeRecordPlan.setAmount(createFinanceMonthPlanRequest.getAmount());
        financeRecordPlan.setUser(user);

        LOG.info("Saving FinanceRecordPlan for User: {}" + user.getEmail());
        return financeMonthPlanRepository.save(financeRecordPlan);
    }
}
