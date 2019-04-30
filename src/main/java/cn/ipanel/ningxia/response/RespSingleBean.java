package cn.ipanel.ningxia.response;

public class RespSingleBean<T> extends BaseRespBean {

	private T content;

	
	public RespSingleBean() {
	}

	public RespSingleBean(T content,Integer code,String msg) {
		super(code, msg);
		this.content = content;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}
	
	
}
