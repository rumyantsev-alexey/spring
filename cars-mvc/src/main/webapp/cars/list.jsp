<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Main page</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Cache-Control" content="no-cache">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>

    <script>

        function goAdd() {
            window.location.href = '/cars/add?new';
        }

        jQuery(document).ready(function($) {
            $("#mark").hide();
            $(function() {
                $("#filters").focus();
            });
            $(".clickable-row").click(function() {
                window.location = $(this).data("href");
            });
        });

        function goFilter() {
            $.ajax({
                type: "POST",
                url: "/cars/list",
                contentType: 'application/json',
                dataType:"html",
                data:JSON.stringify({action:$("#filters").val(), mark:$("#mark").val()}),
                success: function(data, textStatus, jqXHR){
                    $("#tbody").html("");
                    $("#tbody").html(data);
                },
                error: function(data, textStatus, jqXHR){
                }
                })
        }

        function goReset() {
            window.location = "/cars/list";
        }

        function setEnable() {
            $("#reset").prop("disabled", false);
            $("#apply").prop("disabled", false);
            if ($("#filters").val()==="selectmark") {
                $("#mark").show();
            } else {
                $("#mark").hide();
            }
        }

    </script>

    Current user:${sessionScope.loginUser.name}<br>
    <form method='post' action='/cars/login'>
        <input type="hidden" name='login' value=''/>
        <input type="hidden" name='pass' value=''/>
        <input type='submit' class="btn btn-default" name='but3' value='Logout'/>
    </form>
    <br>
    <c:if test="${sessionScope.loginUser.name != 'anonymus'}">
        <form method='get' action='/cars/add'>
           <input type='button' class="btn btn-default" name='but2' value='Add new ad' onclick="goAdd();" />
        </form>
    </c:if>
    <br>
    <hr align="left" width="50%" size="4"  /><br>
    Filters:
    <select name="filters" id="filters" onchange="setEnable();">
        <option selected disabled>Выберите фильтр</option>
        <option value="actual">Все актуальные</option>
        <option value="lastday">Все за сегодня</option>
        <option value="withfoto">Все с фото</option>
        <option value="selectmark">Выбраная марка</option>
    </select>
    <select name="mark" id="mark">
        <option selected disabled>Выберите марку</option>
        <c:forEach items="${requestScope.MarkEntityList}"  var="mrk">
            <option value="${mrk.name}">${mrk.name}</option>
        </c:forEach>
    </select>
    <input type='button' class="btn btn-default" name='reset' id='reset' value='Сбросить' onclick="goReset();" disabled/>
    <input type='button' class="btn btn-default" name='apply' id='apply' value='Применить' onclick="goFilter();" disabled/><br>
    <hr align="left" width="50%" size="4"  /><br>
    <table class="table table-hover" id="List">
        <thead>
        <tr>
            <th>Id</th>
            <th>Model</th>
            <th>Issue</th>
            <th>Price</th>
            <th>Created</th>
            <th>Author</th>
            <th>Actual</th>
            <th>Foto</th>
        </tr>
        </thead>
        <tbody id="tbody">
        <jsp:include page="table.jsp" />
        </tbody>
    </table>

    </body>
</html>
