/**
 * 
 */
package com.slice.word.search.manager;

import java.io.IOException;

import com.slice.word.search.dto.WordSearchDTO;

/**
 * @author karthy
 *
 */
public interface WordSearchManager {
	
	WordSearchDTO wordSearcher(String word) throws IOException;
}
