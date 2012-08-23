/**
 * 
 */
package com.wookler.server;

import java.util.HashMap;
import java.util.List;

import com.sqewd.open.dal.api.persistence.AbstractEntity;
import com.sqewd.open.dal.core.persistence.DataManager;
import com.wookler.entities.users.Profile;

/**
 * @author subhagho
 * 
 */
public class SessionCache {
	public static final long _SESSION_TIMEOUT_ = 1000 * 60 * 120; // 2 Hrs.

	private static class SessionCacheObject {
		public Profile Profile;
		public long LastAccessed = System.currentTimeMillis();

		public boolean isExpired() {
			long timestamp = System.currentTimeMillis();
			if (timestamp - LastAccessed > _SESSION_TIMEOUT_)
				return true;
			return false;
		}
	}

	private HashMap<String, SessionCacheObject> sessions = new HashMap<String, SessionCache.SessionCacheObject>();

	public Profile get(String sessionid) {
		if (sessions.containsKey(sessionid)) {
			SessionCacheObject sco = sessions.get(sessionid);
			if (sco.isExpired()) {
				sessions.remove(sessionid);
			} else {
				sco.LastAccessed = System.currentTimeMillis();
				return sco.Profile;
			}
		}
		return null;
	}

	public Profile add(String sessionid, String id) throws Exception {
		List<AbstractEntity> entities = DataManager.get().read("ID=" + id,
				Profile.class, 1);
		if (entities == null || entities.size() <= 0)
			throw new Exception("No profiles found for ID [" + id + "]");

		Profile profile = (Profile) entities.get(0);
		SessionCacheObject sco = new SessionCacheObject();
		sco.Profile = profile;

		sessions.put(sessionid, sco);

		return profile;
	}

	public void remove(String sessionid) {
		if (sessions.containsKey(sessionid)) {
			sessions.remove(sessionid);
		}
	}
}
