package de.chaot.smp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.chaot.smp.datamodel.ArtistBO;
import de.chaot.smp.services.ArtistService;
import de.chaot.smp.services.MusicbrainzWebServiceConsumer;

@Controller
public class ArtistController {
	
	private Logger logger = LoggerFactory.getLogger(ArtistController.class);

	@Autowired
	ArtistService artistService;
	
	@Autowired
	MusicbrainzWebServiceConsumer consumer;
	
    @RequestMapping("/artists")
    public String artists(Model model, @RequestParam(value = "page") int pageNoInGui) {
    	model.addAttribute("artistList", artistService.getPageContent(pageNoInGui - 1));
    	model.addAttribute("totalPages", artistService.getTotalPages());
    	model.addAttribute("page", pageNoInGui);
        return "artists";
    }
    
    @RequestMapping("/artist")
    public String artist(Model model, @RequestParam("artistId")Long artistId) {
    	logger.trace("call /album");
    	ArtistBO artistBO = artistService.findById(artistId);
    	model.addAttribute("artist", artistBO);
    	
    	String url = consumer.getImgForArtist(artistBO.getId());
    	model.addAttribute("artistImgUrl", url);
    	
        return "artist";
    }

}