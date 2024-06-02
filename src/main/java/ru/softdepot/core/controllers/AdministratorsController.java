package ru.softdepot.core.controllers;

import jakarta.servlet.ServletContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.softdepot.config.SpringConfig;
import ru.softdepot.core.dao.*;
import ru.softdepot.core.models.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @PostMapping("/program/new")
    public ResponseEntity<?> addGame(
            @RequestParam("name") String name,
            @RequestParam("short_description") String shortDescription,
            @RequestParam("full_description") String fullDescription,
            @RequestParam("price") BigDecimal price,
            @RequestParam("tags[]") List<Integer> tags) {
        int id = -1;

        List<Tag> tagList = new ArrayList<>();

        for (int tagId : tags) {
            tagList.add(tagDAO.getById(tagId));
        }

        Program program = new Program(name, price, fullDescription, 1, shortDescription, tagList);

        ///
        System.out.println(program.getName());
        System.out.println(program.getShortDescription());
        System.out.println(program.getFullDescription());
        System.out.println(program.getPrice());
        System.out.println("Теги:");
        for (Tag tag : tagList) {
            System.out.println(tag.getName());
        }
        ///

        id = programDAO.add(program);

        for (int tagId : tags) {
            try {
                programDAO.addTag(id, tagId, 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (id == -1) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(id);

    }

    @PostMapping("/program/id{program-id}/add-info")
    public ResponseEntity<?> addInfoToGame(
            @PathVariable("program-id") int programId,
            @RequestPart("logo") MultipartFile logo,
            @RequestPart("header") MultipartFile header,
            @RequestPart("screenshots") List<MultipartFile> screenshots,
            ServletContext servletContext) {
        Program program = null;

        String programsPath = servletContext.getRealPath(SpringConfig.getProgramsPath());
        File programPath = new File(programsPath + "/id" + programId);
        File installersPath = new File(programsPath + "/id" + programId + "/installers");
        File screenshotsPath = new File(programsPath + "/id" + programId + "/screenshots");

        programPath.mkdir();
        installersPath.mkdir();
        screenshotsPath.mkdir();

        try {
            program = programDAO.getById(programId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        program.setLogo(logo);
        program.setHeaderImg(header);
        program.setScreenshots(screenshots);

        System.out.println(logo.getOriginalFilename());
        System.out.println(header.getOriginalFilename());
        for (var file : screenshots) {
            System.out.println(file.getOriginalFilename());
        }

        programDAO.update(program);
        return ResponseEntity.ok().build();
    }
}