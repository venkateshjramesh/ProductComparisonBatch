package helper;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import enums.QueryEnum;

public class Helper {

	private static Helper helper = null;

	private Helper(){ }


	public static Helper getInstance( ) {
		if(helper == null) {
			helper = new Helper();
		}
		return helper;
	}

	public void insertIntoCategory(String query, PreparedStatement preparedStmt , Connection conn,String category,java.sql.Date startDate) throws SQLException{
		 preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString (1, category);
	      preparedStmt.setDate   (2, startDate);	      
	      preparedStmt.execute();
	      preparedStmt = null;
	}
	
	public void insertIntoErrorRecords(String query, PreparedStatement preparedStmt , Connection conn,String records,java.sql.Date startDate) throws SQLException{
		 preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString (1, records);
	      preparedStmt.setDate   (2, startDate);	    
	      preparedStmt.execute();
	      preparedStmt = null;
	}

	public void deleteFromAvgPrice(String query, PreparedStatement preparedStmt , Connection conn) throws SQLException{
		preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		preparedStmt = null;
	}
	
	public void insertIntoSubCategory(String query, PreparedStatement preparedStmt , Connection conn,String subCategory,java.sql.Date startDate,Integer categoryId) throws SQLException{
		 preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString (1, subCategory);
	      preparedStmt.setDate   (2, startDate);
	      preparedStmt.setInt(3, categoryId);	      
	      preparedStmt.execute();
	      preparedStmt = null;
	}
	
	
	public void insertIntoProduct(String query, PreparedStatement preparedStmt , Connection conn,String id,String title,String store,Float price,Integer subCategoryId,java.sql.Date startDate) throws SQLException{
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, id);
			preparedStmt.setString(2, title);
			preparedStmt.setString(3, store);
			preparedStmt.setFloat(4, price);
			preparedStmt.setInt(5, subCategoryId);
			preparedStmt.setDate(6, startDate);
			preparedStmt.execute();
			preparedStmt = null;
	}
	
	public Integer getCategoryId(String query, PreparedStatement preparedStmt , Connection conn,String category) throws SQLException{
		 preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString(1, category);
	      ResultSet rs = preparedStmt.executeQuery();
	      preparedStmt = null;
		Integer categoryId = null;
	      while (rs.next() && categoryId == null) {
			  categoryId =  rs.getInt("category_id");
	      }
		rs = null;
		return categoryId;
	}
	
	public Integer getSubCategoryId(String query, PreparedStatement preparedStmt , Connection conn,String subCategory) throws SQLException{
		 preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString(1, subCategory);
	      ResultSet rs1 = preparedStmt.executeQuery();
	      preparedStmt = null;
		Integer subCategoryId = null;
	      while (rs1.next() && subCategoryId == null) {
	    	 // categoryId = rs1.getInt("category_id");
			  subCategoryId =  rs1.getInt("subcategory_id");
	      }
		rs1 = null;
		return subCategoryId;
	}
	
	public ResultSet getAveragePrice(String query, PreparedStatement preparedStmt , Connection conn) throws SQLException{
		 preparedStmt = conn.prepareStatement(query);
	      ResultSet rs = preparedStmt.executeQuery();
	      preparedStmt = null;
	      return rs;
	    
	}
	
	public void insertIntoAvgPrice(ResultSet rs, PreparedStatement preparedStmt , Connection conn) throws SQLException{
		while (rs.next()) {
	    	  //rs.getInt("category_id");
	    	 // rs.getString("store");
	    	 // rs.getFloat("avg_price");
	    	  
	    	  preparedStmt = conn.prepareStatement(QueryEnum.INSERT_INTO_AVG_PRICE.description);
		      preparedStmt.setInt (1, rs.getInt("category_id"));
		      preparedStmt.setString(2, rs.getString("store"));
		      preparedStmt.setFloat(3,  rs.getFloat("avg_price"));	
		      preparedStmt.execute();
		      //preparedStmt = null;
	      }
	}

	public void moveFile(File afile,File bfile){
		InputStream inStream = null;
		OutputStream outStream = null;

		try{

			/*File afile =new File("SampleData.csv");
			File bfile =new File("D:\\folderB\\SampleData.csv");*/

			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);

			byte[] buffer = new byte[1024];

			int length;
			//copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0){
				outStream.write(buffer, 0, length);
			}

			inStream.close();
			outStream.close();

			//delete the original file
			afile.delete();


		}catch(IOException e){
			e.printStackTrace();
		}
	}

	

}
