package ru.softdepot.core.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.softdepot.core.dao.DeveloperDAO;
import ru.softdepot.core.dao.TagDAO;
import ru.softdepot.core.models.Developer;
import ru.softdepot.core.models.Program;
import ru.softdepot.core.models.Tag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

@Controller
@RequestMapping("/developer")
public class DevelopersController {
    TagDAO tagDAO = new TagDAO();
    DeveloperDAO developerDAO = new DeveloperDAO();

    @GetMapping("/id{id}")
    public String getDeveloper(@PathVariable("id") int id, Model model) {
        try {
            Developer developer = developerDAO.getById(id);
            List<Program> uploadedPrograms = developerDAO.getPrograms(id);

            model.addAttribute("developer", developer);
            model.addAttribute("programs", uploadedPrograms);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user/dev/index";
    }

    @GetMapping("/id{id}/upload-program")
    public String uploadProgram(@PathVariable("id") int id, Model model) {
        try {
            Developer developer = developerDAO.getById(id);
            Vector<Tag> allTags = new Vector<>(tagDAO.getAll());
            Program program = new Program();
            program.setName("Squad");

            model.addAttribute("developer", developer);
            model.addAttribute("program", program);
            model.addAttribute("allTags", allTags);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user/dev/upload_program/index";
    }

    @PostMapping(value = "/id{id}/upload-program/check",
            consumes = "multipart/form-data")
    public String uploadProgramCheck(
            @PathVariable("id") int developerId,
            @ModelAttribute("developer") @Valid Developer developer,
            @ModelAttribute("program") @Valid Program program,
            @ModelAttribute("allTags") @Valid Vector<Tag> allTags,
            BindingResult bindingResult){


        if (bindingResult.hasErrors()){
            var errors = bindingResult.getAllErrors();
            for (var error : errors){
                System.out.println(error.getDefaultMessage());
            }
            return "redirect:/developer/id"+ developerId + "/upload-program";
        }

        System.out.println(program.getName());



//        return "redirect:/program/id1";
        return "redirect:/developer/id"+ developerId + "/upload-program";
    }

    private void uploadFile(String path, String fileName, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File uploadedFile = new File(path, fileName);
            file.transferTo(uploadedFile);
        } else throw new FileNotFoundException("No file uploaded");
    }
}