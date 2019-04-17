package com.github.ants280.jeff.farm.ws;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main
{
	private final HttpServer server;

	Main(URI uri)
	{
		ResourceConfig resourceConfig = new ResourceConfig().packages(
			"com.github.ants280.jeff.farm.ws.resources")
			.registerInstances(new InjectionBinder());

		this.server = GrizzlyHttpServerFactory.createHttpServer(uri,
			resourceConfig,
			false); // auto-start flag
	}

	public static void main(String[] args)
		throws IOException, URISyntaxException
	{
		loadProperties();

		String scheme = System.getProperty("server.scheme");
		String host = System.getProperty("server.host");
		String port = System.getProperty("server.port");
		int portNum = Integer.parseInt(port);
		URI uri = new URI(scheme, null, // userInfo
			host, portNum, "/jeff-farm-ws", // path
			null, // query
			null); // fragment
		Main main = new Main(uri);

		try
		{
			main.startServer();

			Logger logger = Logger.getGlobal();
			logger.log(Level.INFO,
				"Server started at {0}.  Press ENTER to stop",
				uri);

			logger.log(Level.INFO,
				"[DONE]{0}",
				(char) System.in.read()); // BLOCKS
		}
		finally
		{
			main.stopServer();
		}
	}

	private static void loadProperties()
	{
		try (
			InputStream inputStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("app.properties"))
		{
			System.getProperties().load(inputStream);
		}
		catch (IOException ex)
		{
			throw new RuntimeException("Could not read properties.", ex);
		}
	}

	void startServer() throws IOException
	{
		server.start();
	}

	void stopServer()
	{
		server.shutdownNow();
	}
}
