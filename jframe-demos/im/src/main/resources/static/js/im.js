var contactItem = $("#x-contact-item");
var chatContent = $(".x-chat-content");
var alertDiv = $("#alert");
var alarm = document.getElementById("alarm");
var audio = document.getElementById("audio");
var chatListFlag = true;
var page = 1;
var stompClient = null;
var stompOn = true;
var reConn = 0;
var waveTimer;
var recTimer;
var recorder;
var recording = false;
var recSec = 0;

// 对话列表
function getChatList() {
    chatListFlag = false;
    Ajax.ajax({
        url: '/im/chatList',
        params: {
            "loginId": userId
        },
        success: function (data) {
            data.forEach(function (chat) {
                addChatElement(chat, false);
            });
        }
    });
}


/**
 * 生成对话列表
 * @param {*} chat 对话对象
 * @param {boolean} prepend 前追加｜后追加
 */
function addChatElement(chat, prepend) {
    if (!chat.lastMsg) {
        return;
    }
    $(".menu-list-none").hide();
    var html = '<li class="ant-menu-item" data-chatno="' + chat.chatNo + '" data-otherid="' + chat.otherId + '" data-othername="'
        + chat.otherName + '" data-unread="' + chat.unread + '" style="padding-left: 24px;">' +
        '<div class="nav-text"><div class="nav-text-left"><span style="vertical-align: middle;">' + chat.otherName + (chat.online ? '<i class="fa fa-circle fa-online"></i>' : '') + '</span>';
    if (chat.unread > 0) {
        html += '<span class="ant-badge">' + chat.unread + '</span>';
    }
    html += '</div><div class="nav-text-desc">' + chat.lastMsg.content + '</div></div>' +
        '<div class="nav-op">' + formatTime(chat.lastMsg.createTime) + '</div></li>';
    if (prepend) {
        contactItem.prepend(html);
    } else {
        contactItem.append(html);
    }
}

// 历史消息获取
function getHistoryMsg() {
    Ajax.ajax({
        url: '/im/msgList',
        params: {
            "chatNo": chatNo,
            "pageNo": 1
        },
        success: function (data) {
            data.list.forEach(function (msg) {
                addMsgElement(msg.msgType, msg.content, msg.toId, msg.readed, formatTime(msg.createTime), true, msg.extra);
            });
            if (data.pageNum == data.pages) {
                // 判断是否还有下页
                showNoneMsg();
                page = -1;
            } else {
                // 页码+1
                page++;
            }
            scrollBtm();
        }
    });
}

// 滚动历史消息
function scrollHistoryMsg() {
    if (page == -1) {
        return;
    }
    Ajax.ajax({
        async: false,
        url: '/im/msgList',
        params: {
            "chatNo": chatNo,
            "pageNo": page
        },
        success: function (data) {
            data.list.forEach(function (msg) {
                addMsgElement(msg.msgType, msg.content, msg.toId, msg.readed, formatTime(msg.createTime), true, msg.extra);
            });
            if (data.pageNum == data.pages) {
                showNoneMsg();
                page = -1;
            } else {
                page = data.nextPage; //更新页码
            }
        }
    });
}

/**
 * 追加文本消息
 * @param {*} type 消息类型
 * @param {*} content 消息内容
 * @param {*} toId 接收人id
 * @param {*} readed 是否已读
 * @param {*} time 时间，默认当前
 * @param {boolean} prepend 前追加｜后追加
 * @param {*} extra 额外数据
 */
function addMsgElement(type, content, toId, readed, time, prepend, extra) {
    // userId == toId 收信(左)
    // otherId == fromId 发信(右)
    var html = '<div class="x-message-group' + (userId == toId ? '' : ' x-message-right') + '">' +
        '<div class="x-message-user">' + (userId == toId ? otherName : '') + '</div><div class="x-message-content">';
    if (type == 'txt') {
        html += '<p class="x-message-text">' + filterEmoji(content) + '</p>';
    } else if (type == 'pic') {
        html += '<div class="x-message-img"><img src="' + content + '" width="100%" style="vertical-align: middle;"></div>';
    } else if (type == 'aud') {
        var sec = typeof (extra) == "undefined" ? 0 : extra;
        html += '<div class="x-message-audio"><div class="audio-btn" data-sec="' + sec + '" data-src="' + content + '"><i class="fa fa-volume-up"></i><span>' + sec + '\'\'</span></div></div>';
    } else if (type == 'link') {
        var extraTxt = typeof (extra) == "undefined" ? "链接" : (extra == "" ? "链接" : extra);
        html += '<p class="x-message-link" data-link="' + content + '"><i class="fa fa-link"></i> ' + extraTxt + '</p>';
    }
    html += '</div>';
    if (userId == toId) {
        // 如果是收信
        html += '<div class="x-message-time">' + time + '</div>';
    } else {
        html += '<div class="x-message-time"><span class="x-message-status"><i class="fa fa-check' + (readed ? ' fa-read' : ' fa-unread') + '"></i></span>' + time + '</div>';
    }
    html += '</div>';
    if (prepend) { //追加到最前面（历史）
        chatContent.prepend(html);
    } else { //追加到最后（发送）
        chatContent.append(html);
    }
}

/**
 * 发送消息
 * @param {*} type txt文本｜pic图片|aud语音|link链接
 * @param {*} content
 * @param {*} extra 额外数据
 */
function send(type, content, extra) {
    if (!stompOn) {
        // 重新连接
        showAlert("网络连接失败，请刷新页面重试");
        return;
    }
    stompClient.send("/ws/send", {}, JSON.stringify({
        "chatNo": chatNo,
        "fromId": userId,
        "toId": otherId,
        "content": content,
        "msgType": type,
        "extra": typeof (extra) == "undefined" ? "" : extra
    }));
    $("#msg-content").val('');
    addMsgElement(type, content, otherId, false, getTime(), false, extra);
    updateChatItem(chatNo, null, content, type, false);
    scrollBtm();
}

/**
 * 更新对话列表
 * @param {*} inChatNo 传入的对话编号
 * @param {*} fromId 发件人id
 * @param {*} content 消息内容
 * @param {*} msgType 消息类型
 * @param {boolean} receive 接收｜无
 */
function updateChatItem(inChatNo, fromId, content, msgType, receive) {
    $(".menu-list-none").hide();
    var ele = $(".ant-menu-item[data-chatno='" + inChatNo + "']");
    if (fromId != null && ele.text() == "" && !chatListFlag) {
        // 新增对话项条件：来自发件人；对话列表无该编号；非直接对话模式
        var chat = {
            "chatNo": inChatNo,
            "otherId": fromId,
            "otherName": queryNickname(fromId),
            "unread": 1,
            "lastMsg": {
                "content": content,
                "createTime": getTime()
            }
        }
        addChatElement(chat, true);
        return;
    }
    if (msgType == 'txt') {
        ele.find(".nav-text-desc").text(content.length > 15 ? (content.substr(0, 15) + '...') : content);
        ele.find(".nav-op").text(getTime());
    } else if (msgType == 'pic') {
        ele.find(".nav-text-desc").text("[图片]");
    } else if (msgType == 'aud') {
        ele.find(".nav-text-desc").text("[语音]");
    } else if (msgType == 'link') {
        ele.find(".nav-text-desc").text("[链接]");
    }
    if (receive && inChatNo != chatNo) {
        // 如果是接收，并且当前对话不是该编号
        var badge = ele.find(".ant-badge").text();
        if (badge == "") {
            ele.find(".nav-text-left").append('<span class="ant-badge">1</span>');
            ele.data("unread", 1);
        } else {
            var count = parseInt(badge) + 1;
            ele.find(".ant-badge").text(count);
            ele.data("unread", count);
        }
    }
}

// 已读回执
function markRead(chatNo) {
    stompClient.send("/ws/read", {}, JSON.stringify({
        "loginId": userId,
        "otherId": otherId,
        "chatNo": chatNo
    }));
}

function queryNickname(id) {
    var name = "";
    Ajax.ajax({
        async: false,
        url: '/im/queryNickname',
        params: {
            "userId": id
        },
        success: function (data) {
            name = data.data;
        }
    });
    return name;
}

function showNoneMsg() {
    chatContent.prepend('<div class="message-none">没有更多消息啦~</div>');
}

// 滚动到底部
function scrollBtm() {
    chatContent.animate({scrollTop: chatContent.prop("scrollHeight")});
}

// 过滤Emoji
function filterEmoji(txt) {
    if (txt.indexOf('[:') == -1) {
        return txt;
    }
    for (var i = 0; i < emoji.length; i++) {
        var curr = '[:' + emoji[i].code + ']';
        txt = txt.replace(curr, '<img src="' + emoji[i].data + '" width="20" height="20" />');
    }
    return txt;
}

// 获取时间
function getTime() {
    var curTime = new Date();
    return curTime.format('MM-dd hh:mm:ss');
}

function formatTime(time) {
    return time.substr(5, time.length);
}

// 播放提示音
function playSound() {
    alarm.play();
}

// 提示框 n秒消失
function showAlert(msg) {
    alertDiv.text(msg);
    alertDiv.animate({"top": "0px", "opacity": 1});
    setTimeout(function () {
        alertDiv.animate({"top": "-50px", "opacity": 0}, function () {
        });
    }, 3000);
}

function randomNumber(max, min) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function startWave() {
    clearInterval(waveTimer);
    waveTimer = setInterval(function () {
        $.each($(".wave-item"), function (k, v) {
            $(v).css("height", randomNumber(50, 10) + "px");
        });
    }, 200);
}

function stopWave() {
    clearInterval(waveTimer);
    $(".wave-item").css("height", "30px");
}

// 重置对话
function clearChat() {
    $(".x-message-group").remove();
    $(".message-none").remove();
    page = 1;
}

// 开始录音
function startRecording() {
    HZRecorder.get(function (rec) {
        recorder = rec;
        recorder.start();
    });
}

// 取消/关闭/重置录音
function resetAudio() {
    $(".ant-tip-text").text("点击开始录音，最大不超过60秒");
    stopWave();
    $(".hold-btn-rec").removeClass("active");
    $(".hold-btn-cancel").hide();
    $("#record-modal").hide();
    clearInterval(recTimer);
    recSec = 0;
    recording = false;
    if (recorder) {
        recorder.stop();
        recorder.clear();
    }
}

// ws连接
function connect() {
    var socket = (window.location.protocol == "https:" ? "wss://" : "ws://") + window.location.host + "/websocket";
    stompClient = Stomp.client(socket);
    stompClient.debug = false;
    var headers = {
        UID: userId,
        PWD: ''
    }
    stompClient.connect(headers, function (frame) {
        stompOn = true;
        console.log('WS Connected: ' + frame);
        stompClient.subscribe('/topic/receive', function (ret) {//接收
            //console.log(ret);
            var data = JSON.parse(ret.body);
            updateChatItem(data.chatNo, data.fromId, data.content, data.msgType, true);
            if (data.chatNo == chatNo) {
                // 仅对当前会话有效
                addMsgElement(data.msgType, data.content, data.toId, false, getTime(), false, data.extra);
                markRead(data.chatNo);
            }
            playSound();
            scrollBtm();
        });
        stompClient.subscribe('/topic/read', function (ret) {//已读
            //console.log(ret);
            var data = JSON.parse(ret.body);
            if (data.chatNo == chatNo) {
                $(".fa-unread").addClass("fa-read").removeClass("fa-unread");
            }
        });
    }, function (error) {
        stompOn = false;
        showAlert("网络连接失败，正在重新连接");
        setTimeout(function () {
            if (reConn >= 30) {
                return;
            }
            connect();
            reConn++;
        }, 5000);
    });
}

var volumeFlag = false;
var columeNum = 0;
var columeMax = 0;
var columeTimer;

$(function () {
    // 直接对话模式
    if (chatNo != '') {
        $("#chater").text(otherName);
        clearChat();
        $(".menu-list").css("left", "-100%");
        getHistoryMsg();
    } else {
        getChatList();
    }

    connect();

    // 对话列表点击事件
    $(".ant-menu").on("click", ".ant-menu-item", function () {
        $(".menu-list").animate({"left": "-100%"});
        var thisChatNo = $(this).data("chatno");
        if (thisChatNo == chatNo) {
            // 相同会话直接跳过
            return;
        }

        clearChat();
        // 更新属性
        chatNo = thisChatNo;
        otherId = $(this).data("otherid");
        otherName = $(this).data("othername");
        $("#chater").text(otherName);

        getHistoryMsg();
        // 标记已读
        if ($(this).data("unread") > 0) {
            markRead(chatNo);
            $(this).find(".ant-badge").remove();
            $(this).data("unread", 0);
        }
    });

    var dropload = $('.x-chat-content').dropload({
        domUp: {
            domClass: 'dropload-up',
            domRefresh: '<div class="dropload-refresh">↓下拉加载</div>',
            domUpdate: '<div class="dropload-update">↑释放加载</div>',
            domLoad: '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
        },
        loadUpFn: function (me) {
            scrollHistoryMsg();
            dropload.resetload();
        }
    });

    $(".menu-list").on("click", ".menu-list-none", function () {
        $(".menu-list").animate({"left": "-100%"});
    });

    // 返回
    $(".fl").click(function () {
        $(".menu-list").animate({"left": "0px"});
        if (chatListFlag) { //直接对话模式返回再获取对话列表
            getChatList();
        }
    });

    $(".fr").click(function () {
        $(".ant-dropdown-placement").fadeToggle();
    });

    // Emoji
    $(".emojier").click(function () {
        $(".ant-dropdown-emoji-content").fadeToggle();
    });

    // 回车键发送
    $("#msg-content").keyup(function (event) {
        if (event.keyCode == 13) {
            var content = $("#msg-content").val();
            if (content == '') {
                return;
            }
            send('txt', content);
        }
    });
    // 按钮发送
    $(".input-send").click(function () {
        var content = $("#msg-content").val();
        if (content == '') {
            return;
        }
        send('txt', content);
    });

    // 录音弹窗
    $(".recorder").click(function () {
        $("#record-modal").show();
    });

    $(".linker").click(function () {
        $("#link-modal").show();
    });

    // 发送链接
    $("#link-form-button").click(function () {
        var content = $("#link-form-content").val();
        var extra = $("#link-form-extra").val();
        if (content == "") {
            showAlert("请输入链接地址");
            return;
        }
        send('link', content, extra);
        $(".ant-modal-wrap").hide();
        $("#link-form-content").val('');
        $("#link-form-extra").val('');
    });

    // iframe显示链接
    $(".x-chat-content").on("click", ".x-message-link", function () {
        $("#iframe-modal").show();
        var oldLink = $(".modal-iframe").attr("src");
        var link = $(this).data("link");
        if (oldLink != link) {
            $(".modal-iframe").attr("src", link);
        }
    });

    // 取消录音
    $(".hold-btn-cancel").click(function () {
        resetAudio();
    });

    // 开始/结束录音
    $(".hold-btn-rec").click(function () {
        if (recording) {
            // 处理、上传录音
            if (recorder == null) {
                return;
            }
            recorder.stop();
            //console.log("over 录音时长：" + recSec);
            recorder.upload("/uploadAudio", function (state, e) {
                switch (state) {
                    case 'ok':
                        var data = JSON.parse(e.target.responseText);
                        if (data.error == 0) {
                            send('aud', data.url, recSec);
                        } else {
                            showAlert(data.message);
                        }
                        resetAudio();
                        break;
                    case 'error':
                        showAlert("语音上传失败");
                        break;
                }
            });
        } else {
            $(".ant-tip-text").html("再次点击发送语音<span id='recTimer' style='font-weight: 800;margin-left: 8px;'></span>");
            recording = true;
            startWave();
            $(".hold-btn-rec").addClass("active");
            $(".hold-btn-cancel").show();
            // 录音开始
            startRecording();
            // 计时器
            recTimer = setInterval(function () {
                if (recSec > 60) {
                    resetAudio();
                }
                recSec++;
                if (recSec >= 50) {
                    $("#recTimer").css("color", "#f30b0b").text(recSec < 10 ? ('0' + recSec) : recSec);
                } else {
                    $("#recTimer").text(recSec < 10 ? ('0' + recSec) : recSec);
                }
            }, 1000);
        }
    });

    $(".ant-modal-mask, .modal-iframe-close").click(function () {
        $(".ant-modal-wrap").hide();
    });

    // 播放语音
    $(".x-chat-content").on("click", ".audio-btn", function () {
        var t = $(this);
        var src = t.data("src");
        var sec = t.data("sec");
        audio.src = src;
        audio.play();
        if (sec <= 0) {
            return;
        }

        columeMax = sec * 1000 / 500;
        columeTimer = setInterval(function () {
            columeNum++;
            if (columeNum > columeMax) {
                columeMax = 0;
                columeNum = 0;
                volumeFlag = false;
                clearInterval(columeTimer);
            }
            if (volumeFlag) {
                t.find(".fa").removeClass("fa-volume-up").addClass("fa-volume-down");
                volumeFlag = false;
            } else {
                t.find(".fa").removeClass("fa-volume-down").addClass("fa-volume-up");
                volumeFlag = true;
            }
        }, 500);
    });

    // Emoji点击追加到文本框
    $(".x-emoji").on("click", ".ant-dropdown-menu-item", function () {
        var code = $(this).data("code");
        $("#msg-content").val($("#msg-content").val() + "[:" + code + "]");
        $(".ant-dropdown-emoji-content").hide();
    });

    $(".menu-trash").click(function () {
        $(".ant-dropdown-placement").hide();
    });


    // 关闭弹框
    $(".x-chat-content").click(function () {
        $(".ant-dropdown-emoji-content").hide();
        $(".ant-dropdown-placement").hide();
    });

    var maxImgSize = 5 * 1024 * 1024;
    // 上传图片
    $("#uploadImage").change(function () {
        var formData = new FormData();
        var file = document.getElementById('uploadImage').files[0];
        if (file.type != "image/jpg" && file.type != "image/jpeg" && file.type != "image/png" && file.type != "image/gif") {
            showAlert("图片格式不正确" + file.type);
            return;
        }
        formData.append('file', file);
        var size = file.size || file.fileSize;
        if (size > maxImgSize) {
            showAlert("图片最大不能超过5兆(M)");
            return;
        }
        $.ajax({
            url: "/upload",
            type: "post",
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.error == 0) {
                    send('pic', data.url);
                } else {
                    showAlert(data.message);
                }
            },
            error: function (data) {
                showAlert("上传图片失败")
            }
        });
    });

    // 初始化Emoji弹框内容
    var emojiHtml = '';
    emoji.forEach(function (emo) {
        emojiHtml += '<li class="ant-dropdown-menu-item ib" data-code="' + emo.code + '"><img src="' + emo.data + '"></li>'
    });
    $(".x-emoji").html(emojiHtml);
});

Ajax = {
    ajax: function (options) {
        var defaults = {
            async: true,
            dataType: 'json',
            url: null,
            params: null,
            success: null,
            error: null
        };
        var config = $.extend(defaults, options);
        if (config.url) {
            $.ajax({
                type: "POST",
                async: config.async,
                // contentType : 'application/json',
                url: config.url,
                data: config.params,
                dataType: config.dataType,
                success: function (data) {
                    if (config.success) {
                        config.success(data);
                    }
                },
                error: function (er) {
                    showAlert('Request Error');
                }
            });
        } else {
            showAlert('Request Url Is Error');
        }
    }
};

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds()
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

if (navigator.mediaDevices === undefined) {
    navigator.mediaDevices = {};
}
if (navigator.mediaDevices.getUserMedia === undefined) {
    navigator.mediaDevices.getUserMedia = function (constraints) {
        var getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
        if (!getUserMedia) {
            return Promise.reject(new Error('getUserMedia()不支持'));
        }
        return new Promise(function (resolve, reject) {
            getUserMedia.call(navigator, constraints, resolve, reject);
        });
    }
}
window.URL = window.URL || window.webkitURL;
//navigator.mediaDevices.getUserMedia   navigator.getUserMedia
//navigator.mediaDevices.getUserMedia = navigator.mediaDevices.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;

var HZRecorder = function (stream, config) {
    config = config || {};
    config.sampleBits = config.sampleBits || 8;      //采样数位 8, 16
    config.sampleRate = config.sampleRate || (44100 / 6);   //采样率(1/6 44100)

    //创建一个音频环境对象
    var audioContext = window.AudioContext || window.webkitAudioContext;
    var context = new audioContext();
    var audioInput = context.createMediaStreamSource(stream);
    // 第二个和第三个参数指的是输入和输出都是单声道,2是双声道。
    var recorder = context.createScriptProcessor(4096, 1, 1);

    var audioData = {
        size: 0,          //录音文件长度
        buffer: [],     //录音缓存
        inputSampleRate: context.sampleRate,    //输入采样率
        inputSampleBits: 16,       //输入采样数位 8, 16
        outputSampleRate: config.sampleRate,    //输出采样率
        outputSampleBits: config.sampleBits,       //输出采样数位 8, 16
        input: function (data) {
            this.buffer.push(new Float32Array(data));
            this.size += data.length;
        },
        compress: function () { //合并压缩
            //合并
            var data = new Float32Array(this.size);
            var offset = 0;
            for (var i = 0; i < this.buffer.length; i++) {
                data.set(this.buffer[i], offset);
                offset += this.buffer[i].length;
            }
            //压缩
            var compression = parseInt(this.inputSampleRate / this.outputSampleRate);
            var length = data.length / compression;
            var result = new Float32Array(length);
            var index = 0, j = 0;
            while (index < length) {
                result[index] = data[j];
                j += compression;
                index++;
            }
            return result;
        },
        encodeWAV: function () {
            var sampleRate = Math.min(this.inputSampleRate, this.outputSampleRate);
            var sampleBits = Math.min(this.inputSampleBits, this.outputSampleBits);
            var bytes = this.compress();
            var dataLength = bytes.length * (sampleBits / 8);
            var buffer = new ArrayBuffer(44 + dataLength);
            var data = new DataView(buffer);

            var channelCount = 1;//单声道
            var offset = 0;

            var writeString = function (str) {
                for (var i = 0; i < str.length; i++) {
                    data.setUint8(offset + i, str.charCodeAt(i));
                }
            }

            // 资源交换文件标识符
            writeString('RIFF');
            offset += 4;
            // 下个地址开始到文件尾总字节数,即文件大小-8
            data.setUint32(offset, 36 + dataLength, true);
            offset += 4;
            // WAV文件标志
            writeString('WAVE');
            offset += 4;
            // 波形格式标志
            writeString('fmt ');
            offset += 4;
            // 过滤字节,一般为 0x10 = 16
            data.setUint32(offset, 16, true);
            offset += 4;
            // 格式类别 (PCM形式采样数据)
            data.setUint16(offset, 1, true);
            offset += 2;
            // 通道数
            data.setUint16(offset, channelCount, true);
            offset += 2;
            // 采样率,每秒样本数,表示每个通道的播放速度
            data.setUint32(offset, sampleRate, true);
            offset += 4;
            // 波形数据传输率 (每秒平均字节数) 单声道×每秒数据位数×每样本数据位/8
            data.setUint32(offset, channelCount * sampleRate * (sampleBits / 8), true);
            offset += 4;
            // 快数据调整数 采样一次占用字节数 单声道×每样本的数据位数/8
            data.setUint16(offset, channelCount * (sampleBits / 8), true);
            offset += 2;
            // 每样本数据位数
            data.setUint16(offset, sampleBits, true);
            offset += 2;
            // 数据标识符
            writeString('data');
            offset += 4;
            // 采样数据总数,即数据总大小-44
            data.setUint32(offset, dataLength, true);
            offset += 4;
            // 写入采样数据
            if (sampleBits === 8) {
                for (var i = 0; i < bytes.length; i++, offset++) {
                    var s = Math.max(-1, Math.min(1, bytes[i]));
                    var val = s < 0 ? s * 0x8000 : s * 0x7FFF;
                    val = parseInt(255 / (65535 / (val + 32768)));
                    data.setInt8(offset, val, true);
                }
            } else {
                for (var i = 0; i < bytes.length; i++, offset += 2) {
                    var s = Math.max(-1, Math.min(1, bytes[i]));
                    data.setInt16(offset, s < 0 ? s * 0x8000 : s * 0x7FFF, true);
                }
            }
            return new Blob([data], {type: 'audio/mp3'});
        }
    };

    //开始录音
    this.start = function () {
        audioInput.connect(recorder);
        recorder.connect(context.destination);
    }

    //停止
    this.stop = function () {
        recorder.disconnect();
    }

    //获取音频文件
    this.getBlob = function () {
        this.stop();
        return audioData.encodeWAV();
    }

    //回放
    this.play = function (audio) {
        audio.src = window.URL.createObjectURL(this.getBlob());
    }
    //清除
    this.clear = function () {
        audioData.buffer = [];
        audioData.size = 0;
    }

    //上传
    this.upload = function (url, callback) {
        var fd = new FormData();
        fd.append("file", this.getBlob());
        var xhr = new XMLHttpRequest();
        if (callback) {
            xhr.upload.addEventListener("progress", function (e) {
                callback('uploading', e);
            }, false);
            xhr.addEventListener("load", function (e) {
                callback('ok', e);
            }, false);
            xhr.addEventListener("error", function (e) {
                callback('error', e);
            }, false);
            xhr.addEventListener("abort", function (e) {
                callback('cancel', e);
            }, false);
        }
        xhr.open("POST", url);
        xhr.send(fd);
    }
    /*
    recorder.upload("/uploadAudio", function (state, e) {
        switch (state) {
            case 'uploading':
                //var percentComplete = Math.round(e.loaded * 100 / e.total) + '%';
                break;
            case 'ok':
                var ret = e.target.responseText;
                console.log(ret);
                break;
            case 'error':
                alert("上传失败");
                break;
            case 'cancel':
                alert("上传被取消");
                break;
        }
    });
    * */
    //音频采集
    recorder.onaudioprocess = function (e) {
        audioData.input(e.inputBuffer.getChannelData(0));
        //record(e.inputBuffer.getChannelData(0));
    }
};
// 抛出异常
HZRecorder.throwError = function (message) {
    showAlert(message);
    resetAudio();
    throw new function () {
        this.toString = function () {
            return message;
        }
    }
}
// 获取录音机
HZRecorder.get = function (callback, config) {
    if (callback) {
        navigator.mediaDevices.getUserMedia({audio: true})
            .then(function (stream) {
                var rec = new HZRecorder(stream, config);
                callback(rec);
            })
            .catch(function (error) {
                switch (error.name) {
                    case 'PERMISSION_DENIED':
                    case 'PermissionDeniedError':
                        HZRecorder.throwError('用户拒绝提供信息');
                        break;
                    case 'NOT_SUPPORTED_ERROR':
                    case 'NotSupportedError':
                        HZRecorder.throwError('浏览器不支持硬件设备');
                        break;
                    case 'MANDATORY_UNSATISFIED_ERROR':
                    case 'MandatoryUnsatisfiedError':
                        HZRecorder.throwError('无法发现指定的硬件设备');
                        break;
                    default:
                        HZRecorder.throwError('当前浏览器和微信不支持录音功能');
                        break;
                }
            });
    }
};

// dropload
!function (a) {
    "use strict";

    function g(a) {
        a.touches || (a.touches = a.originalEvent.touches)
    }

    function h(a, b) {
        b._startY = a.touches[0].pageY, b.touchScrollTop = b.$scrollArea.scrollTop()
    }

    function i(b, c) {
        c._curY = b.touches[0].pageY, c._moveY = c._curY - c._startY, c._moveY > 0 ? c.direction = "down" : c._moveY < 0 && (c.direction = "up");
        var d = Math.abs(c._moveY);
        "" != c.opts.loadUpFn && c.touchScrollTop <= 0 && "down" == c.direction && !c.isLockUp && (b.preventDefault(), c.$domUp = a("." + c.opts.domUp.domClass), c.upInsertDOM || (c.$element.prepend('<div class="' + c.opts.domUp.domClass + '"></div>'), c.upInsertDOM = !0), n(c.$domUp, 0), d <= c.opts.distance ? (c._offsetY = d, c.$domUp.html(c.opts.domUp.domRefresh)) : d > c.opts.distance && d <= 2 * c.opts.distance ? (c._offsetY = c.opts.distance + .5 * (d - c.opts.distance), c.$domUp.html(c.opts.domUp.domUpdate)) : c._offsetY = c.opts.distance + .5 * c.opts.distance + .2 * (d - 2 * c.opts.distance), c.$domUp.css({height: c._offsetY}))
    }

    function j(b) {
        var c = Math.abs(b._moveY);
        "" != b.opts.loadUpFn && b.touchScrollTop <= 0 && "down" == b.direction && !b.isLockUp && (n(b.$domUp, 300), c > b.opts.distance ? (b.$domUp.css({height: b.$domUp.children().height()}), b.$domUp.html(b.opts.domUp.domLoad), b.loading = !0, b.opts.loadUpFn(b)) : b.$domUp.css({height: "0"}).on("webkitTransitionEnd mozTransitionEnd transitionend", function () {
            b.upInsertDOM = !1, a(this).remove()
        }), b._moveY = 0)
    }

    function k(a) {
        "" != a.opts.loadDownFn && a.opts.autoLoad && a._scrollContentHeight - a._threshold <= a._scrollWindowHeight && m(a)
    }

    function l(a) {
        a._scrollContentHeight = a.opts.scrollArea == b ? e.height() : a.$element[0].scrollHeight
    }

    function m(a) {
        a.direction = "up", a.$domDown.html(a.opts.domDown.domLoad), a.loading = !0, a.opts.loadDownFn(a)
    }

    function n(a, b) {
        a.css({"-webkit-transition": "all " + b + "ms", transition: "all " + b + "ms"})
    }

    var f, b = window, c = document, d = a(b), e = a(c);
    a.fn.dropload = function (a) {
        return new f(this, a)
    }, f = function (a, b) {
        var c = this;
        c.$element = a, c.upInsertDOM = !1, c.loading = !1, c.isLockUp = !1, c.isLockDown = !1, c.isData = !0, c._scrollTop = 0, c._threshold = 0, c.init(b)
    }, f.prototype.init = function (f) {
        var l = this;
        l.opts = a.extend(!0, {}, {
            scrollArea: l.$element,
            domUp: {
                domClass: "dropload-up",
                domRefresh: '<div class="dropload-refresh">↓下拉刷新</div>',
                domUpdate: '<div class="dropload-update">↑释放更新</div>',
                domLoad: '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
            },
            domDown: {
                domClass: "dropload-down",
                domRefresh: '<div class="dropload-refresh">↑上拉加载更多</div>',
                domLoad: '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
                domNoData: '<div class="dropload-noData">暂无数据</div>'
            },
            autoLoad: !0,
            distance: 50,
            threshold: "",
            loadUpFn: "",
            loadDownFn: ""
        }, f), "" != l.opts.loadDownFn && (l.$element.append('<div class="' + l.opts.domDown.domClass + '">' + l.opts.domDown.domRefresh + "</div>"), l.$domDown = a("." + l.opts.domDown.domClass)), l._threshold = l.$domDown && "" === l.opts.threshold ? Math.floor(1 * l.$domDown.height() / 3) : l.opts.threshold, l.opts.scrollArea == b ? (l.$scrollArea = d, l._scrollContentHeight = e.height(), l._scrollWindowHeight = c.documentElement.clientHeight) : (l.$scrollArea = l.opts.scrollArea, l._scrollContentHeight = l.$element[0].scrollHeight, l._scrollWindowHeight = l.$element.height()), k(l), d.on("resize", function () {
            clearTimeout(l.timer), l.timer = setTimeout(function () {
                l._scrollWindowHeight = l.opts.scrollArea == b ? b.innerHeight : l.$element.height(), k(l)
            }, 150)
        }), l.$element.on("touchstart", function (a) {
            l.loading || (g(a), h(a, l))
        }), l.$element.on("touchmove", function (a) {
            l.loading || (g(a, l), i(a, l))
        }), l.$element.on("touchend", function () {
            l.loading || j(l)
        }), l.$scrollArea.on("scroll", function () {
            l._scrollTop = l.$scrollArea.scrollTop(), "" != l.opts.loadDownFn && !l.loading && !l.isLockDown && l._scrollContentHeight - l._threshold <= l._scrollWindowHeight + l._scrollTop && m(l)
        })
    }, f.prototype.lock = function (a) {
        var b = this;
        void 0 === a ? "up" == b.direction ? b.isLockDown = !0 : "down" == b.direction ? b.isLockUp = !0 : (b.isLockUp = !0, b.isLockDown = !0) : "up" == a ? b.isLockUp = !0 : "down" == a && (b.isLockDown = !0, b.direction = "up")
    }, f.prototype.unlock = function () {
        var a = this;
        a.isLockUp = !1, a.isLockDown = !1, a.direction = "up"
    }, f.prototype.noData = function (a) {
        var b = this;
        void 0 === a || 1 == a ? b.isData = !1 : 0 == a && (b.isData = !0)
    }, f.prototype.resetload = function () {
        var b = this;
        "down" == b.direction && b.upInsertDOM ? b.$domUp.css({height: "0"}).on("webkitTransitionEnd mozTransitionEnd transitionend", function () {
            b.loading = !1, b.upInsertDOM = !1, a(this).remove(), l(b)
        }) : "up" == b.direction && (b.loading = !1, b.isData ? (b.$domDown.html(b.opts.domDown.domRefresh), l(b), k(b)) : b.$domDown.html(b.opts.domDown.domNoData))
    }
}(window.jQuery);