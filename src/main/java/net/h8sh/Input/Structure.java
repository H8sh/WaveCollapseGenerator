package net.h8sh.Input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Structure implements Serializable {
    @JsonProperty("Mesh")
    public Mesh mesh;
    @JsonProperty("Rotation")
    public Rotation rotation;
    @JsonProperty("Sockets")
    public Sockets sockets;
    @JsonProperty("Name")
    public Name name;
    @JsonProperty("Neighbors")
    public List<Neighbors> neighbors;

    public List<Neighbors> getNeighbors() {
        return neighbors;
    }

    public Mesh getMesh() {
        return mesh;
    }
    public Rotation getRotation() {
        return rotation;
    }
    public Sockets getSockets() {
        return sockets;
    }

    public Name getName() {
        return name;
    }
}