<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
welcome

<button id="load-button">load data</button>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
  	$(document).ready(function(){
  			$("#load-button").click(function(){
  					$.ajax({
  						url:'/hello',
  						type:'GET',
  						success: function(response){
  							var obj = response;
  							alert(obj);
  						}
  					})
  			}); 
  		
  	});
 </script>
</body>
</html>  