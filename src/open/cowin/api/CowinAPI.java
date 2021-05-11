package open.cowin.api;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import open.cowin.models.Centers;

public class CowinAPI extends API implements CowinInterface{

	public CowinAPI(String uri) {
		super(uri);
	}

	public Centers calendarByDistrict(int districtId, String date) {

		Response response = target.path("/api/v2/appointment/sessions/public/calendarByDistrict")
								  .queryParam("district_id", Integer.toString(districtId))
								  .queryParam("date", date)
								  .request()
//								  .header("authority", "cdn-api.co-vin.in")
//								  .header("User-Agent", "Chrome/90.0.4430.93")
//								  .accept(MediaType.APPLICATION_JSON)
								  .cacheControl(CacheControl.valueOf("no-cache"))
								  .get();
		logger.debug(response.getHeaders());
		String responseStr = response.readEntity(String.class);
		logger.debug(responseStr);
		return gson.fromJson(responseStr, Centers.class);

	}
	
}
