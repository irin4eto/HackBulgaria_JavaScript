import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.csvreader.CsvReader;

public class QueriesCSVFile {

	private String CSVFileName;
	private List<Hashtable<String, Object>> records;

	private static final String SELECT = "SELECT";
	private static final String LIMIT = "LIMIT";
	private static final String SHOW = "SHOW";
	private static final String SUM = "SUM";
	private static final String FIND = "FIND";

	private static final String SPACE_COLUMN = "-";
	

	public QueriesCSVFile(String CSVFileName) {
		super();
		this.CSVFileName = CSVFileName;
		this.readCSVFile();
	}
	

	public void readCSVFile() {
		CsvReader products;
		records = new ArrayList<Hashtable<String, Object>>();

		try {

			products = new CsvReader(CSVFileName);
			while (products.readRecord()) {
				Hashtable<String, Object> record = new Hashtable<String, Object>();
				for (int i = 0; i < products.getColumnCount(); i++) {
					Object value = products.get(i);
					record.put(products.getHeaders()[i], value);
				}
				records.add(record);
			}
			products.close();

		} catch (FileNotFoundException e) {
			System.err.println("The file is not founded!");
		} catch (IOException e) {
			System.err.println("The file cannot be readed!");
		}
	}
	

	public void validateAndExecuteStatement(String query)
			throws WrongQueryExceptrion {

		boolean callQuery = true;
		int limit = records.size();

		// validate and execute SELECT (LIMIT) statement
		if (query.toLowerCase().contains(SELECT.toLowerCase())) {
			String[] words = query.split(" ");
			if (words[0] == SELECT) {

				String[] columns = query.replaceAll("\\s+", "").substring(6)
						.split(",");
				for (int i = 0; i < columns.length; i++) {

					if (columns[i] == LIMIT
							&& (i == 1 || !columns[i].matches("\\d+"))) {
						callQuery = false;
					}

					if (columns[i].matches("\\d+")) {
						limit = Integer.parseInt(columns[i]);
					}

					if (!records.contains(columns[i]) && columns[i] != LIMIT
							&& !columns[i].matches("\\d+")) {
						callQuery = false;
					}
				}
				if (callQuery) {
					selectLimitStatement(columns, limit);
					return;
				} else {
					throw new WrongQueryExceptrion("Wrong SELECT statement");
				}
			} else {
				throw new WrongQueryExceptrion("Wrong SELECT statement");
			}
		}

		// validate and execute SUM LIMIT statement
		else if (query.toLowerCase().contains(SUM.toLowerCase())) {
			callQuery = true;
			String[] words = query.split(" ");
			if (words[0] == SUM && !records.contains(words[1])) {
				for (int i = 0; i < records.size(); i++) {
					Object value = records.get(i).get(words[1]);
					if (!value.toString().matches("\\d+")) {
						callQuery = false;
					}
				}
			}
			if (callQuery) {
				sumStatement(words[1]);
				return;
			} else {
				throw new WrongQueryExceptrion("Wrong SELECT statement");
			}
		}

		// validate and execute SHOW LIMIT statement
		else if (query.trim().toLowerCase().equals(SHOW.toLowerCase())) {
			showStatement();
			return;
		}

		// validate and execute FIND LIMIT statement
		else if (query.toLowerCase().contains(FIND.toLowerCase())) {
			String[] words = query.split(" ");
			if (words[0].equals(FIND) && words.length == 2) {
				findStatement(words[1]);
			}
		} else {
			throw new WrongQueryExceptrion("Wrong SELECT statement");
		}
	}
	

	public void selectLimitStatement(String[] columns, int limit) {
		StringBuilder columnNames = new StringBuilder("| ");
		StringBuilder columnSpace = new StringBuilder("|");
		StringBuilder values = new StringBuilder("| ");

		for (String column : columns) {
			columnNames.append(column + " |");
			columnSpace.append(StringUtils.repeat(SPACE_COLUMN,
					column.length() + 2) + "|");
		}

		for (int i = 0; i < limit; i++) {
			Hashtable<String, Object> record = records.get(i);
			for (Entry<String, Object> e : record.entrySet()) {
				values.append(e.getValue() + " |");
			}
		}

		System.out.println(columnNames.toString());
		System.out.println(columnSpace.toString());
		System.out.println(values.toString());
	}
	

	public void sumStatement(String column) {
		int sum = 0;
		for (Hashtable<String, Object> record : records) {
			sum += Integer.parseInt(record.get(column).toString());
		}
		System.out.println(sum);
	}
	

	public void showStatement() {
		StringBuilder columnNames = new StringBuilder("");
		for (Map.Entry<String, Object> column : records.get(0).entrySet()) {
			columnNames.append(column.getKey() + ", ");
		}
		System.out.println(columnNames.toString());
	}
	

	public void findStatement(String word) {
		StringBuilder columnNames = new StringBuilder("| ");
		StringBuilder columnSpace = new StringBuilder("|");
		StringBuilder values = new StringBuilder("| ");

		for (Map.Entry<String, Object> column : records.get(0).entrySet()) {
			columnNames.append(column.getKey() + " |");
			columnSpace.append(StringUtils.repeat(SPACE_COLUMN, column.getKey()
					.length() + 2)
					+ "|");
		}

		for (int i = 0; i < records.size(); i++) {
			Hashtable<String, Object> record = records.get(i);
			for (Map.Entry<String, Object> column : record.entrySet()) {
				if (column.getValue().toString().contains(word)) {
					for (Map.Entry<String, Object> val : record.entrySet()) {
						values.append(val.getValue() + " |");
					}
				}
			}
		}
		
		System.out.println(values.toString());
	}

}
