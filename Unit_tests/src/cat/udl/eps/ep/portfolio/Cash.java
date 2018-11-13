package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.services.MoneyExchange;
import cat.udl.eps.ep.services.RatioDoesNotExistException;
import cat.udl.eps.ep.services.StockExchange;

import java.math.BigDecimal;

/**
 * This class describes a Cash amount as an investment.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class Cash implements Investment {
    /**
     * The amount of Money corresponding to this Cash investment.
     */
    public Money money;

    /**
     * Constructor of the class.
     *
     * @param money The amount of Money.
     */
    public Cash(Money money) {
        this.money = money;
    }

    /**
     * Returns the amount of Money into the current currency, or the desired one, using the MoneyExchanger to know the
     * current value in the market.
     *
     * @param currencyTo The currency in what we want to get the value.
     * @param moneyEx    The MoneyExchanger service.
     * @param stockEx    The StockExchanger service, not used in this class.
     * @return The amount of Money.
     * @throws EvaluationException Throws this exception if any error happens with the MoneyExchanger service.
     */
    @Override
    public Money evaluate(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {
        try {
            // Return value into the same currency
            if (money.getCurrency().equals(currencyTo)) {
                return this.money;
            }
            // Return value into another currency
            else {
                BigDecimal ratio = moneyEx.exchangeRatio(money.getCurrency(), currencyTo);

                return this.money.change(ratio, currencyTo);
            }
        } catch (RatioDoesNotExistException e) {
            throw new EvaluationException("The ratio between currencies does not exist");
        }
    }
}