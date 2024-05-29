package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.softdepot.core.dao.AdministratorDAO;
import ru.softdepot.core.dao.TagDAO;
import ru.softdepot.core.models.Administrator;
import ru.softdepot.core.models.Tag;
import ru.softdepot.core.models.User;

import java.util.List;

@Controller
@RequestMapping("/administrator")
public class AdministratorsController {
    private final AdministratorDAO administratorDAO;
    private final TagDAO tagDAO;

    public AdministratorsController(AdministratorDAO administratorDAO, TagDAO tagDAO) {
        this.administratorDAO = administratorDAO;
        this.tagDAO = tagDAO;
    }

    @GetMapping("/id{id}")
    public String adminPublicPage(@PathVariable("id") int id, Model model) {
        if (CurrentUser.get() != null) {
            if (CurrentUser.get().getUserType() != User.Type.Administrator)
                return "redirect:/";
        }
        else return "redirect:/";

        System.out.printf("Getting administrator with id=%d...\n", id);

        try {
            Administrator admin = administratorDAO.getById(id);
            model.addAttribute("admin", admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "user/admin/index";
    }

    @GetMapping("/id{id}/categories")
    public String adminCategoriesPage(@PathVariable("id") int id, Model model) {
        if (CurrentUser.get() != null) {
            if (CurrentUser.get().getUserType() != User.Type.Administrator)
                return "redirect:/";
        }
        else return "redirect:/";

        try {
            Administrator admin = administratorDAO.getById(id);
            List<Tag> tags = tagDAO.getAll();
            model.addAttribute("admin", admin);
            model.addAttribute("tags", tags);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "user/admin/categories";
    }

}
