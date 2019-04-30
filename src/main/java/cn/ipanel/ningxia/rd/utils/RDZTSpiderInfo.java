package cn.ipanel.ningxia.rd.utils;

import java.util.Date;

public class RDZTSpiderInfo {
	
	private String firstPage;
	private String schedulerStr;
	private String meettingRegex;
	private String listRegex;
	private String[] urlEnds;
	private Date deadline;
	
	
	
	public RDZTSpiderInfo(String firstPage,
			String schedulerStr, String meettingRegex, String listRegex,
			String[] urlEnds, Date deadline) {
		super();
		this.firstPage = firstPage;
		this.schedulerStr = schedulerStr;
		this.meettingRegex = meettingRegex;
		this.listRegex = listRegex;
		this.urlEnds = urlEnds;
		this.deadline = deadline;
	}
	public RDZTSpiderInfo(String firstPage,
			String schedulerStr, String meettingRegex, String listRegex,
			String[] urlEnds) {
		super();
		this.firstPage = firstPage;
		this.schedulerStr = schedulerStr;
		this.meettingRegex = meettingRegex;
		this.listRegex = listRegex;
		this.urlEnds = urlEnds;
	}
	
	public String getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}
	public String getSchedulerStr() {
		return schedulerStr;
	}
	public void setSchedulerStr(String schedulerStr) {
		this.schedulerStr = schedulerStr;
	}
	public String getMeettingRegex() {
		return meettingRegex;
	}
	public void setMeettingRegex(String meettingRegex) {
		this.meettingRegex = meettingRegex;
	}
	public String getListRegex() {
		return listRegex;
	}
	public void setListRegex(String listRegex) {
		this.listRegex = listRegex;
	}
	public String[] getUrlEnds() {
		return urlEnds;
	}
	public void setUrlEnds(String[] urlEnds) {
		this.urlEnds = urlEnds;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	

	
	
}
