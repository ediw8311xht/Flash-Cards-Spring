
var flset = [];


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

var information = {"id": ""};

$(document).ready(function() {

    information["id"] = $("#flashcardsetId").text().substring(4);

    $.get("/flashcard/ajax/Flashcardset?id=" + information["id"],
        function(data) {
            console.log("SUCCESFUL");
            console.log("GET");
            console.log(data["id"]);
            console.log(data["name"]);
            console.log(data["flashcards"]);
            console.log(data["flashcards"][0]);
            if (data["flashcards"].length >= 2) {
                flset = data["flashcards"][0].split(",");
                for (let i = 0; i < flset.length; i++) {
                    flset[i] = flset[i].split("\\");
                    insert_flashcard(flset[i][0], flset[i][1]);
                }
            }
        }
    );

    $("#add-flashcard").on("click", function(){
        flset.push(["asdfsa", "bigboy"]);
        let abcd = {"id": information["id"],
                    "front": "asdfsa",
                    "back" : "bigboy"};
        $(new_flashcard).insertBefore("#add-flashcard");
        $(".flashcard").last().children().first().focus();
        $.post("/flashcard/ajax/Flashcardset/addCard",
               {"id": information["id"], "front": "asdfsa", "back" : "bigboy"},
               function(data) {
                   console.log(data);
               }
        );
    });
});
