var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#from").prop("disabled", connected);
    $("#text").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    
    
    if (connected) {
        $("#conversation").show();        
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
	$("#text").focus();
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
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

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'from': $("#from").val(),'text': $("#text").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() {
    	if($("#from").val() ==''){
    		$("#from").focus();
    	}else{   
    	connect();     	
    }
    });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName();$("#text").val(''); $("#text").focus(); });
});

