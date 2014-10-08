package cz.geek.pglike.messages;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Message produced by client
 */
public interface FrontendMessage {

	/**
	 * Encode the message
	 * @return buffer, must be flipped
	 */
	ByteBuffer encode();
}
