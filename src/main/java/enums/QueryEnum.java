package enums;

/**
 * This Enum holds the values of types of corridors available in a floor
 * 
 * @author Venkatesh R
 * @version 1.0
 * @since 2016-03-17
 */
public enum QueryEnum {

	CATEGORY_INSERT(1, "CATEGORY_INSERT", "INSERT IGNORE INTO `product_compare`.`category` ( `description`, `last_modified_date`)	 VALUES	(?,?)"),
	SUB_CATEGORY_INSERT(2, "SUB_CATEGORY_INSERT","INSERT IGNORE INTO `product_compare`.`sub_category` ( `description`, `last_modified_date`, `category_id`)	VALUES	(?,?,?)"),
	PRODUCT_INSERT(3, "PRODUCT_INSERT","INSERT INTO `product_compare`.`product` ( `product_id`, `description`, `store`, `price`, `subcategory_id`, `last_modified_date`)	 VALUES	(?,?,?,?,?,?)"),
	GET_CATEGORY_ID(4, "GET_CATEGORY_ID","SELECT `category_id`  FROM `product_compare`.`category` WHERE `description` = ? "),
	GET_SUB_CATEGORY_ID(5, "GET_SUB_CATEGORY_ID","SELECT subcategory_id from sub_category where description = ?"),
	INSERT_ERROR_RECORDS(6, "INSERT_ERROR_RECORDS","INSERT INTO `product_compare`.`error_records` ( `record`,`last_modified_date`)	 VALUES	(?,?)"),
	AVG_PRICE_QUERY(7, "AVG_PRICE_QUERY","select product.store,category.category_id,avg(product.price) as avg_price from product LEFT JOIN sub_category  ON product.subcategory_id = sub_category.subcategory_id LEFT JOIN category  ON sub_category.category_id = category.category_id group by product.store , category.category_id order by category.category_id,product.store"),
	INSERT_INTO_AVG_PRICE(8, "INSERT_INTO_AVG_PRICE","INSERT INTO `product_compare`.`price_aggregate` ( `category_id`,`store`,`avg_price`)	 VALUES	(?,?,?)"),
	DELETE_AVG_PRICE(9,"DELETE_AVG_PRICE","DELETE FROM `product_compare`.`price_aggregate`");
	
	private int code;
	private String name;
	public String description;

	private QueryEnum(int code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

}


