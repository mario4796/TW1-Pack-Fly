document.addEventListener("DOMContentLoaded", function () {
    const childrenInput = document.getElementById("children");
    const childrenAgesInput = document.getElementById("children_ages");

    function toggleChildrenAges() {
        const childrenCount = parseInt(childrenInput.value, 10);

        if (childrenCount === 0) {
            childrenAgesInput.disabled = false;
            childrenAgesInput.placeholder = "Sin niños";
            childrenAgesInput.value = "";
        } else {
            childrenAgesInput.disabled = false;
            childrenAgesInput.placeholder = "5,8,10...";
        }
    }

    toggleChildrenAges();

    childrenInput.addEventListener("input", toggleChildrenAges);
});
