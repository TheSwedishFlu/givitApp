function buttonClicked() {
    const points = document.querySelector(".qty").value;

    if (points > 10) {
        window.alert("You have earned a Givit Badge on this trade!");
    }
    else {
        window.alert("You still have to few points to earn a Givit Badge");
        }

        return
    }
}