<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>WebSocket</title>
</head>
<body>
<div id="main-content" class="container">
    <div class="row">
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <label for="connect">WebSocket connection:</label>
                    <button id="connect" class="btn btn-default" type="button">Connect</button>
                    <button id="disconnect" class="btn btn-default" type="button" disabled="disabled">Disconnect
                    </button>
                </div>
            </form>
        </div>
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <input type="text" id="target" class="form-control" placeholder="send to...">
                    <input type="text" id="msg" class="form-control" placeholder="Your message here...">
                </div>
                <button id="send" class="btn btn-default" type="button">Send</button>
                <button id="sendall" class="btn btn-default" type="button">SendAll</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="conversation" class="table table-striped">
                <thead>
                <tr>
                    <th>sender</th>
                    <th>message</th>
                    <th>date</th>
                </tr>
                </thead>
                <tbody id="greetings">
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="static/js/jquery-2.1.1.min.js"></script>
<script src="static/js/stomp-2.3.3.min.js"></script>
<script>
    var stompClient = null;

    function connect() {
        //var socket = new SockJS('ws://127.0.0.1:8081/websocket'); // or WebSocket('/websocket')
        var socket = "ws://" + window.location.host + "/websocket";
        //stompClient = Stomp.over(socket);
        stompClient = Stomp.client(socket);
        //stompClient.debug = false;
        //stompClient.heartbeat.outgoing = 20000;
        var headers = {
            UID: getQueryString("uid"),
            NAME: getQueryString("name"),
            PWD: ''
        }
        stompClient.connect(headers, function (frame) {
            //console.log('Connected: ' + frame);
            setConnected(true);
            stompClient.subscribe('/topic/chat', function (ret) {
                console.log(ret);
                var json = JSON.parse(ret.body);
                $("#greetings").append("<tr>" +
                    "<td>" + json.username + "</td>" +
                    "<td>" + json.message + "</td>" +
                    "<td>" + json.date + "</td>" +
                    "</tr>");
            });
        }, function (error) {
            console.log(error);
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    function setConnected(connected) {
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) {
            $("#conversation").show();
        } else {
            $("#conversation").hide();
        }
        $("#greetings").html("");
    }

    $(function () {
        connect();

        $("#connect").click(function () {
            connect();
        });
        $("#disconnect").click(function () {
            disconnect();
        });
        $("#send").click(function () {
            stompClient.send("/ws/send", {}, JSON.stringify({'target': $("#target").val(), 'message': $("#msg").val()}));
        });

        $("#sendall").click(function () {
            stompClient.send("/ws/sendall", {}, null);
        });
    });

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)
            return unescape(r[2]);
        return null;
    }
</script>
</body>
</html>