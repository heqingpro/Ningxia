package cn.ipanel.ningxia.response;

import java.util.Date;

/** 展示新闻列表是，返回的数据
 * @author qinmian
 *
 */
public class ItemBean{

	private String id;
	private String title;
	private Date releaseDate;
	
	
	
	public ItemBean(String id, String title) {
		this(id, title, null);
	}
	public ItemBean(String id, String title, Date releaseDate) {
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
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
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
}
