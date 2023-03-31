package dk.kea.webshopdemo.repository;

import dk.kea.webshopdemo.model.Product;
import dk.kea.webshopdemo.utility.ConnectionManager;
import dk.kea.webshopdemo.utility.StatementManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductRepository
	{
		// database properties injectes med Value
		//private final static	String db_URL="jdbc:mysql://mglinux.mysql.database.azure.com:3306/webshopdemo?useSSL=true";
		//private final String uid="Hermes";
		//private final String pwd="Pegasus2606";
		@Value("${spring.datasource.url}")
		private String DB_URL;
		@Value("${spring.datasource.username}")
		private String UID;
		@Value("${spring.datasource.password}")
		private String PWD;
		public  List<Product> getAll(){
			List<Product> productList=new ArrayList<>();
			try {
				Connection connection= ConnectionManager.getConnection(DB_URL, UID, PWD);
				Statement statement=connection.createStatement();
				final String SQL_QUERY="SELECT * FROM webshopdemo.products";
				ResultSet resultSet=statement.executeQuery(SQL_QUERY);
				while (resultSet.next()){
					int id=resultSet.getInt(1);
					String name=resultSet.getString(2);
					double price=resultSet.getDouble(3);
					Product product=new Product(id,name,price);
					productList.add(product);
					//System.out.println(product);
				}
			}catch (SQLException e){
				System.out.println("could not query database");
				e.printStackTrace();
			}
			return productList;
		}


		public void addProduct(Product newProduct)
			{
				// connect to db:
				try {
					//Connection connection= ConnectionManager.getConnection(DB_URL, UID, PWD);
					final String createQuery="INSERT INTO products(name,price) VALUES (?,?)";
					PreparedStatement preparedStatement= StatementManager.getPreparedStatement(DB_URL,UID,PWD,createQuery);

					//sæt atributter
					preparedStatement.setString(1,newProduct.getName());
					preparedStatement.setDouble(2,newProduct.getPrice());

					//execute statement
					preparedStatement.executeUpdate();
				}catch(SQLException e){
					System.out.println("could not create new product");
					e.printStackTrace();
				}

			}

			public void updateProduct(Product product){
			// connect to DB
				try {
				//	Connection connection=ConnectionManager.getConnection(DB_URL, UID, PWD);
					//SQL Statement
					final String UPDATE_QUERY=" UPDATE products SET name= ?, price =? WHERE id=?";
					// Prepared Statement
					PreparedStatement preparedStatement=StatementManager.getPreparedStatement(DB_URL,UID,PWD,UPDATE_QUERY);
					// set parameters
					String name=product.getName();
					double price= product.getPrice();
					int id=product.getId();
					preparedStatement.setInt(3,id);
					preparedStatement.setDouble(2,price);
					preparedStatement.setString(1,name);
					//execute statement
					preparedStatement.executeUpdate();

				}catch(SQLException e){
					System.out.println("could not update product");
					e.printStackTrace();
				}
			}

		public Product findProductByID(int updateId)
			{
				// SQL-Statement
				final String FIND_QUERY="SELECT * FROM products WHERE id=?";
				Product product=new Product();
				product.setId(updateId);
				try {
					//DB connection
					//Connection connection=ConnectionManager.getConnection(DB_URL, UID, PWD);
					// prepared Statement
					PreparedStatement preparedStatement=StatementManager.getPreparedStatement(DB_URL,UID,PWD,FIND_QUERY);
					//set parameters
					preparedStatement.setInt(1,updateId);

					//execute statement
					ResultSet resultSet=preparedStatement.executeQuery();
					//få produkt ud af resultatet
					resultSet.next();
					String name=resultSet.getString(2);
					double price=resultSet.getDouble(3);
					product.setName(name);
					product.setPrice(price);


				}catch(SQLException e){
					System.out.println("could not find product");
					e.printStackTrace();
				}


				//return product

				return product;
			}
			public void deleteById(int id){
			//sql
			final String DELETE_QUERY="DELETE FROM products WHERE id=?";
			//connect
				try {


					//Connection connection = ConnectionManager.getConnection(DB_URL, UID, PWD);

					//prepared statemenets
					PreparedStatement preparedStatement = StatementManager.getPreparedStatement(DB_URL,UID,PWD,DELETE_QUERY);
					//set param
					preparedStatement.setInt(1,id);
					//execute
					preparedStatement.executeUpdate();

				}catch (SQLException e){
					System.out.println(" could not delete product");
					e.printStackTrace();
				}

			//
			}
	}
