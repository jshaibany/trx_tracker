package com.example.trx_tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trx_tracking.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{

}
