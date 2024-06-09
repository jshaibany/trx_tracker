package com.example.trx_tracking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.trx_tracking.entity.Transaction;
import com.example.trx_tracking.repository.TransactionRepository;

@Service
public class TransactionService {

	private final TransactionRepository transactionRepository;
	
	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
		
		
	}
	
	public List<Transaction> findAll(){
		
		return transactionRepository.findAll();
	}
	
	public List<Transaction> findAllById(List<Long> id){
		
		return transactionRepository.findAllById(id);
	}
	
	public Transaction add(Transaction trx) {
		
		return transactionRepository.save(trx);
	}
	
	public int deleteOutdatedTransactions(Long timeStamp){
		
		return transactionRepository.deleteOutdatedTransactions(timeStamp);
	}
}
