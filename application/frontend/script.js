document.addEventListener("DOMContentLoaded", function() {
    const moreInfoBtn = document.querySelector(".btn-primary");

    moreInfoBtn.addEventListener("click", function(event) {
        event.preventDefault();
        const missionSection = document.querySelector("#more-info");
        missionSection.scrollIntoView({ behavior: "smooth" });
    });
});
