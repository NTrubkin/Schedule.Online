var data;
const FILTERS_KEY = 'filters';
const TODAY_DAY_BLOCK_ID = "today-day-block";
const TODAY_BG_COLOR = '#EF5350';
const DEL_LESSON_CONF = 'Удалить занятие?';
const DEL_EVENT_CONF = 'Удалить событие?';
var canEditLessons = false;
var canEditEvents = false;


function initIndexPage(canEditLessons, canEditEvents) {
    this.canEditLessons = canEditEvents;
    this.canEditEvents = canEditEvents;
    if (group == null) {
        setBlockDisplaying('withoutGroupPanel', true);
    }
    else {
        setBlockDisplaying('filterBlock', true);
        setBlockDisplaying('actionBlock', true);
        setBlockDisplaying('addLessonBtn', canEditLessons);
        setBlockDisplaying('addEventBtn', canEditEvents);
        loadData();
        loadFilter();
        showData();
    }
}

function loadData() {
    $.ajax({
        type: 'GET',
        url: urlPrefix + '/api/group/lessonsAndEvents',
        dataType: 'json',
        async: false,
        success: function (result) {
            data = result;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            bootbox.alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}

function showData() {
    showRecords();
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

    if (records.length > 0) {
        var currentDay = null;
        var firstDay = new Date(records[0].startDatetime);
        firstDayBlock = true;
        for (var i = 0; i < records.length; i++) {
            var date = new Date(records[i].startDatetime);
            if (!date.isSameDateAs(currentDay)) {
                currentDay = date;
                showDayBlock(firstDay, currentDay);
            }

            showRecordBlock(records[i]);
        }

        var todayBlock = $('#' + TODAY_DAY_BLOCK_ID);
        var todayBtn = $('#curDayBtn');
        if (todayBlock != null && todayBlock.offset() != null) {
            todayBtn.css('display', 'block');
        }
        else {
            todayBtn.css('display', 'none');
        }
    }
}

const emptyBlock = '<hr class="daysDivider">';
const dayBlock =
    '        <div {3} class="contentBlock dayBlock">\n' +
    '            <div class="daySubblock">\n' +
    '                <p>{0}<br>{1} {2}</p>\n' +
    '            </div>\n' +
    '            <div class="daySubblock" style="text-align: right">\n' +
    '                <p>Неделя {4}<br>{5}</p>\n' +
    '            </div>\n' +
    '        </div>';

function showDayBlock(firstDate, currentDate) {
    if (firstDayBlock) {
        firstDayBlock = false;
    }
    else {
        $('#recordsWithDividers').append(emptyBlock);
    }

    var id = '';
    if (currentDate.isSameDateAs(new Date())) {
        id = 'id="{0}"'.f(TODAY_DAY_BLOCK_ID);
    }

    var weekNumber = calculateWeekNumber(firstDate, currentDate) + 1;

    $('#recordsWithDividers').append(dayBlock.f(days[currentDate.getDay()],
        currentDate.getDate(),
        monthArr[currentDate.getMonth()],
        id,
        weekNumber,
        isEven(weekNumber) ? 'нч' : 'чет'));
    $('#' + TODAY_DAY_BLOCK_ID).css('background-color', TODAY_BG_COLOR);
}

const LESSON_BLOCK =
    '        <div class="contentBlock recordBlock">\n' +
    '            <div class="recordSubblock">\n' +
    '                <a href="{0}/lesson?id={12}" id="{10}" class="editBtn"><img src="{0}/resources/icon/editBtn24.png"></a>\n' +
    '            </div>\n' +
    '            <div class="recordSubblock bodyRecordSubblock" onclick="switchBlockDisplaying(\'{5}\'); if({13}) {switchBlockVisibility(\'{10}\'); switchBlockVisibility(\'{11}\'); }">\n' +
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
    '                <a id="{11}" class="deleteBtn" onclick="deleteLesson(\'{12}\')"><img src="{0}/resources/icon/deleteBtn24.png"/></a>\n' +
    '            </div>\n' +
    '        </div>';

const MOBILE_LESSON_BLOCK =
    '        <div class="contentBlock recordBlock">\n' +
    '            <div class="recordSubblock bodyRecordSubblock" onclick="switchBlockDisplaying(\'{5}\');">\n' +
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
    '                        <a href="{0}/lesson?id={12}" id="{10}" class="editBtn"><img src="{0}/resources/icon/editBtn24.png">Редактировать</a>\n' +
    '                        <a id="{11}" class="deleteBtn" onclick="deleteLesson(\'{12}\')"><img src="{0}/resources/icon/deleteBtn24.png"/>Удалить</a>\n' +
    '                    </div>' +

    '                <hr class="recordHr">\n' +
    '            </div>\n' +
    '        </div>';

function showLessonBlock(lesson) {
    var start = new Date(lesson.startDatetime);
    var end = new Date(lesson.endDatetime);
    var lessonBlock = IS_MOBILE ? MOBILE_LESSON_BLOCK : LESSON_BLOCK;
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
        showTags(lesson),
        generateCSSId(LESSON_PREFIX, lesson.id, EDIT_POSTFIX),
        generateCSSId(LESSON_PREFIX, lesson.id, DEL_POSTFIX),
        lesson.id,
        canEditLessons.toString()));
    if (IS_MOBILE) {
        setBlockDisplaying(generateCSSId(LESSON_PREFIX, lesson.id, EDIT_POSTFIX), canEditLessons);
        setBlockDisplaying(generateCSSId(LESSON_PREFIX, lesson.id, DEL_POSTFIX), canEditLessons);
    }
}

const LESSON_PREFIX = 'lesson-';
const EVENT_PREFIX = 'event-';
const DET_BLOCK_POSTFIX = '-detailsBlock';
const EDIT_POSTFIX = '-edit';
const DEL_POSTFIX = '-delete';

const EVENT_BLOCK =
    '       <div class="contentBlock recordBlock">\n' +
    '            <div class="recordSubblock editLessonSubblock">\n' +
    '                <a href="{0}/event?id={10}" id="{8}" class="editBtn"><img src="{0}/resources/icon/editBtn24.png"></a>\n' +
    '            </div>\n' +
    '            <div class="recordSubblock bodyRecordSubblock" onclick="switchBlockDisplaying(\'{3}\'); if({11}) {switchBlockVisibility(\'{8}\'); switchBlockVisibility(\'{9}\'); }" >\n' +
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
    '                <a id="{9}" class="deleteBtn" onclick="deleteEvent(\'{10}\')"><img src="{0}/resources/icon/deleteBtn24.png"/></a>\n' +
    '            </div>\n' +
    '       </div>';

const MOBILE_EVENT_BLOCK =
    '       <div class="contentBlock recordBlock">\n' +
    '            <div class="recordSubblock bodyRecordSubblock" onclick="switchBlockDisplaying(\'{3}\');" >\n' +
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
    '                        <a href="{0}/event?id={10}" id="{8}" class="editBtn"><img src="{0}/resources/icon/editBtn24.png">Редактировать</a>\n' +
    '                        <a id="{9}" class="deleteBtn" onclick="deleteEvent(\'{10}\')"><img src="{0}/resources/icon/deleteBtn24.png"/>Удалить</a>\n' +
    '                    </div>' +

    '                <hr class="recordHr">\n' +
    '            </div>\n' +
    '       </div>';

function showEventBlock(event) {
    var start = new Date(event.startDatetime);
    var eventBlock = IS_MOBILE ? MOBILE_EVENT_BLOCK : EVENT_BLOCK
    $('#recordsWithDividers').append(eventBlock.f(
        urlPrefix,
        formatTime(start.getHours()),
        formatTime(start.getMinutes()),
        generateCSSId(EVENT_PREFIX, event.id, DET_BLOCK_POSTFIX),
        event.name,
        event.place,
        event.description,
        showTags(event),
        generateCSSId(EVENT_PREFIX, event.id, EDIT_POSTFIX),
        generateCSSId(EVENT_PREFIX, event.id, DEL_POSTFIX),
        event.id,
        canEditEvents.toString()));
    if (IS_MOBILE) {
        setBlockDisplaying(generateCSSId(EVENT_PREFIX, event.id, EDIT_POSTFIX), canEditEvents);
        setBlockDisplaying(generateCSSId(EVENT_PREFIX, event.id, DEL_POSTFIX), canEditEvents);
    }
}

function saveFilter() {
    var filters = Cookies.get(FILTERS_KEY);
    if (filters == null) {
        filters = {};
    }
    else {
        filters = JSON.parse(filters);
    }

    filters[account.id] = {
        lessons: $('#lessonFilter').prop('checked'),
        events: $('#eventFilter').prop('checked'),
        search: $('#searchFilter').val(),
        tags: $('#tagsFilter').val(),
        hideLast: $('#hideLastFilter').prop('checked')
    };
    Cookies.set(FILTERS_KEY, filters);
}


// todo подумать о проблеме безопасности cookie
// хранить поле поиска и тегов нежелательно
// Варианты: шифровать, хранить в бд, отказаться от этих полей
function loadFilter() {
    var filters = Cookies.get(FILTERS_KEY);
    if (filters == null) {
        return;
    }

    var filter = JSON.parse(filters)[account.id];
    if (filter == null) {
        return;
    }

    if (filter.lessons == null || filter.events == null || filter.search == null || filter.tags == null || filter.hideLast == null) {
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
    for (var i = 0; i < records.length; i++) {
        console.log();
        if (isLesson(records[i]) && !$('#lessonFilter').prop('checked')) {
            continue;
        }

        if (isEvent(records[i]) && !$('#eventFilter').prop('checked')) {
            continue;
        }

        if ($('#hideLastFilter').prop('checked') && records[i].startDatetime < currentDatetime) {
            continue;
        }

        if ($('#searchFilter').val() != '' && !fitBySearchFilter(records[i], $('#searchFilter').val())) {
            continue;
        }

        if ($('#tagsFilter').val() != '' && !fitByTagsFilter(records[i], $('#tagsFilter').val())) {
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

    if (isEvent(record)) {
        return record.name.toString().search(regex) !== -1 ||
            record.place.toString().search(regex) !== -1 ||
            record.description.toString().search(regex) !== -1;
    }

    return false;
}

function scrollToToday() {
    var elem = $('#' + TODAY_DAY_BLOCK_ID);
    if (elem != null && elem.offset() != null) {
        $('html, body').animate({
            scrollTop: elem.offset().top
        }, 1000);
    }
}

function showTags(record) {
    var tags = $.map(record.tags, function (tag) {
        return tag.name;
    });
    return tags.join(", ");
}

function fitByTagsFilter(record, filter) {
    var filterArr = filter.split(' ');
    var tags = $.map(record.tags, function (tag) {
        return tag.name;
    });
    return tags.some(function (v) {
        return filterArr.indexOf(v) >= 0;
    });
}

function deleteLesson(lessonId) {
    bootbox.confirm(DEL_LESSON_CONF,
        function (result) {
            if (result) {
                $.ajax({
                    type: 'DELETE',
                    url: urlPrefix + '/api/lesson/' + lessonId,
                    success: function () {
                        loadData();
                        showData();
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        bootbox.alert(jqXHR.status + ' ' + errorThrown);
                    }
                });
            }
        });
}

function deleteEvent(eventId) {
    bootbox.confirm(DEL_EVENT_CONF, function (result) {
        if (result) {
            $.ajax({
                type: 'DELETE',
                url: urlPrefix + '/api/event/' + eventId,
                success: function () {
                    loadData();
                    showData();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    bootbox.alert(jqXHR.status + ' ' + errorThrown);
                }
            });
        }
    });
}

const DEFAULT_GROUP_NAME = "Новая группа";

function createGroup() {
    var group = {name: DEFAULT_GROUP_NAME};
    $.ajax({
        type: 'POST',
        url: urlPrefix + '/api/group',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(group),
        success: function () {
            bootbox.alert('Группа успешно создана.', function () {
                window.location.href = urlPrefix + (window.mobilecheck() ? '/group?m=true' : '/group');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            bootbox.alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}

