<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Car</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Cache-Control" content="no-cache">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <script>
            var state;
            jQuery(document).ready(function() {
                state = $("#check").prop("checked");
            });

            function checkFun() {
                $("#save").prop("disabled", $("#check").prop("checked") == state);
            }

        </script>

        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-4"> <b>Объявление:</b> ${requestScope.curcar.note}</div>
            </div><br>
            <div class="row">
                <div class="col-sm-4"> <b>Город:</b> ${requestScope.curcar.city.name} </div>
                <div class="col-sm-4"><b>Марка:</b> ${requestScope.curcar.mark.name}</div>
                <div class="col-sm-4"><b>Модель:</b> ${requestScope.curcar.model.name}</div>
            </div>
            <div class="row">
                <div class="col-sm-4"> <b>Цена:</b> ${requestScope.curcar.price} </div>
                <div class="col-sm-4"><b>Год выпуска:</b> ${requestScope.curcar.issue} </div>
                <div class="col-sm-4"><b>Пробег:</b> ${requestScope.curcar.dist} </div>
            </div><br>
            <div class="row">
                <div class="col-sm-4"> <b>Коробка передач:</b> ${requestScope.curcar.trans.name} </div>
                <div class="col-sm-4"><b>Тип кузова:</b> ${requestScope.curcar.btype.name} </div>
                <div class="col-sm-4"><b></b> </div>
            </div>
            <div class="row">
                <div class="col-sm-4"> <b>Тип двигателя:</b> ${requestScope.curcar.etype.name} </div>
                <div class="col-sm-4"><b>Объем двигателя:</b> ${requestScope.curcar.enginecapacity} </div>
                <div class="col-sm-4"><b>Мощность двигателя:</b> ${requestScope.curcar.power} </div>
            </div>
            <div class="row">
                <div class="col-sm-4"> <b>Привод:</b> ${requestScope.curcar.dunit.name} </div>
                <div class="col-sm-4"><b>Руль:</b> ${requestScope.curcar.wheel.name} </div>
                <div class="col-sm-4"></div>
            </div>
            <div class="row">
                <div class="col-sm-4"></div>
                <div class="col-sm-4"></div>
                <div class="col-sm-4"><b>Автор:</b> ${requestScope.curcar.user.name} </div>
            </div><br>
            <c:forEach items="${requestScope.curcar.fotos}"  var="foto">
                <img src="/cars/image?cid=${foto.id}" height="100" width="200"/>
            </c:forEach>
            <br><label class="checkbox-inline"><input type="checkbox" value="" id="check" onclick="checkFun();"
                <c:if test="${!requestScope.curcar.old}">
                    <c:out value=" checked "/>
                </c:if>
            <c:if test="${sessionScope.loginUser.name != requestScope.curcar.user.name}">
                <c:out value=" disabled "/>
            </c:if>
            >Объявление актуально</label><br>
            <form method='post' action='/cars/ad?id=<c:out value="${requestScope.curcar.id}"/>'>
                <input type="hidden" name='cid' value='<c:out value="${requestScope.curcar.id}"/>'/>
                <input type='submit' class="btn btn-default" id="save" name='save' value='Записать'  disabled />
            </form><br>
            <input type='button' class="btn btn-default" name='but3' onclick="window.location.href = '/cars/list';" value='Назад'/>
        </div>
    </body>
</html>
