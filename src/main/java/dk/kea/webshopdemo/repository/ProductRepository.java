package dk.kea.webshopdemo.repository;

import dk.kea.webshopdemo.model.Product;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductRepository
	{
		// database propertie
		private final static	String db_URL="jdbs:mysql://localhost:3306/webshopdemo";
		private final String uid="root;";
		private final String pwd="Pegasus2606";

		public List<Product> getAll(){
			List<Product> productList=new ArrayList<>();
			try {
				Connection connection= DriverManager.getConnection(db_URL,uid,pwd);
				Statement statement=connection.createStatement();
				final String SQL_QUERY="SELECT * FROM products";
				ResultSet resultSet=statement.executeQuery(SQL_QUERY);
				while (resultSet.next()){
					int id=resultSet.getInt(1);
					String name=resultSet.getString(2);
					double price=resultSet.getDouble(3);
					Product product=new Product(id,name,price);
					productList.add(product);
					System.out.println(product);
				}
			}catch (SQLException e){
				System.out.println("could not query databse");
				e.printStackTrace();
			}
			return productList;
		}


	}
