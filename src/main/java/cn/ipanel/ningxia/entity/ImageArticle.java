package cn.ipanel.ningxia.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** 政务网塞上江南中三个含有图片的专题对应实体
 * @author qinmian
 *
 */
@Entity
@Table(name="img_article")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ImageArticle implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id" , length=32)
	private String id;
	
	@Column(name="title")
	private String title;//标题
	
	@Column(name="content",columnDefinition="text")
	private String content;//内容
	
	@Column(name="source")
	private String source;//信息来源
	
	@Column(name="release_time")
	private Date releaseTime;//发布时间
	
	@Column(name="topic_name")
	private String topicName;//所属专题
	
	@Column(name="img")
	private String img;//列表展示图片

	public ImageArticle() {
		super();
	}

	public ImageArticle(String title, String content, String source,
			Date releaseTime, String topicName, String img) {
		super();
		this.title = title;
		this.content = content;
		this.source = source;
		this.releaseTime = releaseTime;
		this.topicName = topicName;
		this.img = img;
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

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "SSJNPictureArticle [id=" + id + ", title=" + title
				+ ", content=" + content + ", source=" + source
				+ ", releaseTime=" + releaseTime + ", topicName=" + topicName
				+ ", img=" + img + "]";
	}
	
	
}
