const cardsPorCategoria = {
    destinos: [
        {
            titulo: "Escapada a la Costa",
            texto: "Incluye vuelo, 3 noches de hotel y traslados. Desde $250.000",
            imagen: "img/escapada_a_la_costa.webp"
        },
        {
            titulo: "Aventura en Bariloche",
            texto: "Vuelo + hotel + excursiones. Todo por $295.000",
            imagen: "img/bariloche.webp"
        },
        {
            titulo: "Fin de semana en Buenos Aires",
            texto: "Vuelos y estadía céntrica. Ideal para escapada urbana. Desde $180.000",
            imagen: "img/Buenos_Aires.webp"
        },
        {
            titulo: "Relax en Cataratas",
            texto: "Descubrí Iguazú con este paquete completo por $310.000",
            imagen: "img/cataratas.webp"
        }
    ],
    hoteles: [
        {
            titulo: "Hotel en Mendoza",
            texto: "Lujo y confort en el corazón del vino. Desde $120.000",
            imagen: "img/hotel_mendoza.webp"
        },
        {
            titulo: "Hotel en Mar del Plata",
            texto: "Con vista al mar y desayuno incluido. Desde $180.000",
            imagen: "img/hotel_mardel.webp"
        },
        {
            titulo: "Hotel en Córdoba",
            texto: "Ideal para negocios o turismo. Desde $90.000",
            imagen: "img/hotel_cordoba.webp"
        },
        {
            titulo: "Hotel en el Delta",
            texto: "Naturaleza y confort a solo una hora de Buenos Aires. Desde $160.000",
            imagen: "img/hotel_delta.webp"
        }
    ],
    autos: [
        {
            titulo: "Alquiler SUV",
            texto: "Perfecto para escapadas a la montaña. Desde $50.000 por día",
            imagen: "img/suv.webp"
        },
        {
            titulo: "Compacto económico",
            texto: "Ideal para ciudad. Desde $25.000 por día",
            imagen: "img/compacto_economico.webp"
        },
        {
            titulo: "Auto familiar",
            texto: "Comodidad y espacio. Desde $35.000 por día",
            imagen: "img/familiar.webp"
        },
        {
            titulo: "Deportivo de lujo",
            texto: "Para los que buscan adrenalina. Desde $95.000 por día",
            imagen: "img/deportivo.webp"
        }
    ],
    actividades: [
        {
            titulo: "Tour en bici por Palermo",
            texto: "Guía incluido. Desde $12.000",
            imagen: "img/tour_palermo.webp"
        },
        {
            titulo: "Clase de cocina regional",
            texto: "Descubrí sabores argentinos. Desde $18.000",
            imagen: "img/cocina_regional.webp"
        },
        {
            titulo: "Paseo en barco por el Delta",
            texto: "Duración 2 horas. Desde $20.000",
            imagen: "img/paseo_barco.webp"
        },
        {
            titulo: "Visita a bodega en Mendoza",
            texto: "Degustación incluida. Desde $30.000",
            imagen: "img/bodega_mendoza.webp"
        }
    ]
};

function renderCards(categoria) {
    const contenedor = document.getElementById("card-container");
    contenedor.innerHTML = "";

    cardsPorCategoria[categoria].forEach(card => {
        contenedor.innerHTML += `
      <div class="col">
        <div class="card h-100 shadow-sm">
          <img src="${card.imagen}" class="card-img-top" alt="${card.titulo}">
          <div class="card-body">
            <h5 class="card-title">${card.titulo}</h5>
            <p class="card-text">${card.texto}</p>
          </div>
        </div>
      </div>
    `;
    });
}

document.addEventListener("DOMContentLoaded", () => {
    renderCards("destinos");

    document.querySelectorAll("#category-buttons .btn").forEach(btn => {
        btn.addEventListener("click", () => {
            document.querySelectorAll("#category-buttons .btn").forEach(b => b.classList.remove("active"));
            btn.classList.add("active");

            const categoria = btn.dataset.category;
            renderCards(categoria);
        });
    });
});
