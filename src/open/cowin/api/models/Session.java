package open.cowin.api.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Session {
	
	@SerializedName("session_id")
	private String sessionId;
	private String date;
	@SerializedName("available_capacity")
	private double availableCapacity;
	@SerializedName("min_age_limit")
	private int minAgelimit;
	private String vaccine;
	private String[] slots;

}
