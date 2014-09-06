package me.currencies.model;


/**
 * Exception indicating that the currency value was invalid
 */
public class InvalidCurrencyException extends IllegalArgumentException {
    /**
     * Constructs a <tt>InvalidCurrencyException</tt> with no detail message.
     */
    public InvalidCurrencyException() {
        super();
    }

    /**
     * Constructs a <tt>InvalidCurrencyException</tt> with the specified detail
     * message.
     *
     * @param message the detail message
     */
    public InvalidCurrencyException(String message) {
        super(message);
    }
}
