package cn.ipanel.ningxia.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="letter")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Letter implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 来信人（必选）
	来信时间（必选）
	诉求类型（必选）
	所属区域（必选）
	来信标题（必选）
	来信内容（必选）
	回复部门（必选）
	回复内容（必选）
	回复时间（必选）
	 * **/
	@Id
	@Column(name="id" , length=32)
	private String id;
	
	@Column(name="sender")
	private String sender;//发送人
	
	@Column(name="send_time")
	private Date sendTime;//发送时间
	
	@Column(name="appeal")
	private String appeal;//投诉类型
	
	@Column(name="area")
	private String area;//地区
	
	@Column(name="letter_title")
	private String letterTitle;//来信标题
	
	@Column(name="letter_content",columnDefinition="text")
	private String letterContent;//来信内容
	
	@Column(name="reply_dept")
	private String replyDept;//回复部门
	
	@Column(name="reply_content",columnDefinition="text")
	private String replyContent;//回复内容
	
	@Column(name="reply_time")
	private Date replyTime;//回复时间
	
	
	public Letter() {
		super();
	}
	
	public Letter(String sender, Date sendTime, String appeal, String area,
			String letterTitle, String letterContent, String replyDept,
			String replyContent, Date replyTime) {
		super();
		this.sender = sender;
		this.sendTime = sendTime;
		this.appeal = appeal;
		this.area = area;
		this.letterTitle = letterTitle;
		this.letterContent = letterContent;
		this.replyDept = replyDept;
		this.replyContent = replyContent;
		this.replyTime = replyTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getAppeal() {
		return appeal;
	}
	public void setAppeal(String appeal) {
		this.appeal = appeal;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getLetterTitle() {
		return letterTitle;
	}
	public void setLetterTitle(String letterTitle) {
		this.letterTitle = letterTitle;
	}
	
	public String getLetterContent() {
		return letterContent;
	}
	public void setLetterContent(String letterContent) {
		this.letterContent = letterContent;
	}
	public String getReplyDept() {
		return replyDept;
	}
	public void setReplyDept(String replyDept) {
		this.replyDept = replyDept;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	
}
