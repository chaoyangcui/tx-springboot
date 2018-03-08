package com.tx.txspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Eric
 * @since  2018/2/28 13:21
 * Description
 */
@Service
public class EmployeeService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<Map<String, Object>> employee() {
        List<Map<String, Object>> employees = jdbcTemplate.queryForList("SELECT * FROM employees LIMIT 2;");
        // System.out.println(employees);
        return employees;
    }

}
