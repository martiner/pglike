package cz.geek.pglike.messages;

import java.nio.ByteBuffer;

/**
 * Unknown message sent by server
 */
public class UnknownBackendMessage implements BackendMessage {

	private final char type;
	private final int size;

	public UnknownBackendMessage(byte type, ByteBuffer buffer) {
		this.type = (char) type;
		this.size = buffer.limit();
	}

	@Override
	public String toString() {
		return "UnknownBackendMessage " + "type=" + type + ", size=" + size;
	}
}
