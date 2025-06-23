document.addEventListener("DOMContentLoaded", function () {
    const hoy = new Date();
    const yyyy = hoy.getFullYear();
    const mm = String(hoy.getMonth() + 1).padStart(2, "0");
    const dd = String(hoy.getDate()).padStart(2, "0");
    const fechaMinima = `${yyyy}-${mm}-${dd}`;

    // Selector para los tres forms
    const forms = [
        document.querySelector('form[action*="/buscar-hoteles"]'),
        document.querySelector('form[action*="/busqueda-vuelo"]'),
        document.querySelector('form[action*="/excursiones"]')
    ].filter(Boolean); // solo los que existen en la vista

    // Setea min de fecha en hoteles y vuelos (si existen)
    const checkInInput = document.querySelector("#checkIn");
    const checkOutInput = document.querySelector("#checkOut");
    if (checkInInput) checkInInput.setAttribute("min", fechaMinima);
    if (checkOutInput) checkOutInput.setAttribute("min", fechaMinima);

    const fechaIdaInput = document.querySelector("#fechaIda");
    const fechaVueltaInput = document.querySelector("#fechaVuelta");
    if (fechaIdaInput) fechaIdaInput.setAttribute("min", fechaMinima);
    if (fechaVueltaInput) fechaVueltaInput.setAttribute("min", fechaMinima);

    // Hoteles: lógica niños/edades
    const childrenInput = document.getElementById("children");
    const childrenAgesInput = document.getElementById("children_ages");
    if (childrenInput) {
        childrenInput.addEventListener("input", function () {
            toggleChildrenAges();
            validarChildrenAges();
        });
    }
    if (childrenAgesInput) {
        childrenAgesInput.addEventListener("input", validarChildrenAges);
    }
    if (childrenAgesInput) toggleChildrenAges();

    forms.forEach((form) => {
        form.addEventListener("submit", function (event) {
            event.preventDefault();

            let fechasValidas = true;
            // Validar fechas hoteles
            if (checkInInput && checkOutInput) fechasValidas = validarFechas(checkInInput, checkOutInput);
            // Validar fechas vuelo
            if (fechaIdaInput && fechaVueltaInput) fechasValidas = validarFechas(fechaIdaInput, fechaVueltaInput);

            let edadesValidas = true;
            if (childrenInput && childrenAgesInput) edadesValidas = validarChildrenAges();

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

    // Función para fechas (generalizada)
    function validarFechas(input1, input2) {
        if (!input1 || !input2) return true;
        const value1 = input1.value;
        const value2 = input2.value;
        if (!value1 || !value2) {
            input2.setCustomValidity("");
            input2.classList.remove("is-invalid");
            return true;
        }
        if (value2 <= value1) {
            input2.setCustomValidity("La fecha de salida debe ser posterior a la de entrada.");
            input2.classList.add("is-invalid");
            return false;
        } else {
            input2.setCustomValidity("");
            input2.classList.remove("is-invalid");
            return true;
        }
    }

    // Funciones hoteles - edades niños (solo aplica si existen los campos)
    function toggleChildrenAges() {
        if (!childrenInput || !childrenAgesInput) return;
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
        if (!childrenInput || !childrenAgesInput) return true;
        const childrenCount = parseInt(childrenInput.value, 10);
        const value = childrenAgesInput.value.trim();
        childrenAgesInput.setCustomValidity("");
        childrenAgesInput.classList.remove("is-invalid", "is-valid");
        if (isNaN(childrenCount) || childrenCount === 0) return true;
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