/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

onload = function()
{
    loadAllMessages();
    $("#submit").click(addNewMessage);
}

function loadAllMessages(){
    var request = new XMLHttpRequest();
    request.open("GET","http://localhost:8080/guestbook/api/messages");
    request.onload = function()
    {
        if(request.status === 200)
        {
            $("#messages").empty();
            var messages = JSON.parse(request.responseText);
            for(var i = 0;i<messages.length;i++)
            {
                var item = $("<p>");
                item.html("<strong>"+messages[i].name+"</strong> zei :  </br>"+messages[i].message);
                $("#messages").append(item);
            }
            
            $("#error").empty();
        }
        else
        {
        $("#error").text("Oeps er is iets fout gelopen")
        }
    };
    request.send(null);
}

function addNewMessage(){
    var message = {};
    message.name = $("#name").val();
    message.message = $("#message").val();
    
    var request = new XMLHttpRequest();
    request.open("POST","http://localhost:8080/guestbook/api/messages");
    request.onload = function(){
        if(request.status === 201)
        {
            $("#error").empty();
            loadAllMessages();
        }
        else
        {
            $("#error").text("Shit, something went wrong");
            
        }
        
    };
    request.setRequestHeader("Content-type","application/json");
    request.send(JSON.stringify(message));
   
}