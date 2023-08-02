package net.h8sh.Input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Mesh implements Serializable {
    @JsonProperty("Mesh_type")
    public String mesh_type;

    public String getMesh_type() {
        return mesh_type;
    }

    public void setMesh_type(String mesh_type) {
        this.mesh_type = mesh_type;
    }
}