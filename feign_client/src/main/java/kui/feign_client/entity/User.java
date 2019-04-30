package kui.feign_client.entity;

public class User {

	private int id;
	private String u_id;
	private String name;
	private String password;
	private String interest_label;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getInterest_label() {
		return interest_label;
	}
	public void setInterest_label(String interest_label) {
		this.interest_label = interest_label;
	}
	@Override
	public String toString() {
		return "User [u_id=" + u_id + ", name=" + name + ", password=" + password + ", interest_label=" + interest_label
				+ "]";
	}
	
	
	
}
