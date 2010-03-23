package yeungda.coffeescript.lang;

import com.intellij.lexer.FlexAdapter;
import com.intellij.psi.tree.IElementType;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static yeungda.coffeescript.lang.Tokens.*;

public class LexerUnitTest {

    @Test
    public void shouldLexBadCharacters() {
        assertThat(lexing("~"), tokenisesTo(BAD_CHARACTER));
    }

    @Test
    public void shouldLexNumbers() {
        assertThat(lexing("1"), tokenisesTo(NUMBER));
        assertThat(lexing("0x1234ff"), tokenisesTo(NUMBER));
        assertThat(lexing("1.2e+1"), tokenisesTo(NUMBER));
        assertThat(lexing("1.1e-1"), tokenisesTo(NUMBER));
        assertThat(lexing("1.1e1"), tokenisesTo(NUMBER));
    }

    @Test
    public void shouldLexOperators() {
        assertThat(lexing("+"), tokenisesTo(OPERATOR));
        assertThat(lexing("*"), tokenisesTo(OPERATOR));
        assertThat(lexing("&"), tokenisesTo(OPERATOR));
        assertThat(lexing("|"), tokenisesTo(OPERATOR));
        assertThat(lexing("/"), tokenisesTo(OPERATOR));
        assertThat(lexing("-"), tokenisesTo(OPERATOR));
        assertThat(lexing("%"), tokenisesTo(OPERATOR));
        assertThat(lexing("="), tokenisesTo(OPERATOR));
        assertThat(lexing("<"), tokenisesTo(OPERATOR));
        assertThat(lexing(">"), tokenisesTo(OPERATOR));
        assertThat(lexing(":"), tokenisesTo(OPERATOR));
        assertThat(lexing("!"), tokenisesTo(OPERATOR));
        assertThat(lexing("?"), tokenisesTo(OPERATOR));
    }

    @Test
    public void shouldLexString() {
        assertThat(lexing("\"x\""), tokenisesTo(STRING, STRING, STRING));
        assertThat(lexing("'x'"), tokenisesTo(STRING, STRING, STRING));
        assertThat(lexing("\"\\\""), tokenisesTo(STRING, STRING_LITERAL));
        assertThat(lexing("'\\'"), tokenisesTo(STRING, STRING_LITERAL));
        assertThat(lexing("\""), tokenisesTo(STRING));
        assertThat(lexing("\"\\\\"), tokenisesTo(STRING, STRING_LITERAL));
        assertThat(lexing("\"\\x"), tokenisesTo(STRING, BAD_CHARACTER));
        assertThat(lexing("\"\n"), tokenisesTo(STRING, LINE_TERMINATOR));
        assertThat(lexing("'\\\\"), tokenisesTo(STRING, STRING_LITERAL));
        assertThat(lexing("'\\x"), tokenisesTo(STRING, BAD_CHARACTER));
        assertThat(lexing("'\n"), tokenisesTo(STRING, LINE_TERMINATOR));
    }

    @Test
    public void shouldLexMultiLineStrings() {
        assertThat(lexing("'\nx"), tokenisesTo(STRING, Tokens.LINE_TERMINATOR, STRING));
        assertThat(lexing("'\rx"), tokenisesTo(STRING, LINE_TERMINATOR, STRING));
    }

    @Test
    public void shouldLexIdentifier() {
        assertThat(lexing("word"), tokenisesTo(IDENTIFIER));
        assertThat(lexing("$word"), tokenisesTo(IDENTIFIER));
        assertThat(lexing("$aA0_$"), tokenisesTo(IDENTIFIER));
    }

    @Test
    public void shouldLexKeywords() {
        // javascript keywords
        assertThat(lexing("if"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("else"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("true"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("false"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("new"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("return"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("try"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("catch"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("finally"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("throw"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("break"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("continue"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("for"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("in"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("while"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("delete"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("instanceof"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("typeof"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("switch"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("super"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("extends"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("class"), tokenisesTo(Tokens.KEYWORD));
        // coffee aliases
        assertThat(lexing("and"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("or"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("is"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("isnt"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("not"), tokenisesTo(Tokens.KEYWORD));
        // coffee keywords
        assertThat(lexing("then"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("unless"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("yes"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("no"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("on"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("off"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("of"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("by"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("where"), tokenisesTo(Tokens.KEYWORD));
        assertThat(lexing("when"), tokenisesTo(Tokens.KEYWORD));
    }

    @Test
    public void shouldLexReservedWords() {
        assertThat(lexing("case"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("default"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("do"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("function"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("var"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("void"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("with"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("const"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("let"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("debugger"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("enum"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("export"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("import"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("native"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("__extends"), tokenisesTo(Tokens.RESERVED_WORD));
        assertThat(lexing("__hasProp"), tokenisesTo(Tokens.RESERVED_WORD));
    }

    private Matcher<Collection> tokenisesTo(IElementType... identifier) {
        return equalTo((Collection) Arrays.asList(identifier));
    }


    @Test
    public void shouldLexComment() {
        assertThat(lexing("#"), tokenisesTo(COMMENT));
        assertThat(lexing("# "), tokenisesTo(COMMENT));
        assertThat(lexing(" #"), tokenisesTo(COMMENT));
        assertThat(lexing("#x"), tokenisesTo(COMMENT));
    }

    private Collection<IElementType> lexing(final CharSequence charSequence) {
        final FlexAdapter lexer = new FlexAdapterWithCommunicationSkills(
                new Lexer((Reader) null));
        lexer.start(charSequence);
        ArrayList<IElementType> tokenTypes = new ArrayList<IElementType>();
        while (lexer.getCurrentPosition().getOffset() < lexer.getBufferEnd()) {
            tokenTypes.add(lexer.getTokenType());
            lexer.advance();
        }
        return tokenTypes;
    }

}
