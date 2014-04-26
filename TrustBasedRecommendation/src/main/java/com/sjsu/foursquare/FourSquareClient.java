package com.sjsu.foursquare;

//Reference code------four square java client api reference
//https://code.google.com/p/foursquare-api-java/source/browse/trunk/src/main/java/fi/foyt/foursquare/api/FoursquareApi.java
import java.util.Map;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class FourSquareClient {

	String clientId, clientSecret, callBackURL;

	public FourSquareClient(String clientId, String clientSecret, String callBackURL) {

		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.callBackURL = callBackURL;
	}

	/**
	 * Returns a list of venues near the current location identified by place
	 * (i.e. Chicago, IL, optionally matching the search term.
	 * 
	 * @see <a href="https://developer.foursquare.com/docs/venues/search.html"
	 *      target="_blank">https://developer.foursquare.com/docs/venues/search.html</a>
	 * 
	 * @param near
	 *            the name of a city or town which can be geocoded by foursquare
	 * @param query
	 *            a search term to be applied against titles.
	 * @param limit
	 *            number of results to return, up to 50.
	 * @param intent
	 *            one of checkin, match or specials
	 * @param categoryId
	 *            a category to limit results to
	 * @param url
	 *            a third-party URL
	 * @param providerId
	 *            identifier for a known third party
	 * @param linkedId
	 *            identifier used by third party specifed in providerId
	 *            parameter
	 * @return VenuesSearchResult object wrapped in Result object
	 * @throws FoursquareApiException
	 *             when something unexpected happens
	 */

	public Result<VenuesSearchResult> getVenueSearch(String near, String query, Integer limit, String intent, String categoryId, String url, String providerId, String linkedId) throws FoursquareApiException {

		FoursquareApi foursquareApi = new FoursquareApi(clientId, clientSecret, callBackURL);

		Result<VenuesSearchResult> result = foursquareApi.venuesSearch(near, query, limit, intent, categoryId, url, providerId, linkedId);

		return result;

	}

	/**
	 * Returns a list of venues near the current location, optionally matching
	 * the search term.
	 * 
	 * @see <a href="https://developer.foursquare.com/docs/venues/search.html"
	 *      target="_blank">https://developer.foursquare.com/docs/venues/search.html</a>
	 * 
	 * @param ll
	 *            latitude and longitude of the user's location. (Required for
	 *            query searches)
	 * @param llAcc
	 *            accuracy of latitude and longitude, in meters. (Does not
	 *            currently affect search results.)
	 * @param alt
	 *            altitude of the user's location, in meters. (Does not
	 *            currently affect search results.)
	 * @param altAcc
	 *            accuracy of the user's altitude, in meters. (Does not
	 *            currently affect search results.)
	 * @param query
	 *            a search term to be applied against titles.
	 * @param limit
	 *            number of results to return, up to 50.
	 * @param intent
	 *            one of checkin, match or specials
	 * @param categoryId
	 *            a category to limit results to
	 * @param url
	 *            a third-party URL
	 * @param providerId
	 *            identifier for a known third party
	 * @param linkedId
	 *            identifier used by third party specifed in providerId
	 *            parameter
	 * @return VenuesSearchResult object wrapped in Result object
	 * @throws FoursquareApiException
	 *             when something unexpected happens
	 * */

	public Result<VenuesSearchResult> getVenueSearch(String ll, Double llAcc, Double alt, Double altAcc, String query, Integer limit, String intent, String categoryId, String url, String providerId, String linkedId) throws FoursquareApiException {
		FoursquareApi foursquareApi = new FoursquareApi(clientId, clientSecret, callBackURL);
		Result<VenuesSearchResult> result = foursquareApi.venuesSearch(ll, llAcc, alt, altAcc, query, limit, intent, categoryId, url, providerId, linkedId);
		return result;
	}

	/**
	 * Generic search which takes a map of parameters The map is converted into
	 * parameters for the search API call with key/value pairs matching
	 * https://developer.foursquare.com/docs/venues/search
	 * 
	 * */
	public Result<VenuesSearchResult> getVenueSearch(Map<String, String> params) throws FoursquareApiException {
		FoursquareApi foursquareApi = new FoursquareApi(clientId, clientSecret, callBackURL);
		Result<VenuesSearchResult> result = foursquareApi.venuesSearch(params);
		return result;
	}
	
	
	
	
	public Result<Category[]> getCategories() throws FoursquareApiException {
		FoursquareApi foursquareApi = new FoursquareApi(clientId, clientSecret, callBackURL);
		Result<Category[]> result = foursquareApi.venuesCategories();
		
		return result;
	}
}
