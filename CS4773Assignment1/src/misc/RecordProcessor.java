package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class RecordProcessor {
	private static String [] firstName;
	private static String [] lastName;
	private static int [] age;
	private static String [] employmentType;
	private static double [] paymentAmmount;
	
	private static void initializeEmployeeRecordVariables(int ammountOfPeople)
	{
		firstName = new String[ammountOfPeople];
		lastName = new String[ammountOfPeople];
		age = new int[ammountOfPeople];
		employmentType = new String[ammountOfPeople];
		paymentAmmount = new double[ammountOfPeople];
	}
	
	private static boolean processEmployeeInfo(String currentLine, Scanner console, int ammountOfPeople)
	{
		String [] words = currentLine.split(",");

		int c2 = 0; 
		for(;c2 < lastName.length; c2++) {
			if(lastName[c2] == null)
				break;
			
			if(lastName[c2].compareTo(words[1]) > 0) {
				for(int i = ammountOfPeople; i > c2; i--) {
					firstName[i] = firstName[i - 1];
					lastName[i] = lastName[i - 1];
					age[i] = age[i - 1];
					employmentType[i] = employmentType[i - 1];
					paymentAmmount[i] = paymentAmmount[i - 1];
				}
				break;
			}
		}
		
		firstName[c2] = words[0];
		lastName[c2] = words[1];
		employmentType[c2] = words[3];

		try {
			age[c2] = Integer.parseInt(words[2]);
			paymentAmmount[c2] = Double.parseDouble(words[4]);
		} catch(Exception e) {
			System.err.println(e.getMessage());
			console.close();
			return false;
		}
		return true;
	}
	
	public static String processFile(String f) {
		StringBuffer newEmployeeRecord = new StringBuffer();
		
		Scanner console = null;
		try {
			console = new Scanner(new File(f));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return null;
		}
		
		int ammountOfPeople = 0;
		while(console.hasNextLine()) {
			String currentLine = console.nextLine();
			if(currentLine.length() > 0)
				ammountOfPeople++;
		}
		
		console.close();

		try {
			console = new Scanner(new File(f));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return null;
		}
		
		initializeEmployeeRecordVariables(ammountOfPeople);
		
		ammountOfPeople = 0;
		while(console.hasNextLine()) {
			String currentLine = console.nextLine();
			if(currentLine.length() > 0) {
				if(!processEmployeeInfo(currentLine,console,ammountOfPeople))
					return null;
				ammountOfPeople++;
			}
		}
		
		if(ammountOfPeople == 0) {
			System.err.println("No records found in data file");
			console.close();
			return null;
		}
		
		//print the rows
		newEmployeeRecord.append(String.format("# of people imported: %d\n", firstName.length));
		
		newEmployeeRecord.append(String.format("\n%-30s %s  %-12s %12s\n", "Person Name", "Age", "Emp. Type", "Pay"));
		for(int i = 0; i < 30; i++)
			newEmployeeRecord.append(String.format("-"));
		newEmployeeRecord.append(String.format(" ---  "));
		for(int i = 0; i < 12; i++)
			newEmployeeRecord.append(String.format("-"));
		newEmployeeRecord.append(String.format(" "));
		for(int i = 0; i < 12; i++)
			newEmployeeRecord.append(String.format("-"));
		newEmployeeRecord.append(String.format("\n"));
		
		for(int i = 0; i < firstName.length; i++) {
			newEmployeeRecord.append(String.format("%-30s %-3d  %-12s $%12.2f\n", firstName[i] + " " + lastName[i], age[i]
				, employmentType[i], paymentAmmount[i]));
		}
		
		int sum1 = 0;
		float avg1 = 0f;
		int c2 = 0;
		double sum2 = 0;
		double avg2 = 0;
		int c3 = 0;
		double sum3 = 0;
		double avg3 = 0;
		int c4 = 0;
		double sum4 = 0;
		double avg4 = 0;
		for(int i = 0; i < firstName.length; i++) {
			sum1 += age[i];
			if(employmentType[i].equals("Commission")) {
				sum2 += paymentAmmount[i];
				c2++;
			} else if(employmentType[i].equals("Hourly")) {
				sum3 += paymentAmmount[i];
				c3++;
			} else if(employmentType[i].equals("Salary")) {
				sum4 += paymentAmmount[i];
				c4++;
			}
		}
		avg1 = (float) sum1 / firstName.length;
		newEmployeeRecord.append(String.format("\nAverage age:         %12.1f\n", avg1));
		avg2 = sum2 / c2;
		newEmployeeRecord.append(String.format("Average commission:  $%12.2f\n", avg2));
		avg3 = sum3 / c3;
		newEmployeeRecord.append(String.format("Average hourly wage: $%12.2f\n", avg3));
		avg4 = sum4 / c4;
		newEmployeeRecord.append(String.format("Average salary:      $%12.2f\n", avg4));
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		int c1 = 0;
		for(int i = 0; i < firstName.length; i++) {
			if(hm.containsKey(firstName[i])) {
				hm.put(firstName[i], hm.get(firstName[i]) + 1);
				c1++;
			} else {
				hm.put(firstName[i], 1);
			}
		}

		newEmployeeRecord.append(String.format("\nFirst names with more than one person sharing it:\n"));
		if(c1 > 0) {
			Set<String> set = hm.keySet();
			for(String str : set) {
				if(hm.get(str) > 1) {
					newEmployeeRecord.append(String.format("%s, # people with this name: %d\n", str, hm.get(str)));
				}
			}
		} else { 
			newEmployeeRecord.append(String.format("All first names are unique"));
		}

		HashMap<String, Integer> hm2 = new HashMap<String, Integer>();
		int c21 = 0;
		for(int i = 0; i < lastName.length; i++) {
			if(hm2.containsKey(lastName[i])) {
				hm2.put(lastName[i], hm2.get(lastName[i]) + 1);
				c21++;
			} else {
				hm2.put(lastName[i], 1);
			}
		}

		newEmployeeRecord.append(String.format("\nLast names with more than one person sharing it:\n"));
		if(c21 > 0) {
			Set<String> set = hm2.keySet();
			for(String str : set) {
				if(hm2.get(str) > 1) {
					newEmployeeRecord.append(String.format("%s, # people with this name: %d\n", str, hm2.get(str)));
				}
			}
		} else { 
			newEmployeeRecord.append(String.format("All last names are unique"));
		}
		
		//close the file
		console.close();
		
		return newEmployeeRecord.toString();
	}
	
}