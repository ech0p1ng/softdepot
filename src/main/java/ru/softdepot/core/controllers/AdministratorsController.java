package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.softdepot.core.dao.AdministratorDAO;
import ru.softdepot.core.models.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorsController {
    private final AdministratorDAO administratorDAO;

    public AdministratorsController(AdministratorDAO administratorDAO) {
        this.administratorDAO = administratorDAO;
    }

    @GetMapping("/id{id}")
    public String adminPublicPage(@PathVariable("id") int id, Model model) {
        System.out.printf("Getting administrator with id=%d...\n", id);

        try {
            Administrator admin = administratorDAO.getById(id);
            model.addAttribute("admin", admin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user/admin/index";
    }

}
