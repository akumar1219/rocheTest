package com.roche.test.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class Utility {

	public static String getJsonResponseFromEndPoint(String uri) {
		Client cleint = ClientBuilder.newClient();
		WebTarget target = cleint.target(uri);
		return target.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
	}

	public static boolean isValidIP(String ipAddr) {
		Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
		Matcher mtch = ptn.matcher(ipAddr);
		return mtch.find();
	}


}
