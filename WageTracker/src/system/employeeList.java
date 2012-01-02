package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * sqlite database using zentus sqlitejdbc-v056
 * methods for implementing and retrieving data
 * @author Jhallman
 *
 */
public class employeeList {
	
	public static void initDB() throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:employees.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("create table if not exists employees" +
				"(id_number INTEGER PRIMARY KEY, lastname VARCHAR(255) NOT NULL, " +
				"firstname VARCHAR(255) NOT NULL, wage NUMBER(10) NOT NULL);");
		conn.close();
	}
	
	public static void addEmployee(String lastname, String firstname, double wage) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:employees.db");
		Statement stat = conn.createStatement();
		PreparedStatement prep = conn.prepareStatement(
				"insert into employees values (?, ?, ?, ?);");
		
		//assign next highest id number to new employee
		ResultSet rs = stat.executeQuery("select max(ID_NUMBER) from employees;");
		int id = rs.getInt(1) + 1;
		rs.close();
		prep.setInt(1, id);
		
		//assign personal information
		prep.setString(2, lastname);
		prep.setString(3, firstname);
		prep.setDouble(4, wage);
		prep.addBatch();
		
		//send batch to table
		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);
		
		//create hours table for new employee with title format hours_xx where xx is the employee id number
		stat.executeUpdate("create table if not exists hours_" + id + "(date DATE NOT NULL, hours NUMBER(100) NULL);");

		conn.close();
	}
	
	public static void addHours(int id, double hours) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:employees.db");
		
		PreparedStatement prep = conn.prepareStatement(
				"insert into hours_" + id + " values (?, ?);");
		
		//wrap data into batch
		java.sql.Date timeStamp = new java.sql.Date(new java.util.Date().getTime());
		prep.setDate(1, timeStamp);
		prep.setDouble(2, hours);
		prep.addBatch();
		
		//send batch to table
		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);
		
		conn.close();
	}
	
	public static void getEmployeeData() throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:employees.db");
		Statement stat = conn.createStatement();
		
		ResultSet rs = stat.executeQuery("select * from employees;");
		while (rs.next()) {
			System.out.println(rs.getString("ID_NUMBER") + " | " + rs.getString("LASTNAME") + 
					" | " + rs.getString("FIRSTNAME") + " | " + rs.getString("WAGE"));
		}
		rs.close();
		conn.close();
	}
	
	public static void getEmployeeHours(int id) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:employees.db");
		Statement stat = conn.createStatement();
		
		ResultSet hoursData = stat.executeQuery("select * from hours_" + id + ";");
		while (hoursData.next()) {
			System.out.println(hoursData.getDate("date") + " | " + hoursData.getDouble("hours"));
		}
		
		hoursData.close();
		conn.close();
	}
}
