document.addEventListener('DOMContentLoaded', () => {
    const tipoViajeSelect = document.getElementById('tipoViaje');
    const divRegreso = document.getElementById('divFechaVuelta');
    const fechaVueltaInput = document.getElementById('fechaVuelta');

    const origenCol = document.querySelector('#origen').closest('.col-md-3');
    const destinoCol = document.querySelector('#destino').closest('.col-md-3');
    const fechaIdaCol = document.querySelector('#fechaIda').closest('.col-md-3');

    const fadeDuration = 300;

    const toggleRegreso = () => {
        const selected = tipoViajeSelect.value;

        if (selected === 'IDA') {
            divRegreso.classList.remove('fade-in');
            divRegreso.classList.add('fade-out');

            setTimeout(() => {
                divRegreso.style.display = 'none';
                divRegreso.classList.remove('fade-out');
                fechaVueltaInput.value = '';

                [origenCol, destinoCol, fechaIdaCol].forEach(col => {
                    col.classList.remove('col-md-3');
                    col.classList.add('col-md-4');
                });
            }, fadeDuration);
        } else {
            divRegreso.style.opacity = '0';
            divRegreso.style.display = 'block';

            void divRegreso.offsetWidth;

            divRegreso.classList.add('fade-in');

            setTimeout(() => {
                divRegreso.classList.remove('fade-in');
                divRegreso.style.opacity = '';
            }, fadeDuration);

            [origenCol, destinoCol, fechaIdaCol].forEach(col => {
                col.classList.remove('col-md-4');
                col.classList.add('col-md-3');
            });
        }
    };

    tipoViajeSelect.addEventListener('change', toggleRegreso);
    toggleRegreso();
});
