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
    CustomerDAO customerDAO = new CustomerDAO();

    @GetMapping("/id{id}")
    public String customerPublicPage(@PathVariable("id") int id, Model model) {
        Customer customer = null;
        try {
            customer = customerDAO.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Program> purchasedPrograms = null;
        try {
            purchasedPrograms = customerDAO.getPurchasedPrograms(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Review> reviews = customerDAO.getAllReviewsByCustomer(customer);
            List<Program> programsInCart = customerDAO.getProgramsInCart(customer);

            for (Review review : reviews) {
                Program program = purchasedPrograms.stream().filter(p -> p.getId() == review.getProgramId()).findFirst().get();
                review.setProgramName(program.getName());
            }

            model.addAttribute("customer", customer);
            model.addAttribute("purchasedPrograms", purchasedPrograms);
            model.addAttribute("reviews", reviews);
            model.addAttribute("programsInCart", programsInCart);
        return "user/customer/index";
    }
}
