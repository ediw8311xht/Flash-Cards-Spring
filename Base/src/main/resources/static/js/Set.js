
var flnum = 0;
var flset = [];

function replace_string(origin_str, r_substr, n_substr) {
    let n_str = origin_str.split(r_substr);
    return n_str.join(n_substr);
}

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

function np_flashcard(event) {
    if (event.target.id == "flashcard-previous") {
        if (flnum <= 0) {  flnum = flset.length - 1;  }
        else {  flnum--;  }
    }
    else {
        flnum++;
    }
    $("#flashcard-front").text(flset[flnum % flset.length][0]);
    $("#flashcard-back").text(flset[flnum % flset.length][1]);
    $("#flashcard-front").css("display", "inline");
    $("#flashcard-back").css("display", "none");
}


function shuffle_array(arr) {

    for (let i = 0; i < arr.length; i++) {
        let rindex1 = Math.floor(Math.random() * arr.length);
        let rindex2 = Math.floor(Math.random() * arr.length);
        [arr[rindex1], arr[rindex2]] = [arr[rindex2], arr[rindex1]];
    }

    return arr;
}

var information = {"id": ""};

$(document).ready(function(){
    information["id"] = $("#flashcardsetId").text();
    console.log("INFORMATION: ");
    console.log(information);

    $.get("/flashcard/ajax/Flashcardset?id=" + information["id"],
        function(data) {
            console.log(data);
            console.log(data["flashcardsStr"]);
            flset = data["flashcardsStr"].split(",");
            for (let i = 0; i < flset.length; i++) {
                flset[i] = replace_string(flset[i], ".~.", ",").split("\\");
            }
            flset = shuffle_array(flset);
            console.log(flset);
            $("#flashcard-front").text(flset[flnum % flset.length][0]);
            $("#flashcard-back").text(flset[flnum % flset.length][1]);
            $("#flashcard").on("click", flip_flashcard);
            $("#flashcard-next").on("click", np_flashcard);
            $("#flashcard-previous").on("click", np_flashcard);

        }
    );

    $("#copy-flashcard").one("click", function(event){
        $.get("/flashcard/ajax/Flashcardset/copy?id=" + information["id"],
            function(data) {
                if (data) {
                    window.alert("Succesfully saved flashcardset.");
                }
            }
        )
    });

});
