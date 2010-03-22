package yeungda.coffeescript.lang;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import com.intellij.lexer.LexerPosition;
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

public class LexerUnitTest {

    @Test
    public void shouldLexNumbers() {
        final Matcher<Iterable<IElementType>> tokenisesWithNumber = hasItem(Tokens.NUMBER);
        assertThat(lexing("1"), tokenisesWithNumber);
        assertThat(lexing("0x1234ff"), tokenisesWithNumber);
        assertThat(lexing("1.2e+1"), tokenisesWithNumber);
        assertThat(lexing("1.1e-1"), tokenisesWithNumber);
        assertThat(lexing("1.1e1"), tokenisesWithNumber);
    }

    @Test
    public void shouldLexOperators() {
        final Matcher<Iterable<IElementType>> tokenisesWithAnOperator = hasItem(Tokens.OPERATOR);
        assertThat(lexing("+"), tokenisesWithAnOperator);
        assertThat(lexing("*"), tokenisesWithAnOperator);
        assertThat(lexing("&"), tokenisesWithAnOperator);
        assertThat(lexing("|"), tokenisesWithAnOperator);
        assertThat(lexing("/"), tokenisesWithAnOperator);
        assertThat(lexing("-"), tokenisesWithAnOperator);
        assertThat(lexing("%"), tokenisesWithAnOperator);
        assertThat(lexing("="), tokenisesWithAnOperator);
        assertThat(lexing("<"), tokenisesWithAnOperator);
        assertThat(lexing(">"), tokenisesWithAnOperator);
        assertThat(lexing(":"), tokenisesWithAnOperator);
        assertThat(lexing("!"), tokenisesWithAnOperator);
        assertThat(lexing("?"), tokenisesWithAnOperator);
    }

    @Test
    public void shouldLexString() {
        assertThat(lexing("\"x\""), tokenisesTo(Tokens.STRING));
        assertThat(lexing("'x'"), tokenisesTo(Tokens.STRING));
        assertThat(lexing("\"\\\"1\""), tokenisesTo(Tokens.STRING));
        assertThat(lexing("'\\'1'"), tokenisesTo(Tokens.STRING));
    }

    @Test
    public void shouldLexIdentifier() {
        assertThat(lexing("word"), tokenisesTo(Tokens.IDENTIFIER));
        assertThat(lexing("$word"), tokenisesTo(Tokens.IDENTIFIER));
        assertThat(lexing("$aA0_$"), tokenisesTo(Tokens.IDENTIFIER));
    }

    private Matcher<Collection> tokenisesTo(IElementType... identifier) {
        return equalTo((Collection) Arrays.asList(identifier));
    }

    @Test
    public void shouldLexAssignment() {
        assertThat(lexing(":"), hasItem(Tokens.ASSIGNMENT));
        assertThat(lexing("="), hasItem(Tokens.ASSIGNMENT));
    }

    @Test
    public void shouldLexComment() {
        assertThat(lexing("#"), hasItem(Tokens.COMMENT));
    }

    private Collection<IElementType> lexing(final CharSequence charSequence) {
        final FlexAdapter lexer = new FlexAdapterWithCommunicationSkills(charSequence,
                new Lexer((Reader) null));
        lexer.start(charSequence);
        ArrayList<IElementType> tokenTypes = new ArrayList<IElementType>();
        while (lexer.getCurrentPosition().getOffset() < lexer.getBufferEnd()) {
            tokenTypes.add(lexer.getTokenType());
            lexer.advance();
        }
        return tokenTypes;
    }

    private static class FlexAdapterWithCommunicationSkills extends FlexAdapter {
        private final CharSequence charSequence;

        public FlexAdapterWithCommunicationSkills(CharSequence charSequence, FlexLexer lexer) {
            super(lexer);
            this.charSequence = charSequence;
        }

        @Override
        public String toString() {
            return "lexer with charsequence: " + charSequence.toString();
        }

        @Override
        public LexerPosition getCurrentPosition() {
            try {
                return super.getCurrentPosition();
            } catch (Error error) {
                throw new Error("Failed for input: " + toString(), error);
            }
        }

        @Override
        public void advance() {
            try {
                super.advance();
            } catch (Error error) {
                throw new Error("Failed for input: " + toString(), error);
            }
        }
    }
}
