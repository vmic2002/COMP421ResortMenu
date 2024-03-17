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
			System.out.println("\t4. "+option4);
			System.out.println("\t5. "+option5);
			System.out.println("\t6. "+option6);
			System.out.print("Enter option: ");  
			String str= sc.nextLine();//reads string   
			if (str.equals("1")) {
				option1(statement);
			} else if (str.equals("2")) {
				System.out.println("Enter email address:");
				String emailAddress = sc.nextLine();
				if (customerAccountExists(statement, emailAddress)) {
					System.out.println("A customer account with this email address already exists!");
				} else {
					System.out.println("Enter cardNumber: (10 digits)");
					String cardNumber = sc.nextLine();
					System.out.println("Enter full name:");
					String fullName = sc.nextLine();
					System.out.println("Enter date of birth: (YYYY-MM-DD)");
					String dateOfBirth = sc.nextLine();
					option2(statement, emailAddress, cardNumber, fullName, dateOfBirth);
				}
			} else if (str.equals("3")) {
				System.out.println("Enter email address:");
				String emailAddress = sc.nextLine();
				if (!customerAccountExists(statement, emailAddress)) {
					System.out.println("No customer account exists with this email address!");
				} else {
					System.out.println("Enter card number:");//CARD NUMBER SERVES AS PASSWORD
					String cardNumber = sc.nextLine();
					option3(statement, emailAddress, cardNumber);
				}
			} else if (str.equals("4")) {
				System.out.println("Login Menu:");
				System.out.println("Enter email address:");
				String email = sc.nextLine();//reads string
				if (!customerAccountExists(statement, email)) {
					System.out.println("No customer account found with this email address, please choose option 2 to make an account...");
				} else { 
					System.out.println("Enter reservation type (Premium or Regular):"); 
					String reservationType = sc.nextLine();
					System.out.println("Enter the number of days of the vacation (please enter a whole number):");
					String numDays = sc.nextLine();
					System.out.println("Enter the number of people in your party:");
					String numPeople = sc.nextLine();
					System.out.println("Enter the time at which you would like the shuttle to pick you up at the airport: (XX:XX)");
					String pickupTime = sc.nextLine();
					System.out.println("Enter the name of the airport you will land at:");
					String airportName = sc.nextLine();
					System.out.println("Enter the arrival date: (YYYY-MM-DD)");
					String date = sc.nextLine();
					option4(statement, email, reservationType, numDays, numPeople, pickupTime, airportName, date);
				}
			} else if (str.equals("5")) {
				option5(statement);
			} else if (str.equals("6")) {
				System.out.println("Quitting...");
				break;
			}
		}
	}


	private static boolean customerAccountExists(Statement statement, String email) {
		String sql = "SELECT emailAddress FROM CustomerAccount WHERE emailAddress = '"+email+"'";
		ResultSet rs;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void sendUpdateRequest(Statement statement, String sql) {
		try {
			System.out.println(sql);
			statement.executeUpdate(sql);
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
		sendUpdateRequest(statement, sql);
	}
	private static void option2(Statement statement, String emailAddress, String cardNumber, String fullName, String dateOfBirth) {
		//DONE
		String sql = "INSERT INTO CustomerAccount (emailAddress, cardNumber, fullName, dateOfBirth) VALUES ('"+emailAddress+"', "+cardNumber+", '"+fullName+"', '"+dateOfBirth+"')";
		sendUpdateRequest(statement, sql);
	}
	private static void option3(Statement statement, String email, String cardNumber) {
		//DONE
		//perform select to make sur cardnumber matches, if it does delete account. if it doesnt, print wrong card number could not delete account
		String sql = "SELECT cardNumber FROM CustomerAccount WHERE emailAddress = '"+email+"'";
		ResultSet rs;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		try {
			rs.next();
			String cardNum = rs.getString(1);
			if (!cardNumber.equals(cardNum)) {
				System.out.println("Wrong card number! Could not delete account...");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		sql = "DELETE FROM CustomerAccount WHERE emailAddress = '"+email+"'";
		sendUpdateRequest(statement, sql);
	}
	private static void option4(Statement statement, String email, String reservationType, String numDays, String numPeople, String pickupTime, String airportName, String date) {
		//DONE
		//first find max trip id and increment by one for new trip entry
		String findMaxSql = "SELECT MAX(tripID) AS maxTripID FROM Trip";
		ResultSet rs;
		try {
			rs = statement.executeQuery(findMaxSql);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		int maxID;
		try {
			rs.next();
			maxID = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		String sql = "INSERT INTO Trip (tripID, emailAddress, reservationType, numDays, numPeopleInParty, pickupTime, airportName, date) VALUES ("+(maxID+1)+", '"+email+"', '"+reservationType+"', "+numDays+", "+numPeople+", '"+pickupTime+"', '"+airportName+"', '"+date+"')";
		sendUpdateRequest(statement, sql);
	}
	private static void option5(Statement statement) {
		String sql = "TODO";
		sendUpdateRequest(statement, sql);
	}
}

