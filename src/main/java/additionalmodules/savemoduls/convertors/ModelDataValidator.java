package additionalmodules.savemoduls.convertors;

import models.*;

public class ModelDataValidator {

    public <T extends Task> boolean validateTask(T modelDataToSave) {
        if (modelDataToSave.getId() == null) {
            return false;
        } else if (modelDataToSave.getDescription() == null) {
            return false;
        } else if (modelDataToSave.getStatus() == null) {
            return false;
        }

        if (modelDataToSave instanceof Epic epic) {
            return epic.getSubTusksIdes() != null;
        }

        return true;
    }
}
