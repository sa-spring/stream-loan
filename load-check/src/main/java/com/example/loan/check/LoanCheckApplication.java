package com.example.loan.check;

import com.example.loan.model.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class LoanCheckApplication {

    private static final Logger log = LoggerFactory.getLogger(LoanCheckApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LoanCheckApplication.class, args);
        log.info("The Loancheck Application has started...");
    }

    @Bean
    public Consumer<Loan> checkLoan() {
        return new LoanChecker();
    }
}
