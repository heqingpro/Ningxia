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
@Table(name="article")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Article implements Serializable{
	private static final long serialVersionUID = 1L;

	/**文章标题（必选）
	文章副标题（可选）
	正文（必选）
	图片链接（可选）
	图片说明（可选）
	视频链接（可选）
	视频说明（可选）
	新闻来源（可选）
	新闻发表时间（可选）
	第几届委员会（可选）
	所属专题名称（可选）**/
	@Id
	@Column(name="id",length=32)
	private String id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="subtitle")
	private String subtitle;
	
	@Column(name="content",columnDefinition="longtext")
	private String content;
	
	@Column(name="source")
	private String source;
	
	@Column(name="release_time")
	private Date releaseTime;
	
	@Column(name="meetting")
	private String meeting;
	
	@Column(name="topic_name")
	private String topicName;
	public Article() {
	}
	
	public Article(String title, String subtitle, String content,
			String source, Date releaseTime, String meeting, String topicName) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.content = content;
		this.source = source;
		this.releaseTime = releaseTime;
		this.meeting = meeting;
		this.topicName = topicName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getMeeting() {
		return meeting;
	}
	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	
	
}
