<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Please login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="col-sm-2"></div>
        <h1>Please login</h1>
        <form method='post' class="form-horizontal" action='/cars/login/process'>
            <div class="form-group">
                <label class="control-label col-sm-2" for="login">Login:</label>
                <div class="col-sm-10">
                    <input type='input' class="form-control" name='login' id='login' value='' required/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="pass">Password:</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" name='pass' id='pass' value=''/>
                </div>
            </div>
            <div class="col-sm-2"></div>
            <input type='reset' class="btn btn-default" name='but1' value='Reset'/>
            <input type='submit' class="btn btn-default" name='but2' value='Login'/>
        </form>
        <form method='get' class="form-horizontal" action='/cars/list'>
            <div class="col-sm-3">
                 <input type='submit' class="btn btn-default" name='but2' value='Login as Anonymous'/>
            </div>
        </form>
    </body>
</html>
