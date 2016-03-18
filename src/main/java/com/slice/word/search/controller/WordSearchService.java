/**
 * 
 */
package com.slice.word.search.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.slice.word.search.dto.WordSearchDTO;
import com.slice.word.search.handler.WordSearchHandler;
import com.slice.word.search.manager.WordSearchManager;

/**
 * @author karthy
 *
 */
@RestController
public class WordSearchService {

	@Autowired
	public WordSearchManager manager;

	static Logger logger = Logger.getLogger(WordSearchHandler.class);

	@RequestMapping(value = "/wordsearch/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WordSearchDTO> get(@PathVariable("word") String word) {

		logger.debug("Entering WordSearchService -> get method");

		WordSearchDTO wordSearchDTO = null;

		if (StringUtils.isEmpty(word))
			return new ResponseEntity<WordSearchDTO>(HttpStatus.BAD_REQUEST);

		try {
			wordSearchDTO = manager.wordSearcher(word);

		} catch (IOException e) {
			return new ResponseEntity<WordSearchDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<WordSearchDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.debug("Exiting WordSearchService -> get method");
		return new ResponseEntity<WordSearchDTO>(wordSearchDTO, HttpStatus.OK);

	}

}
