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
import com.wookler.entities.users.Contribution;
import com.wookler.entities.users.Notification;
import com.wookler.entities.users.Profile;
import com.wookler.entities.users.Subscription;

/**
 * @author subhagho
 * 
 */
@Path("/wookler/crud/users/")
public class WooklerUserCrudServices {
	private static final Logger log = LoggerFactory
			.getLogger(WooklerUserCrudServices.class);

	/**
	 * Save the user profile.
	 * 
	 * @param req
	 *            - Servlet Request
	 * @param profile
	 *            - User Profile
	 * @return
	 * @throws Exception
	 */
	@Path("/save/profile")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> profile(@Context HttpServletRequest req,
			Profile profile) throws Exception {
		try {
			int count = DataManager.get().save(profile);
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
	 * Save the subscription.
	 * 
	 * @param req
	 *            - Servlet Request
	 * @param subscription
	 *            - Subscription
	 * @return
	 * @throws Exception
	 */
	@Path("/save/subscription")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> subscription(
			@Context HttpServletRequest req, Subscription subscription)
			throws Exception {
		try {
			if (subscription.getValue() == null
					|| subscription.getValue().isEmpty())
				subscription.setValue(Subscription._SUBSCRIBE_ALL_);
			int count = DataManager.get().save(subscription);
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
	 * Save the subscription.
	 * 
	 * @param req
	 *            - Servlet Request
	 * @param notification
	 *            - Notification
	 * @return
	 * @throws Exception
	 */
	@Path("/save/notification")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> notification(
			@Context HttpServletRequest req, Notification notification)
			throws Exception {
		try {
			int count = DataManager.get().save(notification);
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
	 * Save the subscription.
	 * 
	 * @param req
	 *            - Servlet Request
	 * @param contribution
	 *            - Contribution
	 * @return
	 * @throws Exception
	 */
	@Path("/save/contribution")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JResponse<WooklerResponse> contribution(
			@Context HttpServletRequest req, Contribution contribution)
			throws Exception {
		try {
			int count = DataManager.get().save(contribution);
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
