package de.chaot.smp.controller;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaylistController {

	private Logger logger = LoggerFactory.getLogger(PlaylistController.class);
	
	
	private int currentSong = 0;
	

    @GetMapping(value = "/playlist/currentSong", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> currentSong() {
    	return Collections.singletonMap("response", "" + currentSong);
    }
}