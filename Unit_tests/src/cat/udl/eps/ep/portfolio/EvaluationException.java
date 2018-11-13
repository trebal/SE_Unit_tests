package cat.udl.eps.ep.portfolio;

/**
 * This class describes an Exception when evaluating an investment.
 *
 * @author Ramon de Llano Chamorro
 * @version 1.0 22 Jan 2018
 */

public class EvaluationException extends Exception {
    private String message;

    public EvaluationException(String message) {
        this.message = message;
    }
}