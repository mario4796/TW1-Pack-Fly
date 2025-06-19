package com.tallerwebi.presentacion.dtos;

import com.tallerwebi.dominio.entidades.Hotel;

import java.util.List;
public class HotelDto{

   // private Long id;
    private String name;
    private String ciudad;
    private String checkIn;
    private String checkout;
    private Integer adults;
    private Integer children;
    private String childen_ages;
    private String link;
    private Double overall_rating;
    private Integer reviews;
    private List<Image> images;
    private RatePerNight rate_per_night;
    private List<String> amenities;
    private Price price;
    private Double precio;

    public static class Price {
        private Double extracted;

        public Double getExtracted() {
            return extracted;
        }

        public void setExtracted(Double extracted) {
            this.extracted = extracted;
        }
    }


    public String getLink() {
        return link;
    }

    public Double getOverall_rating() {
        return overall_rating;
    }

    public Integer getReviews() {
        return reviews;
    }

    public List<Image> getImages() {
        return images;
    }

    public RatePerNight getRate_per_night() {
        return rate_per_night;
    }

    public List<String> getAmenities() {
        return amenities;
    }


    public static class Image {
        private String thumbnail;

        public String getThumbnail() {
            return thumbnail;
        }
    }

    public static class RatePerNight {
        private String lowest;
        private Double extracted_lowest;

        public String getLowest() {
            return lowest;
        }

        public Double getExtracted_lowest() {
            return extracted_lowest;
        }

        public void setLowest(String lowest) {
            this.lowest = lowest;
        }

        public void setExtracted_lowest(Double extracted_lowest) {
            this.extracted_lowest = extracted_lowest;
        }
    }

    public HotelDto(String name, String ciudad, String checkIn, String checkOut, Integer adult, Integer children) {
        this.name = name;
        this.ciudad = ciudad;
        this.checkIn = checkIn;
        this.checkout = checkOut;
        this.adults = adult;
        this.children = children;
    }

    private HotelDto(String ciudad, String checkIn, String checkOut, Integer adults, Integer children, String children_ages){
        this.ciudad =ciudad;
        this.checkIn= checkIn;
        this.checkout = checkOut;
        this.adults= adults;
        this.children= children;
        this.childen_ages=children_ages;
    }

    public HotelDto(String name, String ciudad, String checkIn, String checkOut, Integer adults, Integer children, Double precio) {
        this.name = name;
        this.ciudad = ciudad;
        this.checkIn = checkIn;
        this.checkout = checkOut;
        this.adults = adults;
        this.children = children;
        this.precio = precio;
    }


    public void setPrecio(Double preciobdd) { this.precio = preciobdd; }

    public HotelDto() {
        // Constructor por defecto requerido por Jackson
    }

    public HotelDto(com.tallerwebi.dominio.entidades.Hotel hotel){
        //this.id = hotel.getId();
        this.name=hotel.getName();
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getName() {
        return this.name;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public String getChilden_ages() {
        return childen_ages;
    }

    public void setChilden_ages(String childen_ages) {
        this.childen_ages = childen_ages;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setOverall_rating(Double overall_rating) {
        this.overall_rating = overall_rating;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setRate_per_night(RatePerNight rate_per_night) {
        this.rate_per_night = rate_per_night;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public Hotel obtenerEntidad(){
        Hotel hotel = new Hotel();
        return this.obtenerEntidad(hotel);
    }

    public Hotel obtenerEntidad(Hotel hotel){
        //hotel.setId(this.id);
        hotel.setName(this.name);
        return hotel;
    }

    public Double getPrecio() {
        if (this.precio != null) {
            return this.precio;
        } else if (price != null && price.getExtracted() != null) {
            return price.getExtracted();
        } else if (rate_per_night != null && rate_per_night.getExtracted_lowest() != null) {
            return rate_per_night.getExtracted_lowest();
        } else {
            return 0.0;
        }
    }

}