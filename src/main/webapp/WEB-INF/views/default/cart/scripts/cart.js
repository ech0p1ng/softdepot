function get_row(program) {
    let header_url = program.headerUrl;
    let id = program.id;
    let name = program.name;
    let priceStr = program.priceAsString;

    let row_html = "" +
        "<div class=\"cart-game-row\">" +
        "    <img class=\"preview\" src=\""+ header_url +"\" />" +
        "    <a class=\"cart-game-description\" href=\"/program/id"+id+"\" target=\"_blank\">" +
        "        <span class=\"cart-game-title\" >"+name+"</span>" +
        "    </a>" +
        "    <div class=\"cart-game-price\">" +
        "        <span class=\"cart-game-price-title\">"+priceStr+"</span>" +
        "        <button class=\"cart-game-remove button\" program-id=\""+id+"\"></button>" +
        "    </div>" +
        "</div>";
    return row_html;
}

function show_cart() {
    $("#cart-games-list").empty();
    let sum_price = 0;
    $.ajax({
        type: "GET",
        url: "/program/get-cart",
        contentType: "application/json; charset=utf-8",
        success: function (programs) {
            programs.forEach(function (program){
               let row = get_row(program);
               $("#cart-games-list").append(row);
               sum_price += program.price;
            });
            $('#total-cost').html(sum_price + ' руб.');
        },
        error: function () {

        }
    })


    $("#cart-bg").css("visibility", "inherit");
}

function close_cart() {
    $("#cart-bg").css("visibility", "hidden");
}

$("#cart-exit-button").on("click", close_cart);

//Вызов метода из SpringMVC по адресу /program/id{id}/add-to-cart
function cart_handler() {
    let programId = $('.add-to-cart').attr('program-id');
    console.log(programId);

    $.ajax({
        type: 'POST',
        url: '/program/id' + programId + '/add-to-cart',
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            // $('[program-id="'+programId+'"]').css("background-color", "darkred");
            // $('[program-id="'+programId+'"]').html('<span>Удалить из корзины</span>');
            // console.log("program with id " + programId + " added to cart")
            alert(response)
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText)
        }
    })
}
// $('.add-to-cart').click = cart_handler;