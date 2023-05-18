package recipes.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import recipes.dao.RecipeDao;
import recipes.exception.DbException;

public class RecipeService {
	private static final String SCHEMA_FILE = "recipe_schema.sql"; // service layer interacts with schema

	private RecipeDao recipeDao = new RecipeDao(); // can now access DAO from service

	public void createAndPopulateTables() {
		loadFromFile(SCHEMA_FILE);
	}

	private void loadFromFile(String fileName) { // load schema file from jar
		String content = readFileContent(fileName);
		List<String> sqlStatements = convertContentToSqlStatements(content);

		// test, printing sql statements
		sqlStatements.forEach(line -> System.out.println(line));

		recipeDao.executeBatch(sqlStatements);
	}

	private List<String> convertContentToSqlStatements(String content) {
// convert all SQL statements to a string, do this by checking for semicolons
		// remove all SQL comments
		content = removeComments(content);
		// remove whitespace, replace w single space
		content = replaceWhitespaceSequencesWithSingleSpace(content);
		// return all lines as a list
		return exctractLinesFromContent(content);
	}

	private List<String> exctractLinesFromContent(String content) {
		List<String> lines = new LinkedList<>();

		while (!content.isEmpty()) { // while not empty
			int semicolon = content.indexOf(";");

			if (semicolon == -1) {
				if (!content.isBlank()) {
					lines.add(content);
				}
				content = "";
			} else {
				lines.add(content.substring(0, semicolon).trim()); // goes from line up to semicolon and trims whtspc
				content = content.substring(semicolon + 1);
			}
		}

		return lines;
	}

	private String replaceWhitespaceSequencesWithSingleSpace(String content) {
		return content.replaceAll("\\s+", " "); // \s tells to look for wht space, replace with single spc
	}

	private String removeComments(String content) {
		StringBuilder builder = new StringBuilder(content); // initialize builder w content
		int commentPos = 0; // counter

// loop thru content, look for comments, go from comment to comment
		while ((commentPos = builder.indexOf("-- ", commentPos)) != -1) {
			int eolPos = builder.indexOf("\n", commentPos + 1); // end of line pos

			if (eolPos == -1) {
				builder.replace(commentPos, builder.length(), ""); // at end of file
			} else {
				builder.replace(commentPos, eolPos + 1, "");
			}
		}

		return builder.toString();
	}

	private String readFileContent(String fileName) {
		try {
			Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
			return Files.readString(path); // returning the string from the file
		} catch (Exception e) {
			throw new DbException(e);
		}
	}

// test if works
	public static void main(String[] args) {
		new RecipeService().createAndPopulateTables();
	}

} // end this program