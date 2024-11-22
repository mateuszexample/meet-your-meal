package pl.molsztyn.meetyourmeal.domain.exception;

public class OrderCancellationException extends RuntimeException {

    public OrderCancellationException(final String message) {
        super(message);
    }
}
