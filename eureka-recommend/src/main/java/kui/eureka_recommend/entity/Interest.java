package kui.eureka_recommend.entity;

public class Interest {
	private String u_id;
	private int n_no;
	private int val;
	private String timestamp;
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public int getN_no() {
		return n_no;
	}
	public void setN_no(int n_no) {
		this.n_no = n_no;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Interest [u_id=" + u_id + ", n_no=" + n_no + ", val=" + val + ", timestamp=" + timestamp + "]";
	}
	
}
