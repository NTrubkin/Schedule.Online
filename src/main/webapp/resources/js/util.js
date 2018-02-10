/**
 * Позволяет форматировать строки
 * Пример var s2 = "My name: {0}, age: {1}!".f("Neo", 20);
 * https://wcoder.github.io/notes/string-format-for-string-formating-in-javascript
 * @type {String.f}
 */
String.prototype.format = String.prototype.f = function(){
    var args = arguments;
    return this.replace(/\{(\d+)\}/g, function(m,n){
        return args[n] ? args[n] : m;
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