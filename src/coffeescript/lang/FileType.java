/*
 * Copyright 2010 David Yeung
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
