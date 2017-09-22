<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <meta name="description" content="">
    <meta name="author" content="">
    
    <title>그랑몬스터</title>
	
    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- Plugin CSS -->
    <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/sb-admin.css" rel="stylesheet">
  </head>

  <body class="fixed-nav sticky-footer bg-dark" id="page-top">
    <div class="content-wrapper">
      <div class="container-fluid">
		<jsp:include page="navi.jsp" flush="false"/>
        <!-- Breadcrumbs -->
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <h4>영수증 관리</h4>
          </li>
          <li class="breadcrumb-item active">Management Billing</li>
        </ol>
        
        <!-- Tables -->
        <div class="card mb-3">
          <div class="card-header">
            <i class="fa fa-table"></i>
            Billing List
          </div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered" width="100%" id="dataTable" cellspacing="0">
                <thead>
                  <tr>
                    <th>아이디</th>
                    <th>회사명</th>
                    <th>공란1</th>
                    <th>공란2</th>
                    <th>공란3</th>
                  </tr>
                </thead>
                <tfoot>
                  <tr>
                    <th>ID</th>
                    <th>Company Name</th>
                    <th>Temp1</th>
                    <th>Temp2</th>
                    <th>Temp3</th>
                  </tr>
                </tfoot>
                <tbody>
                <c:forEach var="item" items="${billinglist}">
                <tr>
			        <th>${item.companyID}</th>
			        <th>${item.companyName}</th>
			        <th>test</th>
			        <th>test</th>
			        <th>test</th>
			    </tr>
			    </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <div id="tableTime" class="card-footer small text-muted">
            Updated yesterday at 11:59 PM
          </div>
        </div>
        
      </div>
      <!-- /.container-fluid -->

    <!-- /.content-wrapper -->
	<jsp:include page="footer.jsp" flush="false"/>
    </div>
    
     <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/popper/popper.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    <script src="vendor/chart.js/Chart.min.js"></script>
    <script src="vendor/datatables/jquery.dataTables.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.js"></script>

    <!-- Custom scripts for this template -->
    <script src="js/sb-admin.min.js"></script>
    
    <!-- Custom scripts for this template -->
	<script type="text/javascript">
	 $(document).ready(function(){
		 var d = new Date();
		 $('#navi_billing').attr('class',"nav-item active");
		 $('#tableTime').text('Updated ' + d.getFullYear()+'/'+(d.getMonth() + 1)+'/'+d.getDate()+' '+d.getHours()
				 +':'+d.getMinutes()+':'+d.getSeconds());
		});
	}
    </script>
  </body>

</html>