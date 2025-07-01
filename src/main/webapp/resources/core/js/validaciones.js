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
        document.querySelector('form[action*="/buscar-excursiones"]')
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

    forms.forEach((form) => {
        form.addEventListener("submit", function (event) {
            event.preventDefault();

            let fechasValidas = true;
            // Validar fechas hoteles
            if (checkInInput && checkOutInput) fechasValidas = validarFechas(checkInInput, checkOutInput);
            // Validar fechas vuelo
            if (fechaIdaInput && fechaVueltaInput) fechasValidas = validarFechas(fechaIdaInput, fechaVueltaInput);

            if (!fechasValidas || !form.checkValidity()) {
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

    // Validación de cambio de contraseña (perfil-usuario)
    const formCambiarPassword = document.querySelector("#formCambiarPassword");
    if (formCambiarPassword) {
        const nueva = document.querySelector("#nueva");
        const repetir = document.querySelector("#repetir");

        formCambiarPassword.addEventListener("submit", function (e) {
            if (nueva && repetir && nueva.value !== repetir.value) {
                e.preventDefault();
                repetir.classList.add("is-invalid");

                if (!document.querySelector("#errorRepetir")) {
                    const errorMsg = document.createElement("div");
                    errorMsg.className = "invalid-feedback";
                    errorMsg.id = "errorRepetir";
                    errorMsg.innerText = "Las contraseñas no coinciden.";
                    repetir.parentNode.appendChild(errorMsg);
                }
            } else {
                repetir.classList.remove("is-invalid");
                const error = document.querySelector("#errorRepetir");
                if (error) error.remove();
            }
        });
    }

    // Validación de cambio de email (perfil-usuario)
    const formEmail = document.querySelector("#formCambiarEmail");
    if (formEmail) {
        const emailInput = document.querySelector("#email");

        formEmail.addEventListener("submit", function (e) {
            if (!emailInput.checkValidity()) {
                e.preventDefault();
                emailInput.classList.add("is-invalid");
            } else {
                emailInput.classList.remove("is-invalid");
            }
        });
    }

    // Validación de cambio de nombre y apellido (perfil-usuario)
    const formNombreApellido = document.querySelector("#formCambiarNombreApellido");
    if (formNombreApellido) {
        const nombreInput = document.querySelector("#nombre");
        const apellidoInput = document.querySelector("#apellido");

        formNombreApellido.addEventListener("submit", function (e) {
            let valido = true;

            if (!nombreInput.checkValidity()) {
                nombreInput.classList.add("is-invalid");
                valido = false;
            } else {
                nombreInput.classList.remove("is-invalid");
            }

            if (!apellidoInput.checkValidity()) {
                apellidoInput.classList.add("is-invalid");
                valido = false;
            } else {
                apellidoInput.classList.remove("is-invalid");
            }

            if (!valido) e.preventDefault();
        });
    }

});