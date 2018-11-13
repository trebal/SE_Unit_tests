package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;
import cat.udl.eps.ep.services.MoneyExchange;
import cat.udl.eps.ep.services.RatioDoesNotExistException;
import cat.udl.eps.ep.services.StockExchange;
import cat.udl.eps.ep.services.TicketDoesNotExistException;

/**
 * This class describes a Future buy of actions. It consists in the current value of the stock (pricePerShare
 * multiplied by the number of tickets) in the market minus the agreed value of the stock.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class FutureBuy implements Investment {
    /**
     * The company ticket corresponding to the investment.
     */
    private Ticket ticket;
    /**
     * The amount of tickets.
     */
    private int numShares;
    /**
     * The value (Money) of each ticket.
     */
    private Money pricePerShare;

    /**
     * Constructor of the class.
     *
     * @param ticket        The ticket of the investment.
     * @param numShares     The amount of tickets.
     * @param pricePerShare The value of each ticket.
     */
    public FutureBuy(Ticket ticket, int numShares, Money pricePerShare) {
        this.ticket = ticket;
        this.numShares = numShares;
        this.pricePerShare = pricePerShare;
    }

    /**
     * Calculates the value, or benefit, from the current stock-market value minus the actual cost.
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
            Money ticket_value = stockEx.value(this.ticket);
            Money pricePerShare_normalized;
            // Individual value per ticket
            if (pricePerShare.getCurrency().equals(currencyTo)) {
                pricePerShare_normalized = this.pricePerShare;
            } else {
                pricePerShare_normalized = this.pricePerShare.change(moneyEx.exchangeRatio(
                        pricePerShare.getCurrency(), currencyTo), currencyTo);
            }
            // Stock value
            if (!ticket_value.getCurrency().equals(currencyTo)) {
                ticket_value = ticket_value.change(moneyEx.exchangeRatio(
                        ticket_value.getCurrency(), currencyTo), currencyTo);
            }

            value = ticket_value.multiply(numShares).subtract(pricePerShare_normalized.multiply(numShares));

            return value;
        } catch (RatioDoesNotExistException e) {
            throw new EvaluationException("The ratio between currencies does not exist");
        } catch (TicketDoesNotExistException e) {
            throw new EvaluationException("The ticket with name [" + ticket + "] does not exist");
        }
    }
}