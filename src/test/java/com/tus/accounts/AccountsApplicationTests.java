package com.tus.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest  // This annotation tells Spring Boot to load the application context for the test
public class AccountsApplicationTests {

	@Test
    public void mainMethodShouldRun() {
        // Here we're invoking the main method of AccountsApplication
        // This will ensure that SpringApplication.run() is executed, which covers the main method.
        AccountsApplication.main(new String[]{}); // This explicitly calls the main method
    }
}
