package cz.geek.pglike.login;

import cz.geek.pglike.messages.BackendMessage;

import java.nio.ByteBuffer;

/**
 * @author martin
 */
public class AuthenticationMessage implements BackendMessage {

	private final int type;

	public AuthenticationMessage(ByteBuffer buffer) {
		type = buffer.getInt();
	}
}
