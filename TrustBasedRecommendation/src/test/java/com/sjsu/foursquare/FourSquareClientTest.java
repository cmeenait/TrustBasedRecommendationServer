package com.sjsu.foursquare;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
import fi.foyt.foursquare.api.io.Response;

public class FourSquareClientTest {

	@Test
	public void nearVenueTest() {
		String clientID = "FQ2N5PKZRPQVZFWLP3MC3H43O5N2YUCTPYFGS3W503OGOGQP";
		String clientSecret = "42ZECLAWYZOBY5W1EOSV2PFUJZCRCXFTOZN0ITSGEZAB4YDY";
		String callBackURL = "http://www.testapp.com";

		FourSquareClient fourSquareClient = new FourSquareClient(clientID, clientSecret, callBackURL);

		try {
			Result<VenuesSearchResult> result = fourSquareClient.getVenueSearch("santa clara", null, null, null, "4bf58dd8d48988d142941735", null, null, null);
			if (result.getMeta().getCode() == 200) {
				// if query was ok we can finally we do something with the data
				for (CompactVenue venue : result.getResult().getVenues()) {
					// TODO: Do something we the data
					System.out.println(venue.getName());
					System.out.println(venue.getLocation().getAddress());
					System.out.println(venue.getLocation().getCity());
					System.out.println("------------------");
				}
			}

			else {
				System.out.println("Error occured: ");
				System.out.println("  code: " + result.getMeta().getCode());
				System.out.println("  type: " + result.getMeta().getErrorType());
				System.out.println("  detail: " + result.getMeta().getErrorDetail());
			}

		} catch (FoursquareApiException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void coordinatesVenueTest() {
		String clientID = "FQ2N5PKZRPQVZFWLP3MC3H43O5N2YUCTPYFGS3W503OGOGQP";
		String clientSecret = "42ZECLAWYZOBY5W1EOSV2PFUJZCRCXFTOZN0ITSGEZAB4YDY";
		String callBackURL = "http://www.testapp.com";

		FourSquareClient fourSquareClient = new FourSquareClient(clientID, clientSecret, callBackURL);

		try {
			Result<VenuesSearchResult> result = fourSquareClient.getVenueSearch("+37.189396, -121.705327", null, null, null, null, null, null, "4bf58dd8d48988d142941735" , null, null, null);
			if (result.getMeta().getCode() == 200) {
				// if query was ok we can finally we do something with the data
				for (CompactVenue venue : result.getResult().getVenues()) {
					// TODO: Do something we the data
					System.out.println(venue.getName());
				}
			}

			else {
				System.out.println("Error occured: ");
				System.out.println("  code: " + result.getMeta().getCode());
				System.out.println("  type: " + result.getMeta().getErrorType());
				System.out.println("  detail: " + result.getMeta().getErrorDetail());
			}

		} catch (FoursquareApiException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void genericVenueSearch() {
		String clientID = "FQ2N5PKZRPQVZFWLP3MC3H43O5N2YUCTPYFGS3W503OGOGQP";
		String clientSecret = "42ZECLAWYZOBY5W1EOSV2PFUJZCRCXFTOZN0ITSGEZAB4YDY";
		String callBackURL = "http://www.testapp.com";

		FourSquareClient fourSquareClient = new FourSquareClient(clientID, clientSecret, callBackURL);
		Map<String, String> searchParams = new HashMap<String, String>();
		searchParams.put("near", "santa clara");
		searchParams.put("limit", "2");
		try {
			Result<VenuesSearchResult> result = fourSquareClient.getVenueSearch(searchParams);
			if (result != null) {
				// if query was ok we can finally we do something with the data
				for (CompactVenue venue : result.getResult().getVenues()) {
					// TODO: Do something we the data
					System.out.println(venue.getName());
				}
			}

		} catch (FoursquareApiException e) {

			e.printStackTrace();
		}

	}
	
	
	
	
	

}
