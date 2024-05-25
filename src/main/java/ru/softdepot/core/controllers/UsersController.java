package ru.softdepot.core.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.softdepot.core.dao.AdministratorDAO;
import ru.softdepot.core.dao.CustomerDAO;
import ru.softdepot.core.dao.DeveloperDAO;
import ru.softdepot.core.dao.UserDAO;
import ru.softdepot.core.models.Customer;
import ru.softdepot.core.models.Administrator;
import ru.softdepot.core.models.Developer;
import ru.softdepot.core.models.User;

@Controller
public class UsersController {
    CustomerDAO customerDAO = new CustomerDAO();
    DeveloperDAO developerDAO = new DeveloperDAO();
    AdministratorDAO administratorDAO = new AdministratorDAO();
    UserDAO userDAO = new UserDAO();

    public UsersController(UserDAO userDAO) {
    }


    @GetMapping("/sign-in")
    public String signIn() {
        return "user/sign_in/index";
    }

    @PostMapping("/sign-in/check")
    public String signInCheck(HttpServletRequest request) {
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

    @GetMapping("/registration")
    public String registration() {
        return "user/registration/index";
    }

    @PostMapping("/registration/check")
    public String registrationCheck(HttpServletRequest request) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        Customer customer = null;
        Developer developer = null;

        System.out.printf("E-mail: %s\nPassword: %s\n", email, password);

        try {
            customer = customerDAO.getByEmailAndPassword(email, password);
        } catch (Exception e) { }

        try {
            developer = developerDAO.getByEmailAndPassword(email, password);
        } catch (Exception e) { }

        if (customer == null && developer == null) {
            User.Type type = null;

            if (userType.equals("customer"))       type = User.Type.Customer;
            else if (userType.equals("developer")) type = User.Type.Developer;
            System.out.println(userType);

            User newUser = new User(email, password, username, type);

            try {
                userDAO.add(newUser);
                if (type == User.Type.Customer) {
                    CurrentUser.set(customerDAO.getByEmailAndPassword(email, password));
                } else if (type == User.Type.Developer) {
                    CurrentUser.set(developerDAO.getByEmailAndPassword(email, password));
                }
            } catch (Exception e) {
                e.printStackTrace();
//                return "redirect:/registration";
            }
        }
        else {
            System.out.printf("User with email \"%s\" already exists.\n", email);
            return "redirect:/sign-in";
        }
        return "redirect:/";
    }

    @PostMapping("/log-out")
    public String logOut(HttpServletRequest request) {
        CurrentUser.set(null);
        return "redirect:/";
    }
}
