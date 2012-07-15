/**
 * 
 */
package com.wookler.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.JResponse;
import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.DataManager;
import com.wookler.entities.Sequence;
import com.wookler.entities.VideoMedia;

/**
 * @author subhagho
 * 
 */
@Path("/wookler/core")
public class WooklerCoreServices {
	private static final Logger log = LoggerFactory
			.getLogger(WooklerCoreServices.class);

	@Path("/videos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<List<AbstractEntity>> videos(
			@QueryParam("query") String query) throws Exception {
		log.debug("QUERY [" + query + "]");
		DataManager dm = DataManager.get();
		return JResponse.ok(dm.read(query, VideoMedia.class)).build();
	}

	@Path("/sequences")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<List<AbstractEntity>> sequences(
			@QueryParam("vid") String videoid, @QueryParam("query") String query)
			throws Exception {
		log.debug("VIDEOID:" + videoid);
		String squery = "MEDIAID=" + videoid;
		if (query != null && !query.isEmpty())
			squery = squery + ";" + query;

		log.debug("QUERY [" + squery + "]");

		DataManager dm = DataManager.get();
		return JResponse.ok(dm.read(query, Sequence.class)).build();
	}
}
