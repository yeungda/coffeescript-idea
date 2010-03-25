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

//TODO: regexes
//TODO: @ accessor highlighting

//TODO: extra string literals
//TODO: variable highlighting
//TODO: string interpolation?
public class LexerUnitTest {

    @Test
    public void badCharacters() {
        assertThat(lexing("~"), tokenisedTo(BAD_CHARACTER));
    }

    @Test
    public void strings() {
        assertThat(lexing("\"x\""), tokenisedTo(STRING, STRING, STRING));
        assertThat(lexing("'x'"), tokenisedTo(STRING, STRING, STRING));
        assertThat(lexing("\"\\\""), tokenisedTo(STRING, STRING_LITERAL));
        assertThat(lexing("'\\'"), tokenisedTo(STRING, STRING_LITERAL));
        assertThat(lexing("\""), tokenisedTo(STRING));
        assertThat(lexing("\"\\\\"), tokenisedTo(STRING, STRING_LITERAL));
        assertThat(lexing("\"\\x"), tokenisedTo(STRING, BAD_CHARACTER));
        assertThat(lexing("\"\\n"), tokenisedTo(STRING, STRING_LITERAL));
        assertThat(lexing("'\\\\"), tokenisedTo(STRING, STRING_LITERAL));
        assertThat(lexing("'\\x"), tokenisedTo(STRING, BAD_CHARACTER));
        assertThat(lexing("'\\n"), tokenisedTo(STRING, STRING_LITERAL));
        assertThat(lexing("\"\n"), tokenisedTo(STRING, LINE_TERMINATOR));
        assertThat(lexing("'\n"), tokenisedTo(STRING, LINE_TERMINATOR));
    }

    @Test
    public void multiLineStrings() {
        assertThat(lexing("'\nx"), tokenisedTo(STRING, LINE_TERMINATOR, STRING));
        assertThat(lexing("'\rx"), tokenisedTo(STRING, LINE_TERMINATOR, STRING));
    }

    @Test
    public void identifiers() {
        assertThat(lexing("word"), tokenisedTo(IDENTIFIER));
        assertThat(lexing("$word"), tokenisedTo(IDENTIFIER));
        assertThat(lexing("$aA0_$"), tokenisedTo(IDENTIFIER));
    }

    @Test
    public void keywords() {
        // javascript keywords
        assertThat(lexing("if"), tokenisedTo(KEYWORD));
        assertThat(lexing("else"), tokenisedTo(KEYWORD));
        assertThat(lexing("new"), tokenisedTo(KEYWORD));
        assertThat(lexing("return"), tokenisedTo(KEYWORD));
        assertThat(lexing("try"), tokenisedTo(KEYWORD));
        assertThat(lexing("catch"), tokenisedTo(KEYWORD));
        assertThat(lexing("finally"), tokenisedTo(KEYWORD));
        assertThat(lexing("throw"), tokenisedTo(KEYWORD));
        assertThat(lexing("break"), tokenisedTo(KEYWORD));
        assertThat(lexing("continue"), tokenisedTo(KEYWORD));
        assertThat(lexing("for"), tokenisedTo(KEYWORD));
        assertThat(lexing("in"), tokenisedTo(KEYWORD));
        assertThat(lexing("while"), tokenisedTo(KEYWORD));
        assertThat(lexing("delete"), tokenisedTo(KEYWORD));
        assertThat(lexing("instanceof"), tokenisedTo(KEYWORD));
        assertThat(lexing("typeof"), tokenisedTo(KEYWORD));
        assertThat(lexing("switch"), tokenisedTo(KEYWORD));
        assertThat(lexing("super"), tokenisedTo(KEYWORD));
        assertThat(lexing("extends"), tokenisedTo(KEYWORD));
        assertThat(lexing("class"), tokenisedTo(KEYWORD));
        // coffee aliases
        assertThat(lexing("and"), tokenisedTo(KEYWORD));
        assertThat(lexing("or"), tokenisedTo(KEYWORD));
        assertThat(lexing("is"), tokenisedTo(KEYWORD));
        assertThat(lexing("isnt"), tokenisedTo(KEYWORD));
        assertThat(lexing("not"), tokenisedTo(KEYWORD));
        // coffee keywords
        assertThat(lexing("then"), tokenisedTo(KEYWORD));
        assertThat(lexing("unless"), tokenisedTo(KEYWORD));
        assertThat(lexing("of"), tokenisedTo(KEYWORD));
        assertThat(lexing("by"), tokenisedTo(KEYWORD));
        assertThat(lexing("where"), tokenisedTo(KEYWORD));
        assertThat(lexing("when"), tokenisedTo(KEYWORD));
    }

    @Test
    public void reservedWords() {
        assertThat(lexing("case"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("default"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("do"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("function"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("var"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("void"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("with"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("const"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("let"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("debugger"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("enum"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("export"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("import"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("native"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("__extends"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("__hasProp"), tokenisedTo(RESERVED_WORD));
    }

    @Test
    public void separators() {
        assertThat(lexing("("), tokenisedTo(PARENTHESIS));
        assertThat(lexing("()"), tokenisedTo(PARENTHESIS, PARENTHESIS));
        assertThat(lexing("(x)"), tokenisedTo(PARENTHESIS, IDENTIFIER, PARENTHESIS));
        assertThat(lexing("{"), tokenisedTo(BRACE));
        assertThat(lexing("}"), tokenisedTo(BRACE));
        assertThat(lexing("["), tokenisedTo(BRACKET));
        assertThat(lexing("]"), tokenisedTo(BRACKET));
        assertThat(lexing(";"), tokenisedTo(SEMI_COLON));
        assertThat(lexing(","), tokenisedTo(COMMA));
        assertThat(lexing("."), tokenisedTo(DOT));
    }

    @Test
    public void whitespace() {
        assertThat(lexing(" "), tokenisedTo(WHITESPACE));
        assertThat(lexing("\t"), tokenisedTo(WHITESPACE));
    }

    @Test
    public void booleans() {
        assertThat(lexing("yes"), tokenisedTo(BOOLEAN));
        assertThat(lexing("no"), tokenisedTo(BOOLEAN));
        assertThat(lexing("on"), tokenisedTo(BOOLEAN));
        assertThat(lexing("off"), tokenisedTo(BOOLEAN));
        assertThat(lexing("true"), tokenisedTo(BOOLEAN));
        assertThat(lexing("false"), tokenisedTo(BOOLEAN));
    }

    @Test
    public void accessors() {
        assertThat(lexing("@"), tokenisedTo(ACCESSOR));
    }

    @Test
    public void comment() {
        assertThat(lexing("#"), tokenisedTo(COMMENT));
        assertThat(lexing("# "), tokenisedTo(COMMENT));
        assertThat(lexing(" #"), tokenisedTo(COMMENT));
        assertThat(lexing("#x"), tokenisedTo(COMMENT));
    }

    public static class VerbsUnitTest {

        @Test
        public void operators() {
            assertThat(lexing("foo+"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo*"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo&"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo|"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo/"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo-"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo%"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo<"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo>"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo::"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo!"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo?"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo=="), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo>="), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo<="), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo!="), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("foo++"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing(AnyString.NOUN + AnyString.OPERATOR + AnyString.NOUN), tokenisedTo(AnyToken.NOUN, OPERATOR, AnyToken.NOUN));
        }

        @Test
        public void assignment() {
            assertThat(lexing("foo+="), tokenisedTo(IDENTIFIER, OPERATOR, ASSIGNMENT));
            assertThat(lexing("foo-="), tokenisedTo(IDENTIFIER, OPERATOR, ASSIGNMENT));
            assertThat(lexing("foo="), tokenisedTo(IDENTIFIER, ASSIGNMENT));
            assertThat(lexing("foo:"), tokenisedTo(IDENTIFIER, ASSIGNMENT));
        }

        @Test
        public void identifiers() {
            assertThat(lexing("x+"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("(x+"), tokenisedTo(PARENTHESIS, IDENTIFIER, OPERATOR));
        }

        @Test
        public void should() {
            assertThat(lexing("foo: 1 / 1\nfoo: foo / 1"), tokenisedTo(IDENTIFIER));
        }

    }

    public static class NounsUnitTest {

        @Test
        public void regexes() {
            assertThat(lexing("foo:/"), tokenisedTo(IDENTIFIER, ASSIGNMENT, REGULAR_EXPRESSION));
            assertThat(lexing("foo=/"), tokenisedTo(IDENTIFIER, ASSIGNMENT, REGULAR_EXPRESSION));
            assertThat(lexing("foo(/"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION));
            assertThat(lexing("foo(/x"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION));
            assertThat(lexing("foo(//"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION));
            assertThat(lexing("foo(/\\//"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION_LITERAL, REGULAR_EXPRESSION));
            assertThat(lexing("foo(/\\x/"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION_LITERAL, REGULAR_EXPRESSION));
            assertThat(lexing("foo(/\\\\/"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION_LITERAL, REGULAR_EXPRESSION));
            assertThat(lexing("foo(/\n"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, BAD_CHARACTER));
            assertThat(lexing("foo(/\r"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, BAD_CHARACTER));
        }

        @Test
        public void strings() {
            assertThat(lexing("(\""), tokenisedTo(PARENTHESIS, STRING));
            assertThat(lexing("(\"\"+"), tokenisedTo(PARENTHESIS, STRING, STRING, OPERATOR));
            assertThat(lexing("('"), tokenisedTo(PARENTHESIS, STRING));
            assertThat(lexing("(''+"), tokenisedTo(PARENTHESIS, STRING, STRING, OPERATOR));
        }

        @Test
        public void whitespace() {
            assertThat(lexing("foo: "), tokenisedTo(IDENTIFIER, ASSIGNMENT, WHITESPACE));
        }

        @Test
        public void identifiers() {
            assertThat(lexing("x" + AnyString.VERB), tokenisedTo(IDENTIFIER, AnyToken.VERB));
            assertThat(lexing("\nx"), tokenisedTo(LINE_TERMINATOR, IDENTIFIER));
            assertThat(lexing("x:y"), tokenisedTo(IDENTIFIER, ASSIGNMENT, IDENTIFIER));
        }

    }

    public static class InitialNounUnitTest {

        @Test
        public void numbers() {
            assertThat(lexing("1"), tokenisedTo(NUMBER));
            assertThat(lexing("0x1234ff"), tokenisedTo(NUMBER));
            assertThat(lexing("1.2e+1"), tokenisedTo(NUMBER));
            assertThat(lexing("1.1e-1"), tokenisedTo(NUMBER));
            assertThat(lexing("1.1e1"), tokenisedTo(NUMBER));
            assertThat(lexing("(1"), tokenisedTo(PARENTHESIS, NUMBER));
            assertThat(lexing("1" + AnyString.VERB), tokenisedTo(NUMBER, AnyToken.VERB));
        }

    }

    public static class InitialNounVerbUnitTest {

        @Test
        public void parenthesis() {
            assertThat(lexing("("), tokenisedTo(PARENTHESIS));
            assertThat(lexing(AnyString.NOUN + "("), tokenisedTo(AnyToken.NOUN, PARENTHESIS));
            assertThat(lexing(AnyString.NOUN + AnyString.VERB + "("), tokenisedTo(AnyToken.NOUN, AnyToken.VERB, PARENTHESIS));
            assertThat(lexing("(" + AnyString.NOUN), tokenisedTo(PARENTHESIS, AnyToken.NOUN));
            assertThat(lexing("()"), tokenisedTo(PARENTHESIS, PARENTHESIS));
            assertThat(lexing("(\"\")"), tokenisedTo(PARENTHESIS, STRING, STRING, PARENTHESIS));
            assertThat(lexing("('')"), tokenisedTo(PARENTHESIS, STRING, STRING, PARENTHESIS));
            assertThat(lexing(")"), tokenisedTo(BAD_CHARACTER));
            assertThat(lexing("()" + AnyString.VERB), tokenisedTo(PARENTHESIS, PARENTHESIS, AnyToken.VERB));
        }

        @Test
        public void whitespace() {
            assertThat(lexing(AnyString.NOUN + " "), tokenisedTo(AnyToken.NOUN, WHITESPACE));
        }

        @Test
        public void lineTerminators() {
            assertThat(lexing("\r"), tokenisedTo(LINE_TERMINATOR));
            assertThat(lexing("\n"), tokenisedTo(LINE_TERMINATOR));
            assertThat(lexing(AnyString.NOUN + "\r"), tokenisedTo(AnyToken.NOUN, LINE_TERMINATOR));
            assertThat(lexing(AnyString.NOUN + "\n"), tokenisedTo(AnyToken.NOUN, LINE_TERMINATOR));
            assertThat(lexing(AnyString.NOUN + AnyString.VERB + "\r"), tokenisedTo(AnyToken.NOUN, AnyToken.VERB, LINE_TERMINATOR));
            assertThat(lexing(AnyString.NOUN + AnyString.VERB + "\n"), tokenisedTo(AnyToken.NOUN, AnyToken.VERB, LINE_TERMINATOR));
        }
    }
//
//    @Test
//    @Ignore
//    public void javascript() {
//        assertThat(lexing("`"), tokenisedTo(Tokens.JAVASCRIPT));
//    }

    public static Matcher<Collection> tokenisedTo(IElementType... identifier) {
        return equalTo((Collection) Arrays.asList(identifier));
    }

    public static Collection<IElementType> lexing(final CharSequence charSequence) {
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

    public static class AnyToken {
        private static final IElementType VERB = OPERATOR;
        private static final IElementType NOUN = Tokens.IDENTIFIER;
    }

    public static class AnyString {

        public static final String VERB = "+";
        public static final String NOUN = "x";
        private static final String OPERATOR = "+";
    }
}
