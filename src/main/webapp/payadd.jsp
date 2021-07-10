<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.*" %>
<%@ page import="quesmanagement.dao.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%
    int userID = user.getUserID();
    List<Emp> empList = EmpDao.search(new Emp(null, null, userID));
    String orderID=request.getParameter("orderID")==null?"":request.getParameter("orderID");
    List<Account> accountList =AccountDao.search(new Account(null,userID));
%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            订单管理
            <small>支付</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/index.jsp"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">订单管理</a></li>
            <li class="active">支付</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">支付</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">

                <% String otherErr= (String) request.getAttribute("otherErr");
                    if (otherErr!=null){ %>
                <p  style="color: red" ><%=otherErr%></p>
                <%}%>


                <form role="form" method="post" action="${pageContext.request.contextPath}/payServlet?method=add">
                    <div class="form-group">
                        <label>需支付的订单ID</label>
                        <input type="text" class="form-control" placeholder="订单ID" value="<%=orderID%>" name="orderID">
                    </div>

                    <div class="form-group">
                        <label>支付时间</label>
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" class="form-control pull-right" id="payTime" name="payTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>收款员工</label>
                        <select class="form-control select2 select2-hidden-accessible" style="width: 100%;"
                                tabindex="-1" aria-hidden="true" name="empID">
                            <% if (empList != null) {
                                for (Emp emp : empList) {%>
                            <option value="<%=emp.getEmpID()%>"><%=emp.getEmpID()%>&nbsp;&nbsp;&nbsp;&nbsp;<%=emp.getEmpName()%>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>收款账户</label>
                        <select class="form-control select2 select2-hidden-accessible" style="width: 100%;"
                                tabindex="-1" aria-hidden="true" name="accountID">
                            <% if (accountList != null) {
                                for (Account account : accountList) {%>
                            <option value="<%=account.getAccID()%>">
                                <%=account.getAccID()%>&nbsp;&nbsp;&nbsp;&nbsp;账号:<%=account.getBankAccountID()%> &nbsp;&nbsp;&nbsp;&nbsp;开户行:<%=account.getBank()%>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>支付金额($)</label>
                        <input type="text" class="form-control" placeholder="支付金额" value="" name="amount">
                    </div>

                    <input type="hidden" name="method" value="add">

                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary" onclick="return checkQueID();">提交</button>
                    </div>
                </form>
            </div>
        </div>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->


</div>
<script>
    // 激活导航位置
    setSidebarActive("payAdd");
    $(document).ready(function () {


        // datetime picker
        $('#payTime').datepicker({
            format: "yyyy-mm-dd",
            autoclose: true,
            todayBtn: true,
            language: 'zh-CN'
        });

    });


</script>
</body>

</html>