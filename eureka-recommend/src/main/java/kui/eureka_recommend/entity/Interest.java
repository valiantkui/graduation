package kui.eureka_recommend.entity;                                               
                                                                                   
public class Interest {                                                            
	private String u_id;                                                        
	private int n_no;                                                           
	private float val;                                                          
	private long timestamp;
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
	public float getVal() {
		return val;
	}
	public void setVal(float val) {
		this.val = val;
	}
	
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Interest [u_id=" + u_id + ", n_no=" + n_no + ", val=" + val + ", timestamp=" + timestamp + "]";
	}                                                          
	     
	
	                                                                               
	                                                                               
}                                                                                  
                                                                                   