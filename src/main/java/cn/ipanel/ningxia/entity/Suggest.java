package cn.ipanel.ningxia.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="suggest")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Suggest implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 编号（必选）
	届次（必选）
	类别（必选）
	主办（必选）
	协办单位（必选）
	案由（必选）
	正文（必选）
	办理情况（必选）
	 * 
	 * **/
	
	@Id
	@Column(name="id" , length=32)
	private String id;
	
	@Column(name="num")
	private String num;//编号
	
	@Column(name="period")
	private String period;//届次
	
	@Column(name="topic_name")
	private String topicName;//提案或建议
	
	@Column(name="ask_category")
	private String askCategory;//诉求类型
	
	@Column(name="organizer")
	private String organizer;//主办方
	
	@Column(name="co_oganizer")
	private String co_organizer;//协办方
	
	@Column(name="title")
	private String title;//标题
	
	@Column(name="content",columnDefinition="text")
	private String content;//正文
	
	@Column(name="deal",columnDefinition="text")
	private String deal;//处理
	

	public Suggest() {
		super();
	}


	public Suggest(String num, String period, String topicName,
			String askCategory, String organizer, String co_organizer,
			String title, String content, String deal) {
		super();
		this.num = num;
		this.period = period;
		this.topicName = topicName;
		this.askCategory = askCategory;
		this.organizer = organizer;
		this.co_organizer = co_organizer;
		this.title = title;
		this.content = content;
		this.deal = deal;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNum() {
		return num;
	}


	public void setNum(String num) {
		this.num = num;
	}


	public String getPeriod() {
		return period;
	}


	public void setPeriod(String period) {
		this.period = period;
	}


	public String getTopicName() {
		return topicName;
	}


	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}


	public String getAskCategory() {
		return askCategory;
	}


	public void setAskCategory(String askCategory) {
		this.askCategory = askCategory;
	}


	public String getOrganizer() {
		return organizer;
	}


	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}


	public String getCo_organizer() {
		return co_organizer;
	}


	public void setCo_organizer(String co_organizer) {
		this.co_organizer = co_organizer;
	}

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getDeal() {
		return deal;
	}


	public void setDeal(String deal) {
		this.deal = deal;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	
	
}
