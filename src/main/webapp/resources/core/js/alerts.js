document.addEventListener('DOMContentLoaded', function () {
    // Mostrar toast si existe
    const toastEl = document.getElementById('toastMensaje');
    if (toastEl) {
        const toast = new bootstrap.Toast(toastEl);
        toast.show();
    }

    // Fade para alert tradicional
    document.querySelectorAll('.alert.reserva-exitosa').forEach(function (alert) {
        setTimeout(() => {
            alert.classList.remove('show');
            alert.addEventListener('transitionend', function () {
                alert.remove();
            }, { once: true });
        }, 3000);
    });
});
