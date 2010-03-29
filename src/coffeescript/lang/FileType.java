package coffeescript.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import coffeescript.icon.Icons;

import javax.swing.*;

public class FileType extends LanguageFileType {

    public static final FileType COFFEESCRIPT_FILE_TYPE = new FileType();
    public static final com.intellij.lang.Language COFFEESCRIPT_LANGUAGE = COFFEESCRIPT_FILE_TYPE.getLanguage();
    @NonNls
    public static final String DEFAULT_EXTENSION = "coffee";

    public FileType() {
        super(new Language());
    }

    @NotNull
    @NonNls
    public String getName() {
        return "CoffeeScript";
    }

    @NonNls
    @NotNull
    public String getDescription() {
        return "CoffeeScript Files";
    }

    @NotNull
    @NonNls
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    public Icon getIcon() {
        return Icons.ICON;
    }

}
