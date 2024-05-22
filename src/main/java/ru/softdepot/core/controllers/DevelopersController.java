package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.softdepot.core.dao.DeveloperDAO;
import ru.softdepot.core.models.Developer;
import ru.softdepot.core.models.Program;

import java.util.List;

@Controller
@RequestMapping("/developer")
public class DevelopersController {
    DeveloperDAO dao = new DeveloperDAO();

    @GetMapping("/id{id}")
    public String getDeveloper(@PathVariable("id") int id, Model model){
        try {
            Developer developer = dao.getById(id);
            List<Program> uploadedPrograms = dao.getPrograms(id);
            model.addAttribute("developer", developer);
            model.addAttribute("programs", uploadedPrograms);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("DEVELOPER PAGE");
        return "user/dev/index";
    }
}
