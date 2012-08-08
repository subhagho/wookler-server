/**
 * 
 */
package com.wookler.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sqewd.open.dal.api.utils.LogUtils;
import com.sqewd.open.dal.core.persistence.DataManager;
import com.sun.jersey.api.JResponse;
import com.wookler.entities.media.Creative;
import com.wookler.entities.media.Sequence;
import com.wookler.entities.media.Tag;
import com.wookler.entities.media.VideoMedia;

/**
 * @author subhagho
 * 
 */
@Path("/wookler/crud/media/")
public class WooklerMediaCrudServices {

	private static final Logger log = LoggerFactory
			.getLogger(WooklerMediaCrudServices.class);

	/**
	 * Add/Update/Delete VideoMedia instance.
	 * 
	 * For Add/Update use state = "Unknown" if not sure.
	 * 
	 * @param video
	 *            - Parsed JSON input.
	 * @return
	 * @throws Exception
	 */
	@Path("/save/video")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> video(@Context HttpServletRequest req,
			VideoMedia video) throws Exception {
		try {
			int count = DataManager.get().save(video);
			WooklerResponse response = new WooklerResponse();
			response.setState(EnumResponseState.Success);
			response.setMessage("updated:" + count);
			return JResponse.ok(response).build();
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			log.error(e.getLocalizedMessage());

			WooklerResponse response = new WooklerResponse();
			response.setState(EnumResponseState.Exception);
			response.setMessage(e.getLocalizedMessage());
			return JResponse.ok(response).build();
		}
	}

	/**
	 * Add/Update/Delete Sequence instance.
	 * 
	 * For Add/Update use state = "Unknown" if not sure.
	 * 
	 * @param seq
	 *            - Parsed JSON input.
	 * @return
	 * @throws Exception
	 */
	@Path("/save/sequence")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> sequence(@Context HttpServletRequest req,
			Sequence seq) throws Exception {
		try {
			int count = DataManager.get().save(seq);
			WooklerResponse response = new WooklerResponse();
			response.setState(EnumResponseState.Success);
			response.setMessage("updated:" + count);
			return JResponse.ok(response).build();
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			log.error(e.getLocalizedMessage());

			WooklerResponse response = new WooklerResponse();
			response.setState(EnumResponseState.Exception);
			response.setMessage(e.getLocalizedMessage());
			return JResponse.ok(response).build();
		}
	}

	/**
	 * Add/Update/Delete Creative instance.
	 * 
	 * For Add/Update use state = "Unknown" if not sure.
	 * 
	 * @param creative
	 *            - Parsed JSON input.
	 * @return
	 * @throws Exception
	 */
	@Path("/save/creative")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> creative(@Context HttpServletRequest req,
			Creative creative) throws Exception {
		try {
			int count = DataManager.get().save(creative);
			WooklerResponse response = new WooklerResponse();
			response.setState(EnumResponseState.Success);
			response.setMessage("updated:" + count);
			return JResponse.ok(response).build();
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			log.error(e.getLocalizedMessage());

			WooklerResponse response = new WooklerResponse();
			response.setState(EnumResponseState.Exception);
			response.setMessage(e.getLocalizedMessage());
			return JResponse.ok(response).build();
		}
	}

	/**
	 * Add/Update/Delete VideoMedia instance.
	 * 
	 * For Add/Update use state = "Unknown" if not sure.
	 * 
	 * @param tag
	 *            - Parsed JSON input.
	 * @return
	 * @throws Exception
	 */
	@Path("/save/tag")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> tag(@Context HttpServletRequest req,
			Tag tag) throws Exception {
		try {
			int count = DataManager.get().save(tag);
			WooklerResponse response = new WooklerResponse();
			response.setState(EnumResponseState.Success);
			response.setMessage("updated:" + count);
			return JResponse.ok(response).build();
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			log.error(e.getLocalizedMessage());

			WooklerResponse response = new WooklerResponse();
			response.setState(EnumResponseState.Exception);
			response.setMessage(e.getLocalizedMessage());
			return JResponse.ok(response).build();
		}
	}
}
