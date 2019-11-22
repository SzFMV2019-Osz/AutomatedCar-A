package hu.oe.nik.szfmv.automatedcar.exceptions;


/**
 * Ütközést jelző exception.
 */
public class CrashException extends Exception {

    public CrashException(String message)
    {
        super(message);
    }
}
