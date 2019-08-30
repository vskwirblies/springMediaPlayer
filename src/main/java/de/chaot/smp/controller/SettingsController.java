package de.chaot.smp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.chaot.smp.services.FileWalkerService;

@Controller
public class SettingsController {

	private Logger logger = LoggerFactory.getLogger(SettingsController.class);
	
	@Autowired
	FileWalkerService fileWalkerService;
	
    @RequestMapping("/settings")
    public String settings(Model model) {
    	logger.info("call settings");
    	model.addAttribute("key", "defaultValue");
        return "settings";
    }

    @PostMapping("/settings")
    public String settingsPost() {
    	logger.info("call settingsPost ");
    	fileWalkerService.importFilesFromFolder("");;
        return "settings";
    }
}