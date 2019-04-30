package cn.ipanel.ningxia.utils.bean;

import java.util.Date;

public class SpiderInfo {

	private String firstPageUrl;
	private Date deadline;
	private String cateId;
	private int maxPage;//爬取的最大页码数
	private String meeting;//所属会议
	
	public SpiderInfo(String firstPageUrl, Date deadline, String cateId,
			int maxPage) {
		super();
		this.firstPageUrl = firstPageUrl;
		this.deadline = deadline;
		this.cateId = cateId;
		this.maxPage = maxPage;
	}
	
	public SpiderInfo(String firstPageUrl, Date deadline, String cateId,
			int maxPage, String meeting) {
		super();
		this.firstPageUrl = firstPageUrl;
		this.deadline = deadline;
		this.cateId = cateId;
		this.maxPage = maxPage;
		this.meeting = meeting;
	}
	public String getFirstPageUrl() {
		return firstPageUrl;
	}
	public void setFirstPageUrl(String firstPageUrl) {
		this.firstPageUrl = firstPageUrl;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public String getMeeting() {
		return meeting;
	}
	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
}
