var currentEvent = {
    name : 'Новое событие',
    place : 'Место',
    description : 'Описание события',
    tags : [],
    startDatetime: Date.now(),
    groupId : 0
};
const DEL_EVENT_CONF = 'Удалить событие?';

function initNewEventPage() {
    currentEvent.groupId = group.id;
    putCurrentEventToFields();
}

function initEventPage(event) {
    currentEvent = JSON.parse(event);
    putCurrentEventToFields();
}

function readCurrentEventFromFields() {
    currentEvent.name = $('#nameFld').val();
    currentEvent.place = $('#placeFld').val();
    currentEvent.description = $('#descriptionFld').val();
    currentEvent.tags = $('#tagsFld').val().split(' ');
    currentEvent.startDatetime = new Date($('#startDTPiker').val());
}

function putCurrentEventToFields() {
    $('#nameFld').val(currentEvent.name);
    $('#placeFld').val(currentEvent.place);
    $('#descriptionFld').val(currentEvent.description);
    $('#tagsFld').val(showTags(currentEvent.tags));
    $('#startDTPiker').val(new Date(currentEvent.startDatetime).format(dateFormat.masks.isoDateTime, false));
}

function createNewEvent() {
    readCurrentEventFromFields();
    $.ajax({
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        url: urlPrefix + '/api/event',
        data: JSON.stringify(currentEvent),
        success: function (result) {
            alert('Событие успешно создано');
            window.location.href = urlPrefix + "/";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}

function updateEvent() {
    readCurrentEventFromFields();
    $.ajax({
        type: 'PUT',
        url: urlPrefix + '/api/event',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(currentEvent),
        success: function (result) {
            alert('Событие успешно обновлено');
            window.location.href = urlPrefix + "/";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}

function deleteEvent() {
    if(confirm(DEL_EVENT_CONF)) {
        $.ajax({
            type: 'DELETE',
            url: urlPrefix + '/api/event/' + currentEvent.id,
            success: function (result) {
                alert('Событие успешно удалено');
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