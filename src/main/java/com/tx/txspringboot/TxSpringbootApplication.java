package com.tx.txspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebConfig.class})
public class TxSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxSpringbootApplication.class, args);
	}

}
