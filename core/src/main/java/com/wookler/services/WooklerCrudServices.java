/**
 * 
 */
package com.wookler.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.JResponse;
import com.wookler.core.persistence.DataManager;
import com.wookler.entities.media.Creative;
import com.wookler.entities.media.Sequence;
import com.wookler.entities.media.Tag;
import com.wookler.entities.media.VideoMedia;
import com.wookler.utils.LogUtils;

/**
 * @author subhagho
 * 
 */
@Path("/wookler/core/crud/")
public class WooklerCrudServices {

	private static final Logger log = LoggerFactory
			.getLogger(WooklerCrudServices.class);

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
	public JResponse<WooklerResponse> video(VideoMedia video) throws Exception {
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
	public JResponse<WooklerResponse> sequence(Sequence seq) throws Exception {
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
	public JResponse<WooklerResponse> creative(Creative creative)
			throws Exception {
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
	public JResponse<WooklerResponse> tag(Tag tag) throws Exception {
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
