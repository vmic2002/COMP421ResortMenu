import java.sql.*;
import java.util.Scanner;

import com.ibm.db2.jcc.DB2Driver;
//import sqlj.*;
public class ResortMenu {


	private static final String option1 = "Manager can check for active services";

	private static final String option2 = "Make a customer account";

	private static final String option3 = "Delete a customer account";

	private static final String option4 = "Book Trip";

	private static final String option5 = "Manager can update staff vacation days";

	private static final String option6 = "Quit";

	public static void main(String[] args) throws SQLException {
		// Register the driver.  You must register the driver before you can use it.
		try { DriverManager.registerDriver(new DB2Driver());}
		catch (Exception cnfe){System.out.println("Class not found");}

		// This is the url you must use for DB2.
		//Note: This url may not valid now ! Check for the correct year and semester and server name.
		String url = "jdbc:db2://winter2024-comp421.cs.mcgill.ca:50000/comp421";

		//REMEMBER to remove your user id and password before submitting your code!!
		String your_userid = "cs421g19";
		String your_password = "gRoupcpv1924!";
		//TODO AS AN ALTERNATIVE, you can just set your password in the shell environment in the Unix (as shown below) and read it from there.
		//$  export SOCSPASSWD=yoursocspasswd 
		Connection con = (Connection) DriverManager.getConnection(url,your_userid,your_password);
		Statement statement = con.createStatement();

        System.out.println("HELLO");
		Scanner sc= new Scanner(System.in); //System.in is a standard input stream
		while (true) {
			System.out.println("Resort Main Menu:");
			System.out.println("\t1. "+option1);
			System.out.println("\t2. "+option2);
			System.out.println("\t3. "+option3);
			System.out.println("\t4; "+option4);
			System.out.println("\t5. "+option5);
			System.out.println("\t6. "+option6);
			System.out.print("Enter option: ");  
			String str= sc.nextLine();//reads string   
			if (str.equals("1")) {
				option1(statement);
			} else if (str.equals("2")) {
				option2(statement);
			} else if (str.equals("3")) {
				option3(statement);
			} else if (str.equals("4")) {
				option4(statement);
			} else if (str.equals("5")) {
				option5(statement);
			} else if (str.equals("6")) {
				System.out.println("Quitting...");
				break;
			}
		}
	}

	
	
	private static void sendRequest(Statement statement, String sql) {
		try {
			System.out.println(sql);
			statement.executeUpdate(sql);
			System.out.println("DONE");
		} catch (SQLException e) {
			int sqlCode = e.getErrorCode(); // Get SQLCODE
			String sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
			System.out.println(e);
		}
	}
	private static void option1(Statement statement) {
		String sql = "TODO";
		sendRequest(statement, sql);
	}
	private static void option2(Statement statement) {
		String sql = "TODO";
		sendRequest(statement, sql);
	}
	private static void option3(Statement statement) {
		String sql = "TODO";
		sendRequest(statement, sql);
	}
	private static void option4(Statement statement) {
		//DONE
		String sql = "INSERT INTO Trip (tripID, emailAddress, reservationType, numDays, numPeopleInParty, pickupTime, airportName, date) VALUES (14, 'jake@jmail.com', 'Premium', 7, 4, '15:00', 'LAX', '2024-03-05')";
		sendRequest(statement, sql);
	}
	private static void option5(Statement statement) {
		String sql = "TODO";
		sendRequest(statement, sql);
	}
}

