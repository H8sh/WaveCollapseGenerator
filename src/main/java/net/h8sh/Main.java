package net.h8sh;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.h8sh.Input.Prototype;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final String OUTPUT_FILE_NAME = "OutputPrototypes.json";
    private static final String INPUT_DIRECTORY = "input";
    private static final String OUTPUT_DIRECTORY = "output";
    private static final Path INPUT_PATH = Path.of(INPUT_DIRECTORY);
    private static final boolean isInputDirCreated = Files.exists(INPUT_PATH);
    private static final Path OUTPUT_PATH = Path.of(OUTPUT_DIRECTORY);

    public static void main(String[] args) throws IOException {

        // Check that inputs dir exists
        if (isInputDirCreated) {

            // Parse all .json files in the inputs directory
            List<Prototype> inputJsonFiles = loadJsonInputFiles(INPUT_DIRECTORY);


            // Generate rotated prototypes from the input prototypes
            List<Prototype> rotatedInputJsonFiles = createRotationFromInputJsonFiles(inputJsonFiles);


            // Compute neighbours for all te prototypes
            createRotationFromRotatedInputJsonFiles(rotatedInputJsonFiles);

            // Clear the outputs directory content
            FileUtils.deleteDirectory(new File(OUTPUT_DIRECTORY));
            Files.createDirectory(OUTPUT_PATH);

            // Write all the prototypes with their neighbours data in 1 .json file
            convertToJson(rotatedInputJsonFiles);


        }
    }

    private static List<Prototype> createRotationFromRotatedInputJsonFiles(List<Prototype> rotatedInputJsonFiles) {

        for (Prototype prototype : rotatedInputJsonFiles) {

            int prototypeEast = prototype.getStructure().getSockets().getEast();
            int prototypeWest = prototype.getStructure().getSockets().getWest();
            int prototypeSouth = prototype.getStructure().getSockets().getSouth();
            int prototypeNorth = prototype.getStructure().getSockets().getNorth();

            for (Prototype analyzedPrototype : rotatedInputJsonFiles) {

                int analyzedEast = analyzedPrototype.getStructure().getSockets().getEast();
                int analyzedWest = analyzedPrototype.getStructure().getSockets().getWest();
                int analyzedSouth = analyzedPrototype.getStructure().getSockets().getSouth();
                int analyzedNorth = analyzedPrototype.getStructure().getSockets().getNorth();


                if (prototypeWest == analyzedEast) {
                    prototype.getStructure().getNeighbors().get(0).getWestNeighbor().add(analyzedPrototype.getStructure().getName().getName_type());
                }

                if (prototypeEast == analyzedWest) {
                    prototype.getStructure().getNeighbors().get(0).getEastNeighbor().add(analyzedPrototype.getStructure().getName().getName_type());
                }


                if (prototypeSouth == analyzedNorth) {
                    prototype.getStructure().getNeighbors().get(0).getSouthNeighbor().add(analyzedPrototype.getStructure().getName().getName_type());
                }


                if (prototypeNorth == analyzedSouth) {
                    prototype.getStructure().getNeighbors().get(0).getNorthNeighbor().add(analyzedPrototype.getStructure().getName().getName_type());
                }


            }
        }

        return null;
    }

    public static List<Prototype> loadJsonInputFiles(String path) {
        List<Prototype> inputJsonFiles = new ArrayList<>();

        try (Stream<Path> stream = Files.list(Paths.get(path))) {
            var files = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(p -> p.toFile())
                    .collect(Collectors.toSet());

            ObjectMapper objectMapper = new ObjectMapper();
            for (var file : files) {
                Prototype prototype = objectMapper.readValue(file, Prototype.class);
                inputJsonFiles.add(prototype);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return inputJsonFiles;
    }

    public static List<Prototype> createRotationFromInputJsonFiles(List<Prototype> inputPrototypes) throws IOException {

        List<Prototype> rotatedPrototypes = new ArrayList<>();

        for (Prototype prototype : inputPrototypes) {
            int rotationAllowed = prototype.getStructure().getRotation().getRotation_allowed();
            if (rotationAllowed != 0) {
                for (int rotation = 1; rotation < rotationAllowed; rotation++) {
                    Prototype rotatedPrototype = SerializationUtils.clone(prototype);

                    rotatedPrototype.getStructure().getName().setName_type(prototype.getStructure().getName().getName_type() + "R" + rotation);
                    rotatedPrototype.getStructure().getRotation().setRotation_type(rotation);
                    rotatedPrototype.getStructure().getSockets().setNorth(cycle(prototype, rotation).get(0));
                    rotatedPrototype.getStructure().getSockets().setSouth(cycle(prototype, rotation).get(1));
                    rotatedPrototype.getStructure().getSockets().setEast(cycle(prototype, rotation).get(2));
                    rotatedPrototype.getStructure().getSockets().setWest(cycle(prototype, rotation).get(3));

                    rotatedPrototypes.add(rotatedPrototype);
                }
            }
            prototype.getStructure().getName().setName_type(prototype.getStructure().getName().getName_type() + "R0");
            rotatedPrototypes.add(prototype);
        }

        return rotatedPrototypes;

    }

    public static void convertToJson(List<Prototype> toConvert) throws IOException {
        JSONArray toBeConverted = new JSONArray();

        for (Prototype prototype : toConvert) {
            JSONObject fromObjectToJson = new JSONObject(prototype);
            toBeConverted.put(fromObjectToJson);
        }

        JSONObject outputObject = new JSONObject();
        outputObject.put("prototypes", toBeConverted);

        var outputFilePath = Paths.get(OUTPUT_DIRECTORY, OUTPUT_FILE_NAME);
        File file = outputFilePath.toFile();
        file.createNewFile();        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(outputObject.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    public static List<Integer> cycle(Prototype prototype, int rotationCycle) {
        List<Integer> rotations = new ArrayList<>();
        switch (rotationCycle) {
            case 1:
                rotations.add(prototype.getStructure().getSockets().getEast()); //North
                rotations.add(prototype.getStructure().getSockets().getWest()); //South
                rotations.add(prototype.getStructure().getSockets().getSouth()); //East
                rotations.add(prototype.getStructure().getSockets().getNorth()); //West

            case 2:
                rotations.add(prototype.getStructure().getSockets().getSouth()); //North
                rotations.add(prototype.getStructure().getSockets().getNorth()); //South
                rotations.add(prototype.getStructure().getSockets().getWest()); //East
                rotations.add(prototype.getStructure().getSockets().getEast()); //West

            case 3:
                rotations.add(prototype.getStructure().getSockets().getWest()); //North
                rotations.add(prototype.getStructure().getSockets().getEast()); //South
                rotations.add(prototype.getStructure().getSockets().getNorth()); //East
                rotations.add(prototype.getStructure().getSockets().getSouth()); //West

        }
        return rotations;
    }

}