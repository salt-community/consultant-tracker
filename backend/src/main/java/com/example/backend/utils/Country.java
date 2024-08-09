package com.example.backend.utils;

public enum Country {
    SWEDEN("Sverige"),
    NORWAY("Norge");
    public final String country;
    private Country(String country) {
        this.country = country;
    }
}
