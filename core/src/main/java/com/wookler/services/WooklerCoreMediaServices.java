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

import com.sqewd.open.dal.api.persistence.AbstractEntity;
import com.sqewd.open.dal.api.utils.LogUtils;
import com.sqewd.open.dal.core.persistence.DataManager;
import com.sqewd.open.dal.core.persistence.query.Query;
import com.sqewd.open.dal.server.ServerConfig;
import com.sun.jersey.api.JResponse;
import com.wookler.entities.media.Creative;
import com.wookler.entities.media.ProductHistory;
import com.wookler.entities.media.Sequence;
import com.wookler.entities.media.Tag;
import com.wookler.entities.media.VideoMedia;

/**
 * Core services for fetching Entity data. (CRUD services are part of a separate
 * class.)
 * 
 * @author subhagho
 * 
 */
@Path("/wookler/core/media/")
public class WooklerCoreMediaServices {

	private static final Logger log = LoggerFactory
			.getLogger(WooklerCoreMediaServices.class);

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
			if (type.compareToIgnoreCase("video") == 0
					|| type.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) == 0) {
				types.add(VideoMedia.class);
			}

			if (type.compareToIgnoreCase("sequence") == 0
					|| type.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) == 0) {
				types.add(Sequence.class);
			}
			if (type.compareToIgnoreCase("creative") == 0
					|| type.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) == 0) {
				types.add(Creative.class);
			}
			if (type.compareToIgnoreCase("tag") == 0
					|| type.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) == 0) {
				types.add(Tag.class);
			}
			if (type.compareToIgnoreCase("producthistory") == 0
					|| type.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) == 0) {
				types.add(ProductHistory.class);
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

	/**
	 * Select Video Media and related data.
	 * 
	 * Note: Use URL escape sequence '%3B' for ';'
	 * 
	 * @param videoid
	 *            - Video ID (Optional), default '-'
	 * @param query
	 *            - Filter Query (Optional), default '-'
	 * @param page
	 *            - Page Offset
	 * @param size
	 *            - Page Size
	 * @return
	 * @throws Exception
	 */
	@Path("/videos/{video}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> videos(
			@Context HttpServletRequest req,
			@DefaultValue(ServerConfig._EMPTY_PATH_ELEMENT_) @PathParam("video") String videoid,
			@DefaultValue("") @QueryParam("q") String query,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		try {

			if (videoid == null
					|| videoid.isEmpty()
					|| videoid.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) == 0) {
				String querystr = "";
				if (query.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) != 0) {
					querystr = query;
				}
				int pagec = Integer.parseInt(page);
				int limit = Integer.parseInt(size);
				int count = pagec * limit;

				if (!querystr.isEmpty())
					querystr = querystr + ";";

				querystr = querystr + "LIMIT " + count;

				log.debug("QUERY [" + querystr + "]");
				log.debug("VEDIO-ID [" + videoid + "]");

				DataManager dm = DataManager.get();

				List<AbstractEntity> data = dm.read(querystr, VideoMedia.class);
				WooklerResponse response = new WooklerResponse();
				String path = "/videos" + (query != null ? "?q=" + query : "");
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
							List<AbstractEntity> subdata = data.subList(
									stindex, data.size());
							response.setData(subdata);
						}
					} else
						response.setData(data);
				}
				return JResponse.ok(response).build();
			} else {
				return sequences(req, videoid, query, page, size);
			}
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
	 * Select the latest Video Media and related data.
	 * 
	 * Note: Use URL escape sequence '%3B' for ';'
	 * 
	 * @return
	 * @throws Exception
	 */
	@Path("/videos/latest")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> latest(@Context HttpServletRequest req,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		return custom("PUBLISHED", page, size);
	}

	/**
	 * Select the most popular Video Media and related data.
	 * 
	 * Note: Use URL escape sequence '%3B' for ';'
	 * 
	 * @return
	 * @throws Exception
	 */
	@Path("/videos/popular")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> popular(@Context HttpServletRequest req,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		return custom("VIEWS", page, size);
	}

	private JResponse<WooklerResponse> custom(String column, String page,
			String size) throws Exception {
		try {
			int pagec = Integer.parseInt(page);
			int limit = Integer.parseInt(size);
			int count = pagec * limit;

			String querystr = "LIMIT " + count + ";SORT " + column + " DSC";

			String path = "/videos"
					+ (querystr != null ? "?q=" + querystr : "");

			log.debug("QUERY [" + querystr + "]");
			DataManager dm = DataManager.get();
			List<AbstractEntity> data = dm.read(querystr, VideoMedia.class);
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
	 * Get the sequence associated with a Video Media.
	 * 
	 * @param videoid
	 *            - Video ID (Required)
	 * @param query
	 *            - Filter Query (Optional), default '-'
	 * @return
	 * @throws Exception
	 */
	@Path("/tags/{videoid}/{seqid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> tags(
			@Context HttpServletRequest req,
			@DefaultValue(ServerConfig._EMPTY_PATH_ELEMENT_) @PathParam("videoid") String videoid,
			@DefaultValue(ServerConfig._EMPTY_PATH_ELEMENT_) @PathParam("seqid") String seqid,
			@DefaultValue("") @QueryParam("q") String query,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		try {
			log.debug("VIDEO-ID:" + videoid);
			StringBuffer squery = new StringBuffer("MEDIAID=" + videoid);
			if (seqid.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) != 0) {
				squery.append(Query._QUERY_CONDITION_AND_).append("SEQID=")
						.append(seqid);
			}
			if (query != null && !query.isEmpty()
					&& query.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) != 0)
				squery = squery.append(Query._QUERY_CONDITION_AND_).append(
						query);
			int pagec = Integer.parseInt(page);
			int limit = Integer.parseInt(size);
			int count = pagec * limit;
			squery = squery.append(Query._QUERY_CONDITION_AND_)
					.append("LIMIT ").append(count);

			log.debug("QUERY [" + squery + "]");

			DataManager dm = DataManager.get();
			List<AbstractEntity> data = dm.read(squery.toString(), Tag.class);
			WooklerResponse response = new WooklerResponse();
			String path = "/videos/" + videoid + "?q=" + squery.toString();
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
	 * Get the product trends.
	 * 
	 * @param period
	 *            - Trend period (daily, weekly, monthly, - default total)
	 * @param query
	 *            - Additional filters
	 * @param page
	 *            - page offset
	 * @param size
	 *            - page size
	 * @return
	 * @throws Exception
	 */
	@Path("/products/{period}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> products(
			@Context HttpServletRequest req,
			@DefaultValue(ServerConfig._EMPTY_PATH_ELEMENT_) @PathParam("period") String period,
			@DefaultValue("") @QueryParam("q") String query,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		try {
			String periodtype = "MONTHLYCOUNT";
			if (period.compareToIgnoreCase("weekly") == 0) {
				periodtype = "WEEKLYCOUNT";
			} else if (period.compareToIgnoreCase("monthly") == 0) {
				periodtype = "MONTHLYCOUNT";
			} else if (period.compareToIgnoreCase("daily") == 0) {
				periodtype = "DAILYCOUNT";
			}

			log.debug("PERIOD:" + periodtype);
			StringBuffer squery = new StringBuffer("SORT " + periodtype
					+ " DSC");
			if (query != null && !query.isEmpty()
					&& query.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) != 0)
				squery = squery.append(Query._QUERY_CONDITION_AND_).append(
						query);
			int pagec = Integer.parseInt(page);
			int limit = Integer.parseInt(size);
			int count = pagec * limit;
			squery = squery.append(Query._QUERY_CONDITION_AND_)
					.append("LIMIT ").append(count);

			log.debug("QUERY [" + squery + "]");

			DataManager dm = DataManager.get();
			List<AbstractEntity> data = dm.read(squery.toString(),
					ProductHistory.class);
			WooklerResponse response = new WooklerResponse();
			String path = "/products/" + period + "?q=" + squery.toString();
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
	 * Get the sequence associated with a Video Media.
	 * 
	 * @param videoid
	 *            - Video ID (Required)
	 * @param query
	 *            - Filter Query (Optional), default '-'
	 * @return
	 * @throws Exception
	 */
	@Path("/sequences/{videoid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> sequences(
			@Context HttpServletRequest req,
			@DefaultValue(ServerConfig._EMPTY_PATH_ELEMENT_) @PathParam("videoid") String videoid,
			@DefaultValue("") @QueryParam("q") String query,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		try {
			log.debug("VIDEO-ID:" + videoid);
			StringBuffer squery = new StringBuffer("MEDIAID=" + videoid);
			if (query != null && !query.isEmpty()
					&& query.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) != 0)
				squery = squery.append(Query._QUERY_CONDITION_AND_).append(
						query);
			int pagec = Integer.parseInt(page);
			int limit = Integer.parseInt(size);
			int count = pagec * limit;
			squery = squery.append(Query._QUERY_CONDITION_AND_)
					.append("LIMIT ").append(count);

			log.debug("QUERY [" + squery + "]");

			DataManager dm = DataManager.get();
			List<AbstractEntity> data = dm.read(squery.toString(),
					Sequence.class);
			WooklerResponse response = new WooklerResponse();
			String path = "/videos/" + videoid + "?q=" + squery.toString();
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

	@Path("/creative/{id}/{type}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> creatives(
			@Context HttpServletRequest req,
			@PathParam("id") String id,
			@DefaultValue(ServerConfig._EMPTY_PATH_ELEMENT_) @PathParam("type") String type,
			@DefaultValue("") @QueryParam("q") String query,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		try {
			log.debug("CREATIVE-ID:" + id);
			StringBuffer squery = new StringBuffer("CREATIVE.ID=" + id);
			if (query != null && !query.isEmpty()
					&& query.compareTo(ServerConfig._EMPTY_PATH_ELEMENT_) != 0)
				squery = squery.append(Query._QUERY_CONDITION_AND_).append(
						query);
			int pagec = Integer.parseInt(page);
			int limit = Integer.parseInt(size);
			int count = pagec * limit;
			if (type.compareToIgnoreCase("seqeunce") == 0)
				squery = squery.append(Query._QUERY_CONDITION_AND_)
						.append("LIMIT ").append(count);

			log.debug("QUERY [" + squery + "]");

			DataManager dm = DataManager.get();
			List<AbstractEntity> data = dm.read(squery.toString(),
					Sequence.class);
			WooklerResponse response = new WooklerResponse();
			if (type.compareToIgnoreCase("seqeunce") == 0) {
				String path = "/videos/" + type + "?q=" + squery.toString();
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
							List<AbstractEntity> subdata = data.subList(
									stindex, data.size());
							response.setData(subdata);
						}
					} else
						response.setData(data);
				}
			} else {
				
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
