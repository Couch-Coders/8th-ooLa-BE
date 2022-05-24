package com.couchcoding.oola.validation.error;

public class CustomFieldError {
    private String field;
    private Object value;
    private String reason;

    public CustomFieldError(String field, Object value, String reason) {
        this.field = field;
        this.value = value;
        this.reason = reason;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }




}
