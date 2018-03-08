package com.tx.txspringboot.controller;

import com.tx.txspringboot.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Eric
 * @since  2018/2/28 13:29
 * Description
 */
@Controller
@RequestMapping("/api/")
public class EmployeeController {
    private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private static org.apache.logging.log4j.Logger logger2 = LogManager.getLogger();

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "employees", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Map<String, Object>> employee() {
        List<Map<String, Object>> employees = employeeService.employee();
        logger.info("employees: {}", employees);
        logger2.info("Log4j2 Log Content");
        return employees;
    }

}
