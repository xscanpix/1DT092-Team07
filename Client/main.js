var board = new Image();
var robot = new Image();
var i = 0;

function init() {
    board.src = 'board.png';
    robot.src = 'robot.png';
    window.requestAnimationFrame(draw);
}

function draw() {
    i +=1;
    var ctx = document.getElementById('canvas').getContext('2d');

    ctx.globalCompositeOperation = 'destination-over';
    ctx.clearRect(0, 0, 500, 500); // clear canvas

    ctx.fillStyle = 'rgba(0, 0, 0, 0.4)';
    ctx.strokeStyle = 'rgba(0, 153, 255, 0.4)';
    ctx.save();
    ctx.translate(0, 0);

    // robot
    ctx.translate(i, 50);
    ctx.drawImage(robot, 0, 0);

    ctx.restore();

    ctx.drawImage(board, 0, 0, 500, 500);

    window.requestAnimationFrame(draw);
}

init();
