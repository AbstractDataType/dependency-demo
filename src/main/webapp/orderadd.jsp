<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.*" %>
<%@ page import="quesmanagement.dao.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%
    int userID = user.getUserID();
    List<Emp> empList = EmpDao.search(new Emp(null, null, userID));
    List<Customer> customerList = CustomerDao.search(new Customer(null, userID));
    List<Questionnaire> questionnaireList = QuestionnaireDao.searchNotOrderd(new Questionnaire(null, userID));
%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            订单管理
            <small>添加订单</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/index.jsp"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">订单管理</a></li>
            <li class="active">添加订单</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">添加订单</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <form role="form" method="post" action="${pageContext.request.contextPath}/orderServlet">
                    <div class="form-group">
                        <label>创建时间</label>
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" class="form-control pull-right" id="orderCreateTime" name="orderTime">
                        </div>
                    </div>

                    <div class="form-group">

                        <label>订单创建员工</label>
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

                        <label>选择客户</label>
                        <select class="form-control select2 select2-hidden-accessible" style="width: 100%;"
                                tabindex="-1" aria-hidden="true" name="custID">
                            <% if (customerList != null) {
                                for (Customer customer : customerList) {%>
                            <option value="<%=customer.getCustID()%>"><%=customer.getCustID()%>&nbsp;&nbsp;&nbsp;&nbsp;<%=customer.getCustName()%>
                            </option>
                            <%
                                    }
                                }
                            %>

                        </select>
                    </div>

                    <div class="form-group form-inline">

                        <label>付费方式</label>
                        <div class="radio"><label><input type="radio" name="payType" value="True">预付费</label></div>
                        <div class="radio"><label><input type="radio" name="payType" value="False">按量付费</label></div>

                    </div>

                    <div class="form-group">
                        <label>每张问卷的单价(预付费) 或 名义单价(按使用量付费)($)</label>
                        <input type="text" class="form-control" placeholder="单价" value="" name="unitPrice">
                    </div>

                    <div class="form-group">
                        <div class="box-body table-responsive ">
                            <label>请选择要分配给该订单的问卷</label>
                            <table id="queListTable" class="table table-hover ">
                                <thead>
                                <tr>
                                    <th class="" style="padding-right:0px;width: 24px">
                                    </th>
                                    <th class="text-left">问卷ID</th>
                                    <th class="text-left">问卷标题</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%
                                    if (questionnaireList != null) {
                                        for (Questionnaire que : questionnaireList) {
                                %>
                                <tr>
                                    <td>
                                        <div style="align-content: center">
                                            <input type="checkbox" class="noclass"
                                                   style="width: 18px;height: 18px;align-content: center"
                                                   id="<%=que.getQuestionnarieID()%>">
                                        </div>
                                    </td>
                                    <td><%=que.getQuestionnarieID()%>
                                    </td>
                                    <td><%=que.getQuestionTitle()%>
                                    </td>
                                </tr>
                                <%
                                        }
                                    }
                                %>
                                </tbody>
                            </table>
                        </div>

                    </div>

                    <input type="hidden" name="method" value="add">
                    <input type="hidden" id="queIDList" name="queIDList" value="">

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
    setSidebarActive("ordAdd");
    $(document).ready(function () {


        // datetime picker
        $('#orderCreateTime').datepicker({
            format: "yyyy-mm-dd",
            autoclose: true,
            todayBtn: true,
            language: 'zh-CN'
        });

    });

    function checkQueID() {
        var queTable = document.getElementById("queListTable");
        var checkeds = document.getElementsByClassName("noclass");
        var result = "";
        for (i = 0; i < checkeds.length; i++) {
            if (checkeds[i].checked === true) {
                result += checkeds[i].getAttribute("id") + "+";
            }
        }
        document.getElementById("queIDList").setAttribute("value", result);
        return true;
    }
</script>
</body>

</html>