package com.aikiinc.dbunit.test.base;

/**
 * Base DBUnit base test exception
 * 
 * @Copyright (C) Aiki Innovations Inc 2013-2015 - http://www.aikiinc.com
 * @Author Author: Philip Jahmani Chauvet.
 * @Dated Sep 23, 2013
 */
public class DbUnitBaseTestException extends Exception {
    private static final long serialVersionUID = 1L;

    public DbUnitBaseTestException() {
	super();
    }

    public DbUnitBaseTestException(String message) {
	super(message);
    }

    public DbUnitBaseTestException(Throwable cause) {
	super(cause);
    }

}
