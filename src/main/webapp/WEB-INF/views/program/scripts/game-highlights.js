const article = 0;

function change_current_screenshot() {
    $("#current-screenshot").attr("src", this.src);
}

window.onload = () => {
    var game = games_list[article];

    // Название игры
    document.title = "Soft Depot - " + game.name;

    for (let element of $(".game-name")) {
        element.innerHTML = game.name;
    }

    // Оценка
    $("#short-description").html(game.description);
    $("#score").html(game.score);
    $("#score").css("color", game.getScoreColor(game.score));

    // Цена
    if (game.price > 0) $("#price").html(game.price + " руб.");
    else $("#price").html("Бесплатно");

    // Покупка
    $("#buy-button").on("click", game.buy);

    // Большое описание
    $("#description-text").html(game.big_description);
};
