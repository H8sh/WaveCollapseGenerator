package net.h8sh.Input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Neighbors implements Serializable {
    @JsonProperty("North_neighbor")
    public List<String> northNeighbor;

    @JsonProperty("South_neighbor")
    public List<String> southNeighbor;

    @JsonProperty("East_neighbor")
    public List<String> eastNeighbor;

    @JsonProperty("West_neighbor")
    public List<String> westNeighbor;

    public Neighbors(){
        super();
    }

    public List<String> getWestNeighbor() {
        return westNeighbor;
    }

    public List<String> getSouthNeighbor() {
        return southNeighbor;
    }

    public List<String> getNorthNeighbor() {
        return northNeighbor;
    }

    public List<String> getEastNeighbor() {
        return eastNeighbor;
    }
}
