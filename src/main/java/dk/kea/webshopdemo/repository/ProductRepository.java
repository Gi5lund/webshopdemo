package dk.kea.webshopdemo.repository;

import dk.kea.webshopdemo.model.Product;
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
		//private final String uid="root";
		//private final String pwd="Pegasus2606";
		@Value("${spring.datasource.url}")
		private String db_URL;
		@Value("${spring.datasource.username}")
		private String uid;
		@Value("${spring.datasource.password}")
		private String pwd;
		public  List<Product> getAll(){
			List<Product> productList=new ArrayList<>();
			try {
				Connection connection= DriverManager.getConnection(db_URL,uid,pwd);
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
					Connection connection= DriverManager.getConnection(db_URL,uid,pwd);
					final String createQuery="INSERT INTO products(name,price) VALUES (?,?)";
					PreparedStatement preparedStatement=connection.prepareStatement(createQuery);

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
					Connection connection=DriverManager.getConnection(db_URL,uid,pwd);
					//SQL Statement
					final String UPDATE_QUERY=" UPDATE products SET name= ?, price =? WHERE id=?";
					// Prepared Statement
					PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_QUERY);
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
					Connection connection=DriverManager.getConnection(db_URL,uid,pwd);
					// prepared Statement
					PreparedStatement preparedStatement=connection.prepareStatement(FIND_QUERY);
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


					Connection connection = DriverManager.getConnection(db_URL, uid, pwd);

					//prepared statemenets
					PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
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
