package open.cowin.api.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramMessage {

	@SerializedName("message_id")
	private int messageId;
	private long date;
	private String text;
	
}
