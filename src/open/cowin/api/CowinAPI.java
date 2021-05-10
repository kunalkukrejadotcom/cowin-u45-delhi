package open.cowin.api;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

import open.cowin.api.models.Centers;

public class CowinAPI extends API implements CowinInterface{

	public CowinAPI(String uri) {
		super(uri);
	}

	public Centers calendarByDistrict(int districtId, String date) {

		Response response = target.path("/api/v2/appointment/sessions/public/calendarByDistrict")
								  .queryParam("district_id", Integer.toString(districtId))
								  .queryParam("date", date)
								  .request()
								  .header("User-Agent", "Postman")
								  .cacheControl(CacheControl.valueOf("No Cache"))
								  .get();

		String responseStr = response.readEntity(String.class);
		return gson.fromJson(responseStr, Centers.class);

	}
	
}
