package cn.ipanel.ningxia.response;


/** 展示新闻列表是，返回的数据
 * @author qinmian
 *
 */
public class ImageItemBean{

	private String id;
	private String title;
	private String  image;
	
	
	public ImageItemBean() {
		super();
	}
	public ImageItemBean(String id, String title, String image) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	
}
