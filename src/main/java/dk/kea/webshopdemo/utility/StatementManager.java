package dk.kea.webshopdemo.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementManager
	{
		//PreparedStatement preparedStatement;
		public static PreparedStatement getPreparedStatement(String db_url,String uid, String pwd,String query)throws SQLException
			{

			PreparedStatement preparedStatement=ConnectionManager.getConnection(db_url,uid,pwd).prepareStatement(query);
			return preparedStatement;
		}
	}
