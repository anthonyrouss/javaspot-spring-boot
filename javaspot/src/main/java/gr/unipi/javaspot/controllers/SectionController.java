package gr.unipi.javaspot.controllers;

import gr.unipi.javaspot.exceptions.SectionNotFoundException;
import gr.unipi.javaspot.models.Section;
import gr.unipi.javaspot.services.SectionService;
import gr.unipi.javaspot.utils.ControllerConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sections")
@AllArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @GetMapping("/{id}/learn")
    public String getSectionLearnPage(Model model, @PathVariable int id) {
        Section selectedSection = sectionService.getById(id).orElseThrow(() -> new SectionNotFoundException(id));
        model.addAttribute("section", selectedSection);
        return ControllerConstants.LEARN.getHtmlFilePath();
    }

}
