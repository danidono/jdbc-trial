package sk.didi.jdbctemp.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import sk.didi.jdbctemp.dto.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CustomerDao {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public void insertIntoTable(Integer id, String firstName, String lastName){
        String query = "INSERT INTO customers VALUES (:id, :firstName, :lastName)";
        Map map = new HashMap();
        map.put("id", id);
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        jdbcTemplate.update(query, map);
    }

    public List<Customer> filter(String syllable){
        String query = "SELECT * FROM customers WHERE first_name RLIKE'(^" + syllable + ".*)'";
        MapSqlParameterSource src = new MapSqlParameterSource();
        List<Customer> list = jdbcTemplate.query(query, src, new BeanPropertyRowMapper<>(Customer.class));
            if(list.isEmpty()) {
                log.warn("No customer found with name staring: {}", syllable );
           }
        return list;
    }

    public Customer filterbyFirstName(String lastName){
        String query = "SELECT * FROM customers WHERE first_name = :lastName";
        MapSqlParameterSource src = new MapSqlParameterSource();
        src.addValue("lastName", lastName);
        Customer customer = jdbcTemplate.queryForObject(query, src, new BeanPropertyRowMapper<>(Customer.class));
        return customer;
    }

    public List<Customer> filterLastNameLength(int num){
        String query = "SELECT * FROM customers WHERE LENGTH(last_name)=" + num;
        MapSqlParameterSource src = new MapSqlParameterSource();
        List<Customer> list = jdbcTemplate.query(query, src, new BeanPropertyRowMapper<>(Customer.class));
        if(list.isEmpty()) {
            log.warn("No customer found with last name, that contains of {} characters.", num );
        }
        return list;
    }

    public Customer findByName(String firstName){
        String query = "SELECT first_name, id, last_name FROM customers WHERE first_name = :firstName";
        MapSqlParameterSource src = new MapSqlParameterSource();
        src.addValue("firstName", firstName);
        Customer customer = jdbcTemplate.queryForObject(query, src,  new BeanPropertyRowMapper<>(Customer.class));
        log.info(customer.toString());
        return new Customer(customer.getId(), customer.getFirstName(), customer.getLastName());
    }



}
