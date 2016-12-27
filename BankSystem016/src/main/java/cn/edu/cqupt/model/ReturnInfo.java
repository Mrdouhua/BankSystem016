package cn.edu.cqupt.model;

public class ReturnInfo {
	//状态值
	private String status;
	//提示用户的信息
	private String info;
	//操作成功之后跳转到的页面的url
	private String url;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

}
