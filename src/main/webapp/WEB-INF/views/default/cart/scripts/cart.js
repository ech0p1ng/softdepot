function set_add_to_cart_style_main_page(element){
    // element.css("background-color", "darkgreen");
    element.css("class", "add-to-cart");
    element.html("<span>Добавить в корзину</span>");
    element.attr("onclick", "add_to_cart_from_list(event)");
}

function set_remove_from_cart_style_main_page(element){
    // element.css("background-color", "darkred");
    element.attr("class", "remove-from-cart");
    element.html("<span>Удалить из корзины</span>");
    element.attr("onclick", "remove_from_cart_from_list(event)");
}


function get_programs_from_cart() {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: "/program/get-cart",
            contentType: "application/json; charset=utf-8",
            success: function (programs) {
                sum_price = 0;
                programs.forEach(function (program) {
                    sum_price += program.price;
                });
                resolve(programs);
            },
            error: function () {
                reject();
            }
        })
    })
}

function remove_program_from_cart(programId) {
    $.ajax({
        type: 'GET',
        url: '/program/id' + programId + '/remove-from-cart',
        contentType: "text/plain; charset=utf-8",
        success: function (response) {
            //изменение кнопки на главной
            set_add_to_cart_style_main_page($('[program-id="'+programId+'"'));
            update_cart();
            console.log(response);
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText)
        }
    })
}

function add_program_to_cart(programId) {
    $.ajax({
        type: 'POST',
        url: '/program/id' + programId + '/add-to-cart',
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            //изменение кнопки на главной
            set_remove_from_cart_style_main_page($('[program-id="'+programId+'"'));
            update_cart();
            console.log(response);
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText)
        }
    })
}



function get_cart_row(program) {
    let header_url = program.headerUrl;
    let id = program.id;
    let name = program.name;
    let priceStr = program.priceAsString;

    let row_html = '' +
        '<div class="cart-game-row">' +
        '    <img class="preview" src="'+ header_url +'" />' +
        '    <a class="cart-game-description" href="/program/id'+id+'" target="_blank">' +
        '        <span class="cart-game-title" >'+name+'</span>' +
        '    </a>' +
        '    <div class="cart-game-price">' +
        '        <span class="cart-game-price-title">'+priceStr+'</span>' +
        '        <button class="cart-game-remove button" program-id="'+id+'" onclick="remove_from_cart(event)"></button>' +
        '    </div>' +
        '</div>' ;
    return row_html;
}

function remove_from_cart(event) {
    event.preventDefault(); // предотвращаем стандартное поведение кнопки

    let program_id = event.currentTarget.getAttribute('program-id');
    console.log(program_id);
    $.ajax({
        type: "GET",
        url: "program/id" + program_id + "/remove-from-cart",
        contentType: "application/json; charset=utf-8",
        success: function () {
            update_cart();
        },
        error: function () {}
    });
}

function update_cart() {
    $("#cart-games-list").empty();

    let sum_price = 0;

    get_programs_from_cart()
        .then(programs => {
            programs.forEach(function (program) {
                let row = get_cart_row(program);
                $("#cart-games-list").append(row);
                sum_price += program.price;
            })
            $('#total-cost').html(sum_price + ' руб.');
        })
        .catch(error => {
            console.log(error);
        });


}

function show_cart() {
    update_cart();

    $("#cart-bg").css("visibility", "inherit");
}

function close_cart() {
    $("#cart-bg").css("visibility", "hidden");
}

//Функции для главной страницы
//Вызов метода из SpringMVC по адресу /program/id{id}/add-to-cart
function add_to_cart_from_list(event) {
    event.preventDefault();
    let programId = event.currentTarget.getAttribute('program-id');
    add_program_to_cart(programId);
}

function remove_from_cart_from_list(event) {
    event.preventDefault();
    let programId = event.currentTarget.getAttribute('program-id');
    remove_program_from_cart(programId);
}

$("#cart-exit-button").on("click", close_cart);

document.addEventListener("DOMContentLoaded", function (){
    //выделение кнопок программ, добавленных в корзину
    $('.add-to-cart').each(function (i) {
        let button = $(this);
        let programId = button.attr('program-id');
        get_programs_from_cart()
            .then(programs => {
                programs.forEach(function (program) {
                    if (program.id == programId) {
                        set_remove_from_cart_style_main_page(button);
                    }
                })
            })
            .catch(error => {
                console.log(error);
            });
    });
});


