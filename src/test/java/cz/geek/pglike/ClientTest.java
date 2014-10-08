package cz.geek.pglike;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.*;

public class ClientTest {

	@Test
	public void testxxxx() throws Exception {
		final SocketChannel channel = mock(SocketChannel.class);
		final Client client = new Client(channel);
		client.login(Server.POSTGRES, "martin", "martin", "martin");
		final ArgumentCaptor<ByteBuffer> captor = ArgumentCaptor.forClass(ByteBuffer.class);
		verify(channel, times(2)).write(captor.capture());
		captor.getAllValues();

	}

	@Test
	public void testPostgres() throws Exception {
		final Client client = new Client(Server.POSTGRES, "localhost", "martin", "martin", "martin");
	}

	//@Test
	public void testName() throws Exception {
		new Client(Server.VERTICA, "localhost", "vertica", "", "vertica");
	}

}