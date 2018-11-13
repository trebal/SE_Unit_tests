package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;
import cat.udl.eps.ep.services.MoneyExchange;
import cat.udl.eps.ep.services.RatioDoesNotExistException;
import cat.udl.eps.ep.services.StockExchange;
import cat.udl.eps.ep.services.TicketDoesNotExistException;

/**
 * This class describes a stock (amount of tickets and price per ticket).
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class Stock implements Investment {
    /**
     * The company ticket corresponding to the investment.
     */
    private Ticket ticket;
    /**
     * The amount of tickets.
     */
    private int numShares;

    /**
     * Constructor of the class.
     *
     * @param ticket    The ticket of the company.
     * @param numShares The amount of tickets.
     */
    public Stock(Ticket ticket, int numShares) {
        this.ticket = ticket;
        this.numShares = numShares;
    }

    /**
     * Calculates the total amount of Money worth an stock.
     *
     * @param currencyTo The currency in what we want to get the value.
     * @param moneyEx    The MoneyExchanger service.
     * @param stockEx    The StockExchanger service.
     * @return The resulting amount of Money.
     * @throws EvaluationException Throws this exception if any error happens with the MoneyExchanger or
     *                             StockExchanger service.
     */
    @Override
    public Money evaluate(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {
        Money value;

        try {
            value = stockEx.value(this.ticket).multiply(numShares);
            // Return value into the same currency
            if (value.getCurrency().equals(currencyTo)) {
                return value;
            }
            // Return value into another currency
            else {
                return value.change(moneyEx.exchangeRatio(value.getCurrency(), currencyTo), currencyTo);
            }
        } catch (RatioDoesNotExistException e) {
            throw new EvaluationException("The ratio between currencies does not exist");
        } catch (TicketDoesNotExistException e) {
            throw new EvaluationException("The ticket with name [" + ticket + "] does not exist");
        }
    }
}