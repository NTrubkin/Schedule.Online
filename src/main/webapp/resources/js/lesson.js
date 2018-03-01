var currentLesson = {
    name : 'Новое занятие',
    room : 0,
    teacher : 'Преподаватель',
    tags : [],
    startDatetime: Date.now(),
    endDatetime: Date.now(),
    groupId : 0
};
const DEL_LESSON_CONF = 'Удалить занятие?';

function initNewLessonPage() {
    currentLesson.groupId = group.id;
    putCurrentLessonToFields();
}

function initLessonPage(lesson) {
    currentLesson = JSON.parse(lesson);
    putCurrentLessonToFields();
}

function readCurrentLessonFromFields() {
    currentLesson.name = $('#nameFld').val();
    currentLesson.room = $('#roomFld').val();
    currentLesson.teacher = $('#teacherFld').val();
    currentLesson.tags = $('#tagsFld').val().split(' ');
    currentLesson.startDatetime = new Date($('#startDTPiker').val());
    currentLesson.endDatetime = new Date($('#endDTPiker').val());
}

function putCurrentLessonToFields() {
    $('#nameFld').val(currentLesson.name);
    $('#roomFld').val(currentLesson.room);
    $('#teacherFld').val(currentLesson.teacher);
    $('#tagsFld').val(showTags(currentLesson.tags));
    $('#startDTPiker').val(new Date(currentLesson.startDatetime).format(dateFormat.masks.isoDateTime, false));
    $('#endDTPiker').val(new Date(currentLesson.endDatetime).format(dateFormat.masks.isoDateTime, false));
}

function createLesson() {
    readCurrentLessonFromFields();
    $.ajax({
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        url: urlPrefix + '/api/lesson',
        data: JSON.stringify(currentLesson),
        success: function (result) {
            bootbox.alert('Занятие успешно создано', function(){
                window.location.href = urlPrefix + (window.mobilecheck() ? '/?m=true' : '/');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            bootbox.alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}

function updateLesson() {
    readCurrentLessonFromFields();
    $.ajax({
        type: 'PUT',
        url: urlPrefix + '/api/lesson',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(currentLesson),
        success: function (result) {
            bootbox.alert('Занятие успешно обновлено', function(){
                window.location.href = urlPrefix + (window.mobilecheck() ? '/?m=true' : '/');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            bootbox.alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}

function deleteLesson() {
    bootbox.confirm(DEL_LESSON_CONF, function (result) {
        if (result) {
            $.ajax({
                type: 'DELETE',
                url: urlPrefix + '/api/lesson/' + currentLesson.id,
                success: function () {
                    bootbox.alert('Занятие успешно удалено', function(){
                        window.location.href = urlPrefix + (window.mobilecheck() ? '/?m=true' : '/');
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    bootbox.alert(jqXHR.status + ' ' + errorThrown);
                }
            });
        }
    });
}

function showTags(tags) {
    if(tags.length === 0) {
        return '';
    }
    else {
        tags = $.map(tags, function (tag) {
            return tag.name;
        });
        return tags.join(" ");
    }
}