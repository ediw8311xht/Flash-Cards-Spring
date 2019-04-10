

let flnum = 0;
let flsets = [];


function del_flashcard_callback(event) {
    $(event.target).parent().remove();
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

    //Adds event listener to newly created element.
    $(".delete-flashcard").on("click", del_flashcard_callback);
}

function update_flashcard_server() {
    $.post("/flashcard/ajax/Flashcardset/updateCards", {"id": information["id"], "flashcards": get_flashcards_str()},
           function(data) { console.log(data); });
    console.log("Done with POST Save");
}

function get_flashcards_str() {
    let str = "";
    console.log("getflashcardsstr");
    $(".flashcard").each(function(index) {
        if (str != "") { str += ","; }
        console.log($($($(this).children()[1]).children()[0]).val());
        console.log($($(this).children()[2]).val());
        str += $($($(this).children()[1]).children()[0]).val();
        str += "\\";
        str += $($($(this).children()[2]).children()[0]).val();
    });
    console.log("endflashcardsstr");
    return str;
}

function write_flashcards(flashcards) {
    $(".flashcard").remove();
    if (flashcards == "") {
        return;
    }
    let fbc = flashcards.split(",");
    for (let i = 0; i < fbc.length; i++) {
        let c = fbc[i].split("\\");
        insert_flashcard(c[0], c[1]);
    }
}

var information = {"id": ""};

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
    });

    $(".delete-flashcard").on("click", del_flashcard_callback);

    $("#save-button").on("click", function() {
        $.post("/flashcard/ajax/Flashcardset/updateCards", {"id": information["id"], "flashcards": get_flashcards_str()},
               function(data) { console.log(data); });
        console.log("Done with POST Save");
    });
});
