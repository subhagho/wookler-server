/**
 * 
 */
package com.wookler.services;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.JResponse;
import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.DataManager;
import com.wookler.core.persistence.query.Query;
import com.wookler.entities.Sequence;
import com.wookler.entities.VideoMedia;

/**
 * @author subhagho
 * 
 */
@Path("/wookler/core/data/")
public class WooklerCoreServices {
	private static final String _EMPTY_PATH_ELEMENT_ = "-";

	private static final Logger log = LoggerFactory
			.getLogger(WooklerCoreServices.class);

	/**
	 * Select Video Media and related data.
	 * 
	 * Note: Use URL escape sequence '%3B' for ';'
	 * 
	 * @param videoid
	 *            - Video ID (Optional), default '-'
	 * @param query
	 *            - Filter Query (Optional), default '-'
	 * @return
	 * @throws Exception
	 */
	@Path("/videos/{video}/{query}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<List<AbstractEntity>> videos(
			@DefaultValue(_EMPTY_PATH_ELEMENT_) @PathParam("video") String videoid,
			@DefaultValue(_EMPTY_PATH_ELEMENT_) @PathParam("query") String query)
			throws Exception {
		log.debug("QUERY [" + query + "]");
		log.debug("VEDIO-ID [" + videoid + "]");

		String querystr = "";
		if (query.compareTo(_EMPTY_PATH_ELEMENT_) != 0) {
			querystr = query;
		}

		DataManager dm = DataManager.get();

		if (videoid == null || videoid.isEmpty()
				|| videoid.compareTo(_EMPTY_PATH_ELEMENT_) == 0)
			return JResponse.ok(dm.read(querystr, VideoMedia.class)).build();
		else {
			return sequences(videoid, querystr);
		}
	}

	/**
	 * Get the sequence associated with a Video Media.
	 * 
	 * @param videoid
	 *            - Video ID (Required)
	 * @param query
	 *            - Filter Query (Optional), default '-'
	 * @return
	 * @throws Exception
	 */
	@Path("/sequences/{videoid}/{query}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<List<AbstractEntity>> sequences(
			@DefaultValue(_EMPTY_PATH_ELEMENT_) @PathParam("videoid") String videoid,
			@DefaultValue(_EMPTY_PATH_ELEMENT_) @PathParam("query") String query)
			throws Exception {
		log.debug("VIDEO-ID:" + videoid);
		String squery = "MEDIAID=" + videoid;
		if (query != null && !query.isEmpty()
				&& query.compareTo(_EMPTY_PATH_ELEMENT_) != 0)
			squery = squery + Query._QUERY_CONDITION_AND_ + query;

		log.debug("QUERY [" + squery + "]");

		DataManager dm = DataManager.get();
		return JResponse.ok(dm.read(squery, Sequence.class)).build();
	}
}
