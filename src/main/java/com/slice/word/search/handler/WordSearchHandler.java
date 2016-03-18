/**
 * 
 */
package com.slice.word.search.handler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.slice.word.search.util.ApplicationConstants;

/**
 * @author karthy
 *
 */
public class WordSearchHandler implements ApplicationConstants {

	static Logger logger = Logger.getLogger(WordSearchHandler.class);
	static Map<String, Integer> wordCounterMap = null;

	static {

		if (null == wordCounterMap)
			wordCounterMap = new HashMap<String, Integer>();
	}

	public static Integer readAllLineFromAllFilesRecursively(final String path, final String word) throws IOException {

		logger.debug("Entering method readAllLineFromAllFilesRecursively");
		Integer wordCount = 0;
		final List<String> lines = new ArrayList<String>();
		Map<String, Integer> wordsWithCount = new HashMap<String, Integer>();

		try {

			Files.walk(Paths.get(path), FileVisitOption.FOLLOW_LINKS)
					.filter((p) -> !p.toFile().isDirectory() && p.toFile().getAbsolutePath().endsWith(extension))
					.forEach(p -> fileLinesToList(p, lines));

			lines.stream().filter(line -> !line.isEmpty()).forEach((line) -> getWordCount(line, word, wordsWithCount));

			if (wordsWithCount.containsKey(word))
				wordCount = wordsWithCount.get(word);

		} catch (final IOException e) {

			logger.error(e.getStackTrace());
			throw new IOException(e);

		} catch (final Exception e) {

			logger.error(e.getStackTrace());
			throw new IOException(e);
		}

		logger.debug("Exiting method readAllLineFromAllFilesRecursively");
		return wordCount;
	}

	private static void fileLinesToList(final Path file, final List<String> lines) {

		logger.debug("Entering method fileLinesToList");

		try {
			Files.lines(file, Charset.defaultCharset()).map(String::trim).filter(s -> !s.isEmpty()).forEach(lines::add);

		} catch (final IOException e) {

			logger.error(e.getStackTrace());
		}
		logger.debug("Exiting method fileLinesToList");
	}

	public static void getWordCount(String line, String key, Map<String, Integer> wordsWithCount) {

		logger.debug("Entering method getWordCount");

		if (!line.isEmpty()) {

			String word;
			Pattern pattern = Pattern.compile("([a-zA-Z]+)");
			Matcher matcher = pattern.matcher(line.toLowerCase());

			while (matcher.find()) {

				word = matcher.group(0);
				if (!StringUtils.isEmpty(word) && word.equalsIgnoreCase(key)) {

					if (0 == wordsWithCount.size())
						wordsWithCount.put(word, 1);
					else
						wordsWithCount.put(word, wordsWithCount.get(word) + 1);
				}
			}
		}
		logger.debug("Exiting method getWordCount");

	}

	public static Integer wordCounter(String word) {

		logger.debug("Entering method wordCounter");
		Integer counter = 0;

		if (wordCounterMap.containsKey(word))
			counter = wordCounterMap.get(word);

		counter++;
		wordCounterMap.put(word, counter);

		logger.debug("Entering method wordCounter");
		return counter;
	}

}
