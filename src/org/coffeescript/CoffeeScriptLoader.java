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

package org.coffeescript;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileTypes.FileTypeManager;
import org.jetbrains.annotations.NotNull;

public class CoffeeScriptLoader implements ApplicationComponent {

    public CoffeeScriptLoader() {
    }

    public void initComponent() {
        ApplicationManager.getApplication().runWriteAction(
            new Runnable() {
                public void run() {
                    FileTypeManager.getInstance().registerFileType(CoffeeScriptFileType.COFFEESCRIPT_FILE_TYPE, new String[]{CoffeeScriptFileType.DEFAULT_EXTENSION});
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
