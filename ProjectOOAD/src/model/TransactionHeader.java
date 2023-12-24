package model;

import java.sql.Date;

public class TransactionHeader {
	private int transactionId, staffId;
	private String staffName;
	private Date transactionDate;
	
	public TransactionHeader(int transactionId, int staffId, String staffName, Date transactionDate) {
		super();
		this.transactionId = transactionId;
		this.staffId = staffId;
		this.staffName = staffName;
		this.transactionDate = transactionDate;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public TransactionHeader() {
		// TODO Auto-generated constructor stub
	}

}
