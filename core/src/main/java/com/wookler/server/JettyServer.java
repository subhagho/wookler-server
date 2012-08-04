/**
 * 
 */
package com.wookler.server;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.wookler.core.Env;
import com.wookler.utils.KeyValuePair;

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
		try {
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
				System.err.println("Usage : "
						+ this.getClass().getCanonicalName() + " "
						+ parser.printExample(ExampleMode.ALL));
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

			String jettyhome = serverConfig.getJettyHome();
			if (jettyhome != null && !jettyhome.isEmpty()) {
				System.setProperty("jetty.home", jettyhome);
			}

			log.info("Starting Jetty Server:");
			log.info("\tPort : " + serverConfig.getPort());
			log.info("\tThreads : " + serverConfig.getNumThreads());
			log.info("\tWeb Root : " + serverConfig.getWebRoot());

			// un-comment these to enable tracing of requests and responses

			// sh.setInitParameter("com.sun.jersey.config.feature.Debug",
			// "true");
			// sh.setInitParameter("com.sun.jersey.config.feature.Trace",
			// "true");
			//
			// sh.setInitParameter("com.sun.jersey.spi.container.ContainerRequestFilters",
			// "com.sun.jersey.api.container.filter.LoggingFilter");
			// sh.setInitParameter("com.sun.jersey.spi.container.ContainerResponseFilters",
			// "com.sun.jersey.api.container.filter.LoggingFilter");
			ContextHandlerCollection ctxs = new ContextHandlerCollection();
			server.setHandler(ctxs);

			List<Handler> handlers = new ArrayList<Handler>();

			ServletContextHandler restctx = new ServletContextHandler(
					ServletContextHandler.SESSIONS);
			restctx.setContextPath("/rest");
			restctx.addServlet(sh, "/*");
			handlers.add(restctx);

			if (serverConfig.getWebapps() != null) {
				for (KeyValuePair<String> webapp : serverConfig.getWebapps()) {
					String warfile = serverConfig.getWebRoot() + "/"
							+ webapp.getValue();
					File fi = new File(warfile);
					if (!fi.exists())
						throw new Exception("Cannot find WAR file ["
								+ fi.getAbsolutePath() + "]");

					WebAppContext webctx = new WebAppContext();
					// webctx.setDescriptor(serverConfig.getWebRoot() +
					// "/WEB-INF/web.xml");
					// webctx.setResourceBase(serverConfig.getWebRoot());
					webctx.setContextPath("/web" + webapp.getKey());
					webctx.setWar(warfile);
					webctx.setParentLoaderPriority(true);

					handlers.add(webctx);
				}
			}
			Handler[] harray = new Handler[handlers.size()];
			for (int ii = 0; ii < handlers.size(); ii++) {
				harray[ii] = handlers.get(ii);
			}

			ctxs.setHandlers(harray);

			QueuedThreadPool qtp = new QueuedThreadPool(
					serverConfig.getNumThreads());
			qtp.setName("WooklerJettyServer");
			server.setThreadPool(qtp);

			server.start();
			log.info("Jetty Server running...");
			log.info("Root directory [" + new File(".").getAbsolutePath() + "]");
			server.join();

		} finally {
			Env.dispose();
		}
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
