package edu.shaykemelov.dynamiclogic.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Source(
        @JsonProperty("code") String code,
        @JsonProperty("source") String source) {
}