package yeungda.coffeescript.lang;

import com.intellij.lexer.EmptyLexer;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class SyntaxHighlighter extends SyntaxHighlighterBase {
    @NotNull
    public Lexer getHighlightingLexer() {
        return new EmptyLexer();
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(TextAttributesKey.createTextAttributesKey(
                "COFFEESCRIPT",
                CodeInsightColors.INSTANCE_FIELD_ATTRIBUTES.getDefaultAttributes()
        ));
    }
}
