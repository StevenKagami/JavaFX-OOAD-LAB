package model;

public class Job {

    private int JobId, UserId, PcId;
    private String JobStatus;
	public Job(int jobId, int userId, int pcId, String jobStatus) {
		super();
		JobId = jobId;
		UserId = userId;
		PcId = pcId;
		JobStatus = jobStatus;
	}
	public int getJobId() {
		return JobId;
	}
	public void setJobId(int jobId) {
		JobId = jobId;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public int getPcId() {
		return PcId;
	}
	public void setPcId(int pcId) {
		PcId = pcId;
	}
	public String getJobStatus() {
		return JobStatus;
	}
	public void setJobStatus(String jobStatus) {
		JobStatus = jobStatus;
	}
	
}	
