package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.services.MoneyExchange;
import cat.udl.eps.ep.services.RatioDoesNotExistException;
import cat.udl.eps.ep.services.StockExchange;
import cat.udl.eps.ep.services.TicketDoesNotExistException;

/**
 * This interface is the one that all kind of investments will implement. It uses two services: MoneyExchange
 * and StockExchange.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public interface Investment {
    Money evaluate(Currency currencyTo,
                   MoneyExchange moneyEx,
                   StockExchange stockEx)
            throws EvaluationException, RatioDoesNotExistException, TicketDoesNotExistException;
}