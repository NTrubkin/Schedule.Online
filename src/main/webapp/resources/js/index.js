var data;
const FILTERS_KEY = 'filters';
const TAGS_STUB = 'нч, лекция, зачет';
const TODAY_DAY_BLOCK_ID = "today-day-block";
const TODAY_BG_COLOR = '#EF5350';

function initIndexPage() {
    loadData();
    loadFilter();
    showData();
}

function loadData() {
    $.ajax({
        type: 'GET',
        url: urlPrefix + '/api/comb/mainpage',
        dataType: 'json',
        async: false,
        success: function (result) {
            if (result.user === null) {
                alert('unauthorized user');
                return;
            }

            data = result;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        }
    });
}

function showData() {
    showUser();
    showGroup();
    showRecords();
}

function showUser() {
    $('#accountName').html(data.user.firstName + '<br>' + data.user.secondName);
    $('#accountBlock').css('visibility', 'visible');
}

function showGroup() {
    $('#groupName').text(data.group.name);
    $('#groupBlock').css('visibility', 'visible');
}

function showRecordBlock(record) {
    if (isLesson(record)) {
        showLessonBlock(record)
    }
    else if (isEvent(record)) {
        showEventBlock(record);
    }
}

function isLesson(record) {
    return record.endDatetime != null;
}

function isEvent(record) {
    return record.endDatetime == null;
}

var firstDayBlock;
function showRecords() {
    $('#recordsWithDividers').empty();
    var records = data.lessons.concat(data.events);
    records = filterRecords(records);
    records.sort(function (a, b) {
        return a.startDatetime - b.startDatetime
    });
    var currentDay = null;
    firstDayBlock = true;
    for (var i = 0; i < records.length; i++) {
        var date = new Date(records[i].startDatetime);
        if (!date.isSameDateAs(currentDay)) {
            showDayBlock(date);
            currentDay = date;
        }

        showRecordBlock(records[i]);
    }

    var todayBlock = $('#' + TODAY_DAY_BLOCK_ID);
    var todayBtn = $('#curDayBtn');
    if(todayBlock != null && todayBlock.offset() != null) {
        todayBtn.css('display', 'block');
    }
    else {
        todayBtn.css('display', 'none');
    }
}
const emptyBlock =
    '        <div class="contentBlock emptyBlock">\n' +
    '            <p>STUB TEXT IN STUB BLOCK. SHOULD BE INVISIBLE</p>\n' +
    '        </div>';
const dayBlock =
    '        <div {3} class="contentBlock dayBlock">\n' +
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

    var id = '';
    if(date.isSameDateAs(new Date())) {
        id = 'id="{0}"'.f(TODAY_DAY_BLOCK_ID);
    }

    $('#recordsWithDividers').append(dayBlock.f(days[date.getDay()], date.getDate(), monthArr[date.getMonth()], id));
    $('#' + TODAY_DAY_BLOCK_ID).css('background-color', TODAY_BG_COLOR);
}

const lessonBlock =
    '        <div class="contentBlock recordBlock">\n' +
    '            <div class="recordSubblock">\n' +
    '                <a id="{10}" class="editBtn"><img src="{0}/resources/icon/editBtn24.png"></a>\n' +
    '            </div>\n' +
    '            <div class="recordSubblock bodyRecordSubblock" onclick="switchBlockDisplaying(\'{5}\'); switchBlockVisibility(\'{10}\'); switchBlockVisibility(\'{11}\');">\n' +
    '                <div class="recordHeader">\n' +
    '                    <p class="headerItem recordDetails">\n' +
    '                        {1}:{2}<br>{3}:{4}\n' +
    '                    </p>\n' +
    '                    <p class="headerItem recordName lessonName">{6}</p>\n' +
    '                    <p class="headerItem recordDetails">{7}</p>\n' +
    '                </div>\n' +


    '                    <div id="{5}" class="detailsBlock" style="display: none">\n' +
    '                        <p class="detailText"><span class="detailHeader">Преподаватель: </span>{8}</p>\n' +
    '                        <p class="detailText"><span class="detailHeader">Теги: </span>{9}</p>\n' +
    '                    </div>' +


    '                <hr class="recordHr">\n' +
    '            </div>\n' +
    '            <div class="recordSubblock">\n' +
    '                <a id="{11}" class="deleteBtn"><img src="{0}/resources/icon/deleteBtn24.png"/></a>\n' +
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
        generateCSSId(LESSON_PREFIX, lesson.id, DET_BLOCK_POSTFIX),
        lesson.name,
        lesson.room,
        lesson.teacher,
        TAGS_STUB,
        generateCSSId(LESSON_PREFIX, lesson.id, EDIT_POSTFIX),
        generateCSSId(LESSON_PREFIX, lesson.id, DEL_POSTFIX)));
}

const LESSON_PREFIX = 'lesson-';
const EVENT_PREFIX = 'event-';
const DET_BLOCK_POSTFIX = '-detailsBlock';
const EDIT_POSTFIX = '-edit';
const DEL_POSTFIX = '-delete';

const eventBlock =
    '       <div class="contentBlock recordBlock">\n' +
    '            <div class="recordSubblock editLessonSubblock">\n' +
    '                <a id="{8}" class="editBtn"><img src="{0}/resources/icon/editBtn24.png"></a>\n' +
    '            </div>\n' +
    '            <div class="recordSubblock bodyRecordSubblock" onclick="switchBlockDisplaying(\'{3}\'); switchBlockVisibility(\'{8}\'); switchBlockVisibility(\'{9}\');" >\n' +
    '                <div class="recordHeader">\n' +
    '                    <p class="headerItem recordDetails">{1}:{2}</p>\n' +
    '                    <div class="headerItem">\n' +
    '                        <p class="recordName eventName">\n' +
    '                            {4}\n' +
    '                        </p>\n' +
    '                        <p class="recordDetails placeDetails">\n' +
    '                            {5}\n' +
    '                        </p>\n' +
    '                    </div>\n' +
    '                </div>\n' +


    '                    <div id="{3}" class="detailsBlock" style="display: none">\n' +
    '                        <p class="detailText"><span class="detailHeader">Описание: </span>{6}</p>\n' +
    '                        <p class="detailText"><span class="detailHeader">Теги: </span>{7}</p>\n' +
    '                    </div>' +

    '                <hr class="recordHr">\n' +
    '            </div>\n' +
    '            <div class="recordSubblock deleteLessonSubblock">\n' +
    '                <a id="{9}" class="deleteBtn"><img src="{0}/resources/icon/deleteBtn24.png"/></a>\n' +
    '            </div>';

function showEventBlock(event) {
    var start = new Date(event.startDatetime);
    $('#recordsWithDividers').append(eventBlock.f(
        urlPrefix,
        formatTime(start.getHours()),
        formatTime(start.getMinutes()),
        generateCSSId(EVENT_PREFIX, event.id, DET_BLOCK_POSTFIX),
        event.name,
        event.place,
        event.description,
        TAGS_STUB,
        generateCSSId(EVENT_PREFIX, event.id, EDIT_POSTFIX),
        generateCSSId(EVENT_PREFIX, event.id, DEL_POSTFIX)));
}

function saveFilter() {
    var filters = Cookies.get(FILTERS_KEY);
    if(filters == null) {
        filters = {};
    }
    else {
        filters = JSON.parse(filters);
    }

    var filter = {
        lessons : $('#lessonFilter').prop('checked'),
        events : $('#eventFilter').prop('checked'),
        search : $('#searchFilter').val(),
        tags : $('#tagsFilter').val(),
        hideLast : $('#hideLastFilter').prop('checked')};

    filters[data.user.id] = filter;
    Cookies.set(FILTERS_KEY, filters);
}


// todo подумать о проблеме безопасности cookie
// хранить поле поиска и тегов нежелательно
// Варианты: шифровать, хранить в бд, отказаться от этих полей
function loadFilter() {
    var filters = Cookies.get(FILTERS_KEY);
    if(filters == null) {
        return;
    }

    var filter = JSON.parse(filters)[data.user.id];
    if(filter == null) {
        return;
    }

    if(filter.lessons == null || filter.events == null || filter.search == null ||filter.tags == null ||filter.hideLast == null) {
        return;
    }

    $('#lessonFilter').prop('checked', filter.lessons);
    $('#eventFilter').prop('checked', filter.events);
    $('#searchFilter').val(filter.search);
    $('#tagsFilter').val(filter.tags);
    $('#hideLastFilter').prop('checked', filter.hideLast);
}

function filterRecords(records) {
    var currentDatetime = new Date().getTime();
    var filtered = [];
    for(var i = 0; i < records.length; i++) {
        console.log();
        if (isLesson(records[i]) && !$('#lessonFilter').prop('checked')) {
            continue;
        }

        if (isEvent(records[i]) && !$('#eventFilter').prop('checked')) {
            continue;
        }

        if($('#hideLastFilter').prop('checked') && records[i].startDatetime < currentDatetime) {
            continue;
        }

        if($('#searchFilter').val() != '' && !fitBySearchFilter(records[i], $('#searchFilter').val())) {
            continue;
        }

        filtered.push(records[i]);
    }
    return filtered;
}

function fitBySearchFilter(record, filter) {
    var regex = (new RegExp(filter, "i"));
    if (isLesson(record)) {
        return record.name.toString().search(regex) !== -1 ||
            record.room.toString().search(regex) !== -1 ||
            record.teacher.toString().search(regex) !== -1;
    }

    if(isEvent(record)) {
        return record.name.toString().search(regex) !== -1 ||
            record.place.toString().search(regex) !== -1 ||
            record.description.toString().search(regex) !== -1;
    }

    return false;
}

function scrollToToday() {
    var elem = $('#' + TODAY_DAY_BLOCK_ID);
    if(elem != null && elem.offset() != null) {
        $('html, body').animate({
            scrollTop: elem.offset().top
        }, 1000);
    }
}