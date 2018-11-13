package cat.udl.eps.ep.data;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_UP;

/**
 * This class describes an amount of a certain Currency.
 * This class is immutable.
 * This class uses a rounding mode of type ROUND_UP, and a scale of 2 decimals.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class Money {
    /**
     * Amount of the given Currency.
     */
    private final BigDecimal quantity;

    /**
     * The Currency corresponding to the amount.
     */
    private final Currency currency;

    /**
     * Constructor of the class.
     * @param quantity The amount of the Currency.
     * @param currency The name of the Currency.
     */
    public Money(BigDecimal quantity, Currency currency) {
        this.quantity = quantity.setScale(2, ROUND_UP);
        this.currency = currency;
    }

    /**
     * Gets the Currency as object.
     * @return The Currency.
     */
    public Currency getCurrency() {
        return this.currency;
    }

    /**
     * Gets the Quantity as a BigDecimal.
     * @return The Quantity.
     */
    public BigDecimal getQuantity() {
        return this.quantity;
    }

    /**
     * Returns the result of adding an amount of Money to the current one.
     * @param other The amount to add.
     * @return The result of adding.
     */
    public Money add(Money other) {
        if (!this.getCurrency().equals(other.getCurrency())) {
            throw new IllegalArgumentException("Can not operate with different currencies: [" + this.getCurrency() + "] & [" + other.getCurrency() + "]");
        } else {
            BigDecimal new_quantity = this.quantity.add(other.quantity);
            new_quantity.setScale(2, ROUND_UP);

            return new Money(new_quantity, this.currency);
        }
    }

    /**
     * Returns the result of subtracting an amount of Money to the current one.
     * @param other The amount to subtract.
     * @return The result of subtracting.
     */
    public Money subtract(Money other) {
        if (!this.getCurrency().equals(other.getCurrency())) {
            throw new IllegalArgumentException("Can not operate with different currencies: [" + this.getCurrency() + "] & [" + other.getCurrency() + "]");
        } else {
            BigDecimal new_quantity = this.quantity.subtract(other.quantity);
            new_quantity.setScale(2, ROUND_UP);

            return new Money(new_quantity, this.currency);
        }
    }

    /**
     * Returns the result of multiplying the current amount of Money by multiplier sent by parameter.
     * @param multiplier The ratio to multiply.
     * @return The current Money by the multiplier.
     */
    public Money multiply(int multiplier) {
        BigDecimal new_quantity = this.quantity.multiply(new BigDecimal(multiplier));
        new_quantity.setScale(2, ROUND_UP);

        return new Money(new_quantity, this.currency);
    }

    /**
     * Returns the result of converting the current amount of Money into another Currency.
     * @param ratio The ratio of conversion.
     * @param to The currency to convert.
     * @return The conversion of the Money amount into the new one sent by parameter.
     */
    public Money change(BigDecimal ratio, Currency to) {
        if (this.getCurrency().equals(to)) {
            throw new IllegalArgumentException("Can not change to the same currency: [" + this.getCurrency().name + "]");
        } else {
            return new Money(this.quantity.multiply(ratio), to);
        }
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
     * Returns the hashCode of the Money.
     * @return The hashCode corresponding to the Object.
     */
    public int hashCode() {
        return this.quantity.hashCode() + this.currency.hashCode();
    }

    /**
     * Returns all the attributes into a string.
     * @return A string containing all the attributes.
     */
    public String toString() {
        return "Money quantity: " + this.quantity + ", Currency: " + this.currency.name + ", HashCode: " + hashCode();
    }
}