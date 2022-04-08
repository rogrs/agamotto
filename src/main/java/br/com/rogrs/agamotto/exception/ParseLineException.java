package br.com.rogrs.agamotto.exception;

public class ParseLineException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParseLineException(String message) {
        super(message);
    }

    public ParseLineException(String message, Throwable cause) {
        super(message, cause);
    }
}