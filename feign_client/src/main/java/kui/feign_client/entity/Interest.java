package kui.feign_client.entity;

import java.util.Objects;

public class Interest {                                                            
	private int id;                                                        
	private int n_no;                                                           
	private float val;                                                          
	private long timestamp;
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int hashCode() {
		return Objects.hash(getId(),getN_no());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Interest other = (Interest) obj;
		if (id != other.id)
			return false;
		if (n_no != other.n_no)
			return  false;
		return true;
	}
	@Override
	public String toString() {
		return "Interest [id=" + id + ", n_no=" + n_no + ", val=" + val + ", timestamp=" + timestamp + "]";
	}
	
	
	                                                                               
	                                                                               
}                                                                                  
                                                                                   