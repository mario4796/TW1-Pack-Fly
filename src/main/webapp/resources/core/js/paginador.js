function paginarLista(idLista, idPaginador, itemsPorPagina = 5) {
    const lista = document.getElementById(idLista);
    const paginador = document.getElementById(idPaginador);
    if (!lista || !paginador) return;

    const items = Array.from(lista.children);
    if (items.length <= itemsPorPagina) return;

    let paginaActual = 0;
    const totalPaginas = Math.ceil(items.length / itemsPorPagina);

    function mostrarPagina(pagina) {
        items.forEach((item, index) => {
            item.style.display = (index >= pagina * itemsPorPagina && index < (pagina + 1) * itemsPorPagina)
                ? 'block'
                : 'none';
        });

        paginador.innerHTML = '';
        for (let i = 0; i < totalPaginas; i++) {
            const btn = document.createElement('button');
            btn.className = 'btn btn-sm btn-outline-primary me-1';
            btn.textContent = i + 1;
            if (i === pagina) btn.classList.add('active');
            btn.addEventListener('click', () => {
                paginaActual = i;
                mostrarPagina(paginaActual);
            });
            paginador.appendChild(btn);
        }
    }

    mostrarPagina(paginaActual);
}

document.addEventListener('DOMContentLoaded', () => {
    paginarLista('ul-hoteles', 'paginador-hoteles');
    paginarLista('ul-vuelos', 'paginador-vuelos');
    paginarLista('ul-excursiones', 'paginador-excursiones');
});
