<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Cache-Control" content="no-cache">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>

        <script>
            $( document ).ready(function() {

           })

            function onChange() {
                var mm = $("#mark").val();
                $.ajax({
                    type: "POST",
                    url: "/cars/list",
                    contentType: 'application/json',
                    data:JSON.stringify({action:"mark", mark:$("#mark").val()}),
                    success: function(data, textStatus, jqXHR){
                        mlist = jQuery.parseJSON(jqXHR.responseText);
                        $("#model").html("");
                        $("#model").append("<option selected disabled>Выберите модель</option>");
                        $.each(mlist, function (index, value) {
                            $("#model").append("<option  value='"+value+"'>"+value+"</option>");
                        });
                    },
                    error: function(data, textStatus, jqXHR){
                        console.log('load model list error');
                    }
                })
            }

            function addCars() {
                if($("#note").val() != '') {
                    var formd = new FormData($("#newAd")[0]);
                    $.ajax({
                            type: 'POST',
                            url: '/cars/add',
                            enctype: 'multipart/form-data',
                            data: formd,
                            processData: false,
                            contentType: false,
                            success: function (data) {
                                alert('Send Ad form - OK');
                                window.location.href = '/cars/list';
                            },
                            error: function (data) {
                                alert('Send Ad form - ERROR');
                                window.location.href = '/cars/list';
                            }
                        }
                    );
                } else {
                    alert("Текст объявления не введен");
                }
            }

            function isright(obj)
            {
                var value= +obj.value.replace(/\D/g,'')||0;
                var min = +obj.getAttribute('min');
                var max = +obj.getAttribute('max');
                obj.value = Math.min(max, Math.max(min, value));
            }

        </script>

        <h4>
            <b>Новое объявление пользователя</b>     ${sessionScope.loginUser.name}:
        </h4>
        <div class="container-fluid">
        <form id="newAd" name="newAd" method='post' action='/cars/add' enctype="multipart/form-data">
            <div class="row">
                <div class="col-sm-4"> <label for="note">Текст объявления:</label> <textarea name="note" id="note" ></textarea> </div>
            </div><br>
            <label> Параметры  объявления:</label><br><br>
            <div class="row">
                <div class="col-sm-4">
                    <select name="city" id="city">
                    <option selected disabled>Выберите город</option>
                    <c:forEach items="${requestScope.CityEntityList}"  var="cty">
                        <option value="${cty.name}">${cty.name}</option>
                    </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4">
                    <select name="mark" id="mark" onchange="onChange();">
                        <option selected disabled>Выберите марку</option>
                        <c:forEach items="${requestScope.MarkEntityList}"  var="mrk">
                            <option value="${mrk.name}">${mrk.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4">
                    <select name="model" id="model">
                        <option selected disabled>Выберите модель</option>
                    </select><br><br>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4">
                    <label for="price">Цена(руб):</label>
                    <input type='number' name='price' id='price' value='' min="10" max="100000000" onchange="isright(this);"/>
                </div>
                <div class="col-sm-4">
                    <label for="issue">Год выпуска(от 1900г):</label>
                    <input type='number' name='issue' id='issue' value='' min="1900" max="2019" onchange="isright(this);"/>
                </div>
                <div class="col-sm-4">
                    <label for="dist">Пробег(км):</label>
                    <input type='number' name='dist' id='dist' value='' min="0" max="1000000" onchange="isright(this);"/><br><br>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4">
                    <select name="trans" id="trans">
                        <option selected disabled>Выберите коробку передач</option>
                        <c:forEach items="${requestScope.TransmissionEntityList}"  var="trs">
                            <option value="${trs.name}">${trs.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4">
                    <select name="body" id="body">
                        <option selected disabled>Выберите тип кузова</option>
                        <c:forEach items="${requestScope.BodytypeEntityList}"  var="bdt">
                            <option value="${bdt.name}">${bdt.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4">
                    <select name="engine" id="engine">
                        <option selected disabled>Выберите тип двигателя</option>
                        <c:forEach items="${requestScope.EnginestypeEntityList}"  var="egt">
                            <option value="${egt.name}">${egt.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div><br>
            <div class="row">
                <div class="col-sm-4">
                    <label for="enginecapacity">Объем двигателя(см3):</label>
                    <input type='number' name='enginecapacity' id='enginecapacity' value='' min="100" max="100000" onchange="isright(this);"/>
                </div>
                <div class="col-sm-4"></div>
                <div class="col-sm-4">
                    <label for="power">Мощность двигателя(лс):</label>
                    <input type='number' name='power' id='power' value='' min="1" max="10000" onchange="isright(this);"/><br><br>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4">
                    <select name="drive" id="drive">
                        <option selected disabled>Выберите привод</option>
                        <c:forEach items="${requestScope.DriveunitEntityList}"  var="dru">
                            <option value="${dru.name}">${dru.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4">
                    <select name="wheel" id="wheel">
                        <option selected disabled>Выберите тип руля</option>
                        <c:forEach items="${requestScope.WheelEntityList}"  var="whl">
                            <option value="${whl.name}">${whl.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <br><b>Фото:</b><br>
            Загрузить фото: <input type="file" name="upfile" multiple><br/>
            <input type='reset' class="btn btn-default" name='but1' value='Сброс'/>
            <input type='button' class="btn btn-default" name='but2' onclick="addCars();" value='Сохранить'/>
            <input type='button' class="btn btn-default" name='but3' onclick="window.location.href = '/cars/list';" value='Назад'/>
        </form>
        </div>
    </body>
</html>
