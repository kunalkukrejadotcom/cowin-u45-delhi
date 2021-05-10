package open.cowin.api;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.Gson;

public class API {

	protected Client client;
	protected WebTarget target;
	protected Gson gson;
	
	public API(String uri) {
		System.out.println("Client init...");
		ClientConfig config = new ClientConfig();
		client = ClientBuilder.newClient(config);
		target = client.target(getBaseURI(uri));
		gson = new Gson();
	}

	private static URI getBaseURI(String uri) {
		return UriBuilder.fromUri(uri)
				.build();
	}

//https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?date=10-05-2021&district_id=150
}
