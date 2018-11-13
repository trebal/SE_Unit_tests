package cat.udl.eps.ep.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The class which tests Ticket.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class TicketTest {
    
    private Ticket ticket;

    @Before
    public void initTicket() {
        ticket = new Ticket("TST");
    }

    @Test
    public void testEquals() {
        // New object created for comparing
        Ticket test_ticket = new Ticket("TST");

        boolean result = ticket.equals(test_ticket);
        assertTrue(result);
    }

    @Test
    public void testEqualsFalse() {
        // New object created for comparing
        Ticket test_ticket = new Ticket("CABX");

        boolean result = ticket.equals(test_ticket);
        assertFalse(result);
    }

    @Test
    public void testEqualsDifferentObject() {
        // New object created for comparing
        Currency test_currency = new Currency("TST");

        boolean result = ticket.equals(test_currency);
        assertFalse(result);
    }

    @Test
    public void testToString() {
        String result = ticket.toString();
        String expected = "Ticket name: TST, Hashcode: " + ticket.hashCode();
        assertEquals(expected, result);
    }

}
