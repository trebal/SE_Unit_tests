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
 * This class tests Portfolio.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class PortfolioTest {
    private Portfolio portfolio;

    @Before
    public void initPortfolio() {
        portfolio = new Portfolio();
        FutureBuy investment_1 = new FutureBuy(
                new Ticket("CABX"),
                10,
                new Money(new BigDecimal("10.00"), new Currency("CAD")));

        FutureSell investment_2 = new FutureSell(
                new Ticket("BBVA"),
                10,
                new Money(new BigDecimal("20.00"), new Currency("MXN")));

        Cash investment_3 = new Cash(new Money(new BigDecimal("30.00"), new Currency("EUR")));
        Cash investment_4 = new Cash(new Money(new BigDecimal("-10.00"), new Currency("CHF")));

        portfolio.addInvestment(investment_1);
        portfolio.addInvestment(investment_2);
        portfolio.addInvestment(investment_3);
        portfolio.addInvestment(investment_4);
    }

    @Test
    public void testEvaluate() throws EvaluationException {
        Currency currencyTo = new Currency("USD");

        MoneyExchange moneyExchangeImpl = new MoneyExchange() {
            @Override
            public BigDecimal exchangeRatio(Currency from, Currency to) {
                return new BigDecimal("1.00");
            }
        };

        StockExchange stockExchangeImpl = new StockExchange() {
            @Override
            public Money value(Ticket ticket) {
                return new Money(new BigDecimal("1.00"), new Currency("USD"));
            }
        };

        Money result = portfolio.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
        Money expected = new Money(new BigDecimal("120.00"), new Currency("USD"));

        assertEquals(expected, result);
    }

    @Test
    public void testEvaluateEmptyWallet() throws EvaluationException {
        Portfolio empty_portfolio = new Portfolio();
        Currency currencyTo = new Currency("USD");

        MoneyExchange moneyExchangeImpl = new MoneyExchange() {
            @Override
            public BigDecimal exchangeRatio(Currency from, Currency to) {
                return new BigDecimal("1.00");
            }
        };

        StockExchange stockExchangeImpl = new StockExchange() {
            @Override
            public Money value(Ticket ticket) {
                return new Money(new BigDecimal("1.00"), new Currency("USD"));
            }
        };

        Money result = empty_portfolio.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
        Money expected = new Money(new BigDecimal("0.00"), new Currency("USD"));

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

        portfolio.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
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

        portfolio.evaluate(currencyTo, moneyExchangeImpl, stockExchangeImpl);
    }
}