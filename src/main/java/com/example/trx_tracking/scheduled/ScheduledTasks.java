package com.example.trx_tracking.scheduled;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.trx_tracking.service.TransactionService;

@Component
public class ScheduledTasks {
	
	@Autowired
	private TransactionService transactionService;
	
	@Scheduled(fixedRate = 10000) public void scheduleTaskEvery20Sec() {
        
		Integer t = LocalTime.now(ZoneOffset.UTC).get(ChronoField.MILLI_OF_DAY);
		
		Long timeNow = Long.valueOf(t.toString());
		timeNow-=20000;
		
		
        System.out.println("Running scheduled task at: " + new Date());
        System.out.println("Deleted Transactions: " + transactionService.deleteOutdatedTransactions(timeNow));
    }
}
