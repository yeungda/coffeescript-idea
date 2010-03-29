package coffeescript;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileTypes.FileTypeManager;
import org.jetbrains.annotations.NotNull;
import coffeescript.lang.FileType;

public class Launcher implements ApplicationComponent {
    public Launcher() {
    }

    public void initComponent() {
        ApplicationManager.getApplication().runWriteAction(
                new Runnable() {
                    public void run() {
                        FileTypeManager.getInstance().registerFileType(FileType.COFFEESCRIPT_FILE_TYPE, new String[]{FileType.DEFAULT_EXTENSION});
                    }
                }

        );
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "CoffeeScript";
    }
}
