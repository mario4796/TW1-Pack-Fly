package com.tallerwebi.dominio;

import java.util.List;

public class Hotel {
    private String name;
    private String link;
    private Double overall_rating;
    private Integer reviews;
    private List<Image> images;
    private RatePerNight rate_per_night;
    private List<String> amenities;

    public String getName() {
        return name;
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

        public String getLowest() {
            return lowest;
        }
    }
}
