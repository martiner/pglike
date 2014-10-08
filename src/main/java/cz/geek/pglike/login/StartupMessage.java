package cz.geek.pglike.login;

import cz.geek.pglike.Server;
import cz.geek.pglike.messages.FrontendMessage;
import cz.geek.pglike.messages.MessageUtils;

import java.nio.ByteBuffer;

import static cz.geek.pglike.messages.MessageUtils.CHARSET;
import static cz.geek.pglike.messages.MessageUtils.allocate;

/**
 * The initial message
 */
public class StartupMessage implements FrontendMessage {

	private static final byte[] USER = "user".getBytes(CHARSET);
	private static final byte[] DATABASE = "database".getBytes(CHARSET);

	private final Server server;
	private final String user;
	private final String db;

	public StartupMessage(Server server, String user, String db) {
		this.server = server;
		this.user = user;
		this.db = db;
	}

	@Override
	public ByteBuffer encode() {
		final ByteBuffer buffer = allocate(1024);
		buffer
				.putInt(server.getVersion())
				.put(USER).put(MessageUtils.ZERO)
				.put(user.getBytes(CHARSET)).put(MessageUtils.ZERO)
				.put(DATABASE).put(MessageUtils.ZERO)
				.put(db.getBytes(CHARSET)).put(MessageUtils.ZERO)
				//.put(MessageUtils.ZERO)
		;
		return buffer;
	}

}
