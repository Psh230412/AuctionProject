package secondweek;

import java.util.Objects;

public class User {
	private Integer userno;
	private String name;
	private String password;
	private int deposit;
	
	public User(String name, String password, int deposit) {
		super();
		this.name = name;
		this.password = password;
		this.deposit = deposit;
	}

	public User(Integer no, String name, String password, int deposit) {
		super();
		this.userno = no;
		this.name = name;
		this.password = password;
		this.deposit = deposit;
	}

	public Integer getNo() {
		return userno;
	}

	public void setNo(Integer no) {
		this.userno = no;
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

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(name, other.name) && Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "User [no=" + userno + ", name=" + name + ", password=" + password + ", deposit=" + deposit + "]";
	}

}
