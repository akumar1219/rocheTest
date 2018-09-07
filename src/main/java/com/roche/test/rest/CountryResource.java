package com.roche.test.rest;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;

import com.roche.test.entity.NorthCountry;
import com.roche.test.entity.SouthCountry;
import com.roche.test.excpetion.MaxLimitIpException;
import com.roche.test.excpetion.MinLimitIpException;
import com.roche.test.utility.Utility;

@Path("/")
public class CountryResource {
	private String NORTH_COUNTRIES = "/northcountries";
	private String SOUTH_COUNTRIES = "/southcountries";
	private String IP_VIGILANTE_BASE_URI = "https://ipvigilante.com/json/";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/northcountries")
	public Response getListOfNorthHemiSphereCountries(@Context UriInfo uriInfo) {
		return getListOfCountries(uriInfo);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/southcountries")
	public Response getListOfSouthHemiSphereCountries(@Context UriInfo uriInfo) {
		return getListOfCountries(uriInfo);
	}

	private Response getListOfCountries(@Context UriInfo uriInfo) {
		MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
		List<String> ipList = queryParameters.get("ip");

		if (queryParameters.isEmpty() || null == ipList) {
			throw new MinLimitIpException("Min limit request Ip is 0");
		}

		if (ipList.size() > 5) {
			throw new MaxLimitIpException("Max limit request Ip is 5");
		}

		Set<String> countrySet = new TreeSet();

		for (String ip : ipList) {
			if (Utility.isValidIP(ip)) {
				String response = getJsonResponseFromEndPoint(IP_VIGILANTE_BASE_URI, ip);

				JSONObject responseJson = null;
				try {
					responseJson = new JSONObject(response);
					getCountryNameAndAddToCountrySet(countrySet, responseJson, uriInfo.getPath());
				} catch (JSONException ex) {
					throw new RuntimeException(ex);
				}

			}
		}
		return prepareResponse(countrySet, uriInfo.getPath());
	}

	private Response prepareResponse(Set<String> countrySet, String path) {
		if (NORTH_COUNTRIES.equals(path)) {
			NorthCountry northCountries = () -> countrySet;
			return Response.ok(northCountries).build();

		} else if (SOUTH_COUNTRIES.equals(path)) {
			SouthCountry southCountries = () -> countrySet;
			return Response.ok(southCountries).build();
		}

		return Response.ok().build();

	}

	private void getCountryNameAndAddToCountrySet(Set<String> countrySet, JSONObject responseJson, String path)
		throws JSONException {
		JSONObject data = responseJson.getJSONObject("data");
		String countryName = data.getString("country_name");
		Double latitude = data.getDouble("latitude");
		if (NORTH_COUNTRIES.equals(path) && isCountryInNorthHemisphere(latitude))
			countrySet.add(countryName);

		if (SOUTH_COUNTRIES.equals(path) && !isCountryInNorthHemisphere(latitude))
			countrySet.add(countryName);
	}

	private String getJsonResponseFromEndPoint(String ipvigilanteUrl, String ip) {
		String ipvigilanteUri = new StringBuilder(ipvigilanteUrl).append(ip).toString();
		return Utility.getJsonResponseFromEndPoint(ipvigilanteUri);
	}

	private boolean isCountryInNorthHemisphere(Double latitude) {
		return (latitude >= 0) ? true : false;

	}

}






