package secondweek;

import java.util.Objects;

public class User {
	private Integer userno;
	private String name;
	private String password;
	private int deposit;
	private String bigArea;
	private String mediumArea;
	private String smallArea;

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

	public User(Integer userno, String name, String password, int deposit, String bigArea, String mediumArea,
			String smallArea) {
		super();
		this.userno = userno;
		this.name = name;
		this.password = password;
		this.deposit = deposit;
		this.bigArea = bigArea;
		this.mediumArea = mediumArea;
		this.smallArea = smallArea;
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

	public String getBigArea() {
		return bigArea;
	}

	public void setBigArea(String bigArea) {
		this.bigArea = bigArea;
	}

	public String getMediumArea() {
		return mediumArea;
	}

	public void setMediumArea(String mediumArea) {
		this.mediumArea = mediumArea;
	}

	public String getSmallArea() {
		return smallArea;
	}

	public void setSmallArea(String smallArea) {
		this.smallArea = smallArea;
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
