package cn.ipanel.ningxia.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Table(name="leader")
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Leader implements Serializable{

	private static final long serialVersionUID = 1L;

	/**领导姓名（必选）
	领导职务（可选）
	领导头像（必选）
	工作分工（可选）
	个人简介（必选）**/
	@Id
	@Column(name="id" , length=32)
	private String id;
	
	@Column(name="name")
	private String name;//领导姓名
	
	@Column(name="duties")
	private String duties;//职务
	
	@Column(name="headImage")
	private String headImage;//头像url
	
	@Column(name="job_detail",columnDefinition="text")
	private String jobDetail;//工作分工
	
	@Column(name="resume",columnDefinition="text")//简历
	private String resume;
	@Column(name="belong",length=5)
	private String belong;
	
	
	public Leader() {
		super();
	}
	public Leader(String name, String duties, String headImage,
			String jobDetail, String resume,String belong) {
		super();
		this.name = name;
		this.duties = duties;
		this.headImage = headImage;
		this.jobDetail = jobDetail;
		this.resume = resume;
		this.belong = belong;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDuties() {
		return duties;
	}
	public void setDuties(String duties) {
		this.duties = duties;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getJobDetail() {
		return jobDetail;
	}
	public void setJobDetail(String jobDetail) {
		this.jobDetail = jobDetail;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	
}
