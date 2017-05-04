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
public class ItemDAO {
private static final String TABLE = "FOOD_ITEM";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
		
	public void create(Item item) {
		
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + TABLE + " (NAME, DESCRIPTION, COST, ICON) VALUES (?, ?, ?, ?)", new String[]{"ID"});
	                
	               
	                stmt.setString(1, item.getName());
	                stmt.setString(2, item.getDescription());
	                stmt.setDouble(3, item.getCost());
	                stmt.setString(4, item.getIcon());
	               
	
	                return stmt;
	            }
        }, keyHolder);
		
		item.setId(keyHolder.getKey().longValue());
	}
	
	public void update(Item item) {
		
		
		jdbcTemplate.update(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement stmt = connection.prepareStatement("UPDATE " + TABLE + " SET NAME=?, DESCRIPTION=?, COST=?, ICON=? WHERE ID=?");
	                
	                stmt.setString(1, item.getName());
	                stmt.setString(2, item.getDescription());
	                stmt.setDouble(3, item.getCost());
	                stmt.setString(4, item.getIcon());
	                stmt.setLong(5,  item.getId());
	      
	                
	                
	
	                return stmt;
	            }
        });		
	}
	public void delete(Item item) {
	
		
		jdbcTemplate.update(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement stmt = connection.prepareStatement("DELETE FROM " + TABLE + " WHERE ID=?");
	                
	                
	                stmt.setLong(1, item.getId());
	
	                return stmt;
	            }
        });
	}
	
	
	public Item find(long id) {
		List<Item> list = jdbcTemplate.query(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement stmt = connection.prepareStatement("SELECT ID, NAME, DESCRIPTION, COST, ICON FROM " + TABLE + " WHERE ID = ?");
	                
	                stmt.setLong(1, id);
	
	                return stmt;
	            }
			}
		, parser());
		
		return (list != null && list.size() >= 1) ? list.get(0) : null;
	}

	public List<Item> findAll() {
		List<Item> list = jdbcTemplate.query(
			new PreparedStatementCreator()  {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                return connection.prepareStatement("SELECT ID, NAME, DESCRIPTION, COST, ICON FROM " + TABLE);
	            }
			}
		, parser());
		
		return list;
	}
	
	private static RowMapper<Item> parser() {
		return new RowMapper<Item>() {
			@Override
			public Item mapRow(ResultSet rs, int rowId) throws SQLException, DataAccessException {
				Item item = new Item();
				
				item.setId(rs.getLong("ID"));
				item.setName(rs.getString("NAME"));
				item.setDescription(rs.getString("DESCRIPTION"));
				item.setCost(rs.getDouble("COST"));
				item.setIcon(rs.getString("ICON"));
				
				
				return item;
			}
		};
	}
	
}
