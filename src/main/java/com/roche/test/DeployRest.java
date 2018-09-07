package com.roche.test;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import com.roche.test.entity.NorthCountry;
import com.roche.test.entity.SouthCountry;
import com.roche.test.excpetion.ErrorMessage;
import com.roche.test.excpetion.MaxLimitIpException;
import com.roche.test.excpetion.MaxLimitIpExceptionMapper;
import com.roche.test.excpetion.MinLimitIpException;
import com.roche.test.excpetion.MinLimitIpExceptionMapper;
import com.roche.test.rest.RestApplication;
import com.roche.test.utility.Utility;
import com.roche.test.rest.CountryResource;

public class DeployRest {

	public static void main(String... args) throws Exception {

		Swarm swarm = new Swarm();
		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);

		deployment.addClass(ErrorMessage.class);
		deployment.addClass(MaxLimitIpException.class);
		deployment.addClass(MaxLimitIpExceptionMapper.class);
		deployment.addClass(MinLimitIpException.class);
		deployment.addClass(MinLimitIpExceptionMapper.class);

		deployment.addClass(NorthCountry.class);
		deployment.addClass(SouthCountry.class);
		deployment.addClass(Utility.class);
		deployment.addClass(CountryResource.class);
		deployment.addClass(RestApplication.class);

		deployment.addAllDependencies();
		swarm.start().deploy(deployment);

	}
}
