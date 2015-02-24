import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger extends MyLoggerImpl {
	
	private String fileName;

	public FileLogger(String fileName) {
		super();
		this.fileName = fileName;
	}

	@Override
	public void log(int level, String message) throws ErrorIntegerLevelException {

		super.log(level, message);
		try {
			File file = new File(fileName);
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			if(file.length() > 0) {
			    bw.write("\n"+ getLogMessage());
			} else {
				 bw.write(getLogMessage());
			}
			bw.close();

		} catch (IOException e) {
			System.err.println("This file doesn`t exist!");
		}
	}

	public static void main(String[] argv) {

		MyLogger consoleLogger = new FileLogger("test.txt");
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
