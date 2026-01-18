package com.smith.ingestion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PriorityConfig {

    private final HashMap<String, String> priorityMap = new HashMap<>();

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
}
