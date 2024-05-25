package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.w3c.dom.ls.LSOutput;
import ru.softdepot.core.dao.CustomerDAO;
import ru.softdepot.core.dao.ProgramDAO;
import ru.softdepot.core.models.Customer;
import ru.softdepot.core.models.Program;
import ru.softdepot.core.models.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {
    ProgramDAO programDAO;
    CustomerDAO customerDAO;

    public MainPageController(ProgramDAO programDAO, CustomerDAO customerDAO) {
        this.programDAO = programDAO;
        this.customerDAO = customerDAO;
    }

    @GetMapping("/")
    public String mainPage(Model model){
        List<Program> programs = programDAO.getAll();

        model.addAttribute("programs", programs);
        model.addAttribute("user", CurrentUser.get());
        model.addAttribute("typeCustomer", User.Type.Customer);
        model.addAttribute("typeDeveloper", User.Type.Developer);
        model.addAttribute("typeAdministrator", User.Type.Administrator);

        return "main_page/index";
    };
}
