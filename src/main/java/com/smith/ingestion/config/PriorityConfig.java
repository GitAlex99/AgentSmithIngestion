package com.smith.ingestion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PriorityConfig {

    private final HashMap<String, String> priorityMap = new HashMap<>();

    private final AtomicInteger counterLogHigh = new AtomicInteger(0);

    private final AtomicInteger counterLogMedium = new AtomicInteger(0);

    private final AtomicInteger counterPaymentHigh = new AtomicInteger(0);

    public PriorityConfig(@Value("${event.priority.user.login}") String userLogin,
                          @Value("${event.priority.user.logout}") String userLogout,
                          @Value("${event.priority.user.failed.login}") String userFailedLogin,
                          @Value("${event.priority.user.multiple.failed.login}") String userMultipleFailedLogin,
                          @Value("${event.priority.user.unauthorized}") String userUnauthorized,
                          @Value("${event.priority.payment.success}") String paymentSuccess,
                          @Value("${event.priority.payment.failed}") String paymentFailed,
                          @Value("${event.priority.order.placed}") String orderPlaced,
                          @Value("${event.priority.order.cancelled}") String orderCancelled){

        priorityMap.put("USER_LOGIN", userLogin);
        priorityMap.put("USER_LOGOUT", userLogout);
        priorityMap.put("USER_FAILED_LOGIN", userFailedLogin);
        priorityMap.put("USER_MULTIPLE_FAILED_LOGIN", userMultipleFailedLogin);
        priorityMap.put("USER_UNAUTHORIZED", userUnauthorized);

        priorityMap.put("PAYMENT_SUCCESS", paymentSuccess);
        priorityMap.put("PAYMENT_FAILED", paymentFailed);

        priorityMap.put("ORDER_PLACED", orderPlaced);
        priorityMap.put("ORDER_CANCELLED", orderCancelled);
        
    }

    public String getPriority(String type){
        return priorityMap.get(type);
    }

    public int calculatePartitionHighLog(){
        return counterLogHigh.getAndIncrement() % 3;
    }
    public int calculatePartitionMediumLog(){
        return counterLogMedium.getAndIncrement() % 2 + 3;
    }
    public int calculatePartitionHighPayment(){
        return counterPaymentHigh.getAndIncrement() % 2;
    }
    public int calculatePartitionHighOrder(){
        return counterPaymentHigh.getAndIncrement() % 2;
    }
}
