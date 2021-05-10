package open.cowin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import open.cowin.api.CowinAPI;
import open.cowin.api.TelegramAPI;
import open.cowin.api.models.Center;
import open.cowin.api.models.Session;
import open.cowin.api.models.TelegramResponse;


public class App extends TimerTask  {

	private static final int districtBegin = 140;
	private static final int districtEnd = 150;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	private static final String cseChatId = "-1001476781242";
	private static CowinAPI cowinApi = new CowinAPI("https://cdn-api.co-vin.in");
	private static TelegramAPI telegramApi = new TelegramAPI("https://api.telegram.org");
	//"1344228213"; // this is personal chat
	
	private String lastSentMessage;
	
	@Override
	public void run() {
		try
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 1);
			String date = sdf.format(cal.getTime());
			System.out.println("Looking for date : " + date);
			ArrayList<Center> allCenters = new ArrayList<>();
			for(int i = districtBegin; i <= districtEnd; i++)
			{
				allCenters.addAll(cowinApi.calendarByDistrict(i, date).getCenters());
			}
			
			System.out.println("total number of centers : " + allCenters.size());
			
			if(allCenters.isEmpty())
				return;
			
			System.out.println("Filtering");
			ArrayList<Center> validCenters = new ArrayList<Center>();
			for(Center c : allCenters)
			{
				Center x = filter(c);
				if(!x.getSessions().isEmpty())
					validCenters.add(x);
			}
			
			if (!validCenters.isEmpty()) {
				System.out.println("Valid center count : " + validCenters.size());
				String message = createTelegramMessage(validCenters);
				if(!lastSentMessage.equals(message))
				{
					TelegramResponse resp = telegramApi.sendMessage(cseChatId, message);
					int msgId = resp.getResult().getMessageId();
					telegramApi.unpinChatMessage(cseChatId);
					telegramApi.pinChatMessage(cseChatId, msgId);
					lastSentMessage = message;
				}
				else
				{
					System.out.println("Same as last message. Not Sending");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String createTelegramMessage(ArrayList<Center> validCenters) {
		StringBuilder sb = new StringBuilder();
		Date date = new Date(System.currentTimeMillis());
		sb.append("Updated on : " + date.toLocaleString());
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
