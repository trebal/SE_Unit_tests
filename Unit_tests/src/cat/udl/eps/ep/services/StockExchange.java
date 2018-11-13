package cat.udl.eps.ep.services;

import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;

/**
 * This interface will be implemented by the StockExchanger service.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public interface StockExchange {
    Money value(Ticket ticket) throws TicketDoesNotExistException;
}