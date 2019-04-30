package cn.ipanel.ningxia.page;

public class BasePage {

	private static Integer defaultRows = 10;
	private static Integer defaultStart = 0;
	private Integer rows;
	private Integer page;
	public Integer getRows() {
		if(rows == null || rows <= 0){
			return defaultRows;
		}else{
			return rows;
		}
	}
	public void setRows(Integer rows) {
		this.rows = rows;			
	}
	public Integer getPage() {
		if(page == null || rows < 0){
			return 0;
		}else{
			return page - 1;
		}
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public int getFirst(){
		if(page == null || page <= 0){
			return defaultStart;
		}
		return (page-1)*rows;
	}
	
	
}
