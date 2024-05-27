package ru.softdepot.core.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.softdepot.core.dao.*;
import ru.softdepot.core.exceptions.UserIsNotLoggedInException;
import ru.softdepot.core.models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/program")
public class ProgramsController {
    private final CustomerDAO customerDAO;
    private final CartDAO cartDAO;
    private ProgramDAO programDAO;
    private DeveloperDAO developerDAO;
    private TagDAO tagDAO;

    HttpHeaders responseHeaders;

    public ProgramsController(ProgramDAO programDAO, DeveloperDAO developerDAO, TagDAO tagDAO, CustomerDAO customerDAO, CartDAO cartDAO) {
        this.programDAO = programDAO;
        this.developerDAO = developerDAO;
        this.tagDAO = tagDAO;
        this.customerDAO = customerDAO;
        this.cartDAO = cartDAO;

        //установление кодировки utf-8 ответу,
        //чтобы избавиться от знаков вопроса вместо кириллицы
        responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_PLAIN);
        responseHeaders.set("Content-Type", "text/plain;charset=UTF-8");
    }

    @GetMapping("/id{id}")
    public String programPublicPage(@PathVariable("id") int id, Model model){
        try {
            Program program = programDAO.getById(id);
            model.addAttribute("program", program);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "program/index";
    }

    @GetMapping("/new")
    public String uploadProgram(Model model) {
        if (CurrentUser.get().getUserType() == User.Type.Customer)
            return "redirect:/";

        Developer developer = (Developer) CurrentUser.get();

        Vector<Tag> allTags = new Vector<>(tagDAO.getAll());

        model.addAttribute("developer", developer);
        model.addAttribute("program", new Program());
        model.addAttribute("allTags", allTags);
        model.addAttribute("programMedia", new ProgramMedia());
        return "user/dev/upload_program/index";
    }

    @PostMapping("/new/check")
    public String uploadProgramCheck(@ModelAttribute("program") Program program){
        if (CurrentUser.get().getUserType() == User.Type.Customer)
            return "redirect:/";

        System.out.println(program.getName());
        System.out.println(program.getShortDescription());
        System.out.println(program.getFullDescription());
        System.out.println(program.getPrice());

        return "redirect:/program/new";
    }

    @PostMapping("/id{id}/add-to-cart")
    public ResponseEntity<?> addToCart(Model model, @PathVariable("id") int programId) {
        responseHeaders.setContentType(MediaType.TEXT_PLAIN);
        responseHeaders.set("Content-Type", "text/plain;charset=UTF-8");
        Program program = null;
        String msg = "Unexpected error";
        if (CurrentUser.get() == null) {
            msg = "Чтобы добавить в корзину программу необходимо войти в аккаунт.";
            return new ResponseEntity<>(msg, responseHeaders, HttpStatus.BAD_REQUEST);
        }
        try {
            program = programDAO.getById(programId);

            if (cartDAO.containsProgram(CurrentUser.get().getId(), programId)) {
                msg = "Программа " + program.getName() + " уже находится в корзине!";
                return new ResponseEntity<>(msg, responseHeaders, HttpStatus.CONFLICT);
            }
            else {
                cartDAO.addProgram(CurrentUser.get().getId(), programId);
                msg = String.format("Программа %s добавлена в вашу корзину!\n",
                        program.getName(), CurrentUser.get().getId());
            }
        } catch (Exception e) {
            msg = "Program with [id="+programId+"] does not exist.";
            return new ResponseEntity<>(msg, responseHeaders, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/id{id}/remove-from-cart")
    public ResponseEntity<?> removeFromCart(Model model, @PathVariable("id") int programId) {
        responseHeaders.setContentType(MediaType.TEXT_PLAIN);
        responseHeaders.set("Content-Type", "text/plain;charset=UTF-8");
        Program program = null;
        String msg = "Unexpected error";
        if (CurrentUser.get() == null) {
            msg = "Чтобы удалить из корзины программу необходимо войти в аккаунт.";
            return new ResponseEntity<>(msg, responseHeaders, HttpStatus.BAD_REQUEST);
        }
        try {
            program = programDAO.getById(programId);

            if (cartDAO.containsProgram(CurrentUser.get().getId(), programId)) {
                cartDAO.deleteProgram(CurrentUser.get().getId(), programId);
                msg = "Программа " + program.getName() + " удалена из корзины!";
                return new ResponseEntity<>(msg, responseHeaders, HttpStatus.OK);
            }
            else {
                msg = "Программы " + program.getName() + " нет в вашей корзине";
                return new ResponseEntity<>(msg, responseHeaders, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            msg = "Program with [id="+programId+"] does not exist.";
            return new ResponseEntity<>(msg, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-cart")
    public ResponseEntity<?> getCart() {

        BigDecimal totalCost = BigDecimal.ZERO;
        List<Program> programsInCart = new ArrayList<>();
        if (CurrentUser.get() != null) {
            if (CurrentUser.get().getUserType() == User.Type.Customer) {
                try {
                    programsInCart = customerDAO.getProgramsInCart((Customer) CurrentUser.get());

                    for (Program program : programsInCart) {
                        totalCost = totalCost.add(program.getPrice());
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<List<Program>>(programsInCart, responseHeaders, HttpStatus.OK);
//        model.addAttribute("programsInCart", programsInCart);
//        model.addAttribute("totalCost", totalCost);
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
