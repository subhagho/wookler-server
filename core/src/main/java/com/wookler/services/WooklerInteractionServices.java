/**
 * 
 */
package com.wookler.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sqewd.open.dal.api.persistence.AbstractEntity;
import com.sqewd.open.dal.api.utils.LogUtils;
import com.sqewd.open.dal.core.persistence.DataManager;
import com.sqewd.open.dal.server.ServerConfig;
import com.sun.jersey.api.JResponse;
import com.wookler.entities.interactions.Activity;

/**
 * @author subhagho
 * 
 */
@Path("/wookler/interaction/")
public class WooklerInteractionServices {
	private static final Logger log = LoggerFactory
			.getLogger(WooklerInteractionServices.class);

	@Path("/schema/{type}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> schema(
			@Context HttpServletRequest req,
			@DefaultValue(ServerConfig._EMPTY_PATH_ELEMENT_) @PathParam("type") String type)
			throws Exception {
		try {
			log.debug("[SESSIONID:" + req.getSession().getId() + "]");

			List<Class<?>> types = new ArrayList<Class<?>>();
			if (type.compareToIgnoreCase("activity") == 0
					|| type.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) == 0) {
				types.add(Activity.class);
			}

			List<EntityDef> defs = new ArrayList<EntityDef>();
			for (Class<?> ct : types) {
				EntityDef def = EntityDef.load(ct);
				defs.add(def);
			}
			WooklerResponse response = new WooklerResponse();
			response.setState(EnumResponseState.Success);
			response.setData(defs);
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

	@Path("/activity/{userid}/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> activity(
			@Context HttpServletRequest req,
			@PathParam("userid") String userid,
			@DefaultValue(ServerConfig._EMPTY_PATH_ELEMENT_) @PathParam("id") String id,
			@QueryParam("q") String query,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		try {
			int pagec = Integer.parseInt(page);
			int limit = Integer.parseInt(size);
			int count = pagec * limit;

			String querystr = "PROFILEID=" + userid + " ORDER BY TX_TIMESTAMP DESC";

			if (id.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) != 0) {
				querystr = "ID=" + id + ";" + querystr;
			}

			if (query != null && !query.isEmpty()) {
				querystr = query + ";" + querystr;
			}

			String path = "/contribution/"
					+ (querystr != null ? "?q=" + querystr : "");

			log.debug("QUERY [" + querystr + "]");
			DataManager dm = DataManager.get();
			List<AbstractEntity> data = dm.read(querystr, Activity.class, count);
			WooklerResponse response = new WooklerResponse();

			response.setRequest(path);
			if (data == null || data.size() <= 0) {
				response.setState(EnumResponseState.NoData);
			} else {
				response.setState(EnumResponseState.Success);
				int stindex = limit * (pagec - 1);
				if (stindex > 0) {
					if (stindex > data.size()) {
						response.setState(EnumResponseState.NoData);
					} else {
						List<AbstractEntity> subdata = data.subList(stindex,
								data.size());
						response.setData(subdata);
					}
				} else
					response.setData(data);
			}
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
	 * Save the activity.
	 * 
	 * @param req
	 *            - Servlet Request
	 * @param activity
	 *            - Activity
	 * @return
	 * @throws Exception
	 */
	@Path("/save/activity")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> addactivity(
			@Context HttpServletRequest req, Activity activity)
			throws Exception {
		try {
			int count = DataManager.get().save(activity);
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
