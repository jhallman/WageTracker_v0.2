package system;

import java.util.Scanner;

/**
 * Contains the basic operation methods for the main application
 * @author Jhallman
 *
 */
public class operations {

	/**
	 * 
	 * @throws Exception
	 */
	public static void newEmployee() throws Exception {
		String lastname, firstname;
		double wage;
		
		Scanner input = new Scanner(System.in);
		System.out.println("Last name: ");
		lastname = input.next();
		System.out.println("First name: ");
		firstname = input.next();
		System.out.println("Wage: ");
		wage = input.nextDouble();
		
		employeeList.addEmployee(lastname, firstname, wage);
	}

	public static void printEmployeeData() throws Exception {
		employeeList.getEmployeeData();
	}
	
	public static void printHours() throws Exception {
		int id;
		
		Scanner input = new Scanner(System.in);
		System.out.println("Employee ID: ");
		id = input.nextInt();
		employeeList.getEmployeeHours(id);
	}
	
	public static void addHours() throws Exception {
		int id;
		double hours;
		
		Scanner input = new Scanner(System.in);
		System.out.println("Employee ID: ");
		id = input.nextInt();
		System.out.println("Hours: ");
		hours = input.nextDouble();
			
		employeeList.addHours(id, hours);
	}
}
