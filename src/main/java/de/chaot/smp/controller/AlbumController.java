package de.chaot.smp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.chaot.smp.datamodel.AlbumBO;
import de.chaot.smp.services.AlbumService;

@Controller
public class AlbumController {

	private Logger logger = LoggerFactory.getLogger(AlbumController.class);
	
	@Autowired
	private AlbumService albumservice;
	
    @RequestMapping("/albums")
    public String albums(Model model, @RequestParam(value = "page") int pageNoInGui) {
    	List<AlbumBO> albumList = albumservice.getPageContent(pageNoInGui-1);
    	model.addAttribute("albumList", albumList);
    	model.addAttribute("totalPages", albumservice.getTotalPages());
    	model.addAttribute("page", pageNoInGui);
        return "albums";
    }

    @RequestMapping("/album")
    public String album(Model model, @RequestParam("albumId")Long albumId) {
    	logger.trace("call /album");
    	AlbumBO albumBO = albumservice.findById(albumId);
    	albumservice.sortSongsByTrackNumber(albumBO);
    	model.addAttribute("album", albumBO);
        return "album";
    }
}