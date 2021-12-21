package src;

public abstract class User {
	
	private String userName;
	private String password;
	
	public void setName(String _name) {
		userName = _name;
	}
	
	public String getName() {
		return userName;
	}
	
	public void setPassword(String _pw) {
		password = _pw;
	}
	
	public String getPassword() {
		return password;
	}
	
	@Override
	public String toString() {
		return "Username: " + userName + "\nPassword: " + password;
	}

}
