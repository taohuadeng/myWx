<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <script type="text/javascript">
        var upload = {
            fileuploadLoad: function () {
                var form = document.getElementById('uploadForm');
                form.submit();
                alert('上传成功!');
            },
            reset: function () {
                document.getElementById('uploadForm').reset();//清除附件
            }
        }
    </script>
</head>
<body style="text-align: center">
<form id="uploadForm" action="${rc.contextPath}/test/uploadFile" method="post" enctype="multipart/form-data">
    <input type="file" capture="camera" name="file"><br/>
    <hr/>
    <input type="button" onclick="upload.fileuploadLoad();" value="上传">
</form>
</body>
</html>