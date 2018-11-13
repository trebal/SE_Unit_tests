package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.services.MoneyExchange;
import cat.udl.eps.ep.services.RatioDoesNotExistException;
import cat.udl.eps.ep.services.StockExchange;
import cat.udl.eps.ep.services.TicketDoesNotExistException;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents an amount of investments.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class Portfolio implements Investment {
    /**
     * A list of investments, which can be FutureBuys, FutureSell, Cash and Stock.
     */
    public List<Investment> investments;

    /**
     * Constructor of the class.
     */
    public Portfolio() {
        investments = new LinkedList<>();
    }

    /**
     * Adds a new investment of any kind to the wallet.
     *
     * @param investment
     */
    public void addInvestment(Investment investment) {
        investments.add(investment);
    }

    /**
     * Calculates the total value of the wallet.
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
        Money total_value = new Money(new BigDecimal("0.00"), currencyTo);

        try {
            for (Investment current : this.investments) {
                Money value_current = current.evaluate(currencyTo, moneyEx, stockEx);
                total_value = total_value.add(value_current);
            }

            return total_value;
        } catch (RatioDoesNotExistException e) {
            throw new EvaluationException("The ratio between currencies does not exist");
        } catch (TicketDoesNotExistException e) {
            throw new EvaluationException("The ticket does not exist");
        }
    }
}