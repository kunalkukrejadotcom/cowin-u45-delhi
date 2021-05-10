package open.cowin.api.models;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Center {

	public Center(Center center) {
		this(center.centerId, center.name, center.address, center.stateName, center.districtName, center.pincode,
				center.feeType, new ArrayList<Session>(center.sessions));
	}

	@SerializedName("center_id")
	private long centerId;
	private String name;
	private String address;
	@SerializedName("state_name")
	private String stateName;
	@SerializedName("district_name")
	private String districtName;
	private long pincode;
	@SerializedName("fee_type")
	private String feeType;
	private ArrayList<Session> sessions;

}
