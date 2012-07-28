/**
 * 
 */
package com.wookler.services;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.JResponse;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.wookler.core.Env;
import com.wookler.core.persistence.DataImport;
import com.wookler.core.persistence.csv.CSVPersister;
import com.wookler.core.persistence.csv.EnumImportFormat;
import com.wookler.entities.Creative;
import com.wookler.entities.ProductHistory;
import com.wookler.entities.Sequence;
import com.wookler.entities.Tag;
import com.wookler.entities.VideoMedia;
import com.wookler.utils.FileUtils;
import com.wookler.utils.ListParam;
import com.wookler.utils.LogUtils;
import com.wookler.utils.ValueParam;

/**
 * @author subhagho
 * 
 */
@Path("/import")
public class WooklerDataImport {
	private static final Logger log = LoggerFactory
			.getLogger(WooklerDataImport.class);

	@POST
	@Path("/process")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public JResponse<WooklerResponse> process(
			@FormDataParam("file") InputStream datastream,
			@FormDataParam("file") FormDataContentDisposition fileinfo,
			@QueryParam("entity") String entity,
			@DefaultValue("CSV") @QueryParam("format") String format)
			throws Exception {
		try {
			if (entity == null || entity.isEmpty()) {
				throw new Exception("Missing request query parameter [entity]");
			}
			Class<?> type = null;

			if (entity.compareToIgnoreCase("video") == 0) {
				type = VideoMedia.class;
			} else if (entity.compareToIgnoreCase("sequence") == 0) {
				type = Sequence.class;
			} else if (entity.compareToIgnoreCase("creative") == 0) {
				type = Creative.class;
			} else if (entity.compareToIgnoreCase("tag") == 0) {
				type = Tag.class;
			} else if (entity.compareToIgnoreCase("producthistory") == 0) {
				type = ProductHistory.class;
			}

			if (type == null) {
				throw new Exception("Import of Entity type [" + entity
						+ "] not supported.");
			}
			EnumImportFormat form = EnumImportFormat.parse(format);

			log.debug("Processing upload file for Entity ["
					+ type.getCanonicalName() + "], input format ["
					+ form.name() + "]");
			UUID uuid = UUID.randomUUID();

			String destdir = Env.get().getWorkPath("import") + "/"
					+ uuid.toString();

			File wd = new File(destdir);
			if (!wd.exists())
				wd.mkdirs();

			String filename = destdir + "/" + entity.toUpperCase() + "."
					+ form.name();
			log.debug("Saving uploaded data to [" + filename + "]");
			FileUtils.savefile(datastream, filename);

			doImport(destdir, form, type);

			WooklerResponse response = new WooklerResponse();
			response.setMessage("Imported data for entity {"
					+ type.getCanonicalName() + "} [IMPORT SESSION : "
					+ uuid.toString() + "]");
			return JResponse.ok(response).build();
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			log.error(e.getLocalizedMessage());

			WooklerResponse response = new WooklerResponse();
			response.setState(EnumResponseState.Exception);
			if (log.isDebugEnabled()) {
				response.setMessage(LogUtils.stacktrace(e));
			} else
				response.setMessage(e.getLocalizedMessage());
			return JResponse.ok(response).build();
		}
	}

	private void doImport(String dirname, EnumImportFormat format, Class<?> type)
			throws Exception {
		CSVPersister source = new CSVPersister(format);

		// Setup CSV Persister
		ListParam params = new ListParam();

		ValueParam vp = new ValueParam();
		vp.setKey(CSVPersister._PARAM_KEY_);
		vp.setValue("CSVIMPORTSRC");
		params.add(vp);

		vp = new ValueParam();
		vp.setKey(CSVPersister._PARAM_DATADIR_);
		vp.setValue(dirname);
		params.add(vp);

		source.init(params);

		DataImport importer = new DataImport(source);
		importer.load(new String[] { type.getCanonicalName() });
	}
}
