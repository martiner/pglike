package cz.geek.pglike.messages;

import org.junit.Test;

import java.nio.ByteBuffer;

import static cz.geek.pglike.messages.MessageUtils.readString;
import static java.nio.ByteBuffer.wrap;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class MessageUtilsTest {

	@Test
	public void shouldReadZeroTerminated() throws Exception {
		assertThat(readString(wrap(new byte[]{'a', 'h', 'o', 'j', '\0'})), is("ahoj"));
	}

	@Test
	public void shouldReadTwoStrings() throws Exception {
		final ByteBuffer buffer = wrap(new byte[]{'a', 'h', 'o', 'j', '\0', 'v', 'o', 'l', 'e', '\0'});
		assertThat(readString(buffer), is("ahoj"));
		assertThat(readString(buffer), is("vole"));
	}

	@Test
	public void shouldReadUnTerminated() throws Exception {
		assertThat(readString(wrap(new byte[]{'a', 'h', 'o', 'j'})), is("ahoj"));
	}

	@Test
	public void shouldReadEmptyString() throws Exception {
		assertThat(readString(wrap(new byte[]{'\0'})), is(nullValue()));
	}

	@Test
	public void shouldReadNull() throws Exception {
		assertThat(readString(wrap(new byte[]{})), is(nullValue()));
	}
}