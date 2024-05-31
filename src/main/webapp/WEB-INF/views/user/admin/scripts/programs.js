function add_game_row(program_id, program_name, header_url) {
    let row = '' +
        '<div class="admin-game-row">'+
        '    <img class="preview" src="'+ header_url +'"/>'+
        '    <a class="admin-game-description" href="/program/id'+program_id+'" target="_blank">'+
        '        <span class="admin-game-title" program-id="'+program_id+'">'+program_name+'</span>'+
        '    </a>'+
        '    <div class="admin-game-buttons">'+
        '        <button class="edit-button button" program-id="'+program_id+'" onClick="edit_program(event)"></button>'+
        '       <button class="admin-game-remove button" program-id="'+program_id+'" onClick="remove_program(event)"></button>'+
        '    </div>'+
        '</div>';

    $('#admin-games-list').prepend(row);
}

function update_programs() {
    $('#admin-games-list').empty();
    $.ajax({
        method: 'GET',
        url: '/program/get-all',
        contentType: 'application/json;charset=UTF-8',
        success: function (programs) {
            programs.forEach(function (program) {
                add_game_row(program.id, program.name, program.headerUrl);
            });
        },
        error: function (msg) {
            console.log(msg);
        }
    })
}

function close_form() {
    event.preventDefault();
    $(".add-program-form-bg").css("visibility", "hidden");

}

function add_program_from_admin() {
    $(".add-program-form-bg").css("visibility", "inherit");
}

function remove_program(event) {
    let program_id = event.currentTarget.getAttribute('program-id');
    let program_name = $('.admin-game-title[program-id='+program_id+']');

    let remove_program_answer = confirm('Вы действительно хотите удалить "'+program_name+'"?');
    if (remove_program_answer) {
        $.ajax({
            method: 'POST',
            url: 'program/id'+program_id+'/delete',
            contentType: 'text/plain;charset=UTF-8',
            success: function () {
                update_programs();
            },
            error: function (message) {
                console.error(message);
            }
        })
    }


}

function edit_program(event) {

    $('#form-program-name')

    $(".add-program-form-bg").css("visibility", "inherit");

}

document.addEventListener("click", function (e) {
    if (e.target.className === "close-button") {
        close_form();
    }
})

document.addEventListener("DOMContentLoaded", function (){
    update_programs();
})