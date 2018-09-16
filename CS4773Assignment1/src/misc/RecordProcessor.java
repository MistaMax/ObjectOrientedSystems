package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class RecordProcessor {

	private  ArrayList<EmployeeRecord> employees;
	private  StringBuffer outputBuffer;
	
	
	
	
	public RecordProcessor() {
		employees = new ArrayList<EmployeeRecord>();
		outputBuffer = new StringBuffer();
	}
			
	public static String processFile(String inputFile) {
		try {
			RecordProcessor recordProcessor = new RecordProcessor();
			
			recordProcessor.convertFileToRecords(inputFile);
			
			ReportGenerator reportGenerator = new ReportGenerator(recordProcessor.employees);
			recordProcessor.outputBuffer = reportGenerator.generateReport();
			return recordProcessor.outputBuffer.toString();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		
	}
	
	private void convertFileToRecords(String inputFile) throws FileNotFoundException  {
		Scanner console = null;
		console = new Scanner(new File(inputFile));
		
		while(console.hasNextLine()) {
			String currentLine = console.nextLine();
			if(currentLine.length() > 0)
				this.addRecord(currentLine);
		}
		console.close();
		if (employees.isEmpty())
			throw new RuntimeException("No records found on file");
		Collections.sort(employees);
	}
	
	private void addRecord(String currentLine){
		String [] recordDataEntry = currentLine.split(",");
		EmployeeRecord record = new EmployeeRecord();
		record.setFirstName(recordDataEntry[0]);
		record.setLastName(recordDataEntry[1]);
		record.setAge(recordDataEntry[2]);
		record.setEmploymentType(recordDataEntry[3]);
		record.setPaymentAmmount(recordDataEntry[4]);
		employees.add(record);

	}
}
	
	
