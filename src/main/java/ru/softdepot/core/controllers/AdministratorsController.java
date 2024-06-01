package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.softdepot.core.dao.*;
import ru.softdepot.core.models.*;

import java.util.List;

@Controller
@RequestMapping("/administrator")
public class AdministratorsController {
    private final AdministratorDAO administratorDAO;
    private final TagDAO tagDAO;
    private final PurchaseDAO purchaseDAO;
    private final ProgramDAO programDAO;
    private final CustomerDAO customerDAO;

    public AdministratorsController(AdministratorDAO administratorDAO, TagDAO tagDAO, PurchaseDAO purchaseDAO, ProgramDAO programDAO, CustomerDAO customerDAO) {
        this.administratorDAO = administratorDAO;
        this.tagDAO = tagDAO;
        this.purchaseDAO = purchaseDAO;
        this.programDAO = programDAO;
        this.customerDAO = customerDAO;
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
            model.addAttribute("admin", admin);
            List<Tag> tags = tagDAO.getAll();
            model.addAttribute("tags", tags);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "user/admin/categories";
    }


    @GetMapping("/id{id}/purchases")
    public String getAllPurchases(@PathVariable("id") int id, Model model) {
        if (CurrentUser.get() != null) {
            if (CurrentUser.get().getUserType() != User.Type.Administrator)
                return "redirect:/";
        }
        else return "redirect:/";

        List<Purchase> purchases = purchaseDAO.getAll();

        for (Purchase purchase : purchases) {
            Program program = null;
            Customer customer = null;
            try {
                program = programDAO.getById(purchase.getProgramId());
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            try {
                customer = customerDAO.getById(purchase.getCustomerId());
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }


            try {
                Administrator admin = administratorDAO.getById(id);
                model.addAttribute("admin", admin);
            } catch (Exception e) {
                e.printStackTrace();
            }

            purchase.setCustomerName(customer.getName());
            purchase.setCustomerLink("/customer/id" + customer.getId());

            purchase.setProgramName(program.getName());
            purchase.setProgramLink("/program/id" + program.getId());

        }

        model.addAttribute("purchases", purchases);


        return "user/admin/purchases";
    }

    @GetMapping("/id{id}/programs")
    public String getAllPrograms(@PathVariable("id") int id, Model model) {

        if (CurrentUser.get() != null) {
            if (CurrentUser.get().getUserType() != User.Type.Administrator)
                return "redirect:/";
        }
        else return "redirect:/";

        Administrator admin = null;

        try {
            admin = administratorDAO.getById(id);
            model.addAttribute("admin", admin);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Program> programs = programDAO.getAll();
        model.addAttribute("programs", programs);


        return "user/admin/programs";
    }
}
