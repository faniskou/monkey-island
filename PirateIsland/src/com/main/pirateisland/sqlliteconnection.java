package com.main.pirateisland;

import java.sql.*;
import javax.swing.*;

public class sqlliteconnection {
	Connection conn=null;
	public static Connection dbConnector()
	{
		try{
			Class.forName("org.sqlite.JDBC");	
			Connection conn = DriverManager.getConnection("jdbc:sqlite:data/newdata.db");
			JOptionPane.showMessageDialog(null, "ok succesfull conn");
			return conn;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
	}
}
