package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.softdepot.core.dao.CustomerDAO;
import ru.softdepot.core.models.Customer;

@Controller
@RequestMapping("/customer")
public class CustomersController {
    CustomerDAO customerDAO;

    public CustomersController() {
        customerDAO = new CustomerDAO();
    }

    @GetMapping("/id{id}")
    public String customerPublicPage(@PathVariable("id") int id, Model model) {
        try {
            Customer customer = customerDAO.getById(id);
            model.addAttribute("customer", customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "users/customer/customer";
    }
}
