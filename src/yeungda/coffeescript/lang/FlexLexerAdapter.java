package yeungda.coffeescript.lang;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class FlexLexerAdapter extends FlexAdapter {
    public FlexLexerAdapter() {
        super(new Lexer((Reader) null));
    }
}
