package de.chaot.smp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.chaot.smp.datamodel.PlaylistBO;
import de.chaot.smp.datamodel.SongBO;
import de.chaot.smp.exceptions.SmpException;
import de.chaot.smp.services.SongService;

@Controller
public class PlayerController {

	private Logger logger = LoggerFactory.getLogger(PlayerController.class);
	
	private SongService songService;
	
	private PlaylistBO playlistBO;
	
	private int currentSongIndex = 0;
	
	@Autowired
	public PlayerController(SongService songService) {
		this.songService = songService;
		this.playlistBO = new PlaylistBO();
//		try {
//			this.playlistBO.addSong(songService.findById((long) 3));
//		} catch (SmpException e) {
//			e.printStackTrace();
//		}
	}
	
    
	@GetMapping("/srcFromSongId")
    public void srcFromSongId(HttpServletResponse response, @RequestParam(value = "songId")long songId) throws IOException {
		SongBO songBO = songService.findById(songId);
    	File file = new File(songBO.getFilepath());
    	FileInputStream in = new FileInputStream(file);
    	response.setContentType("audio/mp3");
    	IOUtils.copy(in, response.getOutputStream());
    }
	
    @GetMapping("/srcFromPlaylist")
    public void srcFromPlaylist(HttpServletResponse response) throws IOException {

    	logger.info("call srcFromPlaylist() currentSong is "+ currentSongIndex);
    	if (playlistBO.getSongs().isEmpty()) return;
    	
    	SongBO songBO = playlistBO.getSongs().get(currentSongIndex);
    	File file = new File(songBO.getFilepath());
    	FileInputStream in = new FileInputStream(file);
    	response.setContentType("audio/mp3");
    	IOUtils.copy(in, response.getOutputStream());
    }
    
    @GetMapping("/getCurrentSongIndex")
    public void getCurrentSongIndex(HttpServletResponse response) throws IOException {
    	response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    	PrintWriter out = response.getWriter();
    	out.print(currentSongIndex);
    	out.flush();
    }
    @PostMapping("/onSongEnded")
    public void onSongEnded() {
    	logger.info("call onSongEnded. Ended song was "+currentSongIndex);
    	currentSongIndex = (++currentSongIndex % this.playlistBO.getSongs().size());
    	logger.info("Next song is "+ currentSongIndex);
    }
    
    @PostMapping("/addToPlaylist")
    public String addToPlaylist(@RequestParam(value = "songId")Long songId) throws IOException {
    	logger.info("call addToPlaylist()");
    	songId = songId.longValue();
    	SongBO songBO = songService.findById(songId);
    	logger.info("got a song: "+songBO.getFilepath());
    	try {
			this.playlistBO.addSong(songBO);
		} catch (SmpException e) {
			e.printStackTrace();
		}
    	logger.info("done with addToPlaylist()");
    	return "index";
    }
    
    @GetMapping("/player")
    public String player(Model model) {
    	model.addAttribute("playlist", playlistBO.getSongs());
    	currentSongIndex = 0;
    	return "player";
    }
    
    @PostMapping("/playSongFromPlaylist")
    public String playSongFromPlaylist(Model model) {
    	logger.info("call playSongFromPlaylist");
    	return "player";
    }
}