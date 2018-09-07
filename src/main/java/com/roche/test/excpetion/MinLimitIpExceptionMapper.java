package com.roche.test.excpetion;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MinLimitIpExceptionMapper implements ExceptionMapper<MinLimitIpException> {
	@Override
	public Response toResponse(MinLimitIpException exception) {
		ErrorMessage errorMessage = new ErrorMessage(400, "Min limit request Ip is 1");
		return Response.status(Status.NOT_ACCEPTABLE).entity(errorMessage).build();
	}
}
