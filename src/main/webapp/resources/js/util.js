/**
 * Позволяет форматировать строки
 * Пример var s2 = "My name: {0}, age: {1}!".f("Neo", 20);
 * https://wcoder.github.io/notes/string-format-for-string-formating-in-javascript
 * @type {String.f}
 */
String.prototype.format = String.prototype.f = function(){
    var args = arguments;
    return this.replace(/\{(\d+)\}/g, function(m,n){
        return args[n] == null ? m : args[n].toString() ;
    });
};

Date.prototype.isSameDateAs = function (pDate) {
    return (
        pDate !== null &&
        this.getFullYear() === pDate.getFullYear() &&
        this.getMonth() === pDate.getMonth() &&
        this.getDate() === pDate.getDate()
    );
}

const monthArr=[
    'Января',
    'Февраля',
    'Марта',
    'Апреля',
    'Мая',
    'Июня',
    'Июля',
    'Августа',
    'Сентября',
    'Ноября',
    'Декабря'
];

var days = ['Воскресенье', 'Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота'];

/**
 * Форматирует число, соответствующее часу, минуте, секунде, в строчку из 2х цифр
 * Например 1 (час) в 01
 */
function formatTime(n) {
    return n > 9 ? "" + n: "0" + n;
}

function switchBlockDisplaying(blockId) {
    var block = $('#' + blockId);
    if(block.css("display") === 'block') {
        block.css("display", 'none');
    }
    else {
        block.css("display", 'block');
    }
}

function setBlockDisplaying(blockId, isDisplay) {
    var block = $('#' + blockId);
    if(isDisplay) {
        block.css("display", 'block');
    }
    else {
        block.css("display", 'none');
    }
}

function switchBlockVisibility(blockId) {
    var block = $('#' + blockId);
    if(block.css("visibility") === 'visible') {
        block.css("visibility", 'hidden');
    }
    else {
        block.css("visibility", 'visible');
    }
}

function setBlockVisibility(blockId, isVisible) {
    var block = $('#' + blockId);
    if(isVisible) {
        block.css("visibility", 'visible');
    }
    else {
        block.css("visibility", 'hidden');
    }
}

function generateCSSId(prefix, id ,postfix) {
    return prefix + id + postfix;
}

function isEven(number) {
    return number & 1;
}

function calculateWeeksBetween(date1, date2) {
    // The number of milliseconds in one week
    const ONE_WEEK = 1000 * 60 * 60 * 24 * 7;
    // Convert both dates to milliseconds
    var date1_ms = date1.getTime();
    var date2_ms = date2.getTime();
    // Calculate the difference in milliseconds
    var difference_ms = Math.abs(date1_ms - date2_ms);
    // Convert back to weeks and return hole weeks
    return Math.floor(difference_ms / ONE_WEEK);
}

function calculateWeekNumber(firstDate, requiredDay) {
    var weekStart = new Date(firstDate).setHours(0, 0, 0, 0);
    const ONE_DAY = 1000 * 60 * 60 * 24;
    weekStart -= ONE_DAY * firstDate.getDayFromMonday();
    return calculateWeeksBetween(new Date(weekStart), requiredDay);
}

Date.prototype.getDayFromMonday = function() {
    return this.getDay() === 0 ? 6 : this.getDay() - 1;
}