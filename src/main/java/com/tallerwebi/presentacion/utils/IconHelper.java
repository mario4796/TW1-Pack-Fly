package com.tallerwebi.presentacion.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("iconHelper")
public class IconHelper {

    private static final Map<String, String> amenityIcons = new HashMap<>();
    private static final Map<String, String> amenityTranslations = new HashMap<>();

    static {
        // Inglés
        amenityIcons.put("Free parking", "local_parking");
        amenityIcons.put("Heating", "fireplace");
        amenityIcons.put("Oven stove", "oven");
        amenityIcons.put("Cable TV", "tv");
        amenityIcons.put("Airport shuttle", "airport_shuttle");
        amenityIcons.put("Crib", "crib");
        amenityIcons.put("Elevator", "elevator");
        amenityIcons.put("Ironing board", "iron");
        amenityIcons.put("Smoke-free", "smoke_free");
        amenityIcons.put("Microwave", "microwave");
        amenityIcons.put("Washer", "local_laundry_service");
        amenityIcons.put("Parking", "local_parking");
        amenityIcons.put("Indoor pool", "pool");
        amenityIcons.put("Outdoor pool", "pool");
        amenityIcons.put("Pool", "pool");
        amenityIcons.put("Fitness center", "fitness_center");
        amenityIcons.put("Restaurant", "restaurant");
        amenityIcons.put("Free breakfast", "free_breakfast");
        amenityIcons.put("Spa", "spa");
        amenityIcons.put("Beach access", "beach_access");
        amenityIcons.put("Kid-friendly", "child_friendly");
        amenityIcons.put("Bar", "local_bar");
        amenityIcons.put("Pet-friendly", "pets");
        amenityIcons.put("Room service", "room_service");
        amenityIcons.put("Free Wi-Fi", "wifi");
        amenityIcons.put("Air conditioning", "ac_unit");
        amenityIcons.put("All‑inclusive available", "check_circle");
        amenityIcons.put("Wheelchair accessible", "accessible");
        amenityIcons.put("EV charger", "ev_station");
        amenityIcons.put("Hot tub", "hot_tub");
        amenityIcons.put("Outdoor grill", "outdoor_grill");
        amenityIcons.put("Fireplace", "fireplace");
        amenityIcons.put("Patio or deck", "deck");
        amenityIcons.put("Kitchen", "kitchen");
        amenityIcons.put("Cot", "crib");
        amenityIcons.put("Breakfast ($)", "local_cafe");
        amenityIcons.put("Fitness centre", "fitness_center");
        amenityIcons.put("Child-friendly", "child_friendly");
        amenityIcons.put("Accessible", "savings");
        amenityIcons.put("Smoke-free property", "smoke_free");
        amenityIcons.put("Parking ($)", "local_parking");
        amenityIcons.put("Business centre", "business");
        amenityIcons.put("Full-service laundry", "local_laundry_service");
        amenityIcons.put("Kitchen in some rooms", "kitchen");

        // Traducciones Español → Inglés
        amenityTranslations.put("Aparcamiento gratuito", "Free parking");
        amenityTranslations.put("Calefacción", "Heating");
        amenityTranslations.put("Horno", "Oven stove");
        amenityTranslations.put("TV por cable", "Cable TV");
        amenityTranslations.put("Traslado al aeropuerto", "Airport shuttle");
        amenityTranslations.put("Cuna", "Crib");
        amenityTranslations.put("Ascensor", "Elevator");
        amenityTranslations.put("Tabla de planchar", "Ironing board");
        amenityTranslations.put("Prohibido fumar", "Smoke-free");
        amenityTranslations.put("Microondas", "Microwave");
        amenityTranslations.put("Lavadora", "Washer");
        amenityTranslations.put("Aparcamiento", "Parking");
        amenityTranslations.put("Piscina cubierta", "Indoor pool");
        amenityTranslations.put("Piscina al aire libre", "Outdoor pool");
        amenityTranslations.put("Piscina", "Pool");
        amenityTranslations.put("Gimnasio", "Fitness center");
        amenityTranslations.put("Restaurante", "Restaurant");
        amenityTranslations.put("Desayuno gratuito", "Free breakfast");
        amenityTranslations.put("Spa", "Spa");
        amenityTranslations.put("Acceso a la playa", "Beach access");
        amenityTranslations.put("Apto para niños", "Kid-friendly");
        amenityTranslations.put("Bar", "Bar");
        amenityTranslations.put("Se admiten mascotas", "Pet-friendly");
        amenityTranslations.put("Servicio de habitaciones", "Room service");
        amenityTranslations.put("Wi‑Fi gratis", "Free Wi-Fi");
        amenityTranslations.put("Aire acondicionado", "Air conditioning");
        amenityTranslations.put("Todo incluido", "All‑inclusive available");
        amenityTranslations.put("Accesible para sillas de ruedas", "Wheelchair accessible");
        amenityTranslations.put("Cargador de vehículos eléctricos", "EV charger");
        amenityTranslations.put("Bañera de hidromasaje", "Hot tub");
        amenityTranslations.put("Barbacoa", "Outdoor grill");
        amenityTranslations.put("Chimenea", "Fireplace");
        amenityTranslations.put("Patio o terraza", "Patio or deck");
        amenityTranslations.put("Cocina", "Kitchen");
        amenityTranslations.put("Cuna adicional", "Cot");
        amenityTranslations.put("Desayuno (de pago)", "Breakfast ($)");
        amenityTranslations.put("Centro de negocios", "Business centre");
        amenityTranslations.put("Lavandería", "Full-service laundry");
        amenityTranslations.put("Cocina en algunas habitaciones", "Kitchen in some rooms");
    }

    public String getAmenityIcon(String amenity) {
        if (amenity == null) return "help";
        String icon = amenityIcons.get(amenity);
        if (icon != null) return icon;
        String englishAmenity = amenityTranslations.get(amenity);
        if (englishAmenity != null) {
            icon = amenityIcons.get(englishAmenity);
            if (icon != null) return icon;
        }
        // Log para saber que amenity falta:
        System.out.println("Amenity no reconocido: " + amenity);
        return "help";
    }


}