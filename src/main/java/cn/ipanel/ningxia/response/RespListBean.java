package cn.ipanel.ningxia.response;

import java.util.List;

/** 返回列表数据的bean
 * @author qinmian
 *
 */
public class RespListBean extends BaseRespBean{

	private List<?> list;
	private Integer total;

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	
	
	
}
