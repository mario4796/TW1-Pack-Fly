document.addEventListener("DOMContentLoaded", function () {
    const forms = document.querySelectorAll("form");

    forms.forEach(form => {
        form.addEventListener("submit", function (event) {
            if (!form.checkValidity()) {
                return;
            }

            const submitButton = form.querySelector("button.btn-con-spinner");

            if (submitButton) {
                const spinner = submitButton.querySelector(".spinner-btn");

                if (spinner) {
                    spinner.classList.remove("d-none");
                }

                submitButton.disabled = true;
            }
        });
    });
});
