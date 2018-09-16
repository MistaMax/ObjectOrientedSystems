package misc;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportGenerator {
	
	private  ArrayList<EmployeeRecord> employees;

	private StringBuffer outputBuffer;
	private int ageSum;
	private float averageAge;
	private int commissionEmployeeCount;
	private double commissionPaySum;
	private double commissionAveragePayment;
	private int hourlyEmployeeCount;
	private double hourlyPaySum;
	private double hourlyAveragePayment;
	private int salaryEmployeeCount;
	private double salaryPaySum;
	private double salaryAveragePayment;
	HashMap<String, Integer> firstNamesCount;
	HashMap<String, Integer> lastNamesCount;
	
	public ReportGenerator() {
		
	}
	
	public ReportGenerator(ArrayList<EmployeeRecord> employees) {
		this.outputBuffer = new StringBuffer();
		this.employees = employees;
		this.firstNamesCount = new HashMap<String, Integer>();
		this.lastNamesCount = new HashMap<String, Integer>();
	}
	
	public StringBuffer generateReport() {
		try {
			this.initOutput();
			this.sumUpNumbers();
			this.calculateAverages();
			this.createOutput();
			return outputBuffer;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private void initOutput() {
		outputBuffer.append(String.format("# of people imported: %d\n", employees.size()));
		
		outputBuffer.append(String.format("\n%-30s %s  %-12s %12s\n", "Person Name", "Age", "Emp. Type", "Pay"));
		for(int i = 0; i < 30; i++)
			outputBuffer.append(String.format("-"));
		outputBuffer.append(String.format(" ---  "));
		for(int i = 0; i < 12; i++)
			outputBuffer.append(String.format("-"));
		outputBuffer.append(String.format(" "));
		for(int i = 0; i < 12; i++)
			outputBuffer.append(String.format("-"));
		outputBuffer.append(String.format("\n"));
		
	}
	
	private void sumUpNumbers() {
		for(EmployeeRecord employee : employees) {
			outputBuffer.append(employee.toString());
			this.ageSum += employee.getAge();
			this.incrementFirstNameCount(employee.getFirstName());
			this.incrementLastNameCount(employee.getLastName());
			this.incrementEmployeeType(employee);
			
		}
	}
	
	private void calculateAverages() {
		this.averageAge = this.ageSum / this.employees.size();
		this.commissionAveragePayment = this.commissionPaySum / this.commissionEmployeeCount;
		this.hourlyAveragePayment = this.hourlyPaySum / this.hourlyEmployeeCount;
		this.salaryAveragePayment = this.salaryPaySum / this.salaryEmployeeCount;
	}
	
	private void createOutput() {
		if(firstNamesCount.size() == 0 && lastNamesCount.size() == 0) {
			outputBuffer.append(String.format("All last names are unique"));
			return;
		}
		outputBuffer.append(String.format("\nAverage age:         %12.1f\n", this.averageAge));
		outputBuffer.append(String.format("Average commission:  $%12.2f\n", this.commissionAveragePayment));
		outputBuffer.append(String.format("Average hourly wage: $%12.2f\n", this.hourlyAveragePayment));
		outputBuffer.append(String.format("Average salary:      $%12.2f\n", this.salaryAveragePayment));
		
		outputBuffer.append(String.format("\nFirst names with more than one person sharing it:\n"));
		this.findSharedFirstNames();
		outputBuffer.append(String.format("\nLast names with more than one person sharing it:\n"));
		this.findSharedLastNames();


		

		
	}
	
	private void incrementFirstNameCount(String firstName) {
		if(this.firstNamesCount.containsKey(firstName)) {
			int count = this.firstNamesCount.get(firstName);
			count++;
			this.firstNamesCount.put(firstName, count);
			return;
		}
		this.firstNamesCount.put(firstName, 1);
	}
	
	private void incrementLastNameCount(String lastName) {
		if(this.lastNamesCount.containsKey(lastName)) {
			int count = this.lastNamesCount.get(lastName);
			count++;
			this.lastNamesCount.put(lastName, count);
			return;
		}
		this.lastNamesCount.put(lastName, 1);
	}
	
	private void incrementEmployeeType(EmployeeRecord employee) {
		switch(employee.getEmploymentType()) {
		case "Commission":
			this.commissionEmployeeCount++;
			this.commissionPaySum += employee.getPaymentAmmount();
			break;
		case "Hourly":
			this.hourlyEmployeeCount++;
			this.hourlyPaySum += employee.getPaymentAmmount();
			break;
		case "Salary":
			this.salaryEmployeeCount++;
			this.salaryPaySum += employee.getPaymentAmmount();
		}
	}
	
	private void findSharedFirstNames(){
		int count;
		for(String key : this.firstNamesCount.keySet()) {
			count = this.firstNamesCount.get(key);
			if(count == 1) continue;
			outputBuffer.append(String.format("%s, # people with this name: %d\n", key, count));
		}
	}
	
	private void findSharedLastNames() {
		int count;
		for(String key : this.lastNamesCount.keySet()) {
			count = this.lastNamesCount.get(key);
			if(count == 1) continue;
			outputBuffer.append(String.format("%s, # people with this name: %d\n", key, count));
		}
	}
}
