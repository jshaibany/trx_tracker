package com.example.trx_tracking.controller;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.trx_tracking.entity.Transaction;
import com.example.trx_tracking.service.TransactionService;

@RestController
@RequestMapping("/api/v1")
public class Tracker {

	@Autowired
	TransactionService service;
	@Autowired
	Environment env;
	
	@PostMapping("/checkTransaction")
    public HashMap<String,Object> checkTransaction(@RequestBody HashMap<String,Object> payload) throws Exception {
        /*
         * 
         */
		
		HashMap<String,Object> response = new HashMap<>();
		Long timeFrame = Long.valueOf(env.getProperty("timeframe"));
		
		Long source=Long.valueOf(payload.get("Source").toString());
		Long destination=Long.valueOf(payload.get("Destination").toString());
		Long amount=Long.valueOf(payload.get("Amount").toString().replace(".", ""));
		//BigDecimal amt=new BigDecimal(payload.get("Amount").toString());
		
		Long trxSignature=source+destination+amount;
		
		Integer t = LocalTime.now(ZoneOffset.UTC).get(ChronoField.MILLI_OF_DAY);
		
		//Long timeSignature = LocalTime.now().toNanoOfDay();
		Long timeSignature = Long.valueOf(t.toString());
		
		
		List<Long> ids = new ArrayList<>();
		ids.add(trxSignature);
		
		//Try to find trxSignature in db
		List<Transaction> trxs = service.findAllById(ids);
		
		if(trxs.size()>0) {
			
			for(Transaction tx:trxs) {
				
				
				if(tx.getSource().equals(source) 
						&& tx.getDestination().equals(destination) 
						&& tx.getAmount().contentEquals(payload.get("Amount").toString())) {
					
					//Find time diff
					Long diff=tx.getTimesignature()-timeSignature;
					//System.out.println("Time Diff: "+diff);
					
					if(Math.abs(diff)<timeFrame) {
						
						response.put("Result", -1);
						response.put("Message", "DUPLICATE_TRX");
						
						return response;
					}
				}
				
			}
		}
		
		Transaction trx= new Transaction();
		trx.setId(trxSignature);
		trx.setSource(source);
		trx.setDestination(destination);
		trx.setAmount(payload.get("Amount").toString());
		trx.setTimesignature(timeSignature);
    	
    	
    	response.put("Result", 0);
		response.put("Message", "OK");
		response.put("Tranaction", service.add(trx));
    	return response;
    }
	
	
	@PostMapping("/checkTest")
    public void checkTest(@RequestBody HashMap<String,Object> payload) throws Exception {
       
		
		HashMap<String,Object> response = new HashMap<>();
		
		Long source=Long.valueOf(payload.get("Source").toString());
		Long destination=Long.valueOf(payload.get("Destination").toString());
		Long amount=Long.valueOf(payload.get("Amount").toString().replace(".", ""));
		
		
		Long trxSignature=source+destination+amount;
		
		Integer t = LocalTime.now(ZoneOffset.UTC).get(ChronoField.MILLI_OF_DAY);
		
		//Long timeSignature = LocalTime.now().toNanoOfDay();
		Long timeSignature = Long.valueOf(t.toString());
		
		int c =0;
		for(Long i=10L;i<100000;i+=10) {
			
			c++;
			timeSignature +=i;
			List<Long> ids = new ArrayList<>();
			ids.add(trxSignature);
			
			amount+=i;
			trxSignature=source+destination+amount;
			
			Transaction trx= new Transaction();
			trx.setId(trxSignature);
			trx.setSource(source);
			trx.setDestination(destination);
			trx.setAmount(payload.get("Amount").toString());
			trx.setTimesignature(timeSignature);
	    	service.add(trx);
			
			
		}
		
		System.out.println("Rounds "+c);
		
    }
	
	@PostMapping("/checkThreads")
    public void checkThreads(@RequestBody HashMap<String,Object> payload) throws Exception {
       
		ExecutorService executor = Executors.newFixedThreadPool(15);
		
		HashMap<String,Object> response = new HashMap<>();
		
		Long timeFrame = Long.valueOf(env.getProperty("timeframe"));
		
		Long source=Long.valueOf(payload.get("Source").toString());
		Long destination=Long.valueOf(payload.get("Destination").toString());
		Long amount=Long.valueOf(payload.get("Amount").toString().replace(".", ""));
		
		
		Long trxSignature=source+destination+amount;
		
		Integer t = LocalTime.now(ZoneOffset.UTC).get(ChronoField.MILLI_OF_DAY);
		
		//Long timeSignature = LocalTime.now().toNanoOfDay();
		Long timeSignature = Long.valueOf(t.toString());
		
		Transaction trx= new Transaction();
		trx.setId(trxSignature);
		trx.setSource(source);
		trx.setDestination(destination);
		trx.setAmount(payload.get("Amount").toString());
		trx.setTimesignature(timeSignature);
    	service.add(trx);
    	
    	
		List<Long> ids = new ArrayList<>();
		ids.add(trxSignature);
		//Try to find trxSignature in db
		List<Transaction> trxs = service.findAllById(ids);
		
		Runnable runnable1 = () -> {
            
			if(trxs.size()>0) {
				
				for(Transaction tx:trxs) {
					
					
					if(tx.getSource().equals(source) 
							&& tx.getDestination().equals(destination) 
							&& tx.getAmount().contentEquals(payload.get("Amount").toString())) {
						
						//Find time diff
						Long diff=tx.getTimesignature()-timeSignature;
						System.out.println("Time Diff: "+diff);
						
						if(Math.abs(diff)<timeFrame) {
							
							System.out.println("Time Diff: "+diff);
							
						}
						else {
							System.out.println("Pass: ");
						}
					}
					
				}
			}
        };
        
        Runnable runnable2 = () -> {
            

        	if(trxs.size()>0) {
				
				for(Transaction tx:trxs) {
					
					
					if(tx.getSource().equals(source) 
							&& tx.getDestination().equals(destination) 
							&& tx.getAmount().contentEquals(payload.get("Amount").toString())) {
						
						//Find time diff
						Long diff=tx.getTimesignature()-timeSignature;
						System.out.println("Time Diff: "+diff);
						
						if(Math.abs(diff)<timeFrame) {
							
							System.out.println("Time Diff: "+diff);
							
						}
						else {
							System.out.println("Pass: ");
						}
					}
					
				}
			}
        };
        
        Runnable runnable3 = () -> {
            
        	if(trxs.size()>0) {
				
				for(Transaction tx:trxs) {
					
					
					if(tx.getSource().equals(source) 
							&& tx.getDestination().equals(destination) 
							&& tx.getAmount().contentEquals(payload.get("Amount").toString())) {
						
						//Find time diff
						Long diff=tx.getTimesignature()-timeSignature;
						System.out.println("Time Diff: "+diff);
						
						if(Math.abs(diff)<timeFrame) {
							
							System.out.println("Time Diff: "+diff);
							
						}
						else {
							System.out.println("Pass: ");
						}
					}
					
				}
			}
        };
        
        executor.execute(runnable1);
        executor.execute(runnable2);
        executor.execute(runnable3);
		
    }
}
