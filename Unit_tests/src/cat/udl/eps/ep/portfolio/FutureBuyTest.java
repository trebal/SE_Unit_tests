package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Ticket;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.services.MoneyExchange;
import cat.udl.eps.ep.services.RatioDoesNotExistException;
import cat.udl.eps.ep.services.StockExchange;
import cat.udl.eps.ep.services.TicketDoesNotExistException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * This class tests FutureBuy.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class FutureBuyTest {
    private FutureBuy futureBuy;

    @Before
    public void initFutureBuy() {
        futureBuy = new FutureBuy(new Ticket("IBE"), 10, new Money(new BigDecimal("10.00"), new Currency("EUR")));
    }

    @Test
    public void testEvaluate() throws EvaluationException {
        Currency currencyTo = new Currency("EUR");

        MoneyExchange moneyExchangeImpl = new MoneyExchange() {
            @Override
            public BigDecimal exchangeRatio(Currency from, Currency to) {
                return new BigDecimal("1.00");
            }
        };

        StockExchange stockExchangeImpl = new StockExchange() {
            @Override
            public Money value(Ticket ticket) {
                return new Money(new BigDecimal("20.00"), new Currency("EUR"));
            }
        };

        Money result = futureBuy.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
        Money expected = new Money(new BigDecimal("100.00"), new Currency("EUR"));

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
                return new Money(new BigDecimal("20.00"), new Currency("EUR"));
            }
        };

        Money result = futureBuy.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
        Money expected = new Money(new BigDecimal("110.00"), new Currency("USD"));

        assertEquals(expected, result);
    }

    @Test(expected = EvaluationException.class)
    public void testEvaluateExchangerHasNoRatio() throws EvaluationException {
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

        futureBuy.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
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

        futureBuy.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
    }
}