<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common.jsp"%>
<!-- 内容区域 -->
    <div class="content-wrapper">

        <!-- 内容头部 -->
        <section class="content-header">
            <h1>首页</h1>
            <ol class="breadcrumb">
                <li><a href="index.jsp"><i class="fa fa-home"></i> 首页</a></li>
            </ol>
        </section>
        <!-- 内容头部 /-->

        <!-- 正文区域 -->
        <section class="content">

            <div class="box box-warning">
                <div class="box-body">
                    <h4>本系统是为以下题目为案例设计的系统。</h4>
                    <h4>在开始使用之前请阅读<a href="#howToUse">使用说明</a>部分。</h4>
                </div>
            </div>

            <div class="box box-info">
                <div class="box-header with-border">
                    <i class="fa fa-book"></i>
                    <h3 class="box-title">题目说明</h3>
                </div>
                <div class="box-body" style="font-size: 17px">
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dependability有限公司销售并评估调查表,该调查表主要用作雇佣员工时测试员工的可靠性。</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;调查表的购买客户要求求职者在申请过程中填写调查表。然后打电话将求职者的回答告知Dependability的分析中心,中心雇员将其输入计算机。计算机将保留原始数据,对其进行分析,并将结果通知客户。
                    </p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Joe.D的主要工作是向客户推销Dependability公司的产品。若这些公司同意使用调查表, Dependability公司将为他们指定客户代码,并提供调查表。
                        分析中心将公司的代码输入计算机。若哪家公司需要中心评估调查表时,必须提供其代码。计算机通过该代码验证该客户是否为合法用户,记录调查表的使用情况,并将求职者的回答与该公司的记录相联系。
                    </p>
                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据客户的不同, Dependability公司有不同的定价政策：
                    </p>
                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(1)预付销售。当公司提供调查表时开出全价账单。
                        例如,若寄出100份调查表给客户,商定单价为5美元,
                        则他们将寄出500美元的账单。若客户没有使用这些调查表,则损失自负。这种政策主要用于小批量客户。
                    </p>
                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(2)使用时付账。
                        公司对交付的调查表开出名义费用(一般为每份0.50美元)的账单。每月末,按月内调查表的使用情况另送一份账单。
                    费用按月内使用情况收取,大批量时有折扣,并扣除已付款。例如,若月内评估了300份调查表,协议规定在这种使用量下,
                    每份调查表的价格为5美元。则月末报表中客户欠款额将为1,350美元[300x($5.00 - $0.50)]。
                    这种政策减轻了因调查表遗失或破损而未能使用所带来的损失,主要是面向中等到大批量使用量的客户。
                    </p>

                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月末,计算机产生的报表将送达客户,提供所评估调查表的汇总统计结果。在第2种定价政策下,
                    按显示在报表中的所使用的调查表的数量收费。月报由分析中心的一位兼职雇员(他也是应收账款职员)
                    编制,以便通知客户应付的款项。客户的付款送到分析中心,由应收账款职员负责记录,
                        并存入到公司的支票账户中。</p>
                </div>
                <!-- /.box-body -->

            </div>

            <div class="box box-info" id="howToUse">
                <div class="box-header with-border">
                    <i class="fa fa-book"></i>
                    <h3 class="box-title">使用说明</h3>
                </div>
                <div class="box-body" style="font-size: 17px" >
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、用户需要添加所有必要的信息，包括
                        <a href="${pageContext.request.contextPath}/emplist.jsp">添加员工信息</a>、
                        <a href="${pageContext.request.contextPath}/custlist.jsp">添加客户信息</a>和
                        <a href="${pageContext.request.contextPath}/acclist.jsp">添加账户信息。</a></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、用户需要在系统中
                        <a href="${pageContext.request.contextPath}/queadd.jsp">添加问卷</a>。
                        所有的问卷都可以在
                        <a href="${pageContext.request.contextPath}/quelist.jsp">问卷列表</a>中查看</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、然后就可以增加订单了。 在
                        <a href="${pageContext.request.contextPath}/orderlist.jsp">订单列表</a>中选择对应的订单,
                        然后点击最右端的<button type="button" class="btn bg-olive btn-xs">详情</button>进入"订单详情"。</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、在"订单详情"中点击
                        <button class="btn btn-primary btn-xs">分发问卷</button>以创建分发记录。</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、分发完的问卷可以进行回收操作，方法是在"订单详情"->"问卷一览"中找到对应问卷，
                        在右侧点击 <button type="button" class="btn bg-olive btn-xs">回收</button>按钮。</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6、回收完的问卷可以进行分析。在"订单详情"中点击
                        <button class="btn btn-primary btn-xs">分析问卷</button>以创建分析记录。</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;针对"使用时付费"情形，这里做了一下简化，设计成在每次分析时候填写分析时的每张问卷单价。
                        最终需支付的金额为"名义单价*订单总问卷数+Σ(分析时每张问卷单价*分析的问卷数)"</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7、在"订单详情"中点击
                        <button class="btn btn-primary btn-xs">支付</button>以进行支付</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8、只有添加账户、客户、员工和问卷信息之后才能创建订单，
                        创建订单之后才能进行分发、回收、分析问卷的操作。</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;问卷只能按照"加入订单->分发->回收->分析"的顺序进行操作，并按照相反顺序反操作。</p>
                </div>

                <!-- /.box-body -->

            </div>


        </section>
        <!-- 正文区域 /-->

    </div>
    <!-- 内容区域 /-->
</div>
<script type="application/javascript">
    $(document).ready(function () {
        // 激活导航位置
        setSidebarActive("admin-index");
    });
</script>
</div>
</body>

</html>
<!---->