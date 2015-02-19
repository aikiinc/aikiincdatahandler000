package com.aikiinc.util.jdbc;

/**
 * Base JDBC utility exception
 * 
 * @Copyright (C) Aiki Innovations Inc 2015-2015 - http://www.aikiinc.com
 * @Author: Author: Philip Jahmani Chauvet. 
 * @Dated: Feb 13, 2015
 */
public class JDBCUtilException extends Exception {
    private static final long serialVersionUID = -6832558698829667502L;

    public JDBCUtilException() {
	super();
    }

    public JDBCUtilException(String message) {
	super(message);
    }

    public JDBCUtilException(Throwable cause) {
	super(cause);
    }

    public JDBCUtilException(String message, Throwable cause) {
	super(message, cause);
    }

}
