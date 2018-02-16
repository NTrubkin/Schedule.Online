<div id="menuPanel">
    <div id="menuContentPanel">
        <div id="logoBlock" class="menuBlock">
            <a href="${urlPrefix}" id="logo">Schedule<br><span id="logoDot">.</span>Online</a>
        </div>
        <div id="accountBlock" class="menuBlock">
            <a href="${urlPrefix}/login?logout" id="accountLogout"><img src="${urlPrefix}/resources/icon/logout32-2.png"></a>
            <a href="${urlPrefix}/account" id="accountName">FIRSTNAME<br>SECONDNAME</a>
        </div>
        <div id="groupBlock" class="menuBlock">
            <a  href="${urlPrefix}/group" id="groupName">GROUP</a>
        </div>
    </div>
</div>

<script>
    const account = JSON.parse('${accountDTO}');
    const group = JSON.parse('${groupDTO}');
    $('#accountName').html(account.firstName + '<br>' + account.secondName);
    $('#accountBlock').css('visibility', 'visible');
    $('#groupName').text(group.name);
    $('#groupBlock').css('visibility', 'visible');
</script>