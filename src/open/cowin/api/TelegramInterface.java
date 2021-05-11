package open.cowin.api;

import open.cowin.models.TelegramResponse;

public interface TelegramInterface {
	
	public void getUpdates();

	public TelegramResponse sendMessage(String chatId, String text);
	
	public void unpinChatMessage(String chatId);
	
	public void pinChatMessage(String chatId, int messageId);
}
