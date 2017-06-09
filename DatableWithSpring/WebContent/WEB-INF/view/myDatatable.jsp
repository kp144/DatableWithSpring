<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Come2niks - Datatable Server Side Processing</title>

<!-- Below are the Style Sheets required for Data Tables. These can be customized as required -->
<link rel="stylesheet" type="text/css" href="css/db_site_ui.css">
<link rel="stylesheet" type="text/css" href="css/demo_table_jui.css">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">

<!-- Below is the jQuery file required for using any Jquery component. -->
<script type="text/javascript" src="js/jquery-1.11.3.js"></script>

<!-- Below are the jQuery scripts required for Data Tables. -->
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>

<!-- Initialization code of data table at the time of page load. -->
<script>
	$(document).ready(function() {
		$('#myDatatable').DataTable({
			"jQueryUI" : true,
			"pagingType" : "full_numbers",
			"lengthMenu" : [ [ 5, 10, 50, -1 ], [ 5, 10, 50, "All" ] ],
			"processing": true,
			"bServerSide": true,
	        "sAjaxSource" : "loadServerSideData.do",
	        "sServerMethod": "POST"
		});
	});
</script>
</head>
<body>
	<h1>www.COME2NIKS.com</h1>
	<h3>Datatable Server Side Processing</h3>
	<div style="width: 80%">
		<!-- Below table will be displayed as Data table -->
		<table id="myDatatable" class="display datatable">
			<thead>
				<tr>
					<th>Sr. No.</th>
					<th>Name</th>
					<th>Email</th>
					<th>City</th>
					<th>Country</th>
				</tr>
			</thead>
			<!-- No need to add body tag here :) -->
		</table>
	</div>
</body>
</html>