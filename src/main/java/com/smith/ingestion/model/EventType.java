package com.smith.ingestion.model;

import lombok.Data;

public enum EventType {
    USER_LOGIN,
    USER_LOGOUT,
    ORDER_PLACED,
    PAYMENT_FAILED
}
