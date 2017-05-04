package io.github.michellemc96;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderDAO {

	private static final String TABLE = "FOOD_ORDER";
	private static final String TABLE_ITEMS = "FOOD_ORDER_ITEMS";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private ItemDAO itemService;
	
	public void create(Order order) {
				
		KeyHolder orderId = new GeneratedKeyHolder();
		jdbcTemplate.update(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + TABLE + " (NAME, ADDRESS1, ADDRESS2, CITY, STATE, TEL, COST, STATUS, PAID, EMAIL, PAYMENT_TYPE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new String[]{"ID"});
	                   
	                stmt.setString(1, order.getName());
	                stmt.setString(2, order.getAddress1());
	                stmt.setString(3, order.getAddress2());
	                stmt.setString(4, order.getCity());
	                stmt.setString(5, order.getState());
	                stmt.setString(6, order.getTel());
	                stmt.setDouble(7, order.getCost());
	                stmt.setString(8, order.getStatus());
	                stmt.setBoolean(9, order.getPaid());
	                stmt.setString(10, order.getEmail());
	                stmt.setString(11, order.getPayment());	               
	
	                return stmt;
	            }
	            
        }, orderId);
		
		order.setId(orderId.getKey().longValue());
		
		System.err.println("order_id="+order.getId());
		
		updateOrderItems(order);
	}
	
	public void update(Order order) {		
		jdbcTemplate.update(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement stmt = connection.prepareStatement("UPDATE " + TABLE + " SET NAME=?, ADDRESS1=?, ADDRESS2=?, CITY=?, STATE=?, TEL=?, COST=?, STATUS=?, PAID=?, EMAIL=?, PAYMENT_TYPE=? WHERE ID=?");
	                	                
	                stmt.setString(1, order.getName());
	                stmt.setString(2, order.getAddress1());
	                stmt.setString(3, order.getAddress2());
	                stmt.setString(4, order.getCity());
	                stmt.setString(5, order.getState());
	                stmt.setString(6, order.getTel());
	                stmt.setDouble(7, order.getCost());
	                stmt.setString(8, order.getStatus());
	                stmt.setBoolean(9, order.getPaid());
	                stmt.setString(10, order.getEmail());
	                stmt.setString(11, order.getPayment());
	                stmt.setLong(12, order.getId());
	
	                return stmt;
	            }
        });		

		updateOrderItems(order);
	}	
	
	public void delete(Order order) {	
		jdbcTemplate.update(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement stmt = connection.prepareStatement("DELETE FROM " + TABLE + " WHERE ID=?");
	                	               
	                stmt.setLong(1, order.getId());
	
	                return stmt;
	            }
        });
	                
	    deleteOrderItems(order);
	}
	
	public Order find(long id) {
		List<Order> list = jdbcTemplate.query(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement stmt = connection.prepareStatement("SELECT ID, NAME, ADDRESS1, ADDRESS2, CITY, STATE, TEL, COST, STATUS, PAID, EMAIL, PAYMENT_TYPE FROM " + TABLE + " WHERE ID = ?");
	                
	                stmt.setLong(1, id);
	
	                return stmt;
	            }
			}
		, parser());
		
		return (list != null && list.size() >= 1) ? list.get(0) : null;
	}

	public List<Order> findAll() {
		List<Order> list = jdbcTemplate.query(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                return connection.prepareStatement("SELECT ID, NAME, ADDRESS1, ADDRESS2, CITY, STATE, TEL, COST, STATUS, PAID, EMAIL, PAYMENT_TYPE FROM " + TABLE);
	            }
			}
		, parser());
		
		return list;
	}
	
	public List<Order> findStatus(String[] statuses) {
		List<Order> list = jdbcTemplate.query(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	               return connection.prepareStatement("SELECT ID, NAME, ADDRESS1, ADDRESS2, CITY, STATE, TEL, COST, STATUS, PAID, EMAIL, PAYMENT_TYPE FROM " + TABLE + " WHERE STATUS IN ("+SQLUtil.toIn(statuses)+")");
	            }
	            
			}
		, parser());
		
		return list;
	}

	private List<OrderItem> findOrderItems(Order order) {
		List<OrderItem> list = jdbcTemplate.query(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement stmt = connection.prepareStatement("SELECT ID, ITEM_ID, QUANTITY FROM " + TABLE_ITEMS + " WHERE ORDER_ID = ?");
	                
	                stmt.setLong(1, order.getId());
	                
	                return stmt;
	            }
			}
		, orderItemsParser());
		
		return list;
	}
	
	private void deleteOrderItems(Order order) {
		jdbcTemplate.update(
				new PreparedStatementCreator()  {
		            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		                PreparedStatement stmt = connection.prepareStatement("DELETE FROM " + TABLE_ITEMS + " WHERE ORDER_ID=?");
		                		               
		                stmt.setLong(1, order.getId());
		
		                return stmt;
		            }
	        });
	}	
	
	private void updateOrderItems(Order order) {		
		deleteOrderItems(order);
				
		for (OrderItem item : order.getItems()) {			
			KeyHolder orderItemId = new GeneratedKeyHolder();
			jdbcTemplate.update(
					new PreparedStatementCreator()  {
			            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			                PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + TABLE_ITEMS + " (ORDER_ID, ITEM_ID, QUANTITY) VALUES (?, ?, ?)", new String[]{"ID"});
			                   
			                stmt.setLong(1, order.getId());
			                stmt.setLong(2, item.getItem().getId());
			                stmt.setLong(3, item.getQuantity());
			
			                return stmt;
			            }
		        }, orderItemId);
			
			item.setId(orderItemId.getKey().longValue());			
		}		
	}
	
	private RowMapper<Order> parser() {
		return new RowMapper<Order>() {
			@Override
			public Order mapRow(ResultSet rs, int rowId) throws SQLException, DataAccessException {
				Order order = new Order();
				
				order.setId(rs.getLong("ID"));
				order.setName(rs.getString("NAME"));
				order.setAddress1(rs.getString("ADDRESS1"));
				order.setAddress2(rs.getString("ADDRESS2"));
				order.setCity(rs.getString("CITY"));
				order.setState(rs.getString("STATE"));
				order.setTel(rs.getString("TEL"));
				order.setCost(rs.getDouble("COST"));
				order.setStatus(rs.getString("STATUS"));
				order.setPaid(rs.getBoolean("PAID"));
				order.setEmail(rs.getString("EMAIL"));
				order.setPayment(rs.getString("PAYMENT_TYPE"));
				
				order.setItems(findOrderItems(order));
				
				return order;
			}
		};
	}
	
	private RowMapper<OrderItem> orderItemsParser() {
		return new RowMapper<OrderItem>() {
			@Override
			public OrderItem mapRow(ResultSet rs, int rowId) throws SQLException, DataAccessException {
				OrderItem orderItem = new OrderItem();				
				orderItem.setId(rs.getLong("ID"));				
				long itemId = rs.getLong("ITEM_ID");
				orderItem.setItem(itemService.find(itemId));				
				orderItem.setQuantity(rs.getLong("QUANTITY"));
				return orderItem;
			}
		};
	}
	
}
