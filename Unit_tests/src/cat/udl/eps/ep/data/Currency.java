package cat.udl.eps.ep.data;

/**
 * The class which describes a Currency.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class Currency {
    /**
     * Name of the Currency.
     */
    public final String name;

    /**
     * Constructor of the class.
     *
     * @param name The name of the Currency.
     */
    public Currency(String name) {
        this.name = name;
    }

    /**
     * Checks if both objects are the same.
     *
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
     * Returns the hashCode of the Currency.
     *
     * @return The hashCode corresponding to the Object.
     */
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Returns all the attributes into a string.
     *
     * @return A string containing all the attributes.
     */
    public String toString() {
        return "Currency name: " + name + ", Hashcode: " + hashCode();
    }
}
