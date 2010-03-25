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
import static yeungda.coffeescript.lang.LexerUnitTest.AnyString.NOUN;
import static yeungda.coffeescript.lang.LexerUnitTest.AnyString.VERB;
import static yeungda.coffeescript.lang.Tokens.*;

//TODO: square bracket corrections
//TODO: splats
//TODO: bind function operator
//TODO: javascript
//TODO: half assignment
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
        assertThat(lexing(";"), tokenisedTo(SEMI_COLON));
    }

    @Test
    public void whitespace() {
        assertThat(lexing(" "), tokenisedTo(WHITESPACE));
        assertThat(lexing("\t"), tokenisedTo(WHITESPACE));
    }

    static void assertVerb(String verb, IElementType token) {
        assertThat(lexing(NOUN + verb), tokenisedTo(AnyToken.NOUN, token));
        assertThat(lexing(NOUN + verb + NOUN), tokenisedTo(AnyToken.NOUN, token, AnyToken.NOUN));
    }

    static void assertInitialNoun(String noun, IElementType token) {
        assertThat(lexing(noun), tokenisedTo(token));
        assertThat(lexing("(" + noun), tokenisedTo(PARENTHESIS, token));
        assertThat(lexing(noun + VERB), tokenisedTo(token, AnyToken.VERB));
    }

    static void assertPreposition(String preposition, IElementType prepositionToken) {
        assertThat("preposition [" + preposition + "]", lexing(preposition), tokenisedTo(prepositionToken));
        assertThat("preposition [" + preposition + "]", lexing("(" + preposition), tokenisedTo(PARENTHESIS, prepositionToken));
        assertThat("preposition [" + preposition + "]", lexing(preposition + " " + NOUN), tokenisedTo(prepositionToken, WHITESPACE, AnyToken.NOUN));
    }

    static void assertVerbalPreposition(String verbalPreposition, IElementType token) {
        assertVerb(verbalPreposition, token);
        assertPreposition(verbalPreposition, token);
    }

    static void assertTextualVerbalPreposition(String textualVerbalPreposition, IElementType token) {
        assertTextualVerb(textualVerbalPreposition, token);
        assertPreposition(textualVerbalPreposition, token);
    }

    private static void assertTextualVerb(String textualVerbalPreposition, IElementType token) {
        assertThat(lexing(NOUN + " " + textualVerbalPreposition), tokenisedTo(AnyToken.NOUN, WHITESPACE, token));
        assertThat(lexing(NOUN + " " + textualVerbalPreposition + NOUN), tokenisedTo(AnyToken.NOUN, WHITESPACE, token, AnyToken.NOUN));
    }

    static void assertLastLineElement(String lastLineElement, IElementType token) {
        assertThat(lexing(lastLineElement), tokenisedTo(token));
        assertThat(lexing(NOUN + lastLineElement), tokenisedTo(AnyToken.NOUN, token));
        assertThat(lexing(NOUN + VERB + lastLineElement), tokenisedTo(AnyToken.NOUN, AnyToken.VERB, token));
    }

    public static class VerbsUnitTest {

        @Test
        public void operators() {
            assertVerb("+", OPERATOR);
            assertVerb("*", OPERATOR);
            assertVerb("&", OPERATOR);
            assertVerb("|", OPERATOR);
            assertVerb("/", OPERATOR);
            assertVerb("-", OPERATOR);
            assertVerb("%", OPERATOR);
            assertVerb("<", OPERATOR);
            assertVerb(">", OPERATOR);
            assertVerb("::", OPERATOR);
            assertVerb("!", OPERATOR);
            assertVerb("?", OPERATOR);
            assertVerb("==", OPERATOR);
            assertVerb(">=", OPERATOR);
            assertVerb("<=", OPERATOR);
            assertVerb("!=", OPERATOR);
            assertVerb("++", OPERATOR);
        }

        @Test
        public void assignment() {
            assertThat(lexing("foo+="), tokenisedTo(IDENTIFIER, OPERATOR, ASSIGNMENT));
            assertThat(lexing("foo-="), tokenisedTo(IDENTIFIER, OPERATOR, ASSIGNMENT));
            assertThat(lexing("foo="), tokenisedTo(IDENTIFIER, ASSIGNMENT));
            assertThat(lexing("foo:"), tokenisedTo(IDENTIFIER, ASSIGNMENT));
        }

        @Test
        public void brackets() {
            assertPreverb("]", BRACKET);
        }

        private void assertPreverb(String preverb, IElementType token) {
            assertThat(lexing(NOUN + preverb), tokenisedTo(AnyToken.NOUN, token));
            assertThat(lexing(NOUN + preverb + VERB), tokenisedTo(AnyToken.NOUN, token, AnyToken.VERB));
        }

        @Test
        public void identifiers() {
            assertThat(lexing("x+"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("(x+"), tokenisedTo(PARENTHESIS, IDENTIFIER, OPERATOR));
        }

        @Test
        public void separators() {
            assertThat(lexing(NOUN + "."), tokenisedTo(AnyToken.NOUN, DOT));
            assertThat(lexing(NOUN + ","), tokenisedTo(AnyToken.NOUN, COMMA));
            assertThat(lexing(NOUN + "." + NOUN), tokenisedTo(AnyToken.NOUN, DOT, AnyToken.NOUN));
            assertThat(lexing(NOUN + "," + NOUN), tokenisedTo(AnyToken.NOUN, COMMA, AnyToken.NOUN));
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
            assertThat(lexing("x" + VERB), tokenisedTo(IDENTIFIER, AnyToken.VERB));
            assertThat(lexing("\nx"), tokenisedTo(LINE_TERMINATOR, IDENTIFIER));
            assertThat(lexing("x:y"), tokenisedTo(IDENTIFIER, ASSIGNMENT, IDENTIFIER));
        }

    }

    public static class InitialNounUnitTest {

        @Test
        public void objectLiteral() {
            assertPreposition("{", BRACE);
            assertInitialNoun("}", BRACE);
        }

        @Test
        public void numbers() {
            assertInitialNoun("1", NUMBER);
            assertInitialNoun("0x1234ff", NUMBER);
            assertInitialNoun("1.2e+1", NUMBER);
            assertInitialNoun("1.1e-1", NUMBER);
            assertInitialNoun("1.1e1", NUMBER);
        }

        @Test
        public void booleans() {
            assertInitialNoun("yes", BOOLEAN);
            assertInitialNoun("no", BOOLEAN);
            assertInitialNoun("on", BOOLEAN);
            assertInitialNoun("off", BOOLEAN);
            assertInitialNoun("true", BOOLEAN);
            assertInitialNoun("false", BOOLEAN);
        }

        @Test
        public void keywords() {
            // javascript keywords
            assertPreposition("else", KEYWORD);
            assertPreposition("new", KEYWORD);
            assertPreposition("return", KEYWORD);
            assertPreposition("try", KEYWORD);
            assertPreposition("catch", KEYWORD);
            assertPreposition("finally", KEYWORD);
            assertPreposition("throw", KEYWORD);
            assertPreposition("break", KEYWORD);
            assertPreposition("continue", KEYWORD);
            assertTextualVerbalPreposition("for", KEYWORD);
            assertTextualVerb("in", KEYWORD);
            assertPreposition("while", KEYWORD);
            assertPreposition("delete", KEYWORD);
            assertPreposition("instanceof", KEYWORD);
            assertPreposition("typeof", KEYWORD);
            assertPreposition("switch", KEYWORD);
            assertPreposition("super", KEYWORD);
            assertPreposition("extends", KEYWORD);
            assertPreposition("class", KEYWORD);
            // coffee keywords
            assertTextualVerb("then", KEYWORD);
            assertTextualVerb("unless", KEYWORD);
            assertPreposition("of", KEYWORD);
            assertPreposition("by", KEYWORD);
            assertPreposition("where", KEYWORD);
            assertPreposition("when", KEYWORD);
        }

    }

    public static class InitialNounVerbUnitTest {

        @Test
        public void parenthesis() {
            assertThat(lexing("("), tokenisedTo(PARENTHESIS));
            assertThat(lexing(NOUN + "("), tokenisedTo(AnyToken.NOUN, PARENTHESIS));
            assertThat(lexing(NOUN + VERB + "("), tokenisedTo(AnyToken.NOUN, AnyToken.VERB, PARENTHESIS));
            assertThat(lexing("(" + NOUN), tokenisedTo(PARENTHESIS, AnyToken.NOUN));
            assertThat(lexing("()"), tokenisedTo(PARENTHESIS, PARENTHESIS));
            assertThat(lexing("(\"\")"), tokenisedTo(PARENTHESIS, STRING, STRING, PARENTHESIS));
            assertThat(lexing("('')"), tokenisedTo(PARENTHESIS, STRING, STRING, PARENTHESIS));
            assertThat(lexing(")"), tokenisedTo(BAD_CHARACTER));
            assertThat(lexing("()" + VERB), tokenisedTo(PARENTHESIS, PARENTHESIS, AnyToken.VERB));
        }

        @Test
        public void accessors() {
            assertVerbalPreposition("@", ACCESSOR);
        }

        @Test
        public void brackets() {
            assertVerbalPreposition("[", BRACKET);
        }

        @Test
        public void keywords() {
            assertTextualVerbalPreposition("if", KEYWORD);
            assertTextualVerbalPreposition("and", KEYWORD);
            assertTextualVerbalPreposition("or", KEYWORD);
            assertTextualVerbalPreposition("is", KEYWORD);
            assertTextualVerbalPreposition("isnt", KEYWORD);
            assertTextualVerbalPreposition("not", KEYWORD);
        }

        @Test
        public void functions() {
            assertVerbalPreposition("->", FUNCTION);
        }

        @Test
        public void whitespace() {
            assertThat(lexing(NOUN + " "), tokenisedTo(AnyToken.NOUN, WHITESPACE));
        }

        @Test
        public void comment() {
            assertLastLineElement("#", COMMENT);
            assertLastLineElement("# ", COMMENT);
            assertLastLineElement("#x", COMMENT);
        }

        @Test
        public void lineTerminators() {
            assertLastLineElement("\r", LINE_TERMINATOR);
            assertLastLineElement("\n", LINE_TERMINATOR);
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
