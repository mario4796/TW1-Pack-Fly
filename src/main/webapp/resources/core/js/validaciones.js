document.addEventListener("DOMContentLoaded", function () {
    const hoy = new Date();
    const yyyy = hoy.getFullYear();
    const mm = String(hoy.getMonth() + 1).padStart(2, "0");
    const dd = String(hoy.getDate()).padStart(2, "0");
    const fechaMinima = `${yyyy}-${mm}-${dd}`;

    const checkInInput = document.querySelector("#checkIn");
    const checkOutInput = document.querySelector("#checkOut");
    const childrenInput = document.getElementById("children");
    const childrenAgesInput = document.getElementById("children_ages");

    if (checkInInput) checkInInput.setAttribute("min", fechaMinima);
    if (checkOutInput) checkOutInput.setAttribute("min", fechaMinima);

    // Eventos de fechas
    if (checkInInput) {
        checkInInput.addEventListener("change", validarFechas);
    }
    if (checkOutInput) {
        checkOutInput.addEventListener("change", validarFechas);
    }

    // Eventos de niños
    if (childrenInput) {
        childrenInput.addEventListener("input", function () {
            toggleChildrenAges();
            validarChildrenAges();
        });
    }

    if (childrenAgesInput) {
        childrenAgesInput.addEventListener("input", validarChildrenAges);
    }

    // Inicializar estado del campo children_ages
    toggleChildrenAges();

    const forms = document.querySelectorAll("form");

    forms.forEach((form) => {
        form.addEventListener("submit", function (event) {
            event.preventDefault();

            const fechasValidas = validarFechas();
            const edadesValidas = validarChildrenAges();

            if (!fechasValidas || !edadesValidas || !form.checkValidity()) {
                event.stopPropagation();
                form.classList.add("was-validated");
                return;
            }

            const submitButton = form.querySelector("button.btn-con-spinner");
            if (submitButton) {
                const spinner = submitButton.querySelector(".spinner-btn");
                if (spinner) spinner.classList.remove("d-none");
                submitButton.disabled = true;
            }

            form.classList.add("was-validated");
            form.submit();
        });
    });

    function validarFechas() {
        if (!checkInInput || !checkOutInput) return true;

        const checkInValue = checkInInput.value;
        const checkOutValue = checkOutInput.value;

        if (!checkInValue || !checkOutValue) {
            checkOutInput.setCustomValidity("");
            checkOutInput.classList.remove("is-invalid");
            return true;
        }

        if (checkOutValue <= checkInValue) {
            checkOutInput.setCustomValidity(
                "La fecha de check-out debe ser posterior a la de check-in."
            );
            checkOutInput.classList.add("is-invalid");
            return false;
        } else {
            checkOutInput.setCustomValidity("");
            checkOutInput.classList.remove("is-invalid");
            return true;
        }
    }

    function toggleChildrenAges() {
        const childrenCount = parseInt(childrenInput.value, 10);

        if (isNaN(childrenCount) || childrenCount === 0) {
            childrenAgesInput.readOnly = true;
            childrenAgesInput.placeholder = "Sin niños";
            childrenAgesInput.value = "";
            childrenAgesInput.setCustomValidity("");
            childrenAgesInput.classList.remove("is-invalid", "is-valid");
        } else {
            childrenAgesInput.readOnly = false;
            childrenAgesInput.placeholder = "5,8,10...";
        }
    }

    function validarChildrenAges() {
        const childrenCount = parseInt(childrenInput.value, 10);
        const value = childrenAgesInput.value.trim();

        childrenAgesInput.setCustomValidity("");
        childrenAgesInput.classList.remove("is-invalid", "is-valid");

        if (isNaN(childrenCount) || childrenCount === 0) {
            return true;
        }

        if (value === "") {
            childrenAgesInput.setCustomValidity("Debe ingresar las edades de los niños.");
            childrenAgesInput.classList.add("is-invalid");
            return false;
        }

        const edades = value.split(",").map(e => e.trim());
        if (edades.length !== childrenCount) {
            childrenAgesInput.setCustomValidity(`Debe ingresar exactamente ${childrenCount} edad${childrenCount > 1 ? "es" : ""}.`);
            childrenAgesInput.classList.add("is-invalid");
            return false;
        }

        for (let edad of edades) {
            const num = parseInt(edad, 10);
            if (isNaN(num) || num < 0 || num > 17) {
                childrenAgesInput.setCustomValidity("Las edades deben ser enteros entre 0 y 17.");
                childrenAgesInput.classList.add("is-invalid");
                return false;
            }
        }

        childrenAgesInput.classList.add("is-valid");
        return true;
    }
});
