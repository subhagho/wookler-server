/**
 * 
 */
package com.wookler.server;

import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;
import org.kohsuke.args4j.Option;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.wookler.core.Env;

/**
 * @author subhagho
 * 
 */
public class JettyServer {
	private static final Logger log = LoggerFactory
			.getLogger(JettyServer.class);

	@Option(name = "-port", usage = "Specify the Jetty Server Port")
	private String port;

	@Option(name = "-webroot", usage = "Web Content Root directory")
	private String webroot;

	@Option(name = "-config", usage = "System Configuration file path.", required = true)
	private String config;

	private ServerConfig serverConfig = new ServerConfig();

	private void start(String[] args) throws Exception {

		CmdLineParser parser = new CmdLineParser(this);

		// if you have a wider console, you could increase the value;
		// here 80 is also the default
		parser.setUsageWidth(80);

		try {
			// parse the arguments.
			parser.parseArgument(args);
			Env.create(config);

			serverConfig.init(Env.get().getConfig());
			if (port != null && !port.isEmpty()) {
				serverConfig.setPort(Integer.parseInt(port));
			}
			if (webroot != null && !webroot.isEmpty()) {
				serverConfig.setWebRoot(webroot);
			}
		} catch (CmdLineException e) {
			System.err.println("Usage : " + this.getClass().getCanonicalName()
					+ " " + parser.printExample(ExampleMode.ALL));
			throw e;
		}
		Server server = new Server(serverConfig.getPort());
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
		initMap.put("com.sun.jersey.config.property.packages",
				"com.wookler.services");
		initMap.put("com.sun.jersey.config.property.resourceConfigClass",
				"com.sun.jersey.api.core.PackagesResourceConfig");

		ServletHolder sh = new ServletHolder(new ServletContainer(
				new PackagesResourceConfig(initMap)));

		log.info("Starting Jetty Server:");
		log.info("\tPort : " + serverConfig.getPort());
		log.info("\tThreads : " + serverConfig.getNumThreads());
		log.info("\tWeb Root : " + serverConfig.getWebRoot());

		// un-comment these to enable tracing of requests and responses

		// sh.setInitParameter("com.sun.jersey.config.feature.Debug", "true");
		// sh.setInitParameter("com.sun.jersey.config.feature.Trace", "true");
		//
		// sh.setInitParameter("com.sun.jersey.spi.container.ContainerRequestFilters",
		// "com.sun.jersey.api.container.filter.LoggingFilter");
		// sh.setInitParameter("com.sun.jersey.spi.container.ContainerResponseFilters",
		// "com.sun.jersey.api.container.filter.LoggingFilter");

		Context restctx = new Context(server, "/", Context.SESSIONS);
		restctx.setContextPath("/rest");
		restctx.addServlet(sh, "/*");

		WebAppContext webctx = new WebAppContext();
		webctx.setDescriptor(serverConfig.getWebRoot() + "/WEB-INF/web.xml");
		webctx.setResourceBase(serverConfig.getWebRoot());
		webctx.setContextPath("/web");
		webctx.setParentLoaderPriority(true);

		ContextHandlerCollection ctxs = new ContextHandlerCollection();
		ctxs.setHandlers(new Handler[] { restctx, webctx });

		server.setHandler(ctxs);
		QueuedThreadPool qtp = new QueuedThreadPool(
				serverConfig.getNumThreads());
		qtp.setName("WooklerJettyServer");
		server.setThreadPool(qtp);

		server.start();
		log.info("Jetty Server running...");
		server.join();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new JettyServer().start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
