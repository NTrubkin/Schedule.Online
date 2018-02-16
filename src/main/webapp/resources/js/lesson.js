const DEFAULT_LESSON_VALUES = {
    name : 'Новое занятие',
    room : 0,
    teacher : 'Преподаватель',
    tags : [],
    startDatetime: Date.now(),
    endDatetime: Date.now()
};
const CURRENT_LESSON_ID = 0;

function initNewLessonPage() {
    putLessonToFields(DEFAULT_LESSON_VALUES);
}

function initLessonPage(id) {
    $.ajax({
        type: 'GET',
        url: urlPrefix + '/api/lesson/' + id,
        success: function (result) {
            putLessonToFields(result);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}

function readLessonFromFields() {
    var lesson = {};
    lesson.name = $('#nameFld').val();
    lesson.room = $('#roomFld').val();
    lesson.teacher = $('#teacherFld').val();
    lesson.tags = $('#tagsFld').val().split(' ');
    lesson.startDatetime = new Date($('#startDTPiker').val());
    lesson.endDatetime = new Date($('#endDTPiker').val());
    return lesson;
}

function putLessonToFields(lesson) {
    $('#nameFld').val(lesson.name);
    $('#roomFld').val(lesson.room);
    $('#teacherFld').val(lesson.teacher);
    $('#tagsFld').val(showTags(lesson.tags));
    $('#startDTPiker').val(new Date(lesson.startDatetime).format(dateFormat.masks.isoDateTime, false));
    $('#endDTPiker').val(new Date(lesson.endDatetime).format(dateFormat.masks.isoDateTime, false));
}

function createLesson() {
    $.ajax({
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        url: urlPrefix + '/api/lesson',
        data: JSON.stringify(readLessonFromFields()),
        success: function (result) {
            alert('Занятие успешно создано');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}

function updateLesson() {
    var lesson = getLessonFromFields();
    lesson.id = CURRENT_LESSON_ID;
    $.ajax({
        type: 'PUT',
        url: urlPrefix + '/api/lesson',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(lesson),
        success: function (result) {
            alert('Занятие успешно обновлено');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}

function deleteLesson() {
    $.ajax({
        type: 'DELETE',
        url: urlPrefix + '/api/lesson/' + CURRENT_LESSON_ID,
        data: readLessonFromFields(),
        success: function (result) {
            alert('Занятие успешно удалено');
            window.location.href = urlPrefix + "/";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}

function showTags(tags) {
    if(tags.length === 0) {
        return '';
    }
    else {
        return tags.join(" ");
    }
}