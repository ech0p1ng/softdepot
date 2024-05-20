function change_current_screenshot() {
    $("#current-screenshot").attr("src", this.src);
}

//Определение цвета оценки по числу (0 - красный, 5 - зеленый)
function getScoreColor(score) {
    let range = 512;
    let r = 255;
    let g = 0;
    let b = 0;
    let g1 = parseInt((range / 5) * parseFloat(score));
    g += g1;

    if (g > 255) {
        r -= g - 255;
        g = 255;
    }
    r -= 64;
    g -= 64;
    b -= 64;

    return "rgb(" + r + "," + g + "," + b + ")";
}

window.onload = () => {
    //замена у тега #score цвета в зависимости от оценки
    $("#score").css("color", getScoreColor($("#score").html()));
};

