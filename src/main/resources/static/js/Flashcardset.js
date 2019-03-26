
var flnum = 0;
var flset = [];

function flip_flashcard(event) {
    if ($("#flashcard-back").css("display") == "none") {
        $("#flashcard-front").css("display", "none");
        $("#flashcard-back").css("display", "inline");
    }
    else {
        $("#flashcard-front").css("display", "inline");
        $("#flashcard-back").css("display", "none");
    }

}

function next_flashcard(event) {
    flnum++;
    $("#flashcard-front").text(flset[flnum % flset.length][0]);
    $("#flashcard-back").text(flset[flnum % flset.length][1]);
    $("#flashcard-front").css("display", "inline");
    $("#flashcard-back").css("display", "none");
}

var information = {"id": ""};

$(document).ready(function(){
    information["id"] = $("#flashcardsetId").text().substring(4);
    console.log("INFORMATION: ");
    console.log(information);

    $.get("/flashcard/ajax/Flashcardset?id=" + information["id"],
        function(data) {
            console.log("SUCCESFUL");
            console.log(data);
            console.log(data["id"]);
            console.log(data["name"]);
            console.log(data["flashcards"]);
            flset = data["flashcards"].split(",");
            for (let i = 0; i < flset.length; i++) {
                flset[i] = flset[i].split("\\");
            }
        }
    );

    $("#flashcard-front").text(flset[flnum % flset.length][0]);
    $("#flashcard-back").text(flset[flnum % flset.length][1]);
    $("#flashcard").on("click", flip_flashcard);
    $("#flashcard-next").on("click", next_flashcard);
});
