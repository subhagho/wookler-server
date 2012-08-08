/**
 * 
 */
package com.wookler.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sqewd.open.dal.core.persistence.AbstractEntity;
import com.sqewd.open.dal.core.persistence.DataManager;
import com.sqewd.open.dal.server.ServerConfig;
import com.sqewd.open.dal.utils.LogUtils;
import com.sun.jersey.api.JResponse;
import com.wookler.entities.users.Contribution;
import com.wookler.entities.users.Notification;
import com.wookler.entities.users.Profile;
import com.wookler.entities.users.Subscription;

/**
 * @author subhagho
 * 
 */
@Path("/wookler/core/users/")
public class WooklerCoreUserServices {
	private static final Logger log = LoggerFactory
			.getLogger(WooklerCoreUserServices.class);

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
			if (type.compareToIgnoreCase("profile") == 0
					|| type.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) == 0) {
				types.add(Profile.class);
			}

			if (type.compareToIgnoreCase("notification") == 0
					|| type.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) == 0) {
				types.add(Notification.class);
			}
			if (type.compareToIgnoreCase("contribution") == 0
					|| type.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) == 0) {
				types.add(Contribution.class);
			}
			if (type.compareToIgnoreCase("subscription") == 0
					|| type.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) == 0) {
				types.add(Subscription.class);
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

	@Path("/get/id/{userid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> profile(@Context HttpServletRequest req,
			@PathParam("userid") String id) throws Exception {
		try {
			String query = "ID=" + id;
			String path = "/users?q=" + query;

			log.debug("QUERY [" + query + "]");
			DataManager dm = DataManager.get();
			List<AbstractEntity> data = dm.read(query, Profile.class);
			WooklerResponse response = new WooklerResponse();

			response.setRequest(path);
			if (data == null || data.size() <= 0) {
				response.setState(EnumResponseState.NoData);
			} else {
				response.setState(EnumResponseState.Success);

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

	@Path("/get/email/{emailid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> profilebyemail(
			@Context HttpServletRequest req, @PathParam("emailid") String email)
			throws Exception {
		try {
			String query = "EMAIL=" + email;
			String path = "/users?q=" + query;

			log.debug("QUERY [" + query + "]");
			DataManager dm = DataManager.get();
			List<AbstractEntity> data = dm.read(query, Profile.class);
			WooklerResponse response = new WooklerResponse();

			response.setRequest(path);
			if (data == null || data.size() <= 0) {
				response.setState(EnumResponseState.NoData);
			} else {
				response.setState(EnumResponseState.Success);

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

	@Path("/search/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> search(@Context HttpServletRequest req,
			@QueryParam("q") String query,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		try {
			int pagec = Integer.parseInt(page);
			int limit = Integer.parseInt(size);
			int count = pagec * limit;

			String querystr = "LIMIT " + count + ";SORT EMAIL DSC";

			if (query != null && !query.isEmpty()) {
				querystr = query + ";" + querystr;
			}

			String path = "/users/"
					+ (querystr != null ? "?q=" + querystr : "");

			log.debug("QUERY [" + querystr + "]");
			DataManager dm = DataManager.get();
			List<AbstractEntity> data = dm.read(querystr, Profile.class);
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

	@Path("/contribution/{userid}/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> contribution(
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

			String querystr = "PROFILE.ID=" + userid + ";LIMIT " + count;

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
			List<AbstractEntity> data = dm.read(querystr, Contribution.class);
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

	@Path("/notification/{userid}/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> notification(
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

			String querystr = "PROFILE.ID=" + userid + ";LIMIT " + count
					+ ";SORT TX_TIMESTAMP DSC";

			if (id.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) != 0) {
				querystr = "ID=" + id + ";" + querystr;
			}

			if (query != null && !query.isEmpty()) {
				querystr = query + ";" + querystr;
			}

			String path = "/notification/"
					+ (querystr != null ? "?q=" + querystr : "");

			log.debug("QUERY [" + querystr + "]");
			DataManager dm = DataManager.get();
			List<AbstractEntity> data = dm.read(querystr, Notification.class);
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

	@Path("/subscription/{userid}/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> subscription(
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

			String querystr = "PROFILE.ID=" + userid + ";LIMIT " + count;

			if (id.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) != 0) {
				querystr = "ID=" + id + ";" + querystr;
			}

			if (query != null && !query.isEmpty()) {
				querystr = query + ";" + querystr;
			}

			String path = "/subscription/"
					+ (querystr != null ? "?q=" + querystr : "");

			log.debug("QUERY [" + querystr + "]");
			DataManager dm = DataManager.get();
			List<AbstractEntity> data = dm.read(querystr, Subscription.class);
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
}
