package com.tx.txspringboot;

import com.tx.txspringboot.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TxSpringbootApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	EmployeeService employeeService;

	@Test
	public void employee() {
		employeeService.employee();
	}

	@Test
	public void ws() {

	}
}
