package DataBaseConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnection {
	static Connection connection = null;
	private static String notification = "";
	
	static void getInstance (){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodtransfusion","root","");
			notification = "Success";
		}catch(ClassNotFoundException e){
			notification = "ClassNotFoundException";
		}catch(SQLException e){
			e.printStackTrace();
			notification = "SQLException";
		}
	}
	
	// methode appellée pour afficher les informations depuis la base de donnée
	
	public static ResultSet selectFromDataBase(String query){
		ResultSet result = null;
		Statement statement = null;
		if (connection == null)
			getInstance();
		if (connection != null){
			try{
				statement = connection.createStatement();
				result = statement.executeQuery(query);
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
		
	}
	
	// methode appellée avec les requetes insert, update, delete
	
	public static int updateDataBase(String query){
		int number = -1;
		Statement statement = null;
		if (connection == null)
			getInstance();
		if (connection != null){
			try{
				statement = connection.createStatement();
				number = statement.executeUpdate(query);
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return number;
	}	
}
