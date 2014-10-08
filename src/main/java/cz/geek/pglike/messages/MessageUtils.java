package cz.geek.pglike.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author martin
 */
public abstract class MessageUtils {

	public static final byte ZERO = (byte) 0;
	public static final int INT32 = 4;
	public static final int BYTE = 1;
	public static final Charset CHARSET = StandardCharsets.UTF_8;

	/**
	 * Read zero delimited string from the byte buffer, change buffer position
	 * @param buffer buffer
	 * @return string or null
	 */
	public static String readString(ByteBuffer buffer) {
		buffer.mark();
		boolean hasZero = false;
		while (buffer.hasRemaining()) {
			if (buffer.get() == ZERO) {
				hasZero = true;
				break;
			}
		}
		final int position = buffer.position();
		buffer.reset();
		final int length = position - buffer.position() + (hasZero ? -1 : 0);
		if (length <= 0) {
			return null;
		}

		final ByteBuffer slice = buffer.slice();
		slice.limit(length);
		buffer.position(position);
		return CHARSET.decode(slice).toString();
	}

	public static ByteBuffer allocate(int capacity) {
		return ByteBuffer.allocate(capacity).order(ByteOrder.LITTLE_ENDIAN);
	}
}
