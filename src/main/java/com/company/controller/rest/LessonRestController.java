package com.company.controller.rest;

import com.company.dao.api.AccountDAO;
import com.company.dao.api.LessonDAO;
import com.company.dao.api.PermissionDAO;
import com.company.dto.ErrorMessageDTO;
import com.company.dto.LessonDTO;
import com.company.dto.converter.IEntityConverter;
import com.company.model.Group;
import com.company.model.Lesson;
import com.company.model.Permission;
import com.company.service.auth.CustomUserDetails;
import com.company.service.sender.NotificationService;
import com.company.util.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// todo добавить проверки

@RestController
@RequestMapping(value = "/api/lesson")
public class LessonRestController {

    private static final String NOT_IN_GROUP_MSG = "Вы не состоите в этой группе";
    private static final String NOT_HAVE_PERMS_MSG = "Вы не имеете права для редактирования занятий";
    private static final String NOT_FOUND_MSG = "занятие не найдено";
    private static final String INVALID_NAME_MSG = "неверное имя занятия";
    private static final String INVALID_ROOM_MSG = "неверная аудитория занятия";
    private static final String INVALID_TEACHER_MSG = "неверный преподаватель занятия";
    private static final String INVALID_START_MSG = "неверное время начала занятия";
    private static final String INVALID_END_MSG = "неверное время конца занятия";
    private static final String AUTH_PRINCIPAL_MSG = "Authentication principal should implement ";


    private final LessonDAO lessonDAO;
    private final AccountDAO accountDAO;
    private final PermissionDAO permissionDAO;
    private final IEntityConverter<Lesson, LessonDTO> converter;
    private final NotificationService notificationService;

    @Autowired
    public LessonRestController(LessonDAO lessonDAO, AccountDAO accountDAO, PermissionDAO permissionDAO, IEntityConverter<Lesson, LessonDTO> converter, NotificationService notificationService) {
        this.lessonDAO = lessonDAO;
        this.accountDAO = accountDAO;
        this.permissionDAO = permissionDAO;
        this.converter = converter;
        this.notificationService = notificationService;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity createLesson(@RequestBody LessonDTO lessonDTO, @RequestParam(name = "notify", defaultValue = "true") boolean notify, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }
        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Group group = accountDAO.read(accountId).getGroup();

        ErrorMessageDTO errorDTO = new ErrorMessageDTO();

        if (!checkLessonGroupAndPerms(group, lessonDTO, accountId, errorDTO)) {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        checkLessonFields(lessonDTO, errorDTO);
        if (errorDTO.getMessages() == null) {
            lessonDAO.create(converter.restore(lessonDTO));
            if (notify) {
                notificationService.sendScheduleNotifications(lessonDTO.getGroupId());
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{lessonId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLesson(@PathVariable int lessonId, @RequestParam(name = "notify", defaultValue = "true") boolean notify, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }
        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Group group = accountDAO.read(accountId).getGroup();
        ErrorMessageDTO errorDTO = new ErrorMessageDTO();
        if (group == null) {
            errorDTO.addMessage(NOT_IN_GROUP_MSG);
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        Lesson lesson = lessonDAO.read(lessonId);
        if (lesson == null) {
            errorDTO.addMessage(NOT_FOUND_MSG);
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        if (!lesson.getGroup().getId().equals(group.getId())) {
            errorDTO.addMessage(NOT_IN_GROUP_MSG);
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        Permission permission = permissionDAO.readByAccount(accountId);
        if (!permission.getLessonsEdit()) {
            errorDTO.addMessage(NOT_HAVE_PERMS_MSG);
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        if (notify) {
            notificationService.sendScheduleNotifications(lesson.getGroup().getId());
        }
        lessonDAO.delete(lesson);
        return new ResponseEntity(HttpStatus.OK);
    }

    ResponseEntity<List<LessonDTO>> readLessonsByGroup(int groupId) {
        List<Lesson> lessons = lessonDAO.readAllByGroup(groupId);
        return new ResponseEntity<>(converter.convert(lessons), HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateLesson(@RequestBody LessonDTO lessonDTO, @RequestParam(name = "notify", defaultValue = "true") boolean notify, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }
        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Group group = accountDAO.read(accountId).getGroup();

        ErrorMessageDTO errorDTO = new ErrorMessageDTO();

        if (!checkLessonGroupAndPerms(group, lessonDTO, accountId, errorDTO)) {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        if (lessonDAO.read(lessonDTO.getId()) == null) {
            errorDTO.addMessage(NOT_FOUND_MSG);
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        checkLessonFields(lessonDTO, errorDTO);
        if (errorDTO.getMessages() == null) {
            Lesson lesson = converter.restore(lessonDTO);
            lessonDAO.update(lesson);
            if (notify) {
                notificationService.sendScheduleNotifications(lessonDTO.getGroupId());
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }
    }

    private void checkLessonFields(LessonDTO lessonDTO, ErrorMessageDTO errorDTO) {
        if (!CommonValidator.isNameValid(lessonDTO.getName())) {
            errorDTO.addMessage(INVALID_NAME_MSG);
        }

        if (lessonDTO.getRoom() == null) {
            errorDTO.addMessage(INVALID_ROOM_MSG);
        }

        if (!CommonValidator.isNameValid(lessonDTO.getTeacher())) {
            errorDTO.addMessage(INVALID_TEACHER_MSG);
        }

        if (lessonDTO.getStartDatetime() == null) {
            errorDTO.addMessage(INVALID_START_MSG);
        }

        if (lessonDTO.getEndDatetime() == null) {
            errorDTO.addMessage(INVALID_END_MSG);
        }
    }

    private boolean checkLessonGroupAndPerms(Group group, LessonDTO lessonDTO, int accountId, ErrorMessageDTO errorDTO) {
        if (group == null || group.getId().intValue() != lessonDTO.getGroupId()) {
            errorDTO.addMessage(NOT_IN_GROUP_MSG);
            return false;
        }
        else {
            Permission permission = permissionDAO.readByAccount(accountId);
            if (!permission.getLessonsEdit()) {
                errorDTO.addMessage(NOT_HAVE_PERMS_MSG);
                return false;
            }
        }
        return true;
    }
}
