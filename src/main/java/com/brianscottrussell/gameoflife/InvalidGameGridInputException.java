package com.brianscottrussell.gameoflife;

/*
 * Copyright (c) 2016 Brian Scott Russell  All Rights Reserved.
 * The source code is owned by Brian Scott Russell and is protected by copyright
 * laws and international copyright treaties, as well as other intellectual
 * property laws and treaties.
 */

/**
 * Indicates that a GameGrid input is invalid
 *
 * @author brussell
 */
public class InvalidGameGridInputException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InvalidGameGridInputException(String message) {
        super(message);
    }
}
