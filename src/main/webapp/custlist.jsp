<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.Customer" %>
<%@ page import="quesmanagement.dao.CustomerDao" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%
    request.setCharacterEncoding("utf-8");
    Customer custf = new Customer();
    custf.setUserID(user.getUserID());
    List<Customer> custList = CustomerDao.search(custf);
    int custa =(custList==null?0: custList.size());
    String custErr=(String) request.getAttribute("custErr");
%>
<div class="content-wrapper">

    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            客户管理
            <small>客户列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">客户管理</a></li>
            <li class="active">客户列表</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">客户列表</h3>
            </div>

            <div class="box-body">
                <!--工具栏-->
                <div class="form-group form-inline">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default" title="新建"
                                data-toggle="modal" data-target="#addCust">
                            <i class="fa fa-file-o"></i> 新建
                        </button>
                        <button type="button" class="btn btn-default" title="刷新"
                                onclick="document.location='${pageContext.request.contextPath}/custlist.jsp'">
                            <i class="fa fa-refresh"></i>
                            刷新
                        </button>
                    </div>
                </div>
                <!--工具栏/-->
                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList" class="table table-hover">
                        <thead>
                        <tr>
                            <th class="text-left">客户ID</th>
                            <th class="text-left">客户名称</th>
                            <th class="text-left">客户地址</th>
                            <th class="text-left">客户邮编</th>
                            <th class="text-left">客户Email</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (custa>0) {
                                for (Customer cust : custList) {
                        %>
                        <tr id="<%=cust.getCustID()%>">
                            <td><%=cust.getCustID()%>
                            </td>
                            <td><%=cust.getCustName()%>
                            </td>
                            <td><%=cust.getAddress()%>
                            </td>
                            <td><%=cust.getZip()%>
                            </td>
                            <td><%=cust.getEmail()%>
                            </td>
                            <td class="text-center">
                                <button type="button" class="btn bg-olive btn-xs"
                                        data-toggle="modal" data-target="#editCust"
                                        onclick="return openEdit(document.getElementById('<%=cust.getCustID()%>'))">编辑</button>
                                <button type="button" class="btn bg-olive btn-xs"
                                        onclick="document.location='${pageContext.request.contextPath}/custServlet?method=delete&custID=<%=cust.getCustID()%>'">删除
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
                <div class="pull-left">
                    <div class="form-group form-inline">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default" title="新建"
                                    data-toggle="modal" data-target="#addCust">
                                <i class="fa fa-file-o"></i> 新建
                            </button>
                            <button type="button" class="btn btn-default" title="刷新"
                                    onclick="document.location='${pageContext.request.contextPath}/custlist.jsp'">
                                <i class="fa fa-refresh"></i>
                                刷新
                            </button>
                        </div>
                    </div>
                </div>
                <!--工具栏/-->
                <!-- 数据表格 /-->
                <!--模态窗口add-->
                <div id="addCust" class="modal modal-primary" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">新建客户</h4>
                            </div>
                            <div class="modal-body">
                                <div class="box-body">
                                    <div class="form-horizontal">

                                        <div class="form-group" >
                                            <label  class="col-sm-2 control-label">客户名称:</label>
                                            <div class="col-sm-5">
                                                <input id="add-custName" class="form-control" placeholder="客户名称" value="">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-2 control-label">客户地址:</label>
                                            <div class="col-sm-5">
                                                <input id="add-custAddress" class="form-control" placeholder="客户地址" value="">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-2 control-label">客户邮编:</label>
                                            <div class="col-sm-5">
                                                <input id="add-custZip" class="form-control" placeholder="客户邮编-只能是数字" value="">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-2 control-label">客户Email:</label>
                                            <div class="col-sm-5">
                                                <input id="add-custEmail" class="form-control" placeholder="客户Email" value="">
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
                <div id="editCust" class="modal modal-primary" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">修改客户</h4>
                            </div>
                            <div class="modal-body">
                                <div class="box-body">
                                    <div class="form-horizontal">

                                        <div class="form-group" >
                                            <label  class="col-sm-2 control-label">客户ID:</label>
                                            <div class="col-sm-5">
                                                <input id="edit-custID" class="form-control" disabled="disabled" placeholder="客户名称" value="">
                                            </div>
                                        </div>

                                        <div class="form-group" >
                                            <label  class="col-sm-2 control-label">客户名称:</label>
                                            <div class="col-sm-5">
                                                <input id="edit-custName" class="form-control" placeholder="客户名称" value="">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-2 control-label">客户地址:</label>
                                            <div class="col-sm-5">
                                                <input id="edit-custAddress" class="form-control" placeholder="客户地址" value="">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-2 control-label">客户邮编:</label>
                                            <div class="col-sm-5">
                                                <input id="edit-custZip" class="form-control" placeholder="客户邮编-只能是数字" value="">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-2 control-label">客户Email:</label>
                                            <div class="col-sm-5">
                                                <input id="edit-custEmail" class="form-control" placeholder="客户Email" value="">
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
                        共<%=custa%>条数据。
                    </div>

                    <div class="form-group form-inline">
                       注：添加用户时请填写完整所有信息，以免添加失败。邮编只能填写数字。
                    </div>
                    <% if (custErr!=null){ %>
                    <p class="login-box-msg" style="color: red" ><%=custErr%></p>
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
        var editlists = document.getElementById("editCust").getElementsByTagName("input");
        for (var i=0; i<tdlists.length;i++){
            editlists[i].setAttribute("placeholder",tdlists[i].innerText);
        }
    }

    function addItem() {
        var custName=document.getElementById("add-custName").value;
        var custAddress=document.getElementById("add-custAddress").value;
        var custZip=document.getElementById("add-custZip").value;
        var custEmail=document.getElementById("add-custEmail").value;
        document.location="${pageContext.request.contextPath}/custServlet?method=add&"+
        "custName="+encodeURIComponent(custName)+
            "&custAddress="+encodeURIComponent(custAddress)+
            "&custZip="+encodeURIComponent(custZip)+
            "&custEmail="+encodeURIComponent(custEmail);
    }

    function editItem(){
        var custName=document.getElementById("edit-custName").value;
        var custAddress=document.getElementById("edit-custAddress").value;
        var custZip=document.getElementById("edit-custZip").value;
        var custEmail=document.getElementById("edit-custEmail").value;
        var custID=document.getElementById("edit-custID").getAttribute("placeholder");
        document.location="${pageContext.request.contextPath}/custServlet?method=edit&"+
            "custName="+encodeURIComponent(custName)+
            "&custAddress="+encodeURIComponent(custAddress)+
            "&custZip="+encodeURIComponent(custZip)+
            "&custEmail="+encodeURIComponent(custEmail)+
            "&custID="+encodeURIComponent(custID);
    }

    $(document).ready(function () {
        // 激活导航位置
        setSidebarActive("custList");
    });
    
$.widget.bridge('uibutton', $.ui.button);


</script>
</body>

</html>
<!---->