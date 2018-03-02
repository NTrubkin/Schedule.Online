function showLogin() {
    $('#regBlock').css('display', 'none');
    $('#loginBlock').css('display', 'block');

    var loginTabBtn = $('#loginTabBtn');
    loginTabBtn.removeClass('nonActiveAuthButton');
    loginTabBtn.addClass('activeAuthButton');
    var regBtn = $('#regTabBtn');
    regBtn.removeClass('activeAuthButton');
    regBtn.addClass('nonActiveAuthButton');

}

function showRegistration() {
    $('#loginBlock').css('display', 'none');
    $('#regBlock').css('display', 'block');

    var regBtn = $('#regTabBtn');
    regBtn.removeClass('nonActiveAuthButton');
    regBtn.addClass('activeAuthButton');
    var loginTabBtn = $('#loginTabBtn');
    loginTabBtn.removeClass('activeAuthButton');
    loginTabBtn.addClass('nonActiveAuthButton');

}

function createAccount() {
    var currentAccount = {};
    currentAccount.login = $('#loginFld').val();
    currentAccount.firstName = $('#firstNameFld').val();
    currentAccount.secondName = $('#secondNameFld').val();
    currentAccount.password = $('#passwordFld').val();
    $.ajax({
        type: 'POST',
        url: urlPrefix + '/api/account/registration',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(currentAccount),
        success: function (result) {
            bootbox.alert('Аккаунт успешно создан', function(){
            window.location.reload();});
        },
        error: function (jqXHR, textStatus, errorThrown) {
            bootbox.alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}