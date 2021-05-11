package open.cowin.api;

import java.util.Date;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import open.cowin.models.TelegramResponse;

public class TelegramAPI extends API implements TelegramInterface {

	private static final String botId = "bot1821750359:AAE0j_Oin9koOi1nxlqd4pkkQBAYtTf_pmI";
	public TelegramAPI(String uri) {
		super(uri);
	}

	public void getUpdates() {

	}

	public TelegramResponse sendMessage(String chatId, String text) {

		logger.debug("TelegramAPI : sendMessage()");
		String updatedOn = "";
		Date date = new Date(System.currentTimeMillis());
		updatedOn = "Updated on : " + date.toLocaleString()+"\n";
		Response response = target.path("/"+botId)
								  .path("/sendMessage")
								  .queryParam("chat_id", chatId)
								  .queryParam("text", updatedOn + text)
								  .request()
								  .header("User-Agent", "Postman")
								  .cacheControl(CacheControl.valueOf("No Cache"))
								  .get();

		String responseStr = response.readEntity(String.class);
		logger.debug(responseStr);
		return gson.fromJson(responseStr, TelegramResponse.class);
	}

	public void unpinChatMessage(String chatId) {
		logger.debug("TelegramAPI : unpinChatMessage()");
		Response response = target.path("/"+botId)
				  .path("/unpinChatMessage")
				  .queryParam("chat_id", chatId)
				  .request()
				  .header("User-Agent", "Postman")
				  .cacheControl(CacheControl.valueOf("No Cache"))
				  .get();

		String responseStr = response.readEntity(String.class);
		logger.debug(responseStr);
		
	}

	public void pinChatMessage(String chatId, int messageId) {
		logger.debug("TelegramAPI : pinChatMessage()");
		Response response = target.path("/"+botId)
				  .path("/pinChatMessage")
				  .queryParam("chat_id", chatId)
				  .queryParam("message_id", Integer.toString(messageId))
				  .request()
				  .header("User-Agent", "Postman")
				  .cacheControl(CacheControl.valueOf("No Cache"))
				  .get();

		String responseStr = response.readEntity(String.class);
		logger.debug(responseStr);
	}

}
