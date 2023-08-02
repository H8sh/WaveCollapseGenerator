package net.h8sh.Input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Name implements Serializable {
    @JsonProperty("Name_type")
    public String name_type;

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }
}