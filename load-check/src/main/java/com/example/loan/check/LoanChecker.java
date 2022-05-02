package com.example.loan.check;

import com.example.loan.model.Loan;
import com.example.loan.model.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Consumer;

public class LoanChecker implements Consumer<Loan> {

    public static final Logger log = LoggerFactory.getLogger(LoanChecker.class);
    private static final Long MAX_AMOUNT = 10000L;

    private StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void accept(Loan loan) {
        log.info("{} {} for ${} for {}", loan.getStatus(), loan.getUuid(), loan.getAmount(), loan.getName());

        if (loan.getAmount() > MAX_AMOUNT) {
            loan.setStatus(Statuses.DECLINED.name());
            streamBridge.send("loan-declined", message(loan));
        } else {
            loan.setStatus(Statuses.APPROVED.name());
            streamBridge.send("loan-approved", message(loan));
        }

        log.info("{} {} for ${} for {}", loan.getStatus(), loan.getUuid(), loan.getAmount(), loan.getName());

    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }
    
}
