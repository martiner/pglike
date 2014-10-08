package cz.geek.pglike.login;

import cz.geek.pglike.Server;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StartupMessageTest {
	@Test
	public void testName() throws Exception {
		final ByteBuffer buffer = new StartupMessage(Server.POSTGRES, "martin", "db").encode();
		buffer.flip();
		final byte[] array = new byte[buffer.limit()];
		buffer.get(array);
		assertThat(array, is(new byte[]{
				0, 3, 0, 0,
				'u', 's', 'e', 'r', '\0',
				'm', 'a', 'r', 't', 'i', 'n', '\0',
				'd', 'a', 't', 'a', 'b', 'a', 's', 'e', '\0',
				'd', 'b', '\0',
				'\0'
		}));

	}
}