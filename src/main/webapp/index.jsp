
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>myCRM</title>
    <script type="text/javascript" src="js/jquery-3.5.1.js"></script>
    <script>
        $(function () {
            $("#jdBtn").click(function () {
                $.ajax({
                    url:"myServlet02.do",
                    type:"get",
                    dataType:"json",
                    async:true,
                    success:function (data) {
                        $("#id1").html(data.stu1.id)
                        $("#name1").html(data.stu1.name)
                        $("#age1").html(data.stu1.age)
                        $("#id2").html(data.stu2.id)
                        $("#name2").html(data.stu2.name)
                        $("#age2").html(data.stu2.age)
                    }
                })

            })
        })

    </script>
</head>
<body>
    <button id="jdBtn">点击</button><br/><br/>
    <div id="msg" style="width: 200px;height: 200px;background: pink">
    </div><br/><br/>
    学员1：<br/>
    编号：<span id="id1"></span>
    姓名：<span id="name1"></span>
    年龄：<span id="age1"></span>

    <br/><br/>
    学员1：<br/>
    编号：<span id="id2"></span>
    姓名：<span id="name2"></span>
    年龄：<span id="age2"></span>


</body>
</html>
