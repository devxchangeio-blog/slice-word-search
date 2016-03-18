/**
 * 
 */
package com.slice.word.search.manager;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.slice.word.search.dto.WordSearchDTO;
import com.slice.word.search.handler.WordSearchHandler;
import com.slice.word.search.util.ApplicationConstants;

/**
 * @author karthy
 *
 */
@Component
public class WordSearchManagerImpl implements WordSearchManager, ApplicationConstants {


	public WordSearchDTO wordSearcher(String word) throws IOException {

		WordSearchDTO wordSearch = new WordSearchDTO();
		wordSearch.setWordCount(WordSearchHandler.readAllLineFromAllFilesRecursively(path, word));
		wordSearch.setCounter(WordSearchHandler.wordCounter(word));
		
		return wordSearch;
	}
	
}
