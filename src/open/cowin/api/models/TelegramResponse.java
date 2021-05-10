package open.cowin.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramResponse {

	private boolean ok;
	private TelegramMessage result;
}
