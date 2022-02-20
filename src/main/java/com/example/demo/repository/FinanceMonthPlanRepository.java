package com.example.demo.repository;

import com.example.demo.entity.FinanceMonthPlan;
import com.example.demo.entity.FinanceRecord;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinanceMonthPlanRepository extends JpaRepository<FinanceMonthPlan, Long> {

    List<FinanceMonthPlan> findAllByUser(User user);

    Optional<FinanceMonthPlan> findFinanceMonthPlanByIdAndUser(Long id, User user);
}
