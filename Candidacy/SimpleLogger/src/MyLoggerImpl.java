import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MyLoggerImpl implements MyLogger {
	
	private String logMessage;

	private final static String INFO = "INFO";
	private final static String WARNING = "WARNING";
	private final static String PLSCHECKFFS = "PLSCHECKFFS";

	@Override
	public void log(int level, String message) throws ErrorIntegerLevelException {
		String logLevelString = "";

			if (level == 1) {
				logLevelString = INFO;
			} else if (level == 2) {
				logLevelString = WARNING;
			} else if (level == 3) {
				logLevelString = PLSCHECKFFS;
			} else {
				throw new ErrorIntegerLevelException(
						"Wrong level! It must be 1, 2 or 3!");
			}
			
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		String timestamp = df.format(new Date());
		
		logMessage = String.format("%s::%s::%s", logLevelString, timestamp, message);
	}

	public String getLogMessage() {
		return logMessage;
	}
	
}
