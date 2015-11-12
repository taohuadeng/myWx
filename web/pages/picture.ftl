<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <script src="${rc.contextPath}/js/jquery-1.6.3.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#buttonId").bind("click", function () {
                $('#video').attr("controls", "controls");

                /*video.pause();
                alert('播放');*/
                video.play();
            });

            $('#video').bind('pause', function () {
                $('#video').removeAttr("controls");
                alert('已经暂停！');
            });
        });

        //        $(document).ready(function () {
        //            //var video = $('#video');
        //            var video = document.getElementById("video");
        //            $("#videoDiv").bind("click", "#video", function () {
        //                //video.on('play', function () {
        //                video.pause();
        //                alert('播放');
        //            });
        //        });

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
<div id="videoDiv">
    <video id="video" width="320" height="240">
        <source src="${rc.contextPath}/pages/vedio_1.mp4" type="video/mp4">
        Your browser does not support the video tag.
    </video>
    <input id="buttonId" type="button" value="播放"/>
</div>
<hr/>
<form id="uploadForm" action="${rc.contextPath}/test/uploadFile" method="post" enctype="multipart/form-data">
    <input type="file" capture="camera" name="file" value="拍照"><br/>
    <hr/>
    <input type="button" onclick="upload.fileuploadLoad();" value="上传照片">
</form>
</body>
</html>