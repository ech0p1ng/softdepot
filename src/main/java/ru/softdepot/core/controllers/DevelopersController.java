package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.softdepot.core.dao.DeveloperDAO;
import ru.softdepot.core.dao.TagDAO;
import ru.softdepot.core.models.Developer;
import ru.softdepot.core.models.Program;

import java.util.List;

@Controller
@RequestMapping("/developer")
public class DevelopersController {
    private final TagDAO tagDAO;
    private final DeveloperDAO developerDAO;

    public DevelopersController(TagDAO tagDAO, DeveloperDAO developerDAO) {
        this.tagDAO = new TagDAO();
        this.developerDAO = new DeveloperDAO();
    }

    @GetMapping("/id{id}")
    public String getDeveloper(@PathVariable("id") int id, Model model) {
        try {
            Developer developer = developerDAO.getById(id);
            List<Program> uploadedPrograms = developerDAO.getPrograms(id);

            model.addAttribute("developer", developer);
            model.addAttribute("programs", uploadedPrograms);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user/dev/index";
    }


}

