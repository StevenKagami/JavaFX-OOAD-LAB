package model;

import java.sql.Date;

public class TransactionDetail {
	private int transactionId, pcId;
	private String customerName;
	private Date bookedTime;
	
	public TransactionDetail(int transactionId, int pcId, String customerName, Date bookedTime) {
		super();
		this.transactionId = transactionId;
		this.pcId = pcId;
		this.customerName = customerName;
		this.bookedTime = bookedTime;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getPcId() {
		return pcId;
	}

	public void setPcId(int pcId) {
		this.pcId = pcId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(Date bookedTime) {
		this.bookedTime = bookedTime;
	}

	public TransactionDetail() {
		// TODO Auto-generated constructor stub
	}

}
