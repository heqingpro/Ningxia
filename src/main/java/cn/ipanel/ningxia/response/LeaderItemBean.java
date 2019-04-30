package cn.ipanel.ningxia.response;


/** 展示新闻列表是，返回的数据
 * @author qinmian
 *
 */
public class LeaderItemBean{

	private String id;
	private String name;
	private String duties;
	private String headImage;
	
	public LeaderItemBean(String id, String name, String duties,
			String headImage) {
		this.id = id;
		this.name = name;
		this.duties = duties;
		this.headImage = headImage;
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
	
	
}
