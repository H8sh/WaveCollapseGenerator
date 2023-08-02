package net.h8sh.Input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Rotation implements Serializable {
    @JsonProperty("Rotation_type")
    public int rotation_type;
    @JsonProperty("Rotation_allowed")
    public int rotation_allowed;

    public int getRotation_allowed() {
        return rotation_allowed;
    }

    public void setRotation_allowed(int rotation_allowed) {
        this.rotation_allowed = rotation_allowed;
    }

    public int getRotation_type() {
        return rotation_type;
    }

    public void setRotation_type(int rotation_type) {
        this.rotation_type = rotation_type;
    }
}