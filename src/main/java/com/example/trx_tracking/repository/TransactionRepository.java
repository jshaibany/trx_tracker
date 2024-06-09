package com.example.trx_tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.trx_tracking.entity.Transaction;

import jakarta.transaction.Transactional;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{

	@Modifying
	@Transactional
	@Query("DELETE FROM Transaction WHERE timesignature < ?1")
	int deleteOutdatedTransactions(Long timesignature);
}
