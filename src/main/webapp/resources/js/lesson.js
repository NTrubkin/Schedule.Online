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
            alert('Занятие успешно создано');
            window.location.href = urlPrefix + "/";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + errorThrown);
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
            alert('Занятие успешно обновлено');
            window.location.href = urlPrefix + "/";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}

function deleteLesson() {
    if(confirm(DEL_LESSON_CONF)) {
        $.ajax({
            type: 'DELETE',
            url: urlPrefix + '/api/lesson/' + currentLesson.id,
            success: function (result) {
                alert('Занятие успешно удалено');
                window.location.href = urlPrefix + "/";
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.status + ' ' + errorThrown);
            }
        });
    }
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