package ru.vanilock.bankapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.vanilock.bankapi.controller.UserOperationController;
import ru.vanilock.bankapi.exceptions.UserNotFoundException;

import java.math.BigDecimal;

@SpringBootApplication
public class BankApiApplication {

	private static final Logger logger = LoggerFactory.getLogger(BankApiApplication.class);

	public static void main(String[] args) throws UserNotFoundException {
		ConfigurableApplicationContext app = SpringApplication.run(BankApiApplication.class, args);

		UserOperationController userOperationController =
				(UserOperationController)app.getBean(UserOperationController.class);
		logger.info(userOperationController.getBalance(1L).toString());
		logger.info(userOperationController.takeMoney(1L, new BigDecimal(1000)).toString());
		logger.info(userOperationController.putMoney(1l, new BigDecimal(1000)).toString());


	}
}
