package ru.softdepot.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.softdepot.core.dao.CustomerDAO;
import ru.softdepot.core.models.Customer;
import ru.softdepot.core.models.Program;
import ru.softdepot.core.models.Review;

import java.util.List;

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
            List<Program> purchasedPrograms = customerDAO.getPurchasedPrograms(customer);
            List<Review> reviews = customerDAO.getAllReviewsByCustomer(customer);

            model.addAttribute("customer", customer);
            model.addAttribute("purchasedPrograms", purchasedPrograms);
            System.out.println(customer.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return "user/customer/customer";
        return "user/customer/temp";
    }
}
