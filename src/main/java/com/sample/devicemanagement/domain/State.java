package com.sample.devicemanagement.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum State {

    AVAILABLE("available"),
    IN_USE("in-use"),
    INACTIVE("inactive");

    private final String deviceState;

    State(String state) {
        this.deviceState = state;
    }

    @JsonValue
    public String getText() {
        return deviceState;
    }

    @JsonCreator
    public static State fromText(String text) {
        for (State s : State.values()) {
            if (s.deviceState.equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }

}

