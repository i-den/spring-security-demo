package com.denchevgod.security.validation;

public class EmailExistsException extends Throwable {

    public EmailExistsException(String msg) {
        super(msg);
    }
}
