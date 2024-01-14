package io.github.quinnandrews.spring.data.geo.us.examples;

import java.time.LocalDateTime;

public class City {

    private String name;
    private String stateCode;
    private String stateName;
    private LocalDateTime localDateTime;
    private Integer utcOffset;

    public String getName() {
        return name;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Integer getUtcOffset() {
        return utcOffset;
    }


    public City withName(String name) {
        this.name = name;
        return this;
    }

    public City withStateCode(String stateCode) {
        this.stateCode = stateCode;
        return this;
    }

    public City withStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public City withLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    public City withUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
        return this;
    }
}
