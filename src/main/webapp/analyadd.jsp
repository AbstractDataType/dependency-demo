<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.*" %>
<%@ page import="quesmanagement.dao.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%
    int userID = user.getUserID();
    String orderID = request.getParameter("orderID") == null ? "" : request.getParameter("orderID");
    List<Emp> empList = EmpDao.search(new Emp(null, null, userID));
    String analyAddErr= "";
    if (orderID.equals("")){analyAddErr="参数有误。添加分析记录请在\"订单管理->订单列表\"中选择对应订单，并在相应订单详情中点击\"分析问卷\"按钮";}
%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            订单管理
            <small>添加分析记录</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/index.jsp"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">订单管理</a></li>
            <li class="active">添加分析记录</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="box box-primary">
            <div class="box-header with-banaly">
                <%if(!analyAddErr.equals("")){%><h4 class="box-title" style="color: red"><%=analyAddErr%></h4></div></div></section></div><script>setSidebarActive("analyAdd");</script></div></body></html><% return;}%>
                <h3 class="box-title">添加分析记录</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <form role="form" method="post" action="${pageContext.request.contextPath}/analyServlet">

                    <% String otherErr= (String) request.getAttribute("otherErr");
                        if (otherErr!=null){ %>
                    <p  style="color: red" ><%=otherErr%></p>
                    <%}%>

                    <div class="form-group">
                        <label>对应订单ID</label>
                        <p><%=orderID%></p>
                    </div>

                    <div class="form-group">
                        <label>分析时间</label>
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" class="form-control pull-right" id="analyCreateTime" name="analyTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>负责分析的员工</label>
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
                        <label>按使用量付费时每份单价($)-预付费订单不需要填</label>
                        <input type="text" class="form-control" placeholder="单价" value="" name="unitPrice">
                    </div>


                    <div class="form-group">
                        <%List<Questionnaire> questionnaireList =
                                QuestionnaireDao.searchNotAnalyed(new Questionnaire(null,orderID, userID));%>
                        <div class="box-body table-responsive ">
                            <label>请选择要分析问卷</label>
                            <p style="color:red;">注：请务必在填写完"分析结果"之后选中表格最左边的复选框！不选择的话无法提交。</p>
                            <table id="queListTable" class="table table-hover ">
                                <thead>
                                <tr>
                                    <th class="" style="padding-right:0px;width: 24px"></th>
                                    <th class="text-left">问卷ID</th>
                                    <th class="text-left">问卷标题</th>
                                    <th class="text-left">问卷填写者</th>
                                    <th class="text-left">问卷填写时间</th>
                                    <th class="text-left" style="width: 500px">分析结果</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%
                                    if (questionnaireList != null) {
                                        for (Questionnaire que : questionnaireList) {
                                %>
                                <tr class="noclass">
                                    <td>
                                        <div style="align-content: center">
                                            <input type="checkbox"
                                                   style="width: 18px;height: 18px;align-content: center"
                                                   id="<%=que.getQuestionnarieID()%>">
                                        </div>
                                    </td>
                                    <td><%=que.getQuestionnarieID()%></td>
                                    <td><%=que.getTitle()%></td>
                                    <td><%=que.getFiller()%></td>
                                    <td><%=que.getFillTime()%></td>
                                    <td style="word-break:break-all;text-align:left;">
                                        <textarea class="form-control" rows="10"></textarea>
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
                    <input type="hidden" name="orderID" value="<%=orderID%>">
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
    setSidebarActive("analyAdd");
    $(document).ready(function () {


        // datetime picker
        $('#analyCreateTime').datepicker({
            format: "yyyy-mm-dd",
            autoclose: true,
            todayBtn: true,
            language: 'zh-CN'
        });

    });

    function checkQueID() {
        var queTable = document.getElementById("queListTable");
        var trs = document.getElementsByClassName("noclass");
        var result = "";
        for (var i = 0; i < trs.length; i++) {
            var checkb= trs[i].getElementsByTagName("input")[0];
            var analyresult=trs[i].getElementsByTagName("textarea")[0];
            if (checkb.checked === true) {
                result += checkb.getAttribute("id") + "+";
                result += analyresult.value+"|"
            }
        }
        document.getElementById("queIDList").setAttribute("value", result);
        return true;
    }
</script>
</body>

</html>