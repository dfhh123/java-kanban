package additionalmodules.historymanager;

import java.nio.file.Path;

public interface SavableHistoryManager extends HistoryManager {
    void saveDataToCsv();

    void loadDataFromCsv();

    void setSaveFileDirectory(Path saveFileDirectory);

    Path getSaveFileDirectory();
}
