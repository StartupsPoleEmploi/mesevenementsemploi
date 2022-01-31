package fr.pe.domaine.peactions.controller;


import fr.pe.domaine.peactions.dto.TagDto;
import fr.pe.domaine.peactions.dto.TypeTagDto;
import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.mapper.CustomMapper;
import fr.pe.domaine.peactions.model.Tag;
import fr.pe.domaine.peactions.model.TypeTag;
import fr.pe.domaine.peactions.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tag")
@CrossOrigin(origins = "*")
public class TagController {

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagService service;

    @Autowired
    private CustomMapper customMapper;


    @GetMapping("/all")
    public ResponseEntity<List<TagDto>> getAllTags() {
        logger.info("Get all the tags...");
        List<Tag> tags = service.getAllTags();

        List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag : tags) {
            tagDtoList.add(customMapper.tagToTagDto(tag));
        }

        return ResponseEntity.ok(tagDtoList);
    }

    @GetMapping("type/all")
    public ResponseEntity<List<TypeTagDto>> getAllTypeTags() {
        logger.info("Get all type tags...");
        List<TypeTag> typeTags = service.getAllTypeTags();

        List<TypeTagDto> typeTagDtoList = new ArrayList<>();
        for (TypeTag typeTag : typeTags) {
            typeTagDtoList.add(customMapper.map(typeTag, TypeTagDto.class));
        }

        return ResponseEntity.ok(typeTagDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable(value = "id") long tagId) throws ResourceNotFoundException {
        logger.info("Get tag by id...");
        return ResponseEntity.ok(customMapper.map(service.getTagById(tagId), TagDto.class));
    }

    @GetMapping("/tags/type/{typeId}")
    public ResponseEntity<List<TagDto>> getAllTagsByType(@PathVariable(value = "typeId") long typeId) throws ResourceNotFoundException {
        logger.info("Get all the tags by type...");
        List<Tag> tags = service.getAllTagsByType(typeId);

        List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag : tags) {
            tagDtoList.add(customMapper.tagToTagDto(tag));
        }

        return ResponseEntity.ok(tagDtoList);
    }
}
