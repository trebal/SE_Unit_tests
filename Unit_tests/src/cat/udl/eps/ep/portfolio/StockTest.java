package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;
import cat.udl.eps.ep.services.MoneyExchange;
import cat.udl.eps.ep.services.RatioDoesNotExistException;
import cat.udl.eps.ep.services.StockExchange;
import cat.udl.eps.ep.services.TicketDoesNotExistException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * This class tests Stock.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class StockTest
{
    private Stock stock;
    @Before
    public void initStock()
    {
        stock = new Stock(new Ticket("BTC"), 10);
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

        StockExchange stockExchangeImpl = new StockExchange() {
            @Override
            public Money value(Ticket ticket) {
                return new Money(new BigDecimal("10.00"), new Currency("USD"));
            }
        };

        Money result = stock.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
        Money expected = new Money(new BigDecimal("90.00"), new Currency("CHF"));

        assertEquals(expected, result);
    }

    @Test
    public void testEvaluateIntoDifferentCurrency() throws EvaluationException {
        Currency currencyTo = new Currency("USD");

        MoneyExchange moneyExchangeImpl = new MoneyExchange() {
            @Override
            public BigDecimal exchangeRatio(Currency from, Currency to) {
                return new BigDecimal("1.10");
            }
        };

        StockExchange stockExchangeImpl = new StockExchange() {
            @Override
            public Money value(Ticket ticket) {
                return new Money(new BigDecimal("10.00"), new Currency("EUR"));
            }
        };

        Money result = stock.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
        Money expected = new Money(new BigDecimal("110.00"), new Currency("USD"));

        assertEquals(expected, result);
    }

    @Test(expected = EvaluationException.class)
    public void testEvaluateRatioDoesNotExist() throws EvaluationException {
        Currency currencyTo = new Currency("USD");

        MoneyExchange moneyExchangeImpl = new MoneyExchange() {
            @Override
            public BigDecimal exchangeRatio(Currency from, Currency to) throws RatioDoesNotExistException {
                throw new RatioDoesNotExistException("Ratio does not exist");
            }
        };

        StockExchange stockExchangeImpl = new StockExchange() {
            @Override
            public Money value(Ticket ticket) {
                return new Money(new BigDecimal("1.00"), new Currency("EUR"));
            }
        };

        stock.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
    }

    @Test(expected = EvaluationException.class)
    public void testEvaluateTicketDoesNotExist() throws EvaluationException {
        Currency currencyTo = new Currency("USD");

        MoneyExchange moneyExchangeImpl = new MoneyExchange() {
            @Override
            public BigDecimal exchangeRatio(Currency from, Currency to) {
                return new BigDecimal("1.00");
            }
        };

        StockExchange stockExchangeImpl = new StockExchange() {
            @Override
            public Money value(Ticket ticket) throws TicketDoesNotExistException {
                throw new TicketDoesNotExistException("Ticket does not exist");
            }
        };

        stock.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
    }
}