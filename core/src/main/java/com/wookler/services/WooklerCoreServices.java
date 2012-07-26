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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.JResponse;
import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.DataManager;
import com.wookler.core.persistence.query.Query;
import com.wookler.entities.ProductHistory;
import com.wookler.entities.Sequence;
import com.wookler.entities.Tag;
import com.wookler.entities.VideoMedia;
import com.wookler.utils.LogUtils;

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
			@DefaultValue(_EMPTY_PATH_ELEMENT_) @PathParam("video") String videoid,
			@DefaultValue("") @QueryParam("q") String query,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		try {

			if (videoid == null || videoid.isEmpty()
					|| videoid.compareTo(_EMPTY_PATH_ELEMENT_) == 0) {
				String querystr = "";
				if (query.compareTo(_EMPTY_PATH_ELEMENT_) != 0) {
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
				return sequences(videoid, query, page, size);
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
	public JResponse<WooklerResponse> latest(
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
	public JResponse<WooklerResponse> popular(
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
			@DefaultValue(_EMPTY_PATH_ELEMENT_) @PathParam("videoid") String videoid,
			@DefaultValue(_EMPTY_PATH_ELEMENT_) @PathParam("seqid") String seqid,
			@DefaultValue("") @QueryParam("q") String query,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		try {
			log.debug("VIDEO-ID:" + videoid);
			StringBuffer squery = new StringBuffer("MEDIAID=" + videoid);
			if (seqid.compareTo(_EMPTY_PATH_ELEMENT_) != 0) {
				squery.append(Query._QUERY_CONDITION_AND_).append("SEQID=")
						.append(seqid);
			}
			if (query != null && !query.isEmpty()
					&& query.compareTo(_EMPTY_PATH_ELEMENT_) != 0)
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

	@Path("/products/{period}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> products(
			@DefaultValue(_EMPTY_PATH_ELEMENT_) @PathParam("period") String period,
			@DefaultValue("") @QueryParam("q") String query,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		try {
			String periodtype = "TOTALVIEWS";
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
					&& query.compareTo(_EMPTY_PATH_ELEMENT_) != 0)
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
			@DefaultValue(_EMPTY_PATH_ELEMENT_) @PathParam("videoid") String videoid,
			@DefaultValue("") @QueryParam("q") String query,
			@DefaultValue("1") @QueryParam("p") String page,
			@DefaultValue("20") @QueryParam("s") String size) throws Exception {
		try {
			log.debug("VIDEO-ID:" + videoid);
			StringBuffer squery = new StringBuffer("MEDIAID=" + videoid);
			if (query != null && !query.isEmpty()
					&& query.compareTo(_EMPTY_PATH_ELEMENT_) != 0)
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
}
