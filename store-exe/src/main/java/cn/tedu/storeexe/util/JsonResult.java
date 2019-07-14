package cn.tedu.storeexe.util;


//@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResult<T> {
	
	
	private Integer state; //状态
	private String message; //信息
	private T data; //数据
		
	public JsonResult() {
		super();
	}
	
	public JsonResult(Integer state, T data) {
		super();
		this.state = state;
		this.data = data;
	}
	
	public JsonResult(Throwable e) {
		super();
		this.message = e.getMessage();
	}

	public JsonResult(Integer state) {
		super();
		this.state = state;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
