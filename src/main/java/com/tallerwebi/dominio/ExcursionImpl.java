// src/main/java/com/tallerwebi/dominio/ExcursionImpl.java
package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcursionImpl implements Excursion {
    @JsonProperty("title")
    private String title;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("location")
    private String location;

    @JsonProperty("description")
    private String description;

    @JsonProperty("link")
    private String url;

    @Override public String getTitle()       { return title; }
    @Override public String getStartDate()   { return startDate; }
    @Override public String getLocation()    { return location; }
    @Override public String getDescription() { return description; }
    @Override public String getUrl()         { return url; }

    public void setTitle(String t)           { this.title = t; }
    public void setStartDate(String d)       { this.startDate = d; }
    public void setLocation(String l)        { this.location = l; }
    public void setDescription(String d)     { this.description = d; }
    public void setUrl(String u)             { this.url = u; }
}
