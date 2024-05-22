package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.softdepot.core.dao.ProgramDAO;
import ru.softdepot.core.models.Program;
import ru.softdepot.core.models.User;

import java.util.List;

@Controller
public class MainPageController {
    ProgramDAO programDAO = new ProgramDAO();

    @GetMapping("/")
    public String mainPage(Model model){
        List<Program> programs = programDAO.getAll();

        try {
            System.out.println(CurrentUser.get().getUserType());
        }
        catch (Exception e) {}

        model.addAttribute("programs", programs);

        return "main_page/index";
    };
}
