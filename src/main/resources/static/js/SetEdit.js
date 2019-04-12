

let flnum = 0;
let flsets = [];

var information = {"id": ""};

function replace_string(origin_str, r_substr, n_substr) {
    let n_str = origin_str.split(r_substr);
    return n_str.join(n_substr);
}

function del_flashcard_callback(event) {
    $(event.target).parent().remove();
}

function get_flashcards_str() {
    let str = "";
    $(".flashcard").each(function(index) {
        if (str != "") { str += ","; }
        str += replace_string($($($(this).children()[1]).children()[0]).val(), ",", ".~.");
        str += "\\";
        str += replace_string($($($(this).children()[2]).children()[0]).val(), ",", ".~.");
    });
    return str;
}

function update_flashcard_server() {
    $.post("/flashcard/ajax/Flashcardset/updateCards", {"id": information["id"], "flashcards": get_flashcards_str()},
           function(data) { console.log(data); });
    console.log("Done with POST Save");
}

function insert_flashcard(front, back) {
    let new_flashcard = '<li class="flashcard">\
                        <button type="button" class="delete-flashcard">🗑️</button>\
                        <div class="front-flashcard">\
                            <textarea >' + front + '</textarea>\
                            <p>Front</p>\
                        </div>\
                        <div class="back-flashcard">\
                            <textarea>' + back + '</textarea>\
                            <p>Back</p>\
                        </div>\
                    </li>';

    $(new_flashcard).insertBefore("#add-flashcard");
    $("textarea").change(function(){
            update_flashcard_server();
    });
    //Adds event listener to newly created element.
    $(".delete-flashcard").on("click", del_flashcard_callback);
}


function write_flashcards(flashcards) {
    $(".flashcard").remove();
    if (flashcards == "") {
        return;
    }
    let fbc = flashcards.split(",");
    for (let i = 0; i < fbc.length; i++) {
        let c = replace_string(fbc[i], ".~.", ",").split("\\");
        insert_flashcard(c[0], c[1]);
    }
}



$(document).ready(function() {

    information["id"] = $("#flashcardsetId").text().substring(4);

    console.log(information["id"]);

    $.get("/flashcard/ajax/Flashcardset?id=" + information["id"],
        function(data) {
            console.log("SUCCESSFUL");
            console.log("data: ");
            console.log(data);
            console.log("flashcards");
            console.log(data["flashcards"]);
            console.log("END");
            write_flashcards(data["flashcardsStr"]);
        }
    );

    $("#add-flashcard").on("click", function(event){
        insert_flashcard("", "");
        update_flashcard_server();
    });

    $(".delete-flashcard").on("click", del_flashcard_callback);

});
