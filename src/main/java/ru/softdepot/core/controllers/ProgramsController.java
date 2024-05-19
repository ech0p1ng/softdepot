package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.softdepot.core.dao.ProgramDAO;
import ru.softdepot.core.models.Program;

@Controller
@RequestMapping("/program")
public class ProgramsController {
    private ProgramDAO programDAO;

    public ProgramsController(ProgramDAO programDAO) {
        this.programDAO = programDAO;
    }

    @GetMapping("/id{id}")
    public String programPublicPage(@PathVariable("id") int id, Model model){
        try {
            Program program = programDAO.getById(id);
            float avgEstimation = programDAO.getAverageEstimation(program);
            program.setAverageEstimation(avgEstimation);


            model.addAttribute("program", program);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "program/index";
    }
}
