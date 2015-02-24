
public class ConsoleLogger extends MyLoggerImpl {

	@Override
	public void log(int level, String message) throws ErrorIntegerLevelException {
		super.log(level, message);
		System.out.println(getLogMessage());
	}
	
	public static void main(String[] argv) {
		
		MyLogger consoleLogger = new ConsoleLogger();
		try {
			
			consoleLogger.log(1, "Hello world!");
			consoleLogger.log(2, "The application`s digital signature has error!");
			consoleLogger.log(3, "Checking file system on C!");
			consoleLogger.log(4, "Error level!");
			
		} catch (ErrorIntegerLevelException e) {
			System.err.println("Error level! It must be 1, 2 or 3");
		}
		
	}

}
