<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String regErr=(String) request.getAttribute("regErr");%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">


    <title>
        注册</title>


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
  <![endif]-->

    <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="plugins/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="plugins/adminLTE/css/AdminLTE.css">
    <link rel="stylesheet" href="plugins/iCheck/square/blue.css">
</head>

<body class="hold-transition register-page">
    <div class="register-box">
        <div class="register-logo">


            <a href=#>后台管理系统</a>


        </div>

        <div class="register-box-body">
            <p class="login-box-msg">新用户注册</p>
            <% if (regErr!=null){ %>
            <p class="login-box-msg" style="color: red" ><%=regErr%></p>
            <%}%>
            <form action="/registServlet" method="post">
                <div class="form-group has-feedback">
                    <input type="text" class="form-control" name="userName" id="userName"  placeholder="用户名">
                    <span style="color:red;" id="err1"></span>
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="password" class="form-control" name="userPass" id="userPass" placeholder="密码">
                    <span style="color:red;" id="err2"></span>
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="password" class="form-control"  id="userPass2" placeholder="确认密码">
                    <span style="color:red;" id="err3"></span>
                    <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
                </div>
                <div class="row">
                    <div class="col-xs-8">
                        <div class="checkbox icheck">

                        </div>
                    </div>
                    <!-- /.col -->
                    <div class="col-xs-4">
                        <button type="submit" class="btn btn-primary btn-block btn-flat" onclick="return regCheck()">注册</button>
                    </div>
                    <script type="application/javascript">
                        function regCheck() {
                            var userName= document.getElementById("userName");
                            var userPass=document.getElementById("userPass");
                            var userPass2=document.getElementById("userPass2");
                            if (userName.value===''){
                                document.getElementById("err1").innerText="用户名为空";
                                return false;
                            }
                            if (userPass.value===''){
                                document.getElementById("err2").innerText="密码为空";
                                return false;
                            }
                            if (userPass.value!==userPass2.value){
                                document.getElementById("err3").innerText="密码不一致";
                                return false;
                            }
                            return true;
                        }

                    </script>
                    <!-- /.col -->
                </div>
            </form>

            <a href="login.jsp" class="text-center">我有账号，现在就去登录</a>
        </div>
        <!-- /.form-box -->
    </div>
    <!-- /.register-box -->

    <!-- jQuery 2.2.3 -->
    <!-- Bootstrap 3.3.6 -->
    <!-- iCheck -->
    <script src="plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="plugins/iCheck/icheck.min.js"></script>
    <script>
        $(function() {
            $('input').iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%' // optional
            });
        });
    </script>
</body>

</html>