package system;

import java.util.Scanner;

public class WageTracker {

	/**
	 * main process for wage tracker application
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {	
		Scanner input = new Scanner(System.in);
		employeeList.initDB();
		
		/*
		 * main while loop to control program
		 */
		boolean quit = false;
		int option;
		
		while (!quit) {
			System.out.println("1: New Employee, 2: Print, 3: Add Hours, 0: Quit");
			option = input.nextInt();
			
			switch (option) {
				
				case 1:
					operations.newEmployee();
					break;
				case 2:
					operations.printEmployeeData();
					break;
				case 3:
					operations.addHours();
					break;
				case 4:
					operations.printHours();
					break;
				case 0:
					System.out.println("Goodbye!");
					quit = true;
					break;
				default:
					System.out.println("Not a valid option!");
			}
		}
	}

}