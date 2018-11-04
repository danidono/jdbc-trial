package sk.didi.jdbctemp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import sk.didi.jdbctemp.dao.CustomerDao;
import sk.didi.jdbctemp.dto.Customer;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class JdbctempApplication implements CommandLineRunner {

    @Autowired
    CustomerDao customer;

    public static void main(String[] args) {
        SpringApplication.run(JdbctempApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        customer.insertIntoTable(5, "Tomas", "Chlap");
//        log.info("New Customer inserted");
        List<Customer> list = customer.filter("To");
        log.info(list.toString());

        List<Customer> listLength = customer.filterLastNameLength(8);
        log.info(listLength.stream().map(s -> s.getLastName()).collect(Collectors.toList()).toString());

        try{
            Customer customer3 = customer.filterbyFirstName("Lala");
            log.info("Found a custome by his/her last name. : " + customer3.getLastName());
        } catch (EmptyResultDataAccessException e){
            log.error("Couldnt find the person by its first_name");

        }



        Customer customer2= customer.findByName("Lara");
        log.info(customer2.toString());

//        List<Object[]> listCustomers = Arrays.asList("John Berry", "Lara Craft", "Tom Jones")
//                                                .stream()
//                                                .map(name->name.split(" "))
//                                                .collect(Collectors.toList());
//        listCustomers.forEach(name->log.info("Splitting name for customer {} {}", name[0], name[1]));



//        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name,last_name) VALUES(?,?)", listCustomers);
//        log.info("Querying for the customer John");
//        jdbcTemplate.query("SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[]{"Lara"},
//                            (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
//                            ).forEach(customer -> log.info(customer.toString()));
    }
}
