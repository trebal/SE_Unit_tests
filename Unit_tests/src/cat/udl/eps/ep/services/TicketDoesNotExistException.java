package cat.udl.eps.ep.services;

/**
 * This exception will be thrown by the StockExchanger if the Ticket does not exist.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class TicketDoesNotExistException extends Exception {
    private String message;

    public TicketDoesNotExistException(String message) {
        this.message = message;
    }
}