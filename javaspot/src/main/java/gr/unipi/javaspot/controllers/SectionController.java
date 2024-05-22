package gr.unipi.javaspot.controllers;

import gr.unipi.javaspot.exceptions.SectionNotFoundException;
import gr.unipi.javaspot.models.Section;
import gr.unipi.javaspot.services.SectionService;
import gr.unipi.javaspot.utils.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/{id}/learn")
    public String getSectionLearnPage(Model model, @PathVariable int id) {
        Section selectedSection = sectionService.getById(id).orElseThrow(() -> new SectionNotFoundException(id));
        model.addAttribute("section", selectedSection);
        return ControllerConstants.LEARN.getHtmlFilePath();
    }

}
