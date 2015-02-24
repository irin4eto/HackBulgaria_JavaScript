import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class HTTPLogger extends MyLoggerImpl {

	private String parameter1;
	private String parameter2;
	private String request;

	public HTTPLogger(String parameter1, String parameter2, String request) {
		super();
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
		this.request = request;
	}

	@Override
	public void log(int level, String message) throws ErrorIntegerLevelException {

		super.log(level, message);
		String urlParameters = String.format("param1=%s&param2=%s", parameter1, parameter2);
		byte[] postData = urlParameters.getBytes(Charset.forName("UTF-8"));
		int postDataLength = postData.length;
		try {
			
			URL url = new URL(request);
			HttpURLConnection cox = (HttpURLConnection) url.openConnection();
			cox.setDoOutput(true);
			cox.setDoInput(true);
			cox.setInstanceFollowRedirects(false);
			cox.setRequestMethod("POST");
			cox.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			cox.setRequestProperty("charset", "utf-8");
			cox.setRequestProperty("Content-Length",
					Integer.toString(postDataLength));
			cox.setUseCaches(false);
			DataOutputStream wr = new DataOutputStream(cox.getOutputStream());
			wr.write(postData);
			
		} catch (IOException e) {
			System.err.println("Cannot open and connect to this url!");
		}
	}
	

	public static void main(String[] argv) throws Exception {

		MyLogger consoleLogger = new HTTPLogger("a", "b", "http://example.com/index.php");
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
