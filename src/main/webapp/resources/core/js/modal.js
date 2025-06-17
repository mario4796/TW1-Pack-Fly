document.addEventListener('DOMContentLoaded', function () {

    document.querySelectorAll('.btn-editar-hotel').forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.getElementById('editHotelName').value = this.getAttribute('data-name');
            document.getElementById('editHotelNameHidden').value = this.getAttribute('data-name');
            document.getElementById('editHotelCiudad').value = this.getAttribute('data-ciudad');
            document.getElementById('editHotelCheckIn').value = this.getAttribute('data-checkin');
            document.getElementById('editHotelCheckOut').value = this.getAttribute('data-checkout');
            document.getElementById('editHotelAdults').value = this.getAttribute('data-adults');
            document.getElementById('editHotelChildren').value = this.getAttribute('data-children');
        });
    });

    document.querySelectorAll('.btn-editar-vuelo').forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.getElementById('editVueloEmail').value = this.getAttribute('data-email');
            document.getElementById('editVueloOrigen').value = this.getAttribute('data-origen');
            document.getElementById('editVueloDestino').value = this.getAttribute('data-destino');
            document.getElementById('editVueloFechaIda').value = this.getAttribute('data-fechaida');
            document.getElementById('editVueloFechaVuelta').value = this.getAttribute('data-fechavuelta');
            document.getElementById('editVueloFechaIdaOriginal').value = this.getAttribute('data-fechaida');
            document.getElementById('editVueloFechaVueltaOriginal').value = this.getAttribute('data-fechavuelta');
        });
    });

    document.querySelectorAll('.btn-editar-excursion').forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.getElementById('editExcursionTitle').value = this.getAttribute('data-title');
            document.getElementById('editExcursionUrl').value = this.getAttribute('data-url');
            document.getElementById('editExcursionTitleOriginal').value = this.getAttribute('data-title');
        });
    });

    document.querySelectorAll('.form-eliminar-reserva').forEach(function(form) {
        form.addEventListener('submit', function(event) {
            if (!window.confirm("¿Estás seguro que deseas eliminar esta reserva?")) {
                event.preventDefault();
            }
        });
    });
});