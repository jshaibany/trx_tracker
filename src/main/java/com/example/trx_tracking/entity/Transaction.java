package com.example.trx_tracking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@Column(name = "transaction_id",unique = true)
    private Long id;
	@Column(name = "timesignature")
	private Long timesignature;
	@Column(name = "source")
	private Long source;
	@Column(name = "destination")
	private Long destination;
	@Column(name = "amount")
	private String amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTimesignature() {
		return timesignature;
	}

	public void setTimesignature(Long timesignature) {
		this.timesignature = timesignature;
	}

	public Long getSource() {
		return source;
	}

	public void setSource(Long source) {
		this.source = source;
	}

	public Long getDestination() {
		return destination;
	}

	public void setDestination(Long destination) {
		this.destination = destination;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
