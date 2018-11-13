package cat.udl.eps.ep.services;

import cat.udl.eps.ep.data.Currency;

import java.math.BigDecimal;

/**
 * This interface will be implemented by the MoneyExchanger service.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public interface MoneyExchange {
    BigDecimal exchangeRatio(Currency from, Currency to) throws RatioDoesNotExistException;
}
