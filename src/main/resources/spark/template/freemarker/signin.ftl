<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title> ${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
    <h1>${title}</h1>

    <div class="body">

        <form action="./signin" method="POST">
            Please enter in your username.
            <br/>
            <#if message??>
                <div id="message" class="${message.type}">${message.text}</div>
                </br>
            </#if>
            <p>Username: <input name="username" /></p>
            <br/><br/>
            <button type="login">Login</button>
        </form>
    </div>

</body>