package main;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;

import enums.QueryEnum;
import helper.Helper;

public class ImportCsv {

	public static void main(String[] args) throws IOException, SQLException {
		Properties prop = new Properties();
		InputStream input = new FileInputStream("config.properties");
		// load a properties file
		prop.load(input);

		final String DB_URL = prop.getProperty("dbUrl");

		final String USER = prop.getProperty("userName");
		final String PASS = prop.getProperty("password");

		String csvFile = prop.getProperty("csvFile");
		BufferedReader br = null;
		String line = null;
		String cvsSplitBy = prop.getProperty("csvDelimiter");
		//Helper helper = new Helper();
		Helper helper = Helper.getInstance( );

		Connection conn = null;


		PreparedStatement preparedStmt = null;

		Integer categoryId = null;
		Integer subCategoryId = null;

		try {

			Class.forName(prop.getProperty("jdbcDriver"));

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// create a sql date object so we can use it in our INSERT statement
			Calendar calendar = Calendar.getInstance();
			java.sql.Date startDate = new java.sql.Date(calendar.getTime()
					.getTime());

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] productDetails = line.split(cvsSplitBy);

				// check if this is the header line
				if (productDetails[3].trim().equalsIgnoreCase("price"))
					continue;

				try {
					String id = productDetails[0];
					String title = productDetails[1];
					String store = productDetails[2];
					Float price = Float.parseFloat(productDetails[3]);
					String category = productDetails[4];
					String subCategory = productDetails[5];
				System.out.println(id);
					helper.insertIntoCategory(
							QueryEnum.CATEGORY_INSERT.description,
							preparedStmt, conn, category, startDate);
					categoryId = helper.getCategoryId(
							QueryEnum.GET_CATEGORY_ID.description,
							preparedStmt, conn, category);
					helper.insertIntoSubCategory(
							QueryEnum.SUB_CATEGORY_INSERT.description,
							preparedStmt, conn, subCategory, startDate,
							categoryId);
					subCategoryId = helper.getSubCategoryId(
							QueryEnum.GET_SUB_CATEGORY_ID.description,
							preparedStmt, conn, subCategory);
					helper.insertIntoProduct(
							QueryEnum.PRODUCT_INSERT.description, preparedStmt,
							conn, id, title, store, price, subCategoryId,
							startDate);



				} catch (ArrayIndexOutOfBoundsException e) {

					helper.insertIntoErrorRecords(
							QueryEnum.INSERT_ERROR_RECORDS.description,
							preparedStmt, conn, line, startDate);
				} catch (Exception e) {
					e.printStackTrace();
					helper.insertIntoErrorRecords(
							QueryEnum.INSERT_ERROR_RECORDS.description,
							preparedStmt, conn, line, startDate);
				}

			}


			//calculate the aggregate and populate
			helper.deleteFromAvgPrice(QueryEnum.DELETE_AVG_PRICE.description,preparedStmt,conn);
			ResultSet rs = helper.getAveragePrice(QueryEnum.AVG_PRICE_QUERY.description, preparedStmt , conn);
			helper.insertIntoAvgPrice(rs, preparedStmt , conn) ;


		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStmt != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			helper.moveFile(new File(csvFile),new File(prop.getProperty("destFile")));

		}
		System.out.println("Completed!");
	}
}
