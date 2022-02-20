package com.example.demo.services;

import com.example.demo.entity.FinanceRecord;
import com.example.demo.entity.User;
import com.example.demo.payload.request.CreateFinanceRecordRequest;
import com.example.demo.repository.FinanceRecordRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceRecordService {
    public static final Logger LOG = LoggerFactory.getLogger(FinanceRecordService.class);

    private final FinanceRecordRepository financeRecordRepository;
    private final UserRepository userRepository;

    private User getUserByPrincipal(Principal principal){
        var username = principal.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }
    @Autowired
    public FinanceRecordService(
            FinanceRecordRepository financeRecordRepository,
            UserRepository userRepository
    ) {
        this.financeRecordRepository = financeRecordRepository;
        this.userRepository = userRepository;
    }

    public FinanceRecord createFinanceRecord(CreateFinanceRecordRequest createFinanceRecordRequest, Principal principal){
        var user = getUserByPrincipal(principal);

        return this.createFinanceRecord(createFinanceRecordRequest, user);
    }

    public FinanceRecord createFinanceRecord(CreateFinanceRecordRequest createFinanceRecordRequest, User user){
        var financeRecord = new FinanceRecord();
        financeRecord.setAmount(createFinanceRecordRequest.getAmount());
        financeRecord.setType(createFinanceRecordRequest.getType());
        financeRecord.setUser(user);

        LOG.info("Saving FinanceRecord for User: {}" + user.getEmail());
        return financeRecordRepository.save(financeRecord);
    }

    public List<FinanceRecord> getAllFinanceRecordsByUser(Principal principal){
        var user = getUserByPrincipal(principal);
        return financeRecordRepository.findAllByUser(user);
    }

    public List<FinanceRecord> getAllFinanceRecordsByUserLastMonth(Principal principal){
        var user = getUserByPrincipal(principal);
        var financeRecords = financeRecordRepository.findAllByUser(user);
        var financeRecordLastMonth = financeRecords.stream().filter((financeRecord) ->
                this.checkDate(financeRecord.getCreatedDate())
        ).collect(Collectors.toList());
        return financeRecordLastMonth;
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
}
