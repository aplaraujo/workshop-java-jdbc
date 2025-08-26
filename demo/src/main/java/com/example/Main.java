package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.example.db.DB;
import com.example.entities.Order;
import com.example.entities.Product;
import com.example.entities.enums.OrderStatus;

public class Main {
	public static void main(String[] args) throws SQLException {
		Connection conn = DB.getConnection();

		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery("SELECT * FROM tb_order " +
				"INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id " +
				"INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id");

		// Coleção de pares chave / valor
		Map<Long, Order> map = new HashMap<>();
		Map<Long, Product> mapProd = new HashMap<>();

		while (rs.next()) {
			Long orderId = rs.getLong("order_id");
			if (map.get(orderId) == null) {
				Order order = instantiateOrder(rs);
				map.put(orderId, order);
			}

			Long productId = rs.getLong("product_id");
			if (mapProd.get(productId) == null) {
				Product prod = instantiateProduct(rs);
				mapProd.put(productId, prod);
			}
			// Associação dos produtos com os pedidos
			map.get(orderId).getProducts().add(mapProd.get(productId));
		}

		for(Long order:map.keySet()) {
			System.out.println(map.get(order));
			for(Product prod: map.get(order).getProducts()) {
				System.out.println(prod);
			}
			System.out.println();
		}
	}

	private static Product instantiateProduct(ResultSet rs) throws SQLException {
		Product prod = new Product();
		prod.setId(rs.getLong("product_id"));
		prod.setDescription(rs.getString("description"));
		prod.setName(rs.getString("name"));
		prod.setImageUri(rs.getString("image_uri"));
		prod.setPrice(rs.getDouble("price"));
		return prod;
	}

	private static Order instantiateOrder(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setId(rs.getLong("order_id"));
		order.setLatitude(rs.getDouble("latitude"));
		order.setLongitude(rs.getDouble("longitude"));
		order.setMoment(rs.getTimestamp("moment").toInstant());
		order.setStatus(OrderStatus.values()[rs.getInt("status")]);
		return order;
	}
}