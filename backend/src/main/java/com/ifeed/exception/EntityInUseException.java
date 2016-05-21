package com.ifeed.exception;

/**
 * Created by ipascu on 21.05.2016.
 */
public class EntityInUseException extends Exception
{
    public EntityInUseException() {}

    public EntityInUseException(String message)
    {
        super(message);
    }
}
