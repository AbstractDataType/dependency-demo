<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.Questionnaire" %>
<%@ page import="quesmanagement.dao.QuestionnaireDao" %>
<%@ page import="quesmanagement.entity.Questionnaire" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%
    Questionnaire quef = new Questionnaire();
    quef.setUserID(user.getUserID());
    List<Questionnaire> queList = QuestionnaireDao.search(quef);
    int quea =( queList==null? 0: queList.size());
    String queErr=(String) request.getAttribute("queErr");
%>
<div class="content-wrapper">

    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            我的问卷
            <small>问卷列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/index.jsp"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">我的问卷</a></li>
            <li class="active">问卷列表</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">问卷列表</h3>
            </div>

            <div class="box-body ">

                <!--工具栏-->
                    <div class="form-group form-inline">
                        <% if (queErr!=null){ %>
                        <p  style="color: red" ><%=queErr%></p>
                        <%}%>
                        <div class="btn-group">
                                <button type="button" class="btn btn-default" title="新建"
                                        onclick="document.location='${pageContext.request.contextPath}/queadd.jsp'">
                                    <i class="fa fa-file-o"></i> 新建
                                </button>
                                <button type="button" class="btn btn-default" title="刷新"
                                        onclick="document.location='${pageContext.request.contextPath}/quelist.jsp'">
                                    <i class="fa fa-refresh"></i>
                                    刷新
                                </button>
                            </div>
                    </div>
                <!--工具栏/-->

                <!-- 数据表格 -->
                <div class="table-box table-responsive no-padding">
                    <!--数据列表-->
                    <table id="dataList" class="table table-hover ">
                        <thead>
                        <tr>
                            <th class="text-left">问卷ID</th>
                            <th class="text-left">所属订单</th>
                            <th class="text-left">问卷标题</th>
                            <th class="text-left">已分发？</th>
                            <th class="text-left">已回收？</th>
                            <th class="text-left">已分析？</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (quea>0) {
                                for (Questionnaire que : queList) {
                        %>
                        <tr id="<%=que.getQuestionnarieID()%>">
                            <td><%=que.getQuestionnarieID()%></td>
                            <td><%=que.getOrderID()==null?"":que.getOrderID()%></td>
                            <td><%=que.getTitle()==null?"":que.getTitle()%></td>
                            <td><%if (que.getDistID()!= null){%>是<%}else{%>否<%}%></td>
                            <td><%if (que.getRecvID()!= null){%>是<%}else{%>否<%}%></td>
                            <td><%if (que.getAnalyID()!= null){%>是<%}else{%>否<%}%></td>
                            <td class="text-center">
                                <button type="button" class="btn bg-olive btn-xs"
                                        onclick="document.location='${pageContext.request.contextPath}/queshow.jsp?method=show&queID=<%=que.getQuestionnarieID()%>'">详情</button>
                                <button type="button" class="btn bg-olive btn-xs"
                                        onclick="document.location='${pageContext.request.contextPath}/queshow.jsp?method=edit&queID=<%=que.getQuestionnarieID()%>'">编辑</button>
                                <button type="button" class="btn bg-olive btn-xs"
                                        onclick="document.location='${pageContext.request.contextPath}/queServlet?method=delete&queID=<%=que.getQuestionnarieID()%>'">删除
                                </button>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>


                        </tbody>
                    </table>
                </div>
                    <!--工具栏-->
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="新建"
                                        onclick="document.location='${pageContext.request.contextPath}/queadd.jsp'">
                                    <i class="fa fa-file-o"></i> 新建
                                </button>
                                <button type="button" class="btn btn-default" title="刷新"
                                        onclick="document.location='${pageContext.request.contextPath}/quelist.jsp'">
                                    <i class="fa fa-refresh"></i>
                                    刷新
                                </button>
                            </div>
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
                        共<%=quea%>条数据。
                    </div>

                    <div class="form-group form-inline">
                        <p> 注：问卷被订单占用之后将无法修改删除。添加时请完整填写所有信息。</p>
                        <p> 修改时只有填写的信息会被修改。</p>
                    </div>

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
        setSidebarActive("queList");
    });
</script>
</body>

</html>
<!---->