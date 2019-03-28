<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8" import="java.util.List, entity.Employee"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hibernate Web</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document)
      .ready(
          function() {
            $('#fname')
                .keyup(
                    function() {
                      if ($('#fname').val() != '') {
                        $
                            .ajax({
                              url : 'search',
                              data : {
                                fname : $(
                                    '#fname')
                                    .val()
                              },
                              success : function(
                                  responseText) {
                                var abc = "";
                                for (var i = 0; i < responseText.length; i++) {
                                  abc += responseText[i]
                                      + "<br>";
                                }
                                document
                                    .getElementById("ajaxGetSearchResponse").innerHTML = abc;
                              }
                            });
                      } else {
                        document
                            .getElementById("ajaxGetSearchResponse").innerHTML = '';

                      }
                    });
          });
</script>
</head>

<body>
    <div class="signIn" style="display: block; margin-left: 500px; margin-top: 50px; max-width: 400px;">
        <form method="post" action="signIn">
            <fieldset>
                <legend>Sign In </legend>
                First Name:&nbsp; <input name="fname" type="text" placeholder="Your first name..." /> <br />Last Name:&nbsp; <input name="lname" type="text" placeholder="Your last name..." /> <br />Salary:&nbsp;
                <input name="salary" type="text" placeholder="Salary..." /> <br />Password:&nbsp;
                <input type="password" name="secret" placeholder="Type in your password..." /> <br /> <br />
                <button type="submit" value="Sign In">Sign In</button>
                <br />
            </fieldset>
        </form>
    </div>
    <div class="search" style="display: block; margin-left: 500px; margin-top: 50px; max-width: 400px;">
        <form method="post" action="search" name="search" autocomplete="off">
            <fieldset>
                <legend>Search </legend>
                First Name:&nbsp; <input name="fname" type="text" placeholder="Your first name..." id="fname" /> <br /> <br />
            </fieldset>
        </form>
        <p id="ajaxGetSearchResponse"></p>
    </div>
    <div class="getAllEmp" style="display: block; margin-left: 500px; margin-top: 50px; max-width: 400px;">
        <form method="post" action="getAllEmp" name="search">
            <fieldset>
                <legend>Database </legend>
                <button type="submit">Get Database...</button>
            </fieldset>
        </form>
        <p id="getAllEmpResponse">
            <%
        List<Employee> list = (List) request.getAttribute("list");
        if (list != null) {
          for (Employee e : list) {
            out.print(e.getEid() + " " + e.getFname() + " " + e.getLname() + " " + e.getSalary() + " "
                + e.getDecryptedSecretKey() + "<br>");
          }
        }
      %>
        </p>
    </div>
</body>

</html>