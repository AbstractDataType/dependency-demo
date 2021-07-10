<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.*" %>
<%@ page import="quesmanagement.dao.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%
    int userID=user.getUserID();
%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            问卷管理
            <small>添加问卷</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/index.jsp"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">问卷管理</a></li>
            <li class="active">添加问卷</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">添加问卷</h3>
                </div>
            <!-- /.box-header -->
                <form role="form" method="post" action="${pageContext.request.contextPath}/queServlet">
                    <div class="box-body">
                        <div class="form-group">
                            <label>问卷标题</label>
                            <input type="text" class="form-control" name="queTitle">
                        </div>
                        <div class="form-group">
                            <label>问卷题目</label>
                            <textarea class="form-control" rows="20" name="questionTitle"></textarea>
                        </div>
                        <input type="hidden" name="method" value="add">
                    </div>
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">提交</button>
                    </div>
                </form>
            </div>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->


<script>
    // 激活导航位置
    setSidebarActive("addQue");
</script>
</div>
</body>

</html>