package org.jboss.additional.testsuite.jdk17.present.jaxrs.record;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/WildflyJakarta/jaxrs/src/main/java"})
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("/")
public class SampleResrouce {

	private static final Logger LOG = LoggerFactory.getLogger(SampleResrouce.class);

	@GET
	@Path("class")
	public Response getClazz() throws Exception {
		LOG.info("GET class");
		var representation = new ClassRepresentation("Hello");
		return Response.ok(representation).build();
	}


	@GET
	@Path("record")
	public Response getRecord() throws Exception {
		LOG.info("GET record");
		var representation = new RecordRepresentation("Hello");
		return Response.ok(representation).build();
	}

	@GET
	@Path("jsonb")
	public Response getJsonBind() throws Exception {
		LOG.info("GET record");
		var representation = new RecordRepresentation("Hello");

		try(Jsonb jsonb = JsonbBuilder.create()) {
			String result = jsonb.toJson(representation);
			LOG.info(result);

			RecordRepresentation newRepresentation = jsonb.fromJson(result, RecordRepresentation.class);

			LOG.info(newRepresentation.message());
		}
		return Response.ok(representation).build();
	}
}
