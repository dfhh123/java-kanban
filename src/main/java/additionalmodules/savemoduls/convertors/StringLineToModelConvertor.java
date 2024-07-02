package additionalmodules.savemoduls.convertors;

import models.*;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringLineToModelConvertor {

    public <T extends Task> T convertCsvLineToTask(String csvLine) {
        try {
            String[] csvLines = csvLine.split(",");

            T returnedTask = switch (csvLines[1].toUpperCase()) {
                case "SUBTASK" -> (T) new SubTask();
                case "EPIC" -> (T) new Epic();
                case "TASK" -> (T) new Task();
                default -> throw new RuntimeException("Incorrect task type");
            };

            returnedTask.setId(Integer.parseInt(csvLines[0]));
            returnedTask.setDescription(csvLines[3]);
            returnedTask.setStatus(Statuses.valueOf(csvLines[2]));

            if (returnedTask instanceof SubTask) {
                ((SubTask) returnedTask).setLinkedEpicId(Integer.parseInt(csvLines[4]));
            }

            if (returnedTask instanceof Epic && csvLines.length > 5) {
                ((Epic) returnedTask)
                        .setSubTusksIdes(Arrays.stream(csvLines[5]
                                        .split(" "))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList()));
            }

            return returnedTask;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
