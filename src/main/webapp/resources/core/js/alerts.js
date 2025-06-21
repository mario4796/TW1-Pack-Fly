document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.alert.reserva-exitosa').forEach(function(alert) {
        setTimeout(() => {
            alert.classList.remove('show');
            alert.addEventListener('transitionend', function() {
                alert.remove();
            }, { once: true });
        }, 3000);
    });
});