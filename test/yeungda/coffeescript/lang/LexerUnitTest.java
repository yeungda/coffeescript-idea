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
import static org.junit.matchers.JUnitMatchers.hasItem;
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
        assertThat(lexing("\"\n"), tokenisesTo(STRING, BAD_CHARACTER));
        assertThat(lexing("'\\\\"), tokenisesTo(STRING, STRING_LITERAL));
        assertThat(lexing("'\\x"), tokenisesTo(STRING, BAD_CHARACTER));
        assertThat(lexing("'\n"), tokenisesTo(STRING, BAD_CHARACTER));
        assertThat(lexing("'\nx"), tokenisesTo(STRING, BAD_CHARACTER, IDENTIFIER));
        assertThat(lexing("'\rx"), tokenisesTo(STRING, BAD_CHARACTER, IDENTIFIER));
        assertThat(lexing("'\nx"), tokenisesTo(STRING, BAD_CHARACTER, IDENTIFIER));
    }

    @Test
    public void shouldLexIdentifier() {
        assertThat(lexing("word"), tokenisesTo(IDENTIFIER));
        assertThat(lexing("$word"), tokenisesTo(IDENTIFIER));
        assertThat(lexing("$aA0_$"), tokenisesTo(IDENTIFIER));
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
