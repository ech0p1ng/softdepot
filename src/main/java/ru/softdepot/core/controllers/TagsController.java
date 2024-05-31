package ru.softdepot.core.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.softdepot.core.dao.TagDAO;
import ru.softdepot.core.models.Tag;

import java.util.List;

@Controller
@RequestMapping("/tag")
public class TagsController {
    TagDAO tagDAO;

    public TagsController(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagDAO.getAll();
        return ResponseEntity.ok(tags);
    }

    @PostMapping("/save-tag")
    public ResponseEntity<Tag> saveTag(@RequestBody Tag tag) {
        if (!tagDAO.exists(tag.getId())) {
            try {
                tagDAO.add(tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            tagDAO.update(tag);
        }
        return ResponseEntity.ok(tag);
    }

    @PostMapping("/delete-tag/id{id}")
    public ResponseEntity<Tag> deleteTag(@PathVariable("id") int id) {
        if (tagDAO.exists(id))
            tagDAO.delete(id);
        return ResponseEntity.ok(null);
    }

}
