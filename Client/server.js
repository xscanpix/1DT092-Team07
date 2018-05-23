$( document ).ready(function() {
    setInterval(function() {
        $.ajax({
            url: "http://127.0.0.1:8080/get/robot",
            crossDomain: true,
            success: function(result){
                var data = JSON.parse(result);
                console.log(data)
                var temp = data.Direction;
                var speed = data.Speed;

                $('#temp_display').html(temp);
                $('#speed_display').html(speed);
            }
        });
    }, 500);

});
