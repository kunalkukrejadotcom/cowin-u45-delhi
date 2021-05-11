package open.cowin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import open.cowin.api.CowinAPI;
import open.cowin.api.TelegramAPI;
import open.cowin.models.Center;
import open.cowin.models.Session;
import open.cowin.models.TelegramResponse;


public class App extends TimerTask  {

	private static final Logger logger = Logger.getLogger(App.class);
	
	private static final int DISTRICT_BEGIN = 140;
	private static final int DISTRICT_END = 150;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	private static final String CSE_CHAT_ID = "-1001476781242";
	private static CowinAPI cowinApi = new CowinAPI("https://cdn-api.co-vin.in");
	private static TelegramAPI telegramApi = new TelegramAPI("https://api.telegram.org");
	//"1344228213"; // this is personal chat
	
	private String lastSentMessage = "";
	
	
	
	@Override
	public void run() {
		try
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 1);
			String date = sdf.format(cal.getTime());
			logger.debug("Looking for date : " + date);
			ArrayList<Center> allCenters = new ArrayList<>();
			for(int i = DISTRICT_BEGIN; i <= DISTRICT_END; i++)
			{
				allCenters.addAll(cowinApi.calendarByDistrict(i, date).getCenters());
			}
			
			logger.debug("total number of centers : " + allCenters.size());
			
			if(allCenters.isEmpty())
				return;
			
			logger.debug("Filtering");
			ArrayList<Center> validCenters = new ArrayList<>();
			for(Center c : allCenters)
			{
				Center x = filter(c);
				if(!x.getSessions().isEmpty())
					validCenters.add(x);
			}
			
			if (!validCenters.isEmpty()) {
				logger.debug("Valid center count : " + validCenters.size());
				String message = createTelegramMessage(validCenters);
				if(!lastSentMessage.equals(message))
				{
					TelegramResponse resp = telegramApi.sendMessage(CSE_CHAT_ID, message);
					int msgId = resp.getResult().getMessageId();
					telegramApi.unpinChatMessage(CSE_CHAT_ID);
					telegramApi.pinChatMessage(CSE_CHAT_ID, msgId);
					lastSentMessage = message;
				}
				else
				{
					logger.debug("Same as last message. Not Sending");
				}
			}
			else
			{
				logger.debug("No Valid centers Found. not sending");
			}
		}
		catch (Exception e) {
			logger.error("App.run()");
			logger.error(e.getMessage());
		}
	}
	
	private String createTelegramMessage(ArrayList<Center> validCenters) {
		StringBuilder sb = new StringBuilder();
		for(Center c : validCenters)
		{
			sb.append("\n");
			sb.append(c.getDistrictName());
			sb.append(" :: ");
			sb.append(c.getPincode());
			sb.append("\n");
			sb.append(c.getName());
			sb.append("\n");
			sb.append(c.getAddress());
			sb.append("\n");
			for(Session s : c.getSessions())
			{
				sb.append(s.getAvailableCapacity());
				sb.append(" Slot(s) for ");
				sb.append(s.getVaccine());
				sb.append(" available on ");
				sb.append(s.getDate());
				sb.append("\n");
			}
			sb.append("Fee type : ");
			sb.append(c.getFeeType());
		}
		return sb.toString();
	}

	Center filter(Center center) {
	
		Center temp;
		temp = new Center(center);
		for(int i = 0; i < center.getSessions().size(); i++)
		{
			Session s = center.getSessions().get(i);
			if(s.getMinAgelimit() >= 45 || s.getAvailableCapacity() < 1)
			{
				temp.getSessions().remove(s);
			}
		}
		return temp;
	}
	
}
