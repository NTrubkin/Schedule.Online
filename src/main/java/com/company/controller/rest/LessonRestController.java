package com.company.controller.rest;

import com.company.dao.api.LessonDAO;
import com.company.dao.impl.DAO;
import com.company.dto.LessonDTO;
import com.company.dto.converter.IEntityConverter;
import com.company.dto.converter.LessonConverter;
import com.company.model.Account;
import com.company.model.Lesson;
import com.company.service.sender.NotificationService;
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
    private final IEntityConverter<Lesson, LessonDTO> converter;
    private final NotificationService notificationService;

    @Autowired
    public LessonRestController(LessonDAO lessonDAO, IEntityConverter<Lesson, LessonDTO> converter, NotificationService notificationService) {
        this.lessonDAO = lessonDAO;
        this.converter = converter;
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/{lessonId}", method = RequestMethod.GET)
    public ResponseEntity<LessonDTO> readLesson(@PathVariable int lessonId) {
        return new ResponseEntity<>(converter.convert(lessonDAO.read(lessonId)), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity createLesson(@RequestBody LessonDTO lessonDTO, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        lessonDAO.create(converter.restore(lessonDTO));
        if (notify) {
            notificationService.sendScheduleNotifications(lessonDTO.getGroupId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{lessonId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLesson(@PathVariable int lessonId, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        Lesson lesson = lessonDAO.read(lessonId);
        if (notify) {
            notificationService.sendScheduleNotifications(lesson.getGroup().getId());
        }
        lessonDAO.delete(lesson);
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

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateLesson(@RequestBody LessonDTO lessonDTO, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        Lesson lesson = converter.restore(lessonDTO);
        lessonDAO.update(lesson);
        if (notify) {
            notificationService.sendScheduleNotifications(lessonDTO.getGroupId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
