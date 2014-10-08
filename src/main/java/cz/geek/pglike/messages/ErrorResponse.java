package cz.geek.pglike.messages;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

import static cz.geek.pglike.messages.MessageUtils.readString;

/**
 * Error response
 */
public class ErrorResponse implements BackendMessage {

	private String severity;
	private String sqlstate;
	private String message;
	private final Map<Character,String> fields = new LinkedHashMap<>();

	public ErrorResponse(ByteBuffer buffer) {
		while (buffer.hasRemaining()) {
			final char type = (char) buffer.get();
			switch (type) {
				case MessageUtils.ZERO:
					return;
				case 'S':
					this.severity = readString(buffer);
					break;
				case 'C':
					this.sqlstate = readString(buffer);
					break;
				case 'M':
					this.message = readString(buffer);
					break;
				default:
					fields.put(type, readString(buffer));
					break;
			}
		}
	}

	@Override
	public String toString() {
		return "ErrorResponse " + "severity='" + severity + '\'' + ", sqlstate='" + sqlstate + '\'' +
				", message='" + message + "' fields='" + fields + "'}";
	}
}
