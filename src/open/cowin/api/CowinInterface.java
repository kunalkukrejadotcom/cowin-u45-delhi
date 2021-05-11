package open.cowin.api;

import open.cowin.models.Centers;

public interface CowinInterface {

	public Centers calendarByDistrict(int districtId, String date);
}
