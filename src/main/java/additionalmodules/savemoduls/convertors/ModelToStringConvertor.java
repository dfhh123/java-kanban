package additionalmodules.savemoduls.convertors;

import models.*;

public class ModelToStringConvertor {
    private final ModelDataValidator validator = new ModelDataValidator();

    public <T extends Task> String convaertModelToString(T task) {
        if (!validator.validateTask(task)) {
            throw new IllegalArgumentException("Incorrect task");
        }

        StringBuilder sb = new StringBuilder();

        sb.append(task.getId());
        sb.append(",");

        if (task instanceof SubTask) {
            sb.append("SUBTASK,");
        } else if (task instanceof Epic) {
            sb.append("EPIC,");
        } else {
            sb.append("TASK,");
        }

        sb.append(task.getStatus().toString());
        sb.append(",");
        sb.append(task.getDescription());
        sb.append(",");

        if (task instanceof SubTask) {
            sb.append(((SubTask) task).getLinkedEpicId());
        } else if (task instanceof Epic) {
            sb.append(",");
            sb.append(prepareEpicSubtasksIds((Epic) task));
        }

        sb.append("\n");
        return sb.toString();
    }

    private String prepareEpicSubtasksIds(Epic epic) {
        StringBuilder sb = new StringBuilder();
        for (Integer subTaskId : epic.getSubTusksIdes()) {
            sb.append(subTaskId);
            sb.append(" ");
        }
        return sb.toString();
    }
}
