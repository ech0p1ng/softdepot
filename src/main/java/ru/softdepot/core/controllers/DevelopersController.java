package ru.softdepot.core.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import java.util.Enumeration;
import java.util.List;

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
            List<Tag> allTags = tagDAO.getAll();
            model.addAttribute("developer", developer);
            model.addAttribute("allTags", allTags);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user/dev/upload_program/index";
    }

    @PostMapping("/id{id}/upload-program/check")
    public String uploadProgramCheck(
            @PathVariable("id") int developerId,
            @RequestParam(required = false, name="program-name") String programName,
            @RequestParam(required = false, name="short-description") String shortDescription,
            @RequestParam(required = false, name="full-description") String fullDescription,
            @RequestParam(required = false, name="price") BigDecimal price,
            @RequestParam(required = false, name="logo") MultipartFile logo,
            @RequestParam(required = false, name="win-installer") MultipartFile winInstaller,
            @RequestParam(required = false, name="linux-installer") MultipartFile linuxInstaller,
            @RequestParam(required = false, name="macos-installer") MultipartFile macosInstaller,
            @RequestParam(required = false, name="program-header-img") MultipartFile headerImg,
            @RequestParam(required = false, name="screenshots") MultipartFile[] file) {

        System.out.println(programName);
        System.out.println(shortDescription);
        System.out.println(fullDescription);
        System.out.println(price);
        System.out.println(developerId);
        System.out.println(winInstaller.getContentType());

        return "redirect:/program/id1";
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