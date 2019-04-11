


$(document).ready(function(){

    $(".delete-flashset").on("click", function(event) {
        let del_bool = confirm("Do you really want to delete that flashset?");
        if (del_bool) {
            let del_par_el = $(this).parent();
            $.post("/flashcard/ajax/Flashcardset/delete", {"id": del_par_el.find(".flashset-id").text()},
                    function(data) {
                        del_par_el.remove();
                        if (data == "Success") {
                            del_par_el.remove();
                        }
                     }, "text");

        }
    });

});
