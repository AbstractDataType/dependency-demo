<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String loginErr=(String) request.getAttribute("loginErr");%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">


    <title>问卷管理系统 | Log in</title>


    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <!-- Font Awesome -->
    <!-- Ionicons -->
    <!-- Theme style -->
    <!-- iCheck -->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
  <script src="${pageContext.request.contextPath}/plugins/html5shiv/js/html5shiv.min.js"></script>
  <script src="${pageContext.request.contextPath}/plugins/respond/js/respond.min.js"></script>
  <script src="${pageContext.request.contextPath}/plugins/jQuery/jquery-2.2.3.min.js"></script>
  <script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/plugins/iCheck/icheck.min.js"></script>
  <script>
      $(function() {
          $('input').iCheck({
              checkboxClass: 'icheckbox_square-blue',
              radioClass: 'iradio_square-blue',
              increaseArea: '20%' // optional
          });
      });
  </script>
  <![endif]-->

    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/adminLTE/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/iCheck/square/blue.css">
</head>

<body class="hold-transition login-page">
    <div class="login-box">
        <div class="login-logo">


            <a href=#>后台管理系统Demo</a>


        </div>
        <!-- /.login-logo -->
        <div class="login-box-body">
            <p class="login-box-msg">登录系统</p>
            <% if (loginErr!=null){ %>
                <p class="login-box-msg" style="color: red" ><%=loginErr%></p>
            <%}%>

            <form action="/loginServlet" method="post">
                <div class="form-group has-feedback">
                    <input type="text" class="form-control" name="userName" placeholder="用户名" value="mike">
                </div>
                <div class="form-group has-feedback">
                    <input type="password" class="form-control" name="userPass" placeholder="密码" value="123456">
                </div>
                <div class="row">
                    <div class="col-xs-8">
                    </div>
                    <!-- /.col -->
                    <div class="col-xs-4">
                        <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
                    </div>
                    <!-- /.col -->
                </div>
            </form>
            <a href="register.jsp" class="text-center">新用户注册</a>

        </div>
        <!-- /.login-box-body -->
    </div>
    <!-- /.login-box -->

    <!-- jQuery 2.2.3 -->
    <!-- Bootstrap 3.3.6 -->
    <!-- iCheck -->

</body>

</html>