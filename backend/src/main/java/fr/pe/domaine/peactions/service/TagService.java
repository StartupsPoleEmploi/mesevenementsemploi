package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.Tag;
import fr.pe.domaine.peactions.model.TypeTag;
import fr.pe.domaine.peactions.repository.TagRepository;
import fr.pe.domaine.peactions.repository.TypeTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagService.class);
    @Autowired
    private TagRepository repository;

    @Autowired
    private TypeTagRepository typeTagRepository;

    public List<Tag> getAllTags() {
        return repository.findAll();
    }

    public Tag getTagById(long tagId) throws ResourceNotFoundException {
        Tag tag = repository.findById(tagId).
                orElseThrow(() -> new ResourceNotFoundException("Tag not found for this id:: " + tagId));
        return tag;
    }

    public Tag tagById(long tagId) throws ResourceNotFoundException {
        logger.info("getTagById for this id:: " + tagId);
        Tag tag = repository.findById(tagId).
                orElseThrow(() -> new ResourceNotFoundException("Tag not found for this id:: " + tagId));
        return tag;
    }

    public TypeTag typeTagById(long typeTagId) throws ResourceNotFoundException {
        logger.info("getTagById for this id:: " + typeTagId);
        TypeTag typeTag = typeTagRepository.findById(typeTagId).
                orElseThrow(() -> new ResourceNotFoundException("Tag not found for this id:: " + typeTagId));
        return typeTag;
    }

    public List<TypeTag> getAllTypeTags() {

        return typeTagRepository.findAll();
    }

    public List<Tag> getAllTagsByType(long typeId) throws ResourceNotFoundException {
        TypeTag typeTag = this.typeTagById(typeId);
        return repository.findAllByTypeTag(typeTag);
    }
}
