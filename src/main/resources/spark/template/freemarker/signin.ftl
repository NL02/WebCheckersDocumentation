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
            Please enter in your username and password.

            <br/>
            <p>Username <input name="Username" /></p>
            <p>Password: <input name="Password" /></p>
            <br/><br/>
            <button type="login">Login</button>
        </form>
    </div>

</body>