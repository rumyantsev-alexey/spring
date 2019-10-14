<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:forEach items="${requestScope.carlist}"  var="clist">
    <tr class='clickable-row' data-href='/cars/ad?id=${clist.id}'>
        <td>
            <a href='/cars/ad?id=${clist.id}'>${clist.id}</a>
        </td>
        <td>
                ${clist.model.name}
        </td>
        <td>
                ${clist.issue}
        </td>
        <td>
                ${clist.price}
        </td>
        <td>
                ${clist.created}
        </td>
        <td>
                ${clist.user.name}
        </td>
        <td>
                ${!clist.old}
        </td>
        <td>
            Count : ${fn:length(clist.fotos)}
        </td>
    </tr>
</c:forEach>
