


var new_flashcard = "<div class='flashcard'><textarea class='flashcard-front' spellcheck='false'></textarea><textarea class='flashcard-back' spellcheck='false'></textarea></div>";

function insert_flashcard(front, back) {
    let tcg = "<div class='flashcard'><textarea class='flashcard-front' spellcheck='false'>"
              + front + "</textarea><textarea class='flashcard-back' spellcheck='false'>"
              + back + "</textarea></div>";
    $(tcg).insertBefore("#add-flashcard");
}

function write_flashcards(flashcards) {
    $(".flashcard").remove();
    if (flashcards == "") {
        return;
    }
    let fbc = flashcards.split(",");
    console.log("START FBC");
    console.log(fbc);
    console.log("END FBC");
    for (let i = 0; i < fbc.length; i++) {
        let c = fbc[i].split("\\");
        insert_flashcard(c[0], c[1]);
    }
}

function get_flashcards_str() {
    let str = "";
    console.log("getflashcardsstr");
    $(".flashcard").each(function(index) {
        if (str != "") { str += ","; }
        console.log($($(this).children()[0]).val());
        console.log($($(this).children()[1]).val());
        str += $($(this).children()[0]).val();
        str += "\\";
        str += $($(this).children()[1]).val();
    });
    console.log("endflashcardsstr");
    return str;
}

function save_flashcard(flashcards) {

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
    });

    $("#save-button").on("click", function() {
        $.post("/flashcard/ajax/Flashcardset/updateCards", {"id": information["id"], "flashcards": get_flashcards_str()},
               function(data) { console.log(data); });
        console.log("Done with POST Save");
    });
});
