package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;
import cat.udl.eps.ep.services.MoneyExchange;
import cat.udl.eps.ep.services.RatioDoesNotExistException;
import cat.udl.eps.ep.services.StockExchange;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * This class tests Cash.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class CashTest {
    private Cash cash;

    @Before
    public void initCash() {
        cash = new Cash(new Money(new BigDecimal("10.00"), new Currency("USD")));
    }

    @Test
    public void testEvaluate() throws EvaluationException {
        Currency currencyTo = new Currency("CHF");

        MoneyExchange moneyExchangeImpl = new MoneyExchange() {
            @Override
            public BigDecimal exchangeRatio(Currency from, Currency to) {
                return new BigDecimal("0.9");
            }
        };
        // StockExchange is never used
        StockExchange stockExchangeImpl = new StockExchange() {
            @Override
            public Money value(Ticket ticket) {
                return null;
            }
        };

        Money result = cash.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
        Money expected = new Money(new BigDecimal("9.00"), new Currency("CHF"));

        assertEquals(expected, result);
    }

    @Test
    public void testEvaluateWithSameCurrency() throws EvaluationException {
        Currency currencyTo = new Currency("USD");

        MoneyExchange moneyExchangeImpl = new MoneyExchange() {
            @Override
            public BigDecimal exchangeRatio(Currency from, Currency to) {
                return null;
            }
        };
        // StockExchange is never used
        StockExchange stockExchangeImpl = new StockExchange() {
            @Override
            public Money value(Ticket ticket) {
                return null;
            }
        };

        Money result = cash.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
        Money expected = new Money(new BigDecimal("10.00"), new Currency("USD"));

        assertEquals(expected, result);
    }

    @Test(expected = EvaluationException.class)
    public void testEvaluateExchangerHasNoRatio() throws EvaluationException {
        Currency currencyTo = new Currency("CHF");

        MoneyExchange moneyExchangeImpl = new MoneyExchange() {
            @Override
            public BigDecimal exchangeRatio(Currency from, Currency to) throws RatioDoesNotExistException {
                throw new RatioDoesNotExistException("Ratio does not exist");
            }
        };
        // StockExchange is never used
        StockExchange stockExchangeImpl = new StockExchange() {
            @Override
            public Money value(Ticket ticket) {
                return null;
            }
        };

        cash.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
    }
}