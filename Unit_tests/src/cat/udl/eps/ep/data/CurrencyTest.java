package cat.udl.eps.ep.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests Currency.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class CurrencyTest {

    private Currency currency;

    @Before
    public void initCurrency() {
        currency = new Currency("TST");
    }

    @Test
    public void testEquals() {
        // New object created for comparing
        Currency test_currency = new Currency("TST");

        boolean result = currency.equals(test_currency);
        assertTrue(result);
    }

    @Test
    public void testEqualsFalse() {
        // New object created for comparing
        Currency test_currency = new Currency("CHF");

        boolean result = currency.equals(test_currency);
        assertFalse(result);
    }

    @Test
    public void testEqualsDifferentObject() {
        // New object created for comparing
        Ticket test_ticket = new Ticket("TST");

        boolean result = currency.equals(test_ticket);
        assertFalse(result);
    }

    @Test
    public void testToString() {
        String result = currency.toString();
        String expected = "Currency name: TST, Hashcode: " + currency.hashCode();
        assertEquals(expected, result);
    }
}