package com.example.demo.repository;

import com.example.demo.entity.FinanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinanceRecordRepository extends JpaRepository<FinanceRecord, Long> {

    //List<FinanceRecord> findAllByUserIdDesc(Long id);

    Optional<FinanceRecord> findFinanceRecordById(Long id);
}
