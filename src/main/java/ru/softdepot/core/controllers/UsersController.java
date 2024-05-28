package ru.softdepot.core.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.softdepot.core.dao.AdministratorDAO;
import ru.softdepot.core.dao.CustomerDAO;
import ru.softdepot.core.dao.DeveloperDAO;
import ru.softdepot.core.dao.UserDAO;
import ru.softdepot.core.models.Customer;
import ru.softdepot.core.models.Administrator;
import ru.softdepot.core.models.Developer;
import ru.softdepot.core.models.User;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersController {
    CustomerDAO customerDAO = new CustomerDAO();
    DeveloperDAO developerDAO = new DeveloperDAO();
    AdministratorDAO administratorDAO = new AdministratorDAO();
    UserDAO userDAO = new UserDAO();
    HttpHeaders responseHeaders;
    String encoding = "UTF-8";

    public UsersController(UserDAO userDAO) {
        //установление кодировки utf-8 ответу,
        //чтобы избавиться от знаков вопроса вместо кириллицы
        responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_PLAIN);
        responseHeaders.set("Content-Type", "text/plain;charset=UTF-8");
    }


    @GetMapping("/sign-in")
    public String signIn(
            @RequestParam(value = "message", required = false) String message,
            Model model
    ) {
        if (message == null) message = "";
        model.addAttribute("message", message);
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
            try {
                responseHeaders.setContentType(MediaType.TEXT_PLAIN);
                responseHeaders.set("Content-Type", "text/plain;charset=UTF-8");
                String message = "Неверный логин или пароль";
                String encodedMsg = URLEncoder.encode(message, encoding);
                System.out.println(message);
                System.out.println(encodedMsg);
                return "redirect:/sign-in?message="+encodedMsg;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }



        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration(
            @RequestParam(name = "messages", required = false) String messages,
            Model model
    ) {
        if (messages == null) messages = "";
        String[] messagesArray = messages.split(",");
        model.addAttribute("messages", messagesArray);
        return "user/registration/index";
    }

    @PostMapping("/registration/check")
    public String registrationCheck(HttpServletRequest request) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        List<String> messages = new ArrayList<>();
        String errorMessage = "";

        // ПРОВЕРКА ИМЕНИ ПОЛЬЗОВАТЕЛЯ
        if (username.length() < 5) {
            messages.add("Имя пользователя должно состоять минимум из 5 символов");
        }

        // ПРОВЕРКА ПАРОЛЯ
        //регулярное выражение, проверяющее наличие цифры в строке
        if (!password.matches(".*\\d.*")) {
            messages.add("Пароль должен включать в себя хотя бы одну цифру");
        }
        if (password.length() < 8) {
            messages.add("Пароль должен быть длиной от 8 символов");
        }
        if (password.equals(password.toLowerCase())) {
            messages.add("Пароль должен включать в себя заглавные буквы");
        }

        //формирование и отправка сообщения об ошибке
        if (!messages.isEmpty()) {
            try {
                errorMessage = String.join(",",messages);
                System.out.println(errorMessage);
                String encodedMessage = URLEncoder.encode(errorMessage, encoding);
                return "redirect:/registration?messages="+encodedMessage;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


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
            try {
                System.out.printf("User with email \"%s\" already exists.\n", email);
                errorMessage = "Пользователь с таким e-mail уже существует";
                String encodedMessage = URLEncoder.encode(errorMessage, encoding);
                return "redirect:/sign-in?messages="+encodedMessage;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }

    @GetMapping("/log-out")
    public String logOut() {
        CurrentUser.set(null);
        return "redirect:/";
    }
}
