package com.company.controller.rest;

import com.company.dao.api.LessonDAO;
import com.company.dao.impl.DAO;
import com.company.dto.LessonDTO;
import com.company.dto.converter.LessonConverter;
import com.company.model.Account;
import com.company.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// todo добавить проверки

@RestController
@RequestMapping(value = "/api/lesson")
public class LessonRestController {

    private final LessonDAO lessonDAO;
    private final LessonConverter converter;

    @Autowired
    public LessonRestController(LessonDAO lessonDAO, LessonConverter converter) {
        this.lessonDAO = lessonDAO;
        this.converter = converter;
    }

    @RequestMapping(value = "/{lessonId}", method = RequestMethod.GET)
    public ResponseEntity<LessonDTO> readLesson(@PathVariable int lessonId) {
        return new ResponseEntity<>(converter.convert(lessonDAO.read(lessonId)), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity createLesson(@RequestBody LessonDTO lessonDTO) {
        lessonDAO.create(converter.restore(lessonDTO));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{lessonId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLesson(@PathVariable int lessonId) {
        lessonDAO.delete(lessonId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Lesson>> readLessonsByGroup() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    ResponseEntity<List<LessonDTO>> readLessonsByGroup(int groupId) {
        List<Lesson> lessons = lessonDAO.readAllByGroup(groupId);
        return new ResponseEntity<>(converter.convert(lessons), HttpStatus.OK);
    }
}
