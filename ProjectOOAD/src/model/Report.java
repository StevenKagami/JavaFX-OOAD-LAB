package model;

import java.sql.Date;

public class Report {
	private int reportId,pcId;
	private String reportNote, userRole;
	private Date reportDate;
	public Report(int reportId, int pcId, String reportNote, String userRole, Date reportDate) {
		super();
		this.reportId = reportId;
		this.pcId = pcId;
		this.reportNote = reportNote;
		this.userRole = userRole;
		this.reportDate = reportDate;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public int getPcId() {
		return pcId;
	}
	public void setPcId(int pcId) {
		this.pcId = pcId;
	}
	public String getReportNote() {
		return reportNote;
	}
	public void setReportNote(String reportNote) {
		this.reportNote = reportNote;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	

}
