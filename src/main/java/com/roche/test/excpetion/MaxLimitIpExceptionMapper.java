package com.roche.test.excpetion;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MaxLimitIpExceptionMapper  implements ExceptionMapper<MaxLimitIpException> {
	@Override
	public Response toResponse(MaxLimitIpException exception) {
		ErrorMessage errorMessage = new ErrorMessage(400, "Max limit request Ip is 5");
		return Response.status(Status.NOT_ACCEPTABLE).entity(errorMessage).build();
	}
}
