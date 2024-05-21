package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.softdepot.core.dao.ProgramDAO;
import ru.softdepot.core.models.Program;

import java.util.List;

@Controller
public class MainPageController {
    ProgramDAO programDAO = new ProgramDAO();

    public MainPageController() {
        this.programDAO = new ProgramDAO();
    }

    @GetMapping("/")
    public String mainPage(Model model){
        List<Program> programs = programDAO.getAll();
        for (Program program : programs) {
            float avgEstimation = programDAO.getAverageEstimation(program);
            program.setAverageEstimation(avgEstimation);

        }
        model.addAttribute("programs", programs);

        return "mainPage/index";
    };
}
