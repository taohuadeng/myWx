<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <script src="${rc.contextPath}/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(function () {
            $("form").submit(function (event) {
                console.log($(this).serializeArray());
                event.preventDefault();
            });
        });
    </script>
</head>
<body style="text-align: center">
<form>
    <div><input type="text" name="a" value="1" id="a"></div>
    <div><input type="text" name="a" value="a1" id="a1"></div>
    <div><input type="text" name="b" value="2" id="b"></div>
    <div><input type="hidden" name="c" value="3" id="c"></div>
    <div>
        <textarea name="d" rows="8" cols="40">4</textarea>
    </div>
    <div><select name="e">
        <option value="5" selected="selected">5</option>
        <option value="6">6</option>
        <option value="7">7</option>
    </select></div>
    <div>
        <input type="checkbox" name="f" value="8" id="f">
    </div>
    <div>
        <input type="submit" name="g" value="Submit" id="g">
    </div>
</form>
</body>
</html>