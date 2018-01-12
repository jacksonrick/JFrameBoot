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

<script src="/static/library/jquery/jquery-2.1.1.min.js"></script>
<#--<script src="/static/library/jquery/sockjs-1.1.4.min.js"></script>-->
<script src="/static/library/jquery/stomp-2.3.3.min.js"></script>
<script>
    var stompClient = null;

    function connect() {
        //var socket = new SockJS('ws://127.0.0.1:8081/websocket'); // or WebSocket('/websocket')
        var socket = "ws://" + window.location.host + "/websocket";
        stompClient = Stomp.client(socket);
        //stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            //console.log('Connected: ' + frame);
            stompClient.subscribe('/jf/chat', function (ret) {
                console.log(ret);
                //{"username":"feifei","target":"feifei2","message":"qwert2","date":"2018-01-10 08:34:54"}
                var json = JSON.parse(ret.body);
                $("#greetings").append("<tr>" +
                        "<td>" + json.username + "</td>" +
                        "<td>" + json.message + "</td>" +
                        "<td>" + json.date + "</td>" +
                        "</tr>");
            });
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
        }
        else {
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
            stompClient.send("/app/say", {}, JSON.stringify({'target': $("#target").val(), 'message': $("#msg").val()}));
        });
    });
</script>
</body>
</html>