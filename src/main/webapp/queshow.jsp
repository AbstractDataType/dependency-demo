<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.*" %>
<%@ page import="quesmanagement.dao.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%  request.setCharacterEncoding("utf-8");
    String method=request.getParameter("method")==null?"":request.getParameter("method");
    String queID=request.getParameter("queID")==null?"":request.getParameter("queID");
    int userID=user.getUserID();
    String queShowErr="";
    String disabled="";
    Questionnaire que=null;
    if (!(method.equals("edit")||method.equals("show"))){
        queShowErr="参数有误。查看问卷请到\"问卷管理\"->\"问卷列表\"中选择对应问卷并点击最右边\"详情\"按钮";
    }else {
        if(method.equals("show")){disabled="readonly=\"readonly\"";}
        Questionnaire quef = new Questionnaire(queID,userID);
        List<Questionnaire> ques = QuestionnaireDao.search(quef);
        if (ques == null || ques.size() == 0) {
            queShowErr = "问卷不存在";
        } else {
            que = ques.get(0);
            String dist=que.getDistID()==null?"":que.getDistID();
            if(!dist.equals("") && method.equals("edit")){
                queShowErr = "问卷"+que.getQuestionnarieID()+"已被分发，无法修改";
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
            <small>问卷详情</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/index.jsp"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">问卷管理</a></li>
            <li class="active">问卷详情</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
            <div class="box box-primary">
                    <div class="box-header with-border">
                        <%if(!queShowErr.equals("")){%><h3 class="box-title" style="color: red"><%=queShowErr%></h3></div></div></section></div><script>setSidebarActive("showQue");</script></div></body></html><% return;}%>
                        <h3 class="box-title">问卷<%=que.getQuestionnarieID()%></h3>
                    </div>
                    <!-- /.box-header -->
                    <form role="form" method="post" action="${pageContext.request.contextPath}/queServlet">
                        <div class="box-body">
                            <div class="form-group">
                                <label>问卷标题</label>
                                <input type="text" class="form-control" name="queTitle" <%=disabled%> value="<%=que.getQuestionTitle()%>">
                            </div>
                            <div class="form-group">
                                <label>填写者</label>
                                <input type="text" class="form-control" readonly="readonly" value="<%=que.getFiller()%>">
                            </div>
                            <div class="form-group">
                                <label>填写日期</label>
                                <input type="text" class="form-control pull-right" id="datepicker" readonly="readonly" value="<%=que.getFillTime()%>">
                            </div>
                            <div class="form-group">
                                <label>问卷内容</label>
                                <textarea class="form-control" rows="20" name="questionTitle" <%=disabled%> ><%=que.getQuestionTitle()%></textarea>
                            </div>
                            <div class="form-group">
                                <label>问卷回答</label>
                                <textarea class="form-control" rows="20" readonly="readonly"><%=que.getAnswer()%></textarea>
                            </div>
                        </div>
                        <% if (method.equals("edit")){%>
                        <input type="hidden" name="method" value="edit">
                        <input type="hidden" name="queID" value="<%=que.getQuestionnarieID()%>">
                        <div class="box-footer">
                            <button type="submit" class="btn btn-primary">提交</button>
                        </div>
                        <%}%>
                    </form>
            </div>

            <div class="box box-info">
                <div class="box-header with-border">
                    <h3 class="box-title">问卷<%=que.getQuestionnarieID()%>所属订单</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <form role="form">
                        <!-- text input -->
                        <% String isorder=que.getOrderID()==null?"":que.getOrderID();
                            if ( isorder.equals("")){
                        %><p>问卷不属于任何订单</p><%
                    }else{
                        List<Order> orderList= OrderDao.search(new Order( isorder,userID));
                        if(orderList==null || orderList.size()==0){
                    %><p style="color: red">查询所属订单失败</p><%
                    }else{
                        Order order=orderList.get(0); %>
                        <p>所属订单ID：<%=order.getOrderID()%></p>
                        <p>创建订单员工ID：<%=order.getEmpID()%></p>
                        <p>创建订单员工姓名：<%=order.getEmpname()%></p>
                        <p>创建时间：<%=order.getOrderTime()%></p>
                        <%}
                        }%>
                    </form>
                </div>
                <!-- /.box-body -->
            </div>


            <div class="box box-info">
                <div class="box-header with-border">
                    <h3 class="box-title">问卷<%=que.getQuestionnarieID()%>分发状况</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body" >
                    <form role="form">
                        <!-- text input -->
                        <% String isdist = que.getDistID() == null ? "" : que.getDistID();
                            if (isdist.equals("")) {
                        %><p>问卷尚未分发</p><%
                    } else {
                        List<Dist> distList = DistDao.search(new Dist(isdist, userID));
                        if (distList == null || distList.size() == 0) {
                    %><p style="color: red">查询所属分发记录失败</p><%
                    } else {
                        Dist dist = distList.get(0); %>
                        <p>所属分发ID：<%=dist.getDistID()%></p>
                        <p>分发员工ID：<%=dist.getEmpID()%></p>
                        <p>分发员工姓名：<%=dist.getEmpName()%></p>
                        <p>分发时间：<%=dist.getDistTime()%>
                        </p>
                        <%
                                }
                            }
                        %>
                    </form>
                </div>
            </div>

            <div class="box box-info">
                <div class="box-header with-border">
                    <h3 class="box-title">问卷<%=que.getQuestionnarieID()%>回收状况</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <form role="form">
                        <!-- text input -->
                        <% String isrecv = que.getRecvID() == null ? "" : que.getRecvID();
                            if (isrecv.equals("")) {
                        %><p>问卷尚未回收</p><%
                    } else {
                        List<Recv> recvList = RecvDao.search(new Recv(isrecv, userID));
                        if (recvList == null || recvList.size() == 0) {
                    %><p style="color: red">查询所属回收记录失败</p><%
                    } else {
                        Recv recv = recvList.get(0); %>
                        <p>所属回收ID：<%=recv.getRecvID()%></p>
                        <p>回收员工ID：<%=recv.getEmpID()%></p>
                        <p>回收员工姓名：<%=recv.getEmpName()%></p>
                        <p>回收时间：<%=recv.getRecvTime()%>
                        </p>
                        <%
                                }
                            }
                        %>
                    </form>
                </div>
                <!-- /.box-body -->
            </div>

            <div class="box box-info">
                <div class="box-header with-border">
                    <h3 class="box-title">问卷<%=que.getQuestionnarieID()%>分析状况</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body" id="analyShow">
                    <form role="form">
                        <!-- text input -->
                        <% String isanaly = que.getAnalyID() == null ? "" : que.getAnalyID();
                            if (isanaly.equals("")) {
                        %><p>问卷尚未分析</p><%
                    } else {
                        List<Analy> analyList = AnalyDao.search(new Analy(isanaly, userID));
                        if (analyList == null || analyList.size() == 0) {
                    %><p style="color: red">查询所属分析记录失败</p><%
                    } else {
                        Analy analy = analyList.get(0); %>
                        <p>所属分析ID：<%=analy.getAnalyID()%></p>
                        <p>分析员工ID：<%=analy.getEmpID()%></p>
                        <p>分析员工姓名：<%=analy.getEmpName()%></p>
                        <p>分析时间：<%=analy.getAnalyTime()%></p>
                        <p>分析结果：<%=que.getAnalyResult()%></p>
                        <%
                                }
                            }
                        %>
                    </form>
                </div>
                <!-- /.box-body -->
            </div>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->


<script>
    // 激活导航位置
    setSidebarActive("showQue");
</script>
</div>
</body>

</html>