<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.Emp" %>
<%@ page import="quesmanagement.dao.EmpDao" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%
    Emp empf = new Emp();
    empf.setUserID(user.getUserID());
    List<Emp> empList = EmpDao.search(empf);
    int empa = (empList==null?0:empList.size());
    String empErr=(String) request.getAttribute("empErr");
%>
<div class="content-wrapper">

    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            员工管理
            <small>员工列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">员工管理</a></li>
            <li class="active">员工列表</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">员工列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                    <!--工具栏-->

                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="新建"
                                        data-toggle="modal" data-target="#addEmp">
                                    <i class="fa fa-file-o"></i> 新建
                                </button>
                                <button type="button" class="btn btn-default" title="刷新"
                                        onclick="document.location='${pageContext.request.contextPath}/emplist.jsp'">
                                    <i class="fa fa-refresh"></i>
                                    刷新
                                </button>
                            </div>
                        </div>

                    <!--工具栏/-->
                    <div class="box-body table-responsive no-padding">
                    <!--数据列表-->
                        <table id="dataList" class="table table-hover">
                        <thead>
                        <tr>
                            <th class="text-left">员工ID</th>
                            <th class="text-left">员工姓名</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (empa>0) {
                                for (Emp emp : empList) {
                        %>
                        <tr id="<%=emp.getEmpID()%>">
                            <td><%=emp.getEmpID()%>
                            </td>
                            <td><%=emp.getEmpName()%>
                            </td>
                            <td class="text-center">
                                <button type="button" class="btn bg-olive btn-xs"
                                        data-toggle="modal" data-target="#editEmp"
                                        onclick="return openEdit(document.getElementById('<%=emp.getEmpID()%>'))">编辑</button>
                                <button type="button" class="btn bg-olive btn-xs"
                                        onclick="document.location='${pageContext.request.contextPath}/empServlet?method=delete&empID=<%=emp.getEmpID()%>'">删除
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
                                        data-toggle="modal" data-target="#addEmp">
                                    <i class="fa fa-file-o"></i> 新建
                                </button>
                                <button type="button" class="btn btn-default" title="刷新"
                                        onclick="document.location='${pageContext.request.contextPath}/emplist.jsp'">
                                    <i class="fa fa-refresh"></i>
                                    刷新
                                </button>
                            </div>
                        </div>

                    <!--工具栏/-->
                <!-- 数据表格 /-->
                <!--模态窗口add-->
                <div id="addEmp" class="modal modal-primary" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">录入员工</h4>
                            </div>
                            <div class="modal-body">
                                <div class="box-body">
                                    <div class="form-horizontal">

                                        <div class="form-group" >
                                            <label  class="col-sm-2 control-label">员工名称:</label>
                                            <div class="col-sm-5">
                                                <input id="add-empName" class="form-control" placeholder="员工名称" value="">
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-outline" data-dismiss="modal">关闭</button>
                                <button type="button" class="btn btn-outline" data-dismiss="modal" onclick="return addItem()" >提交</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!--模态窗口add/-->
                <!--模态窗口edit-->
                <div id="editEmp" class="modal modal-primary" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">修改员工信息</h4>
                            </div>
                            <div class="modal-body">
                                <div class="box-body">
                                    <div class="form-horizontal">

                                        <div class="form-group" >
                                            <label  class="col-sm-2 control-label">员工ID:</label>
                                            <div class="col-sm-5">
                                                <input id="edit-empID" class="form-control" disabled="disabled" placeholder="员工名称" value="">
                                            </div>
                                        </div>

                                        <div class="form-group" >
                                            <label  class="col-sm-2 control-label">员工名称:</label>
                                            <div class="col-sm-5">
                                                <input id="edit-empName" class="form-control" placeholder="员工名称" value="">
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-outline" data-dismiss="modal">关闭</button>
                                <button type="button" class="btn btn-outline" data-dismiss="modal" onclick="return editItem()" >提交</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!--模态窗口edit/-->

            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
            <div class="box-footer">
                <div class="pull-left">
                    <div class="form-group form-inline">
                        共<%=empa%>条数据。
                    </div>
                    <div class="form-group form-inline">
                        注：添加时请填写完整所有信息，以免添加失败。修改时只有填写的信息会被修改。
                    </div>
                    <% if (empErr!=null){ %>
                    <p class="login-box-msg" style="color: red" ><%=empErr%></p>
                    <%}%>
                </div>
            </div>
            <!-- /.box-footer-->
        </div>
    </section>
    <!-- 正文区域 /-->

</div>


<script>
    function openEdit(elementById) {
        var tdlists = elementById.getElementsByTagName("td");
        var editlists = document.getElementById("editEmp").getElementsByTagName("input");
        for (var i=0; i<tdlists.length;i++){
            editlists[i].setAttribute("placeholder",tdlists[i].innerText);
        }
    }

    function addItem() {
        var empName=document.getElementById("add-empName").value;
        document.location="${pageContext.request.contextPath}/empServlet?method=add&empName="+encodeURIComponent(empName);
    }

    function editItem(){
        var empName=document.getElementById("edit-empName").value;
        var empID=document.getElementById("edit-empID").getAttribute("placeholder");
        document.location="${pageContext.request.contextPath}/empServlet?method=edit&empName="+encodeURIComponent(empName)+
            "&empID="+encodeURIComponent(empID);
    }

$.widget.bridge('uibutton', $.ui.button);


    $(document).ready(function () {
        // 激活导航位置
        setSidebarActive("empList");
    });
</script>
</body>

</html>
<!---->