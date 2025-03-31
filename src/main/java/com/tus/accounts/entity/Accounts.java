package com.tus.accounts.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Accounts{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="account_holder_id")
    private Long id;
	
	@Column(name="account_number")
    private Long accountNumber;

    @Column(name="account_type")
    private String accountType;

    @Column(name="branch_address")
    private String branchAddress;
    
    @Column(name="account_holder_name", nullable = false)
    private String accountHolderName;

    @Column(name="balance", precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(name="status")
    private String status;  // e.g., Active, Inactive, Closed, Suspended
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal bigDecimal) {
		this.balance = bigDecimal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}