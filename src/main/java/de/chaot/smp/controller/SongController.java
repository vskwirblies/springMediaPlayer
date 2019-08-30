package de.chaot.smp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.chaot.smp.datamodel.SongBO;
import de.chaot.smp.services.SongService;

@Controller
public class SongController {

	@Autowired
	private SongService songService;
	
    @RequestMapping("/songs")
    public String songs(Model model, @RequestParam(value = "page") int pageNoInGui) {
    	model.addAttribute("songList", songService.getPageContent(pageNoInGui-1));
    	model.addAttribute("totalPages", songService.getTotalPages());
    	model.addAttribute("page", pageNoInGui);
        return "songs";
    }

    @RequestMapping("/song")
    public String song(Model model, @RequestParam("songId")Long songId) {
    	SongBO song = songService.findById(songId);
    	model.addAttribute("song", song);
    	return "song";
    }
}