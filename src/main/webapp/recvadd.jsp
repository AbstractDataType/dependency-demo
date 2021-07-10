<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.*" %>
<%@ page import="quesmanagement.dao.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%
    request.setCharacterEncoding("utf-8");
    String queID=request.getParameter("queID")==null?"":request.getParameter("queID");
    String orderID = request.getParameter("orderID") == null ? "" : request.getParameter("orderID");
    int userID=user.getUserID();
    String queShowErr="";
    List<Emp> empList = EmpDao.search(new Emp(null, null, userID));
    Questionnaire que=null;
    if (queID.equals("")||orderID.equals("")){
        queShowErr="参数有误。添加回收记录请在\"订单管理->订单列表\"中选择对应订单，" +
                "并在相应\"订单详情\"->\"问卷一览\"中，点击表格最右侧\"回收\"按钮";
    }else {
        Questionnaire quef = new Questionnaire(queID,userID);
        List<Questionnaire> ques = QuestionnaireDao.search(quef);
        if (ques == null || ques.size() == 0) {
            queShowErr = "问卷不存在";
        } else {
            que = ques.get(0);
            String recv=que.getRecvID()==null?"":que.getRecvID();
            String analy=que.getAnalyID()==null?"":que.getAnalyID();
            if(!(analy.equals("") &&recv.equals(""))){
                queShowErr = "问卷"+que.getQuestionnarieID()+"已被回收";
            }
        }
    }
%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            问卷管理
            <small>回收问卷</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/index.jsp"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">问卷管理</a></li>
            <li class="active">回收问卷</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
            <div class="box box-primary">
                    <div class="box-header with-border">
                        <%if(!queShowErr.equals("")){%><h4 class="box-title" style="color: red"><%=queShowErr%></h4></div></div></section></div><script>setSidebarActive("recvAdd");</script></div></body></html><% return;}%>
                        <h3 class="box-title">问卷<%=que.getQuestionnarieID()%></h3>
                    </div>
                    <!-- /.box-header -->
                    <form role="form" method="post" action="${pageContext.request.contextPath}/recvServlet">
                        <div class="box-body">

                            <% String otherErr= (String) request.getAttribute("otherErr");
                                if (otherErr!=null){ %>
                            <p  style="color: red" ><%=otherErr%></p>
                            <%}%>

                            <div class="form-group">
                                <label>问卷标题</label>
                                <input type="text" class="form-control"  disabled="disabled" value="<%=que.getQuestionTitle()%>">
                            </div>

                            <div class="form-group">
                                <label>负责回收的员工</label>
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
                                <label>填写者</label>
                                <input type="text" class="form-control"  name="filler" value="">
                            </div>
                            <div class="form-group">
                                <label>填写时间</label>
                                <div class="input-group date">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="text" class="form-control pull-right" id="recvCreateTime" name="recvTime">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>问卷内容</label>
                                <textarea class="form-control" rows="20" name="questionTitle" disabled="disabled" ><%=que.getQuestionTitle()%></textarea>
                            </div>
                            <div class="form-group">
                                <label>问卷回答</label>
                                <textarea class="form-control" rows="20"  name="answer"></textarea>
                            </div>
                        </div>

                        <input type="hidden" name="method" value="add">
                        <input type="hidden" name="orderID" value="<%=que.getOrderID()%>">
                        <input type="hidden" name="queID" value="<%=que.getQuestionnarieID()%>">
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
    setSidebarActive("recvAdd");

    $(document).ready(function () {

        // datetime picker
        $('#recvCreateTime').datepicker({
            format: "yyyy-mm-dd",
            autoclose: true,
            todayBtn: true,
            language: 'zh-CN'
        });

    });
</script>
</div>
</body>

</html>