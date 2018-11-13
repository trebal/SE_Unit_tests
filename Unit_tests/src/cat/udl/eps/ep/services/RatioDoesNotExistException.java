package cat.udl.eps.ep.services;

/**
 * This exception will be thrown by the MoneyExchanger if there is not available ratio between currencies.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class RatioDoesNotExistException extends Exception {
    private String message;

    public RatioDoesNotExistException(String message) {
        this.message = message;
    }
}