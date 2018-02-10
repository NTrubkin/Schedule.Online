function initIndexPage() {
    console.log('init');
    $.ajax({
        type: 'GET',
        url: urlPrefix + '/api/comb/mainpage',
        dataType: 'json',
        async: true,
        success: function (result) {
            console.log('analizing result: ' + result);
            if (result['user'] === null) {
                alert('unauthorized user');
                return;
            }

            showUser(result.user);
            showGroup(result.group);
            showLessons(result.lessons);
            console.log('done');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log('fail');
            alert(jqXHR.responseText);
        }
    });
    console.log('init done');
}

function showUser(user) {
    $('#accountName').html(user.firstName + '<br>' + user.secondName);
    $('#accountBlock').css('visibility', 'visible');
}

function showGroup(group) {
    $('#groupName').text(group.name);
    $('#groupBlock').css('visibility', 'visible');
}

function showLessons(lessons) {
    $('#recordsWithDividers').empty();
    lessons.sort(function (a, b) {
        return a.datetime - b.datetime
    });
    var currentDay = null;
    for (var i = 0; i < lessons.length; i++) {
        var date = new Date(lessons[i].datetime);
        if (!date.isSameDateAs(currentDay)) {
            showDayBlock(date);
            currentDay = date;
        }

        showLessonBlock(lessons[i]);
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
    '                <a class="editBtn"><img src="{0}resources/icon/editBtn24.png"></a>\n' +
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
    '                <a class="deleteBtn"><img src="{7}resources/icon/deleteBtn24.png"/></a>\n' +
    '            </div>\n' +
    '        </div>';

function showLessonBlock(lesson) {
    var date = new Date(lesson.datetime);
    $('#recordsWithDividers').append(lessonBlock.f(
        urlPrefix,
        date.getHours(),
        date.getMinutes(),
        date.getHours(),
        date.getMinutes(),
        lesson.name,
        lesson.room,
        urlPrefix));
}