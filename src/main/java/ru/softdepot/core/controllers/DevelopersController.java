package ru.softdepot.core.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.softdepot.core.dao.DeveloperDAO;
import ru.softdepot.core.models.Developer;
import ru.softdepot.core.models.Program;
import ru.softdepot.core.models.User;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/developer")
public class DevelopersController {
    DeveloperDAO dao = new DeveloperDAO();

    @GetMapping("/id{id}")
    public String getDeveloper(@PathVariable("id") int id, Model model) {
        try {
            Developer developer = dao.getById(id);
            List<Program> uploadedPrograms = dao.getPrograms(id);
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
            Developer developer = dao.getById(id);
            model.addAttribute("developer", developer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user/dev/upload_program/index";
    }

    @PostMapping("/id{id}/upload-program/check")
    public String uploadProgramCheck(@PathVariable("id") int id,
                                     @RequestParam("program-name") String programName,
                                     @RequestParam("short-description") String description,
                                     @RequestParam("full-description") String fullDescription,
                                     @RequestParam("price") BigDecimal price,
                                     @RequestParam("logo") MultipartFile file,
                                     @RequestParam("win-installer") MultipartFile winInstaller,
                                     @RequestParam("linux-installer") MultipartFile linuxInstaller,
                                     @RequestParam("macos-installer") MultipartFile macosInstaller,
                                     @RequestParam("program-header-img") MultipartFile headerImg,
                                     @RequestParam("screenshots") MultipartFile[] files) {
        try {
            Developer developer = dao.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user/dev/upload_program/index";
    }
}