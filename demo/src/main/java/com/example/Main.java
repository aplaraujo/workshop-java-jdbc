package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.db.DB;
import com.example.entities.Order;
import com.example.entities.Product;
import com.example.entities.enums.OrderStatus;

public class Main {
	public static void main(String[] args) throws SQLException {
		Connection conn = DB.getConnection();

		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery("select * from tb_order");

		while (rs.next()) {
			Order order = instantiateOrder(rs);
			System.out.println(order);
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

	private static Order instantiateOrder(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setId(rs.getLong("id"));
		order.setLatitude(rs.getDouble("latitude"));
		order.setLongitude(rs.getDouble("longitude"));
		order.setMoment(rs.getTimestamp("moment").toInstant());
		order.setStatus(OrderStatus.values()[rs.getInt("status")]);
		return order;
	}
}