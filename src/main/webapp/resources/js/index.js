function initIndexPage() {
    $.ajax({
        type: 'GET',
        url: urlPrefix + '/api/comb/mainpage',
        dataType: 'json',
        async: true,
        success: function (result) {
            if (result['user'] === null) {
                alert('unauthorized user');
                return;
            }

            showUser(result.user);
            showGroup(result.group);
            showRecords(result.lessons, result.events);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        }
    });
}

function showUser(user) {
    $('#accountName').html(user.firstName + '<br>' + user.secondName);
    $('#accountBlock').css('visibility', 'visible');
}

function showGroup(group) {
    $('#groupName').text(group.name);
    $('#groupBlock').css('visibility', 'visible');
}

function showRecordBlock(record) {
    if(isLesson(record)) {
        showLessonBlock(record)
    }
    else {
        showEventBlock(record);
    }
}

function isLesson(record) {
    return record.endDatetime != null;
}

function showRecords(lessons, events) {
    $('#recordsWithDividers').empty();
    var records = lessons.concat(events);
    records.sort(function (a, b) {
        return a.startDatetime - b.startDatetime
    });
    var currentDay = null;
    for (var i = 0; i < records.length; i++) {
        var date = new Date(records[i].startDatetime);
        if (!date.isSameDateAs(currentDay)) {
            showDayBlock(date);
            currentDay = date;
        }

        showRecordBlock(records[i]);
    }
}

var firstDayBlock = true;
var emptyBlock =
    '        <div class="contentBlock emptyBlock">\n' +
    '            <p>STUB TEXT IN STUB BLOCK. SHOULD BE INVISIBLE</p>\n' +
    '        </div>';
var dayBlock =
    '        <div class="contentBlock dayBlock">\n' +
    '            <div class="daySubblock">\n' +
    '                <p>{0}</p>\n' +
    '            </div>\n' +
    '            <div class="daySubblock">\n' +
    '                <p>{1} {2}</p>\n' +
    '            </div>\n' +
    '        </div>';

function showDayBlock(date) {
    if (firstDayBlock) {
        firstDayBlock = false;
    }
    else {
        $('#recordsWithDividers').append(emptyBlock);
    }
    $('#recordsWithDividers').append(dayBlock.f(days[date.getDay()], date.getDate(), monthArr[date.getMonth()]));
}

var lessonBlock =
    '        <div class="contentBlock recordBlock">\n' +
    '            <div class="recordSubblock editLessonSubblock">\n' +
    '                <a class="editBtn"><img src="{0}/resources/icon/editBtn24.png"></a>\n' +
    '            </div>\n' +
    '            <div class="recordSubblock bodyRecordSubblock">\n' +
    '                <div class="recordHeader">\n' +
    '                    <p class="headerItem recordDetails">\n' +
    '                        {1}:{2}<br>{3}:{4}\n' +
    '                    </p>\n' +
    '                    <p class="headerItem recordName lessonName">{5}</p>\n' +
    '                    <p class="headerItem recordDetails">{6}</p>\n' +
    '                </div>\n' +
    '                <hr class="recordHr">\n' +
    '            </div>\n' +
    '            <div class="recordSubblock deleteLessonSubblock">\n' +
    '                <a class="deleteBtn"><img src="{7}/resources/icon/deleteBtn24.png"/></a>\n' +
    '            </div>\n' +
    '        </div>';

function showLessonBlock(lesson) {
    var start = new Date(lesson.startDatetime);
    var end = new Date(lesson.endDatetime);
    $('#recordsWithDividers').append(lessonBlock.f(
        urlPrefix,
        formatTime(start.getHours()),
        formatTime(start.getMinutes()),
        formatTime(end.getHours()),
        formatTime(end.getMinutes()),
        lesson.name,
        lesson.room,
        urlPrefix));
}

var eventBlock =
    '       <div class="contentBlock recordBlock">\n' +
    '            <div class="recordSubblock editLessonSubblock">\n' +
    '                <a class="editBtn"><img src="{0}/resources/icon/editBtn24.png"></a>\n' +
    '            </div>\n' +
    '            <div class="recordSubblock bodyRecordSubblock">\n' +
    '                <div class="recordHeader">\n' +
    '                    <p class="headerItem recordDetails">{1}:{2}</p>\n' +
    '                    <div class="headerItem">\n' +
    '                        <p class="recordName eventName">\n' +
    '                            {3}\n' +
    '                        </p>\n' +
    '                        <p class="recordDetails placeDetails">\n' +
    '                            {4}\n' +
    '                        </p>\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '                <hr class="recordHr">\n' +
    '            </div>\n' +
    '            <div class="recordSubblock deleteLessonSubblock">\n' +
    '                <a class="deleteBtn"><img src="{5}/resources/icon/deleteBtn24.png"/></a>\n' +
    '            </div>';
function showEventBlock(event) {
    var start = new Date(event.startDatetime);
    $('#recordsWithDividers').append(eventBlock.f(
        urlPrefix,
        formatTime(start.getHours()),
        formatTime(start.getMinutes()),
        event.name,
        event.place,
        urlPrefix));
}