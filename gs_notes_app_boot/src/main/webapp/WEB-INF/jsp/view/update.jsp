<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
</style>
</head>
<body>

<h2>HTML Table</h2>

<form action="${pageContext.request.contextPath}/UpdateStudent/maj" modelAttribute="model">
<table>
  <tr>
    <th>données du fichier</th>
    <th>données de base de données</th>
    <th>Mettre a jour ?</th>
  </tr>
  <c:forEach items= "${listmap}" var="map" varStatus="loop">
  <tr>
  
    <td>${map.get("NewName")},${map.get("NewLastName")}, ${map.get("NewCNE")}</td>
    <td>${map.get("OldName")},${map.get("OldLastName")}, ${map.get("OldCNE")}</td>
    <td><input type="checkbox" id= ${map.get("NewCNE")}  name=${map.get("OldName")} value=${map.get("OldCNE")}></td>
  </tr>
  </c:forEach>
</table>
<button type="submit" class="btn btn-primary col-md-4 ">mettre a jour</button>
</form>
</body>
</html>