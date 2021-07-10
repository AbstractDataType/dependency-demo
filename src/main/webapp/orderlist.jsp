<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.Order" %>
<%@ page import="quesmanagement.dao.OrderDao" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%
    Order orderf = new Order();
    orderf.setUserID(user.getUserID());
    List<Order> orderList = OrderDao.search(orderf);
    int ordera =( orderList==null? 0: orderList.size());
    String orderErr=(String) request.getAttribute("orderErr");
%>
<div class="content-wrapper">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            我的订单
            <small>订单列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/index.jsp"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">我的订单</a></li>
            <li class="active">订单列表</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">订单列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                    <!--工具栏-->
                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="新建"
                                onclick="document.location='${pageContext.request.contextPath}/orderadd.jsp'">
                                    <i class="fa fa-file-o"></i> 新建
                                </button>
                                <button type="button" class="btn btn-default" title="刷新"
                                        onclick="document.location='${pageContext.request.contextPath}/orderlist.jsp'">
                                    <i class="fa fa-refresh"></i>
                                    刷新
                                </button>
                            </div>
                        </div>
                    <!--工具栏/-->
                    <div class="table-box table-responsive no-padding">
                    <!--数据列表-->
                        <table id="dataList" class="table table-hover">
                        <thead>
                        <tr>
                            <th class="text-left">订单ID</th>
                            <th class="text-left">客户</th>
                            <th class="text-left">负责员工</th>
                            <th class="text-left">创建日期</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (ordera>0) {
                                for (Order order : orderList) {
                        %>
                        <tr id="<%=order.getOrderID()%>">
                            <td><%=order.getOrderID()%></td>
                            <td><%=order.getCustName()%></td>
                            <td><%=order.getEmpname()%></td>
                            <td><%=order.getOrderTime()%></td>
                            <td class="text-center">
                                <button type="button" class="btn bg-olive btn-xs"
                                        onclick="document.location='${pageContext.request.contextPath}/ordershow.jsp?orderID=<%=order.getOrderID()%>'">详情</button>
                                <button type="button" class="btn bg-olive btn-xs"
                                        onclick="document.location='${pageContext.request.contextPath}/orderServlet?method=delete&orderID=<%=order.getOrderID()%>'">删除
                                </button>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                        </tbody>
                        <!--
                            <tfoot>
                            <tr>
                            <th>Rendering engine</th>
                            <th>Browser</th>
                            <th>Platform(s)</th>
                            <th>Engine version</th>
                            <th>CSS grade</th>
                            </tr>
                            </tfoot>-->
                    </table>
                    <!--数据列表/-->
                    </div>
                    <!--工具栏-->

                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="新建"
                                        onclick="document.location='${pageContext.request.contextPath}/orderadd.jsp'">
                                    <i class="fa fa-file-o"></i> 新建
                                </button>
                                <button type="button" class="btn btn-default" title="刷新"
                                        onclick="document.location='${pageContext.request.contextPath}/orderlist.jsp'">
                                    <i class="fa fa-refresh"></i>
                                    刷新
                                </button>
                            </div>
                        </div>

                    <!--工具栏/-->
                <!-- 数据表格 /-->

            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
            <div class="box-footer">
                <div class="pull-left">
                    <div class="form-group form-inline">
                        共<%=ordera%>条数据。
                    </div>

                    <div class="form-group form-inline">
                        <p> 注：订单无法被修改，只能删除重建。</p>
                        <p>但是如果订单有后续操作则无法修改。</p>
                    </div>

                    <% if (orderErr!=null){ %>
                    <p  style="color: red" ><%=orderErr%></p>
                    <%}%>
                </div>
            </div>
            <!-- /.box-footer-->
        </div>
    </section>
    <!-- 正文区域 /-->

</div>

<script type="application/javascript">
    $(document).ready(function () {
        // 激活导航位置
        setSidebarActive("ordList");
    });
</script>
</body>

</html>
<!---->