package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.db.DB;
import com.example.entities.Product;

public class Main {
	public static void main(String[] args) throws SQLException {
		Connection conn = DB.getConnection();

		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery("select * from tb_product");

		while (rs.next()) {
			Product prod = instantiateProduct(rs);
			System.out.println(prod);
		}
	}

	private static Product instantiateProduct(ResultSet rs) throws SQLException {
		Product prod = new Product();
		prod.setId(rs.getLong("id"));
		prod.setDescription(rs.getString("description"));
		prod.setName(rs.getString("name"));
		prod.setImageUri(rs.getString("image_uri"));
		prod.setPrice(rs.getDouble("price"));
		return prod;
	}
}