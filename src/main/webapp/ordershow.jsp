<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.*" %>
<%@ page import="quesmanagement.dao.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%  request.setCharacterEncoding("utf-8");
    String orderID=request.getParameter("orderID")==null?"":request.getParameter("orderID");
    int userID=user.getUserID();
    String orderShowErr="";
    String otherErr=(String) request.getAttribute("otherErr");
    Order order=null;//查询出的订单
    if (orderID.equals("")){
        orderShowErr="参数有误，查看订单请在\"订单列表\"中选择订单并点击\"详情\"";
    }else {
        Order orderf = new Order(orderID,userID);
        List<Order> orders = OrderDao.search(orderf);
        if ( orders == null ||  orders.size() == 0) {
            orderShowErr = "订单不存在";
        } else {
            order = orders.get(0);
        }
    }
%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            订单管理
            <small>订单详情</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/index.jsp"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">订单管理</a></li>
            <li class="active">订单详情</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
            <div class="box box-primary">
                    <div class="box-header with-border">
                        <%if(!orderShowErr.equals("")){%><h3 class="box-title" style="color: red"><%=orderShowErr%></h3></div></div></section></div><script>setSidebarActive("ordShow");</script></div></body></html><% return;}%>
                        <h3 class="box-title">订单<%=order.getOrderID()%></h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">

                        <% if (otherErr!=null){ %>
                        <p  style="color: red" ><%=otherErr%></p>
                        <%}%>

                        <div class="form-group form-inline">

                            <button class="btn btn-primary"
                                    onclick="document.location='${pageContext.request.contextPath}/distadd.jsp?orderID=<%=orderID%>'">分发问卷</button>
                            <button class="btn btn-primary"
                                    onclick="document.location='${pageContext.request.contextPath}/analyadd.jsp?orderID=<%=orderID%>'">分析问卷</button>
                            <button class="btn btn-primary"
                                    onclick="document.location='${pageContext.request.contextPath}/payadd.jsp?orderID=<%=orderID%>'">支付</button>
                            <br/>
                            <br/>

                            <strong>客户:</strong>
                            <p><%=order.getCustID()%>&nbsp;&nbsp;<%=order.getCustName()%></p>
                            <p></p>

                            <strong>负责员工:</strong>
                            <p><%=order.getEmpID()%>&nbsp;&nbsp;<%=order.getEmpname()%></p>
                            <p></p>

                            <strong>创建日期:</strong>
                            <p><%=order.getOrderTime()%></p>
                            <p></p>

                            <strong>预付费/按量付费？</strong>
                            <p><%=order.getType() ?"预付费":"按量付费"%></p>
                            <p></p>

                            <strong>预付费单价/按量付费名义单价($)</strong>
                            <p><%=order.getUnitPrice()!=null?order.getUnitPrice().floatValue():"0"%></p>
                            <p></p>

                            <strong>问卷数</strong>
                            <p><%=order.getQuestionnaires()!=null?order.getQuestionnaires().size():"0"%></p>
                            <p></p>

                            <strong>订单总金额($)</strong>
                            <%
                                BigDecimal totalAmount =new BigDecimal(0);
                                totalAmount=totalAmount.add(new BigDecimal(order.getUnit()).multiply(order.getUnitPrice()));
                                for (Analy analy : order.getAnalys()){
                                    totalAmount = totalAmount.add(
                                            analy.getUnitPrice().multiply(new BigDecimal(analy.getUnit())));
                                }
                            %>
                            <p><%=totalAmount.floatValue()%></p>
                            <% if (!order.getType()){%>
                            <p>其中：</p>
                            <p>&nbsp;&nbsp;名义费用 <%=order.getUnit()%> (张) * <%=order.getUnitPrice().floatValue()%>($/张) = <%=order.getUnit()*order.getUnitPrice().floatValue()%> </p>
                            <p>&nbsp;&nbsp;问卷分析的费用详见<a href="#analyResult">问卷分析状况</a>中查看</p>
                            <%}%>

                            <strong>订单已支付金额($)</strong>
                            <%
                                BigDecimal paidAmount=new BigDecimal(0);
                                for (Pay pay : order.getPays()){
                                    paidAmount =paidAmount.add(pay.getAmount());
                                }
                            %>
                            <p><%=paidAmount%></p>
                            <p></p>

                            <strong>订单未支付金额($)</strong>
                            <p><%=totalAmount.subtract(paidAmount).floatValue()%></p>
                            <p></p>
                        </div>
                        </div>
            </div>
            <div class="box box-info">
                <div class="box-header with-border">
                    <h3 class="box-title">支付信息</h3>
                </div>
                <div class="box-body">
                    <div class="form-group form-inline">
                        <% if (order.getPays()==null|| order.getPays().size()==0){%>
                        <p>无支付记录</p>
                        <%}else{ for (Pay pay : order.getPays()){%>

                        <strong>支付记录ID:</strong>
                        <p><%=pay.getPayID()%></p>
                        <p></p>

                        <strong>收款日期</strong>
                        <p><%=pay.getPayTime()%></p>
                        <p></p>

                        <strong>收款员工:</strong>
                        <p><%=pay.getEmpID()%>&nbsp;&nbsp;<%=pay.getEmpName()%></p>
                        <p></p>

                        <% Account account=null;
                        try {
                            account = AccountDao.search(new Account(pay.getAccID(), userID)).get(0);
                        }catch (NullPointerException e){
                            account=new Account(pay.getAccID(), userID);
                        }%>
                        <strong>收款账号:</strong>
                        <p><%=account.getAccID()%>&nbsp;&nbsp;<%=account.getBankAccountID()%>&nbsp;&nbsp;<%=account.getBank()%></p>
                        <p></p>

                        <strong>金额($):</strong>
                        <p><%=pay.getAmount().floatValue()%></p>
                        <p></p>

                        <p></p>
                        <button class="btn bg-olive btn-xs" onclick="document.location='${pageContext.request.contextPath}/payServlet?method=delete&payID=<%=pay.getPayID()%>'">取消支付</button>
                        <br/>
                        <br/>
                        <br/>
                        <%}}%>
                    </div>
                </div>
                <!-- /.box-body -->
            </div>

            <div class="box box-info">
                <div class="box-header with-border">
                    <h3 class="box-title">问卷一览</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body" id="showAllQue">
                    <div class="table-box table-responsive no-padding">
                        <!--数据列表-->
                        <table id="dataList" class="table table-hover ">
                            <thead>
                            <tr>
                                <th class="text-left">问卷ID</th>
                                <th class="text-left">问卷标题</th>
                                <th class="text-left">已分发？</th>
                                <th class="text-left">已回收？</th>
                                <th class="text-left">已分析？</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                    for (Questionnaire que : order.getQuestionnaires()) {
                            %>
                            <tr id="<%=que.getQuestionnarieID()%>">
                                <td><a href="${pageContext.request.contextPath}/queshow.jsp?method=show&queID=<%=que.getQuestionnarieID()%>"><%=que.getQuestionnarieID()%></a></td>
                                <td><%=que.getQuestionTitle()==null?"":que.getQuestionTitle()%></td>
                                <td><%if (que.getDistID()!= null){%>是<%}else{%>否<%}%></td>
                                <td><%if (que.getRecvID()!= null){%>是<%}else{%>否<%}%></td>
                                <td><%if (que.getAnalyID()!= null){%>是<%}else{%>否<%}%></td>
                                <td class="text-center">
                                    <button type="button" class="btn bg-olive btn-xs"
                                            onclick="document.location='${pageContext.request.contextPath}/queshow.jsp?method=show&queID=<%=que.getQuestionnarieID()%>'">详情</button>
                                    <% if (que.getRecvID()==null && que.getDistID()!=null){ %>
                                    <button type="button" class="btn bg-olive btn-xs"
                                            onclick="document.location='${pageContext.request.contextPath}/recvadd.jsp?queID=<%=que.getQuestionnarieID()%>&orderID=<%=que.getOrderID()%>'">回收
                                    </button>
                                    <%}else if (que.getRecvID()!=null && que.getDistID()!=null && que.getAnalyID()==null){%>
                                    <button type="button" class="btn bg-olive btn-xs"
                                            onclick="document.location='${pageContext.request.contextPath}/recvServlet?method=delete&recvID=<%=que.getRecvID()%>'">取消回收
                                    </button>
                                    <%}%>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                            </tbody>
                        </table>
                        <!--数据列表/-->
                    </div>
                </div>
                <!-- /.box-body -->
            </div>

            <div class="box box-info">
                <div class="box-header with-border">
                    <h3 class="box-title">订单分发状况</h3>
                </div>
                <div class="box-body">
                    <div class="form-group form-inline">
                        <% if (order.getDists()==null|| order.getDists().size()==0){%>
                        <p>无分发记录</p>
                        <%}else{ for (Dist dist : order.getDists()){%>

                        <strong>分发记录ID:</strong>
                        <p><%=dist.getDistID()%></p>
                        <p></p>

                        <strong>负责员工:</strong>
                        <p><%=dist.getEmpID()%>&nbsp;&nbsp;<%=dist.getEmpName()%></p>
                        <p></p>

                        <strong>分发日期:</strong>
                        <p><%=dist.getDistTime()%></p>
                        <p></p>

                        <strong>分发问卷(点击以查看详情):</strong>
                        <p>共<%=dist.getQuestionnaires().size()%>张</p>
                         <%for (Questionnaire que : dist.getQuestionnaires()){%>
                        <p>
                            <a href="${pageContext.request.contextPath}/queshow.jsp?method=show&queID=<%=que.getQuestionnarieID()%>">
                                <%=que.getQuestionnarieID()%>
                            </a>
                        </p>
                        <%}%>
                        <p></p>
                        <button class="btn bg-olive btn-xs" onclick="document.location='${pageContext.request.contextPath}/distServlet?method=delete&distID=<%=dist.getDistID()%>'">删除</button>
                        <br/>
                        <br/>
                        <br/>
                        <%}}%>
                    </div>
                </div>
                <!-- /.box-body -->
            </div>

            <div class="box box-info">
                <div class="box-header with-border">
                    <h3 class="box-title">问卷回收记录</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <p>
                        请在<a href="#showAllQue">"问卷一览"</a>中到对应试卷详情中查看
                    </p>
                </div>
                <!-- /.box-body -->
            </div>

            <div class="box box-info">
                <div class="box-header with-border">
                    <h3 class="box-title" id="analyResult">订单分析状况</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <div class="form-group form-inline">
                        <% if (order.getAnalys()==null || order.getAnalys().size()==0){%>
                        <p>无分析记录</p>
                        <%}else{ for (Analy analy : order.getAnalys()){%>

                        <strong>分析记录ID:</strong>
                        <p><%=analy.getAnalyID()%></p>
                        <p></p>

                        <strong>负责员工:</strong>
                        <p><%=analy.getEmpID()%>&nbsp;&nbsp;<%=analy.getEmpName()%></p>
                        <p></p>

                        <strong>分析日期:</strong>
                        <p><%=analy.getAnalyTime()%></p>
                        <p></p>

                        <% if (!order.getType()){%>
                        <strong>在按量计费模式下本次分析的问卷单价($):</strong>
                        <p><%=analy.getUnitPrice().floatValue()%></p>
                        <p></p>
                        <strong>在按量计费模式下本次分析收费($):</strong>
                        <p><%=(new BigDecimal(analy.getUnit()).multiply(analy.getUnitPrice())).floatValue()%></p>
                        <p></p>
                        <%}%>

                        <strong>分析的问卷(点击以查看详情):</strong>
                        <p>共<%=analy.getQuestionnaires().size()%>张</p>
                        <%for (Questionnaire que : analy.getQuestionnaires()){%>
                        <p>
                            <a href="${pageContext.request.contextPath}/queshow.jsp?method=show&queID=<%=que.getQuestionnarieID()%>#analyShow">
                                <%=que.getQuestionnarieID()%>
                            </a>
                        </p>
                        <%}%>
                        <p></p>
                        <button class="btn bg-olive btn-xs" onclick="document.location='${pageContext.request.contextPath}/analyServlet?method=delete&analyID=<%=analy.getAnalyID()%>'">删除</button>
                        <br/>
                        <br/>
                        <br/>
                        <%}}%>
                    </div>
                </div>
                <!-- /.box-body -->
            </div>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->


<script>
    // 激活导航位置
    setSidebarActive("ordShow");
</script>
</div>
</body>

</html>