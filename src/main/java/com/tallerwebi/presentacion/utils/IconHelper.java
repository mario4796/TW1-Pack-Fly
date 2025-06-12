package com.tallerwebi.presentacion.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class IconHelper {

    private static final Map<String, String> amenityIcons = new HashMap<>();

    static {
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
    }

    public String getAmenityIcon(String amenity) {
        return amenityIcons.getOrDefault(amenity, "help");
    }
}