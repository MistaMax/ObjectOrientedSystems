package misc;

import java.io.UncheckedIOException;

public class EmployeeRecord implements Comparable<EmployeeRecord> {
	public static int COMMISSION = 1;
	public static int HOURLY = 2;
	public static int SALARY = 3;
	
	private String firstName;
	private String lastName;
	private int age;
	private EmployeeTypes employmentType;
	private double paymentAmmount;
	
	
	public EmployeeRecord() {
		
	}
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(String age) {
		try {
			this.age = Integer.parseInt(age);
		} catch(NumberFormatException e) {			
			throw new RuntimeException("Invalid age: " + age);
		}
	}

	public String getEmploymentType() {
		return employmentType.toString();
	}

	public void setEmploymentType(String employmentType) throws RuntimeException {
		switch (employmentType.toLowerCase()) {
		case "commission":
			this.employmentType = EmployeeTypes.Commission;
			break;
		case "hourly":
			this.employmentType = EmployeeTypes.Hourly;
			break;
		case "salary":
			this.employmentType = EmployeeTypes.Salary;
			break;
		default:
			throw new RuntimeException("Invalid EmploymentType: " + employmentType);
		}
	}

	public double getPaymentAmmount() {
		return paymentAmmount;
	}

	public void setPaymentAmmount(String payAmmnt) {
		try {
			this.paymentAmmount = Double.parseDouble(payAmmnt);
		} catch(NumberFormatException e) {
			throw new RuntimeException("Invalid payment ammount: " + payAmmnt);
		}
	}
	@Override
	public String toString() {
		return String.format("%-30s %-3d  %-12s $%12.2f\n", this.getFirstName() + " " + this.getLastName(), this.getAge()
				, this.getEmploymentType(), this.getPaymentAmmount());
	}

	@Override
	public int compareTo(EmployeeRecord employee) {
		return this.getLastName().compareTo(employee.getLastName());
	}
	
}
