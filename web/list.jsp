<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>工资管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>

    <script>
        function deleteUser(eno){
            //用户安全提示
            if(confirm("您确定要删除吗？")){
                //访问路径
                location.href="${pageContext.request.contextPath}/delUserServlet?eno="+eno;
            }
        }

        window.onload = function(){
            //给删除选中按钮添加单击事件
            document.getElementById("delSelected").onclick = function(){
                if(confirm("您确定要删除选中条目吗？")){

                   var flag = false;
                    //判断是否有选中条目
                    var cbs = document.getElementsByName("uid");
                    for (var i = 0; i < cbs.length; i++) {
                        if(cbs[i].checked){
                            //有一个条目选中了
                            flag = true;
                            break;
                        }
                    }

                    if(flag){//有条目被选中
                        //表单提交
                        document.getElementById("form").submit();
                    }

                }

            }
            //1.获取第一个cb
            document.getElementById("firstCb").onclick = function(){
                //2.获取下边列表中所有的cb
                var cbs = document.getElementsByName("uid");
                //3.遍历
                for (var i = 0; i < cbs.length; i++) {
                    //4.设置这些cbs[i]的checked状态 = firstCb.checked
                    cbs[i].checked = this.checked;

                }

            }


        }


    </script>
</head>


<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href=""><img src="money.png" width="22" height="22"> 工资管理系统</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li><a style="text-align: left" href="${pageContext.request.contextPath}/findUserByPageServlet">查询员工信息</a></li>
                <li><a style="text-align: left" href="${pageContext.request.contextPath}/salaryServlet">查询工资信息</a></li>
                <li><a style="text-align: left" href="${pageContext.request.contextPath}/deptGradeWelfareServlet">工资设定</a></li>
                <li style="align-content: end"><a href="login.jsp">管理员，退出登录</a></li>
            </ul>
        </div>
    </div>
</nav>
<div> </div>
<div class="container">
    <br>


    <h3 style="text-align: center">用户信息列表</h3>


    <BR>

    <form id="form" action="${pageContext.request.contextPath}/delSelectedServlet" method="post">
        <table border="1" class="table table-bordered table-hover table-striped">
        <tr class="success">
            <th>编号</th>
            <th>姓名</th>
            <th>部门</th>
            <th>性别</th>
            <th>职位</th>
            <th>评价</th>
            <th>工龄</th>
            <th>福利</th>
            <th>操作</th>
        </tr>

        <c:forEach items="${pb.list}" var="employee" varStatus="s">
            <tr>
                <td>${employee.eno}</td>
                <td>${employee.ename}</td>
                <td>${employee.edept}</td>
                <td>${employee.esex}</td>
                <td>${employee.egrade}</td>
                <td>${employee.erank}</td>
                <td>${employee.eage}</td>
                <td>${employee.ewelfare}</td>
                <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/findUserServlet?eno=${employee.eno}">修改</a>&nbsp;
                    <a class="btn btn-default btn-sm" href="javascript:deleteUser(${employee.eno});">删除</a></td>
            </tr>

        </c:forEach>


    </table>
    </form>
    <div style="float: right;margin: 5px;">

        <a class="btn btn-primary" href="${pageContext.request.contextPath}/addAllServlet">添加员工</a>


    </div>


    


    <div >
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${pb.currentPage == 1}">
                    <li class="disabled">
                </c:if>

                <c:if test="${pb.currentPage != 1}">
                    <li>
                </c:if>


                </li>


                <c:forEach begin="1" end="${pb.totalPage}" var="i" >


                    <c:if test="${pb.currentPage == i}">
                        <li class="active"><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=15&ename=${condition.ename[0]}&eno=${condition.eno[0]}&edept=${condition.edept[0]}">${i}</a></li>
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=15&ename=${condition.ename[0]}&eno=${condition.eno[0]}&edept=${condition.edept[0]}">${i}</a></li>
                    </c:if>

                </c:forEach>

                <span style="text-align:center; float: left; font-size: 21px;margin-right: 5px; ">
                          ----共${pb.totalCount}条信息
                </span>

            </ul>
        </nav>


    </div>


</div>


<div class="container">

<h3 style="text-align: center">用户信息筛选</h3>

    <form class="form-inline" action="${pageContext.request.contextPath}/findUserByPageServlet" method="post">
        <table border="2" class="table table-bordered table-hover table-striped" >
            <tr class="success">
                <th><label for="exampleInputName3">员工号</label></th>
                <th><label for="exampleInputName2">姓名</label></th>
                <th><label for="exampleInputEmail2">部门</label></th>
                <th>查询</th>
            </tr>
                <tr>
                    <td><input type="text" name="eno" style="width: 80px" value="${condition.eno[0]}" class="form-control" id="exampleInputName3" ></td>
                    <td><input type="text" name="ename " style="width: 60px" value="${condition.ename[0]}" class="form-control" id="exampleInputName2" ></td>
                    <td><input type="text" name="edept" style="width: 60px" value="${condition.edept[0]}" class="form-control" id="exampleInputEmail2"  ></td>
                    <td><button type="submit" class="btn btn-default">查询</button></td>
                </tr>

        </table>
    </form>
</div>

</body>
</html>
