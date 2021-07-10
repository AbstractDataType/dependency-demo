<%@ page import="java.util.List" %>
<%@ page import="quesmanagement.entity.Account" %>
<%@ page import="quesmanagement.dao.AccountDao" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<%
    request.setCharacterEncoding("utf-8");
    Account accf = new Account();
    accf.setUserID(user.getUserID());
    List<Account> accList = AccountDao.search(accf);
    int acca = (accList==null?0:accList.size());
    String accErr=(String) request.getAttribute("accErr");
%>
<div class="content-wrapper">

    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            我的账户
            <small>账户列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/index.jsp"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="#">我的账户</a></li>
            <li class="active">账户列表</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">账户列表</h3>
            </div>

            <div class="box-body">
                <!-- 数据表格 -->
                    <!--工具栏-->
                    <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="新建"
                                        data-toggle="modal" data-target="#addAcc">
                                    <i class="fa fa-file-o"></i> 新建
                                </button>
                                <button type="button" class="btn btn-default" title="刷新"
                                        onclick="document.location='${pageContext.request.contextPath}/acclist.jsp'">
                                    <i class="fa fa-refresh"></i>
                                    刷新
                                </button>
                            </div>
                        </div>
                    <!--工具栏/-->
                    <div class="table-box table-responsive no-padding">
                    <!--数据列表-->
                        <table id="dataList" class="table table-hover ">
                        <thead>
                        <tr>
                            <th class="text-left">账户ID</th>
                            <th class="text-left">开户行</th>
                            <th class="text-left">账号</th>
                            <th class="text-left">余额</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (acca>0) {
                                for (Account acc : accList) {
                        %>
                        <tr id="<%=acc.getAccID()%>">
                            <td><%=acc.getAccID()%></td>
                            <td><%=acc.getBank()%></td>
                            <td><%=acc.getBankAccountID()%></td>
                            <td><%=acc.getBalance()%></td>
                            <td class="text-center">
                                <button type="button" class="btn bg-olive btn-xs"
                                        data-toggle="modal" data-target="#editAcc"
                                        onclick="return openEdit(document.getElementById('<%=acc.getAccID()%>'))">编辑</button>
                                <button type="button" class="btn bg-olive btn-xs"
                                        onclick="document.location='${pageContext.request.contextPath}/accServlet?method=delete&accID=<%=acc.getAccID()%>'">删除
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
                                        data-toggle="modal" data-target="#addAcc">
                                    <i class="fa fa-file-o"></i> 新建
                                </button>
                                <button type="button" class="btn btn-default" title="刷新"
                                        onclick="document.location='${pageContext.request.contextPath}/acclist.jsp'">
                                    <i class="fa fa-refresh"></i>
                                    刷新
                                </button>
                            </div>
                        </div>
                    </div>
                    <!--工具栏/-->
                <!-- 数据表格 /-->
                <!--模态窗口add-->
                <div id="addAcc" class="modal modal-primary" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">添加账户</h4>
                            </div>
                            <div class="modal-body">
                                <div class="box-body">
                                    <div class="form-horizontal">

                                        <div class="form-group" >
                                            <label  class="col-sm-2 control-label">开户行:</label>
                                            <div class="col-sm-5">
                                                <input id="add-bank" class="form-control" placeholder="开户行" value="">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-2 control-label">账号:</label>
                                            <div class="col-sm-5">
                                                <input id="add-bankAccountID" class="form-control" placeholder="账号" value="">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="add-balance" class="col-sm-2 control-label">初始余额:</label>
                                            <div class="col-sm-5">
                                                <div class="input-group">
                                                    <span class="input-group-addon">$</span>
                                                    <input id="add-balance" type="text" class="form-control" placeholder="输入金额">
                                                </div>
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
                <div id="editAcc" class="modal modal-primary" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">修改账户</h4>
                            </div>
                            <div class="modal-body">
                                <div class="box-body">
                                    <div class="form-horizontal">

                                        <div class="form-group" >
                                            <label  class="col-sm-2 control-label">账户ID:</label>
                                            <div class="col-sm-5">
                                                <input id="edit-accID" class="form-control" disabled="disabled" placeholder="账户ID" value="">
                                            </div>
                                        </div>

                                        <div class="form-group" >
                                            <label  class="col-sm-2 control-label">开户行:</label>
                                            <div class="col-sm-5">
                                                <input id="edit-bank" class="form-control" placeholder="开户行" value="">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-2 control-label">账号:</label>
                                            <div class="col-sm-5">
                                                <input id="edit-bankAccountID" class="form-control" placeholder="账号" value="">
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
                        共<%=acca%>条数据。
                    </div>

                    <div class="form-group form-inline">
                        <p> 注：添加账户信息时请填写完整所有信息，以免添加失败。 账号只能填写数字，且不超过18位。</p>
                        <p>余额只保留到两位小数。修改时只有填写的信息会被修改。如无意外，请勿修改账号信息。</p>
                    </div>

                    <% if (accErr!=null){ %>
                    <p class="login-box-msg" style="color: red" ><%=accErr%></p>
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
        var editlists = document.getElementById("editAcc").getElementsByTagName("input");
        for (var i=0; i<tdlists.length;i++){
            editlists[i].setAttribute("placeholder",tdlists[i].innerText);
        }
    }

    function addItem(){
        var bank=document.getElementById("add-bank").value;
        var bankAccountID=document.getElementById("add-bankAccountID").value;
        var balance=document.getElementById("add-balance").value;
        document.location="${pageContext.request.contextPath}/accServlet?method=add&bank="+encodeURIComponent(bank)+
            "&balance="+encodeURIComponent(balance)+
            "&bankAccountID="+encodeURIComponent(bankAccountID);
    }

    function editItem() {
        var accID=document.getElementById("edit-accID").getAttribute("placeholder");
        var bank=document.getElementById("edit-bank").value;
        var bankAccountID=document.getElementById("edit-bankAccountID").value;
        document.location="${pageContext.request.contextPath}/accServlet?method=edit&accID=" +encodeURIComponent(accID)+
            "&bank="+encodeURIComponent(bank)+
            "&bankAccountID="+encodeURIComponent(bankAccountID);
    }

    $.widget.bridge('uibutton', $.ui.button);

    $(document).ready(function () {
        // 激活导航位置
        setSidebarActive("accList");
    });
</script>
</body>

</html>
<!---->