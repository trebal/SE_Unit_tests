package cat.udl.eps.ep.data;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests Money.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class MoneyTest {

    private Money money;

    @Before
    public void initMoney() {
        money = new Money(new BigDecimal("30.00"), new Currency("USD"));
    }

    @Test
    public void testAdd() {
        Money to_add = new Money(new BigDecimal("70.00"), new Currency("USD"));
        Money expected = new Money(new BigDecimal("100.00"), new Currency("USD"));
        Money result = money.add(to_add);

        assertEquals(expected.getQuantity(), result.getQuantity());
    }

    @Test
    public void testAddDecimalRounding() {
        Money to_add = new Money(new BigDecimal("69.991"), new Currency("USD"));
        Money expected = new Money(new BigDecimal("100.00"), new Currency("USD"));
        Money result = money.add(to_add);

        assertEquals(expected.getQuantity(), result.getQuantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddWithDifferentCurrencies() {
        Money to_add = new Money(new BigDecimal("70.00"), new Currency("CHF"));
        money.add(to_add);
    }

    @Test
    public void testSubtract() {
        Money to_subtract = new Money(new BigDecimal("10.00"), new Currency("USD"));
        Money expected = new Money(new BigDecimal("20.00"), new Currency("USD"));
        Money result = money.subtract(to_subtract);

        assertEquals(expected.getQuantity(), result.getQuantity());
    }

    @Test
    public void testSubtractDecimalPrecision() {
        Money to_subtract = new Money(new BigDecimal("9.991"), new Currency("USD"));
        Money expected = new Money(new BigDecimal("20.00"), new Currency("USD"));
        Money result = money.subtract(to_subtract);

        assertEquals(expected.getQuantity(), result.getQuantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtractWithDifferentCurrencies() {
        Money to_subtract = new Money(new BigDecimal("10.00"), new Currency("CHF"));
        money.subtract(to_subtract);
    }

    @Test
    public void testMultiply() {
        Money expected = new Money(new BigDecimal("90.00"), new Currency("USD"));
        Money result = money.multiply(3);

        assertEquals(expected.getQuantity(), result.getQuantity());
    }

    @Test
    public void testChangeCorrectAmount() {
        Money expected = new Money(new BigDecimal("26.70"), new Currency("CHF"));
        Money result = money.change(new BigDecimal("0.89"), new Currency("CHF"));

        assertEquals(expected.getQuantity(), result.getQuantity());
    }

    @Test
    public void testChangeCorrectCurrency() {
        Money expected = new Money(new BigDecimal("60.00"), new Currency("CHF"));
        Money result = money.change(new BigDecimal("2.00"), new Currency("CHF"));

        assertEquals(expected.getCurrency(), result.getCurrency());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeSameCurrencies() {
        money.change(new BigDecimal(2), new Currency("USD"));
    }

    @Test
    public void testEquals() {
        // New object created for comparing, with the exact same parameters as the main one.
        Money compare_money = new Money(new BigDecimal("30.00"), new Currency("USD"));

        boolean result = money.equals(compare_money);
        assertTrue(result);
    }

    @Test
    public void testEqualsDifferentMoney() {
        // New object created for comparing, with the exact same parameters as the main one.
        Money compare_money = new Money(new BigDecimal("29.00"), new Currency("CHF"));

        boolean result = money.equals(compare_money);
        assertFalse(result);
    }

    @Test
    public void testToString() {
        String expected = "Money quantity: 30.00, Currency: USD, HashCode: 177328";
        String result = money.toString();

        assertEquals(expected, result);
    }
}