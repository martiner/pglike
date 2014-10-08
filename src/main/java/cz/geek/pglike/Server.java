package cz.geek.pglike;

/**
 * Server backend
 */
public enum Server {
	POSTGRES(5432, 196608), VERTICA(5433, 196608);

	private final int port;
	private final int version;

	private Server(int port, int version) {
		this.port = port;
		this.version = version;
	}

	public int getPort() {
		return port;
	}

	public int getVersion() {
		return version;
	}
}
