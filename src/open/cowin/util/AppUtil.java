package open.cowin.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AppUtil {

	public static String exception2String(Throwable t) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);

		t.printStackTrace(pw);

		pw.flush();
		pw.close();

		return sw.toString();
	}
	
}
