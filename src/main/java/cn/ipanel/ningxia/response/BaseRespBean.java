package cn.ipanel.ningxia.response;

public class BaseRespBean {

	protected Integer code;
	protected String msg;
	
	public BaseRespBean() {
	}
	public BaseRespBean(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
