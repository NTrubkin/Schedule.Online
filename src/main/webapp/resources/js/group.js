var currentGroup = {};
var currentMembers_ = [];
var accPermission = {};

function initGroupPage(membersDTO, permissionsDTO) {
    currentGroup = Object.assign({}, group);
    currentMembers_ = mapAccsAndPermsToMembers(JSON.parse(membersDTO), JSON.parse(permissionsDTO));
    if (account.id !== currentGroup.leaderId) {
        $('#nameFld').prop('disabled', true)
    }

    accPermission = currentMembers_.find(x => x.account.id === account.id).permission;

    if (!accPermission.admin) {
        setBlockDisplaying('controlBlock', false);
    }
    showData();
}

function mapAccsAndPermsToMembers(accounts, permissions) {
    var members = [];
    for (var i = 0; i < accounts.length; i++) {
        var member = {};
        member.account = accounts[i];
        member.permission = permissions.find(x => x.accountId === accounts[i].id);
        member.isDeleted = false;
        members[i] = member;
    }
    return members;
}

function mapMembersToPermissions(members) {
    var permissions = [];
    for (var i = 0; i < members.length; i++) {
        if (!members[i].isDeleted) {
            permissions.push(members[i].permission);
        }
    }
    return permissions;
}

function mapMembersToDeletedAccountIds(members) {
    var ids = [];
    for (var i = 0; i < members.length; i++) {
        if (members[i].isDeleted) {
            ids.push(members[i].account.id);
        }
    }
    return ids;
}

function readCurrentGroupFromFields() {
    currentGroup.name = $('#nameFld').val();
}

function putCurrentGroupToFields() {
    $('#nameFld').val(currentGroup.name);
    var leader = currentMembers_.find(x => x.account.id === currentGroup.leaderId);
    $('#leaderFld').text(leader.account.firstName + ' ' + leader.account.secondName);
}

function readCurrentMembersFromTable() {
    for (var i = 0; i < currentMembers_.length; i++) {
        var id = '#' + generateCSSId('', currentMembers_[i].account.id, ADMIN_POSTFIX);
        currentMembers_[i].permission.admin = $(id).prop('checked');

        id = '#' + generateCSSId('', currentMembers_[i].account.id, LESSON_POSTFIX);
        currentMembers_[i].permission.lessonsEdit = $(id).prop('checked');

        id = '#' + generateCSSId('', currentMembers_[i].account.id, EVENT_POSTFIX);
        currentMembers_[i].permission.eventsEdit = $(id).prop('checked');
    }
}

const MEMBERS_BLOCK =
    '            <tr id="{0}" class="settingsRow">\n' +
    '                <td class="settingsCell">\n' +
    '                    <p style="{10}">{1}</p>\n' +
    '                </td>\n' +
    '                <td class="bigTableCell">\n' +
    '                    <input id="{2}" type="checkbox" {5} {13}>\n' +
    '                </td>\n' +
    '                <td class="bigTableCell">\n' +
    '                    <input id="{3}" type="checkbox" {6} {13}>\n' +
    '                </td>\n' +
    '                <td class="bigTableCell">\n' +
    '                    <input id="{4}" type="checkbox" {7} {13}>\n' +
    '                </td>\n' +
    '                <td class="bigTableCell">\n' +
    '                    <a onclick="deleteMember({9})" {12}><img src="{8}/resources/icon/deleteBtn24.png"/></a>\n' +
    '                </td>\n' +
    '                <td class="bigTableCell">\n' +
    '                    <a onclick="makeLeader({9})" {11}><img src="{8}/resources/icon/leader32.png"/></a>\n' +
    '                </td>\n' +
    '            </tr>';

const MEMBER_BLOCK_POSTFIX = '-MemberBlock';
const ADMIN_POSTFIX = '-AdminCheckbox';
const LESSON_POSTFIX = '-LessonCheckbox';
const EVENT_POSTFIX = '-EventCheckbox';

function putCurrentMembersToTable() {
    for (var i = 0; i < currentMembers_.length; i++) {
        $('#membersTable').append(MEMBERS_BLOCK.f(
            generateCSSId('', currentMembers_[i].account.id, MEMBER_BLOCK_POSTFIX),
            currentMembers_[i].account.firstName + ' ' + currentMembers_[i].account.secondName,
            generateCSSId('', currentMembers_[i].account.id, ADMIN_POSTFIX),
            generateCSSId('', currentMembers_[i].account.id, LESSON_POSTFIX),
            generateCSSId('', currentMembers_[i].account.id, EVENT_POSTFIX),
            currentMembers_[i].permission.admin ? 'checked' : '',
            currentMembers_[i].permission.lessonsEdit ? 'checked' : ' ',
            currentMembers_[i].permission.eventsEdit ? 'checked' : ' ',
            urlPrefix,
            currentMembers_[i].account.id,
            currentMembers_[i].isDeleted ? 'text-decoration:line-through' : ' ',
            showLeaderButton(currentMembers_[i]) ? '' : 'style="display:none"',
            showDeleteButton(currentMembers_[i]) ? '' : 'style="display:none"',
            showPermission(currentMembers_[i]) ? '' : 'disabled'
        ));
    }
}

function showLeaderButton(member) {
    return accPermission.admin && group.leaderId === account.id && currentGroup.leaderId !== member.account.id && !member.isDeleted;
}

function showDeleteButton(member) {
    // удалять нельзя лидера, нового лидера, админа если ты админ, участника если ты участник
    return accPermission.admin && currentGroup.leaderId !== member.account.id;
}

function showPermission(member) {
    return accPermission.admin && currentGroup.leaderId !== member.account.id;
}

// жесть. подумать, как сделать лучше
function saveGroup() {
    readCurrentGroupFromFields();
    readCurrentMembersFromTable();
    $.ajax({
        type: 'PUT',
        url: urlPrefix + '/api/group',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(currentGroup),
        success: function (result) {
            savePermisstions();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showAjaxError(jqXHR, errorThrown);
        }
    });
}

function savePermisstions() {
    $.ajax({
        type: 'PUT',
        url: urlPrefix + '/api/group/permission?notify=false',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(mapMembersToPermissions(currentMembers_)),
        success: function (result) {
            deleteMembers();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showAjaxError(jqXHR, errorThrown);
        }
    });
}

function deleteMembers() {
    if (mapMembersToDeletedAccountIds(currentMembers_).length > 0) {
        $.ajax({
            type: 'DELETE',
            url: urlPrefix + '/api/group/member?notify=false',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(mapMembersToDeletedAccountIds(currentMembers_)),
            success: function (result) {
                bootbox.alert('Группа успешно обновлена', function () {
                    window.location.href = urlPrefix + (window.mobilecheck() ? '/?m=true' : '/');
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                showAjaxError(jqXHR, errorThrown);
            }
        });
    }
    else {
        bootbox.alert('Группа успешно обновлена', function () {
            window.location.href = urlPrefix + (window.mobilecheck() ? '/?m=true' : '/');
        });
    }
}

function invite() {
    $.ajax({
        type: 'POST',
        url: urlPrefix + '/api/group/member/invite?login=' + $('#inviteFld').val(),
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(currentGroup),
        success: function (result) {
            bootbox.alert('Приглашение отправлено', function () {
                window.location.reload();
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showAjaxError(jqXHR, errorThrown);
        }
    });
}

function makeLeader(memberId) {
    readCurrentGroupFromFields();
    readCurrentMembersFromTable();
    currentGroup.leaderId = memberId;
    var permission = currentMembers_.find(x => x.account.id === memberId).permission;
    permission.admin = true;
    permission.lessonsEdit = true;
    permission.eventsEdit = true;

    showData();
}

function deleteMember(memberId) {
    readCurrentGroupFromFields();
    readCurrentMembersFromTable();
    var m = currentMembers_.find(x => x.account.id === memberId);
    m.isDeleted = !m.isDeleted;
    showData();
}

const HEADER =
    '            <tr>\n' +
    '                <th class="settingsCell">\n' +
    '                    <p>Имя</p>\n' +
    '                </th>\n' +
    '                <th class="bigTableCell">\n' +
    '                    <p>Админ.</p>\n' +
    '                </th>\n' +
    '                <th class="bigTableCell">\n' +
    '                    <p>Ред. занятий</p>\n' +
    '                </th>\n' +
    '                <th class="bigTableCell">\n' +
    '                    <p>Ред. событий</p>\n' +
    '                </th>\n' +
    '                <th class="bigTableCell">\n' +
    '                    <p>Удалить</p>\n' +
    '                </th>\n' +
    '                <th class="bigTableCell">\n' +
    '                    <p>Назначить главным</p>\n' +
    '                </th>\n' +
    '            </tr>';

function showData() {
    var table = $('#membersTable');
    table.empty();
    table.append(HEADER);
    putCurrentGroupToFields();
    putCurrentMembersToTable();
}

