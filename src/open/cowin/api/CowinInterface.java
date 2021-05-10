package open.cowin.api;

import open.cowin.api.models.Centers;

public interface CowinInterface {

	public Centers calendarByDistrict(int districtId, String date);
}
