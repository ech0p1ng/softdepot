package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.softdepot.core.dao.ProgramDAO;
import ru.softdepot.core.models.Program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
//            float avgEstimation = programDAO.getAverageEstimation(program);
//            program.setAverageEstimation(avgEstimation);


//            program.setScreenshotsUrl(getScreenshots(program.getId()));

//            System.out.println(getHeader(program.getId()));

            for (String sh : program.getScreenshotsUrl()){
                System.out.println(sh);
            }

            model.addAttribute("program", program);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "program/index";
    }

    public List<String> getScreenshots(int id){
        List<String> screenshots = Arrays.asList(
            String.format("/program/id%d/screenshots/sh01.jpg", id),
            String.format("/program/id%d/screenshots/sh02.jpg", id),
            String.format("/program/id%d/screenshots/sh03.jpg", id),
            String.format("/program/id%d/screenshots/sh04.jpg", id),
            String.format("/program/id%d/screenshots/sh05.jpg", id),
            String.format("/program/id%d/screenshots/sh06.jpg", id),
            String.format("/program/id%d/screenshots/sh07.jpg", id),
            String.format("/program/id%d/screenshots/sh08.jpg", id),
            String.format("/program/id%d/screenshots/sh09.jpg", id),
            String.format("/program/id%d/screenshots/sh10.jpg", id)
        );
        return screenshots;

    }

    public String getHeader(int id){
        return String.format("/program/id%d/header.jpg", id);
    }
}
