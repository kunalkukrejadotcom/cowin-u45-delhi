package open.cowin.api;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

import open.cowin.api.models.TelegramResponse;

public class TelegramAPI extends API implements TelegramInterface {

	private static final String botId = "bot1821750359:AAE0j_Oin9koOi1nxlqd4pkkQBAYtTf_pmI";
	
	public TelegramAPI(String uri) {
		super(uri);
	}

	public void getUpdates() {

	}

	public TelegramResponse sendMessage(String chatId, String text) {

		Response response = target.path("/"+botId)
								  .path("/sendMessage")
								  .queryParam("chat_id", chatId)
								  .queryParam("text", text)
								  .request()
								  .header("User-Agent", "Postman")
								  .cacheControl(CacheControl.valueOf("No Cache"))
								  .get();

		String responseStr = response.readEntity(String.class);
		return gson.fromJson(responseStr, TelegramResponse.class);
//		System.out.println(responseStr);
	}

	public void unpinChatMessage(String chatId) {
	
		Response response = target.path("/"+botId)
				  .path("/unpinChatMessage")
				  .queryParam("chat_id", chatId)
				  .request()
				  .header("User-Agent", "Postman")
				  .cacheControl(CacheControl.valueOf("No Cache"))
				  .get();

		String responseStr = response.readEntity(String.class);
		System.out.println(responseStr);
		
	}

	public void pinChatMessage(String chatId, int messageId) {
		Response response = target.path("/"+botId)
				  .path("/pinChatMessage")
				  .queryParam("chat_id", chatId)
				  .queryParam("message_id", Integer.toString(messageId))
				  .request()
				  .header("User-Agent", "Postman")
				  .cacheControl(CacheControl.valueOf("No Cache"))
				  .get();

		String responseStr = response.readEntity(String.class);
		System.out.println(responseStr);
	}

}
