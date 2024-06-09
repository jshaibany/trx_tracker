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
	private Long timesignature;

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

	
}
