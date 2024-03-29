package com.example.demo.services;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.ERole;
import com.example.demo.exceptions.UserExistException;
import com.example.demo.payload.request.CreateFinanceMonthPlanRequest;
import com.example.demo.payload.request.CreateFinanceRecordRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.repository.UserRepository;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailgunService mailgunService;
    private final FinanceRecordService financeRecordService;
    private final FinanceMonthPlanService financeMonthPlanService;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, MailgunService mailgunService1, FinanceRecordService financeRecordService, FinanceMonthPlanService financeMonthPlanService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailgunService = mailgunService1;
        this.financeRecordService = financeRecordService;
        this.financeMonthPlanService = financeMonthPlanService;
    }

    private User getUserByPrincipal(Principal principal){
        var username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }

    public User getUser(Principal principal){
        return getUserByPrincipal(principal);
    }
    public User createUser(SignupRequest userIn){
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setFirstname(userIn.getFirstname());
        user.setMiddlename(userIn.getMiddlename());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getEmail());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRole().add(ERole.ROLE_USER);

        try {
            mailgunService.sendSimpleMessage(user.getEmail());
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        try {
            LOG.info("Saving User {}", userIn.getEmail());
            user = userRepository.save(user);
        } catch (Exception ex){
            LOG.error("Error during registration. {}", ex.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentionals");
        }

        var createFinanceMonthPlanRequest = new CreateFinanceMonthPlanRequest();
        createFinanceMonthPlanRequest.setAmount(100L);
        financeMonthPlanService.createFinancePlan(createFinanceMonthPlanRequest, user);

        var createFinanceRecordRequest = new CreateFinanceRecordRequest();
        createFinanceRecordRequest.setAmount(0L);
        createFinanceRecordRequest.setType(0);
        return user;
    }

    public User updateUser(UserDTO userDTO, Principal principal){
        var user = getUserByPrincipal(principal);

        user.setFirstname(userDTO.getFirstname());
        user.setMiddlename(userDTO.getMiddlename());
        user.setLastname(user.getLastname());

        return userRepository.save(user);
    }
}
