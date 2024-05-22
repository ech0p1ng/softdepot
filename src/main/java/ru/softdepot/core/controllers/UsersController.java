package ru.softdepot.core.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.softdepot.core.dao.AdministratorDAO;
import ru.softdepot.core.dao.CustomerDAO;
import ru.softdepot.core.dao.DeveloperDAO;
import ru.softdepot.core.models.Customer;
import ru.softdepot.core.models.Administrator;
import ru.softdepot.core.models.Developer;

@Controller
public class UsersController {
    CustomerDAO customerDAO = new CustomerDAO();
    DeveloperDAO developerDAO = new DeveloperDAO();
    AdministratorDAO administratorDAO = new AdministratorDAO();


    @GetMapping("/sign-in")
    public String register() {
        return "user/sign_in/index";
    }

    @PostMapping("/auth")
    public String registration(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Customer customer = null;
        Administrator administrator = null;
        Developer developer = null;

        System.out.printf("E-mail: %s\nPassword: %s\n", email, password);

        try {
            customer = customerDAO.getByEmailAndPassword(email, password);
        } catch (Exception e) { }

        try {
            administrator = administratorDAO.getByEmailAndPassword(email, password);
        } catch (Exception e) { }

        try {
            developer = developerDAO.getByEmailAndPassword(email, password);
        } catch (Exception e) { }

        if (customer != null) {
            CurrentUser.set(customer);
        } else if (administrator != null) {
            CurrentUser.set(administrator);
        } else if (developer != null) {
            CurrentUser.set(developer);
        } else {
            return "redirect:/sign-in";
        }



        return "redirect:/";
    }
}
