package cz.geek.pglike;

import cz.geek.pglike.messages.BackendMessage;

/**
 * @author martin
 */
public class ClientException extends RuntimeException {

	public ClientException(String message) {
		super(message);
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientException(BackendMessage message) {
		this(message.toString());
	}
}
