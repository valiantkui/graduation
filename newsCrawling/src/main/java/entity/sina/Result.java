package entity.sina;

import java.util.List;

public class Result {

	private int count;
	private List<Data> data;
	private String encoding;
	private String last_time;
	private String serverSeconds;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public List<Data> getData() {
		return data;
	}
	public void setData(List<Data> data) {
		this.data = data;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getLast_time() {
		return last_time;
	}
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}
	public String getServerSeconds() {
		return serverSeconds;
	}
	public void setServerSeconds(String serverSeconds) {
		this.serverSeconds = serverSeconds;
	}
	@Override
	public String toString() {
		return "Result [count=" + count + ", data=" + data + ", encoding=" + encoding + ", last_time=" + last_time
				+ ", serverSeconds=" + serverSeconds + "]";
	}
	
}
