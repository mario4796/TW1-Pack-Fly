document.addEventListener('DOMContentLoaded', () => {
    const tipoViajeSelect = document.getElementById('tipoViaje');
    const divRegreso = document.getElementById('divFechaVuelta');
    const fechaVueltaInput = document.getElementById('fechaVuelta');

    const origenCol = document.querySelector('#origen').closest('.col-md-3');
    const destinoCol = document.querySelector('#destino').closest('.col-md-3');
    const fechaIdaCol = document.querySelector('#fechaIda').closest('.col-md-3');

    const fadeDuration = 200;

    const toggleRegreso = () => {
        const selected = tipoViajeSelect.value;

        if (selected === 'IDA') {
            divRegreso.classList.add('fade-out');

            setTimeout(() => {
                divRegreso.style.display = 'none';
                fechaVueltaInput.value = '';

                [origenCol, destinoCol, fechaIdaCol].forEach(col => {
                    col.classList.remove('col-md-3');
                    col.classList.add('col-md-4');
                });

                divRegreso.classList.remove('fade-out');
            }, fadeDuration);
        } else {
            [origenCol, destinoCol, fechaIdaCol].forEach(col => {
                col.classList.remove('col-md-4');
                col.classList.add('col-md-3');
            });

            //Revisar fade-in que no funciona bien
            divRegreso.style.display = '';
            void divRegreso.offsetWidth;
            divRegreso.classList.add('fade-in');

            setTimeout(() => {
                divRegreso.classList.remove('fade-in');
            }, fadeDuration);
        }
    };

    tipoViajeSelect.addEventListener('change', toggleRegreso);

    toggleRegreso();
});
