package br.com.bancotestebackend.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class SessaoExpiradaException
        extends NegocioException
{

    public SessaoExpiradaException()
    {
        super();
    }

    public SessaoExpiradaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SessaoExpiradaException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SessaoExpiradaException(String message)
    {
        super(message);
    }

    public SessaoExpiradaException(Throwable cause)
    {
        super(cause);
    }

    @Override
    public String getMessage()
    {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage()
    {
        return super.getLocalizedMessage();
    }

    @Override
    public synchronized Throwable getCause()
    {
        return super.getCause();
    }

    @Override
    public synchronized Throwable initCause(Throwable cause)
    {
        return super.initCause(cause);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    @Override
    public void printStackTrace()
    {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s)
    {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s)
    {
        super.printStackTrace(s);
    }

    @Override
    public synchronized Throwable fillInStackTrace()
    {
        return super.fillInStackTrace();
    }

    @Override
    public StackTraceElement[] getStackTrace()
    {
        return super.getStackTrace();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace)
    {
        super.setStackTrace(stackTrace);
    }

}
