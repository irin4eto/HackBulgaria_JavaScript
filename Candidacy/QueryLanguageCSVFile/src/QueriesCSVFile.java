import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

public class QueriesCSVFile {

	private String CSVFileName;
	private List<RecordCSVFile> records;
	
	private final String firstColumn = "id";
	private final String secondColumn = "name";
	private final String thirdColumn = "hometown";
	
	private static final String SELECT = "SELECT";
	private static final String LIMIT = "LIMIT";
	private static final String SHOW = "SHOW";
	private static final String SUM = "SUM";
	private static final String FIND = "FIND";

	public QueriesCSVFile(String CSVFileName) {
		super();
		this.CSVFileName = CSVFileName;
		this.readCSVFile();
	}

	public void readCSVFile() {
		CsvReader products;
		records = new ArrayList<RecordCSVFile>();
		
		try {
			
			products = new CsvReader(CSVFileName);
			while (products.readRecord()) {
				int firstColumn = Integer.parseInt(products.get(this.firstColumn));
				String secondColumn = products.get(this.secondColumn);
				String thirdColumn = products.get(this.thirdColumn);
				RecordCSVFile record = new RecordCSVFile(firstColumn, secondColumn, thirdColumn);
				records.add(record);
			}
			products.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("The file is not founded!");
		} catch (IOException e) {
			System.err.println("The file cannot be readed!");
		}
	}
	
	
	public void validateAndExecuteStatement(String query) throws WrongQueryExceptrion{
		
		// validate and execute select statement
		if(query.toLowerCase().contains(SELECT.toLowerCase())) {
			
			String[] words = query.split(" ");//trim().isEmpty() ? 0 : query.trim().split("\\s+").length;
			if(words.length == 2) {
				if(words[0] == SELECT && words[1].matches("\\d+")) {
					selectStatement(query);
				}
			}
		}
		
	}
	
	public void selectStatement(String query) {
		if(!query.toLowerCase().contains(LIMIT.toLowerCase())) {
			
		}
	}


}
