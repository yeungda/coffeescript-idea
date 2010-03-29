package coffeescript.lang;

import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;

public class Language extends com.intellij.lang.Language {
    public Language() {
        super("CoffeeScript");
    }

    public static class HighlighterFactory extends SingleLazyInstanceSyntaxHighlighterFactory {
        @NotNull
        protected SyntaxHighlighter createHighlighter() {
            return new SyntaxHighlighter();
        }

    }

}
