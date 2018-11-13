package cat.udl.eps.ep.data;

/**
 * Describes a Ticker of a company in the Stock-market.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */


public class Ticket {
    /**
     * Name of the Ticket.
     */
    public final String name;

    /**
     * Constructor of the class.
     * @param name The name of the Ticket.
     */
    public Ticket(String name) {
        this.name = name;
    }

    /**
     * Checks if both objects are the same.
     * @param other The Object to compare.
     * @return True if they are equal, False otherwise.
     */
    public boolean equals(Object other) {
        if (getClass() != other.getClass()) {
            return false;
        } else {
            return hashCode() == other.hashCode();
        }
    }

    /**
     * Returns the hashCode of the Ticket.
     * @return The hashCode corresponding to the Object.
     */
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Returns all the attributes into a string.
     * @return A string containing all the attributes.
     */
    public String toString() {
        return "Ticket name: " + name + ", Hashcode: " + hashCode();
    }
}