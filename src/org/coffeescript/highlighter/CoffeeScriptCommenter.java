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

package org.coffeescript.highlighter;

import com.intellij.lang.CodeDocumentationAwareCommenter;
import com.intellij.psi.PsiComment;
import com.intellij.psi.tree.IElementType;
import org.coffeescript.lang.lexer.CoffeeScriptTokenTypes;
import org.jetbrains.annotations.Nullable;

public class CoffeeScriptCommenter implements CodeDocumentationAwareCommenter {

    public String getLineCommentPrefix() {
        return "#";
    }

    public String getBlockCommentPrefix() {
        return "###";
    }

    public String getBlockCommentSuffix() {
        return "###";
    }

    public String getCommentedBlockCommentPrefix() {
        return null;
    }

    public String getCommentedBlockCommentSuffix() {
        return null;
    }

    @Nullable
    public IElementType getLineCommentTokenType() {
        return CoffeeScriptTokenTypes.COMMENT;
    }

    @Nullable
    public IElementType getBlockCommentTokenType() {
        return CoffeeScriptTokenTypes.BLOCK_COMMENT;
    }

    @Nullable
    public IElementType getDocumentationCommentTokenType() {
        return null;
    }

    @Nullable
    public String getDocumentationCommentPrefix() {
        return null;
    }

    @Nullable
    public String getDocumentationCommentLinePrefix() {
        return null;
    }

    @Nullable
    public String getDocumentationCommentSuffix() {
        return null;
    }

    public boolean isDocumentationComment(PsiComment element) {
        return false;
    }
}
