package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class RecordProcessor {

	private  ArrayList<EmployeeRecord> employees;
	private  StringBuffer outputBuffer;
	
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
	
	
	public RecordProcessor() {
		firstNamesCount = new HashMap<String, Integer>();
		lastNamesCount = new HashMap<String, Integer>();
		
		ageSum = 0;
		averageAge = 0f;
		commissionEmployeeCount = 0;
		commissionPaySum = 0;
		commissionAveragePayment = 0;
		hourlyEmployeeCount = 0;
		hourlyPaySum = 0;
		hourlyAveragePayment = 0;
		salaryEmployeeCount = 0;
		salaryPaySum = 0;
		salaryAveragePayment = 0;
	}
	
	

			
	public static String processFile(String inputFile) {
		RecordProcessor recordProcessor = new RecordProcessor();
		recordProcessor.convertFileToRecords(inputFile);
		recordProcessor.sumUpNumbers();
		recordProcessor.calculateAverages();
		recordProcessor.createOutput();
		
		return recordProcessor.outputBuffer.toString();
	}
	
	private void convertFileToRecords(String inputFile) {
		Scanner console = null;
		try {
			console = new Scanner(new File(inputFile));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		while(console.hasNextLine()) {
			String currentLine = console.nextLine();
			if(currentLine.length() > 0)
				this.addRecord(currentLine);
		}
		
		console.close();
	}
	
	private void addRecord(String currentLine){
		String [] words = currentLine.split(",");
		EmployeeRecord record = new EmployeeRecord();
		record.setLastName(words[0]);
		record.setFirstName(words[1]);
		record.setAge(words[2]);
		record.setEmploymentType(words[3]);
		record.setPaymentAmmount(words[4]);
		
		employees.add(record);
		
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
		this.initOutput();
		if(firstNamesCount.size() == 0 && lastNamesCount.size() == 0) {
			outputBuffer.append(String.format("All last names are unique"));
			return;
		}
		outputBuffer.append(String.format("\nFirst names with more than one person sharing it:\n"));
		this.findSharedFirstNames();
		outputBuffer.append(String.format("\nLast names with more than one person sharing it:\n"));
		this.findSharedLastNames();


		

		
	}
	
	
	
	private void incrementFirstNameCount(String firstName) {
		int count = 1;
		if(this.firstNamesCount.containsKey(firstName)) {
			count = this.firstNamesCount.get(firstName);
			count++;
			this.firstNamesCount.put(firstName, count);
			return;
		}
		this.firstNamesCount.put(firstName, count);
	}
	
	private void incrementLastNameCount(String lastName) {
		int count = 1;
		if(this.lastNamesCount.containsKey(lastName)) {
			count = this.lastNamesCount.get(lastName);
			count++;
			this.lastNamesCount.put(lastName, count);
			return;
		}
		this.lastNamesCount.put(lastName, count);
	}
	
	private void incrementEmployeeType(EmployeeRecord employee) {
		switch(employee.getEmploymentType()) {
		case 1:
			this.commissionEmployeeCount++;
			this.commissionPaySum += employee.getPaymentAmmount();
			break;
		case 2:
			this.hourlyEmployeeCount++;
			this.hourlyPaySum += employee.getPaymentAmmount();
			break;
		case 3:
			this.salaryEmployeeCount++;
			this.salaryPaySum += employee.getPaymentAmmount();
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
