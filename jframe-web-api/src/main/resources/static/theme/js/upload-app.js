/**
 * webUploader for web/wenxin
 * @version 1.0
 */
!(function ($) {
    $.fn.AppUploader = function (settings) {
        var btn = this;
        var picker = $(this).attr("id");
        var $list = $("#" + settings.id);
        var limit = settings.limit;
        var def = settings.default;

        var files = new Array;
        var index;

        var uploader = WebUploader.create({
            auto: true,
            //swf: '<%=basePath%>static/library/plugins/webuploader/Uploader.swf',
            server: '/upload?t=1',
            pick: '#' + picker,
            resize: false,
            accept: {
                title: 'Files',
                extensions: 'jpg,jpeg,png'
                /*mimeTypes: 'image/!*'*/
            },
            fileNumLimit: limit,
            fileSingleSizeLimit: 5 * 1024 * 1024 // 5M
        });

        uploader.on('fileQueued', function (file) {
            // 创建缩略图
            uploader.makeThumb(file, function (error, src) {
                if (error) {
                    file.type = 2;
                    $img.replaceWith('<img src="/static/library/plugins/webuploader/folder.png">');
                    return;
                }
            }, 80, 80);
        });

        uploader.on('uploadProgress', function (file, percentage) {
            index = layer.open({type: 2, content: '正在上传...'});
        });

        uploader.on('fileDequeued', function (file) {
            removeFile(file);
        });

        uploader.on('uploadAccept', function (obj, ret) {
            if (ret.error == 0) {
                obj.file.url = ret.url; // 新增属性url
                files.push(obj.file);
            } else {
                layer.open({
                    content: ret.message
                });
            }
        });

        /*uploader.on('uploadSuccess', function (file) {
         $('#' + file.id).addClass('upload-state-done');
         });*/

        uploader.on('error', function (type) {
            if (type == "Q_EXCEED_NUM_LIMIT") {
                layer.open({
                    content: '文件数量超出限制'
                });
            } else if (type == "F_EXCEED_SIZE") {
                layer.open({
                    content: '文件大小超出限制'
                });
            } else if (type == "Q_TYPE_DENIED") {
                layer.open({
                    content: '文件类型不符合'
                });
            }
        });

        uploader.on('uploadError', function (file) {
            var $li = $('#' + file.id), $error = $li.find('div.error');
            // 避免重复创建
            if (!$error.length) {
                $error = $('<div class="error"></div>').appendTo($li);
            }
            $error.text('上传失败');
        });

        uploader.on('uploadComplete', function (file) {
            gen();
            layer.close(index);
        });

        var removeFile = function (file) {
            $('#' + file.id).remove();
            for (var i = 0; i < files.length; i++) {
                if (files[i] == file) {
                    files.splice(i, 1);
                    break;
                }
            }
            gen();
        }

        var gen = function () {
            $("input[name='" + settings.id + "']").remove();
            $("#" + settings.id).find(".img-area").remove();
            var urls = new Array();
            $.each(files, function (k, v) {
                var $li = $('<span id="' + v.id + '" class="img-area"><span class="img-del">x</span><img src="' + v.url + '">' + '</span>');
                $list.append($li);
                $li.on('click', 'span.img-del', function () {
                    uploader.removeFile(v);
                });
                urls.push(v.url);
            });
            $list.append('<input type="hidden" name="' + settings.id + '" value="' + urls.join(",") + '" />');
        }

        if (typeof(def) != 'undefined') {
            $list.append('<input type="hidden" name="' + settings.id + '" value="' + def + '" />');
            var urls = def.split(",");
            $.each(urls, function (k, v) {
                var $li = $('<span class="img-area"><img src="' + v + '">' + '</span>');
                $list.append($li);
            });
        }

    }
})(jQuery);