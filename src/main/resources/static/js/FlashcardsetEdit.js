


var new_flashcard = "<div class='flashcard'>\
                        <textarea class='flashcard-front'></textarea>\
                        <textarea class='flashcard-back'></textarea>\
                     </div>";

function insert_flashcard(front, back) {
    let tcg = "<div class='flashcard'> <textarea class='flashcard-front'>"
              + front + "</textarea><textarea class='flashcard-back'>"
              + back + "</textarea></div>";
    $(tcg).insertBefore("#add-flashcard");
}

function write_flashcards(flashcards) {
    $(".flashcard").remove();
    let fbc = flashcards.split(",");
    console.log("START FBC");
    console.log(fbc);
    console.log("END FBC");
    for (let i = 0; i < fbc.length; i++) {
        let c = fbc.split("\\");
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

    $("#add-flashcard").on("click", function(){
        let abcd = {"id": information["id"],
                    "front": "asdfsa",
                    "back" : "bigboy"};
        $(new_flashcard).insertBefore("#add-flashcard");
        $(".flashcard").last().children().first().focus();
        $.post("/flashcard/ajax/Flashcardset/addCard",
               {"id": information["id"], "front": "asdfsa", "back" : "bigboy"},
               function(data) {
                   console.log("POST START");
                   console.log(data);
                   console.log("POST END");
                   write_flashcards(data["flashcardsStr"]);
               }
        );
    });
});
