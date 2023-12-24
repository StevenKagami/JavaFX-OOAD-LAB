package model;

public class User {

	private String username;
    private String password;
    private int age, id;
    private String role;
    
    public User(String username, String password, int age, int id, String role) {
		super();
		this.username = username;
		this.password = password;
		this.age = age;
		this.id = id;
		this.role = role;
	}

	
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



//	public User(String username, String password, int age, String role) {
//		super();
//		this.username = username;
//		this.password = password;
//		this.age = age;
//		this.role = role;
//	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

    
    
}
