package cz.geek.pglike;

import cz.geek.pglike.login.AuthenticationMessage;
import cz.geek.pglike.messages.*;
import cz.geek.pglike.login.StartupMessage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static cz.geek.pglike.messages.MessageUtils.BYTE;
import static cz.geek.pglike.messages.MessageUtils.INT32;
import static cz.geek.pglike.messages.MessageUtils.allocate;

/**
 * @author martin
 */
public class Client {

	private final SocketChannel channel;
	private final ByteBuffer size = allocate(INT32);
	private final ByteBuffer header = allocate(BYTE + INT32);

	public Client(Server server, String host, String user, String pass, String db) {
		this(server, host, user, pass, db, server.getPort());
	}

	public Client(Server server, String host, String user, String pass, String db, int port) {
		try {
			channel = SocketChannel.open(new InetSocketAddress(host, port));
			channel.configureBlocking(true);
		} catch (IOException e) {
			throw new ClientException("Unable to connect " + host + ":" + port, e);
		}
		login(server, user, pass, db);
	}

	public Client(SocketChannel channel) {
		this.channel = channel;
	}

	public void login(Server server, String user, String pass, String db) {
		write(new StartupMessage(server, user, db));
		final BackendMessage message = read();
		if (! (message instanceof AuthenticationMessage)) {
			throw new ClientException(message);
		}
	}

	protected void write(FrontendMessage message) {
		final ByteBuffer buffer = message.encode();
		buffer.flip();
		size.clear();
		size.putInt(buffer.limit() + INT32);
		try {
			System.out.println(channel.write(size));
			System.out.println(channel.write(buffer));
		} catch (IOException e) {
			throw new ClientException("Unable to write", e); // size of message is counted into size
		}
	}

	protected BackendMessage read() {
		try {
			channel.read(header);
			header.flip();
			final byte type = header.get();
			final int size = header.getInt();
			final ByteBuffer buffer = allocate(size - INT32); // size of message is counted into size
			channel.read(buffer);
			return parse(type, buffer);
		} catch (IOException e) {
			throw new ClientException("Unable to read", e);
		}
	}

	private BackendMessage parse(byte type, ByteBuffer buffer) {
		final char t = (char) type;
		switch (type) {
			case 'E':
				throw new ClientException(new ErrorResponse(buffer));
			case 'R':
				new AuthenticationMessage(buffer);
			default:
				return new UnknownBackendMessage(type, buffer);
		}
	}

	public static void main(String... args) {
	}
}
