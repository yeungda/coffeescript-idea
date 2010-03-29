package coffeescript.lang;

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
import static coffeescript.lang.LexerUnitTest.AnyString.NOUN;
import static coffeescript.lang.LexerUnitTest.AnyString.VERB;
import static coffeescript.lang.Tokens.*;

//TODO: @ accessor highlighting
//TODO: extra string literals
//TODO: variable highlighting
//TODO: string interpolation?
//TODO: test NOUNORVERB state
//TODO: backticks in javascript
//TODO: nested ''' in heredocs?
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
        assertInitialNounOfTwoTokens("\"\"", STRING, STRING);
        assertInitialNounOfTwoTokens("''", STRING, STRING);
    }

    @Test
    public void heredocs() {
        assertInitialNounOfTwoTokens("''''''", HEREDOCS, HEREDOCS);
        assertThat(lexing("'''a"), tokenisedTo(HEREDOCS, HEREDOCS));
        assertThat(lexing("''''"), tokenisedTo(HEREDOCS, HEREDOCS));
        assertThat(lexing("'''\n"), tokenisedTo(HEREDOCS, LINE_TERMINATOR));
        assertThat(lexing("'''\r"), tokenisedTo(HEREDOCS, LINE_TERMINATOR));
        assertThat(lexing("'''\n\n'''"), tokenisedTo(HEREDOCS, LINE_TERMINATOR, LINE_TERMINATOR, HEREDOCS));
    }

    static void assertInitialNounOfTwoTokens(String initialNoun, IElementType startToken, IElementType endToken) {
        assertThat(lexing(initialNoun), tokenisedTo(startToken, endToken));
        assertThat(lexing("(" + initialNoun), tokenisedTo(PARENTHESIS, startToken, endToken));
        assertThat(lexing(initialNoun + VERB), tokenisedTo(startToken, endToken, AnyToken.VERB));
        assertThat(lexing(IDENTIFIER + " " + initialNoun), tokenisedTo(IDENTIFIER, WHITESPACE, startToken, endToken));
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
        assertThat(lexing(IDENTIFIER + " " + noun), tokenisedTo(IDENTIFIER, WHITESPACE, token));
    }

    static void assertPreNoun(String preNoun, IElementType preNounToken) {
        assertThat("preNoun [" + preNoun + "]", lexing(preNoun), tokenisedTo(preNounToken));
        assertThat("preNoun [" + preNoun + "]", lexing("(" + preNoun), tokenisedTo(PARENTHESIS, preNounToken));
        assertThat("preNoun [" + preNoun + "]", lexing(preNoun + " " + NOUN), tokenisedTo(preNounToken, WHITESPACE, AnyToken.NOUN));
    }

    static void assertVerbalPreposition(String verbalPreposition, IElementType token) {
        assertVerb(verbalPreposition, token);
        assertPreNoun(verbalPreposition, token);
    }

    static void assertTextualVerbalPreNoun(String textualVerbalPreNoun, IElementType token) {
        assertTextualVerb(textualVerbalPreNoun, token);
        assertPreNoun(textualVerbalPreNoun, token);
    }

    private static void assertTextualVerb(String textualVerbalPreposition, IElementType token) {
        assertThat(lexing(NOUN + " " + textualVerbalPreposition), tokenisedTo(AnyToken.NOUN, WHITESPACE, token));
        assertThat(lexing(NOUN + " " + textualVerbalPreposition + " " + NOUN), tokenisedTo(AnyToken.NOUN, WHITESPACE, token, WHITESPACE, AnyToken.NOUN));
    }

    static void assertLastLineElement(String lastLineElement, IElementType token) {
        assertThat(lexing(lastLineElement), tokenisedTo(token));
        assertThat(lexing(NOUN + lastLineElement), tokenisedTo(AnyToken.NOUN, token));
        assertThat(lexing(NOUN + VERB + lastLineElement), tokenisedTo(AnyToken.NOUN, AnyToken.VERB, token));
    }

    static void assertNounalPreverb(String nounalPreverb, IElementType token) {
        assertPreverb(nounalPreverb, token);
        assertInitialNoun(nounalPreverb, token);
    }

    static void assertPreverb(String preverb, IElementType token) {
        assertThat(lexing(NOUN + preverb), tokenisedTo(AnyToken.NOUN, token));
        assertThat(lexing(NOUN + preverb + VERB), tokenisedTo(AnyToken.NOUN, token, AnyToken.VERB));
    }

    public static class VerbsUnitTest {

        @Test
        public void operators() {
            assertVerbalPreposition("+", OPERATOR);
            assertVerbalPreposition("-", OPERATOR);
            assertVerbalPreposition("*", OPERATOR);
            assertVerb("&", OPERATOR);
            assertVerbalPreposition("&&", OPERATOR);
            assertVerb("|", OPERATOR);
            assertVerbalPreposition("||", OPERATOR);
            assertVerb("/", OPERATOR);
            assertVerbalPreposition("%", OPERATOR);
            assertVerb("<", OPERATOR);
            assertVerb(">", OPERATOR);
            assertVerb("::", OPERATOR);
            assertVerb("!", OPERATOR);
            assertVerbalPreposition("?", OPERATOR);
            assertVerb("==", OPERATOR);
            assertVerb(">=", OPERATOR);
            assertVerb("<=", OPERATOR);
            assertVerb("!=", OPERATOR);
            assertVerb("++", OPERATOR);
            assertVerb("--", OPERATOR);
            assertVerb("...", OPERATOR);
            assertVerb("..", OPERATOR);
            assertVerb("<-", OPERATOR);
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
        public void separators() {
            assertThat(lexing(NOUN + "."), tokenisedTo(AnyToken.NOUN, DOT));
            assertThat(lexing(NOUN + ","), tokenisedTo(AnyToken.NOUN, COMMA));
            assertThat(lexing(NOUN + "." + NOUN), tokenisedTo(AnyToken.NOUN, DOT, AnyToken.NOUN));
            assertThat(lexing(NOUN + "," + NOUN), tokenisedTo(AnyToken.NOUN, COMMA, AnyToken.NOUN));
        }

    }

    public static class NounsUnitTest {

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
            assertPreNoun("{", BRACE);
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
        public void halfAssignments() {
            assertThat(lexing("foo:/ bar"), tokenisedTo(IDENTIFIER, ASSIGNMENT, OPERATOR, WHITESPACE, IDENTIFIER));
        }

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
            assertThat(lexing("foo(/\\$"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION_LITERAL));
            assertThat(lexing("foo(//g"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION, REGULAR_EXPRESSION_FLAG));
            assertThat(lexing("foo(//i"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION, REGULAR_EXPRESSION_FLAG));
            assertThat(lexing("foo(//m"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION, REGULAR_EXPRESSION_FLAG));
            assertThat(lexing("foo(//y"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION, REGULAR_EXPRESSION_FLAG));
            assertThat(lexing("foo(//gimy+"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION, REGULAR_EXPRESSION_FLAG, OPERATOR));
            assertThat(lexing("foo(//."), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION, DOT));
        }

        @Test
        public void keywords() {
            // javascript keywords
            assertTextualVerbalPreNoun("else", KEYWORD);
            assertPreNoun("new", KEYWORD);
            assertPreNoun("return", KEYWORD);
            assertPreNoun("try", KEYWORD);
            assertPreNoun("catch", KEYWORD);
            assertPreNoun("finally", KEYWORD);
            assertPreNoun("throw", KEYWORD);
            assertPreNoun("break", KEYWORD);
            assertPreNoun("continue", KEYWORD);
            assertTextualVerb("in", KEYWORD);
            assertPreNoun("while", KEYWORD);
            assertPreNoun("delete", KEYWORD);
            assertPreNoun("instanceof", KEYWORD);
            assertPreNoun("typeof", KEYWORD);
            assertPreNoun("switch", KEYWORD);
            assertPreNoun("super", KEYWORD);
            assertPreNoun("extends", KEYWORD);
            assertPreNoun("class", KEYWORD);
            // coffee keywords
            assertTextualVerb("then", KEYWORD);
            assertPreNoun("of", KEYWORD);
            assertPreNoun("by", KEYWORD);
            assertPreNoun("where", KEYWORD);
            assertPreNoun("when", KEYWORD);
            assertInitialNoun("this", KEYWORD);
            assertInitialNoun("null", KEYWORD);

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
            assertNounalPreverb(")", PARENTHESIS);
        }

        @Test
        public void accessors() {
            assertVerbalPreposition("@", ACCESSOR);
        }

        @Test
        public void brackets() {
            assertNounalPreverb("]", BRACKET);
            assertVerbalPreposition("[", BRACKET);
        }

        @Test
        public void keywords() {
            assertTextualVerbalPreNoun("if", KEYWORD);
            assertTextualVerbalPreNoun("and", KEYWORD);
            assertTextualVerbalPreNoun("or", KEYWORD);
            assertTextualVerbalPreNoun("is", KEYWORD);
            assertTextualVerbalPreNoun("isnt", KEYWORD);
            assertTextualVerbalPreNoun("not", KEYWORD);
            assertTextualVerbalPreNoun("unless", KEYWORD);
            assertTextualVerbalPreNoun("for", KEYWORD);
        }

        @Test
        public void functions() {
            assertVerbalPreposition("->", FUNCTION);
            assertVerbalPreposition("=>", FUNCTION);
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

    @Test
    public void javascript() {
        assertThat(lexing("` "), tokenisedTo(JAVASCRIPT, JAVASCRIPT));
        assertThat(lexing("`\n"), tokenisedTo(JAVASCRIPT, JAVASCRIPT));
        assertThat(lexing("`\n`"), tokenisedTo(JAVASCRIPT, JAVASCRIPT, JAVASCRIPT));
        assertThat(lexing("`\n`" + NOUN), tokenisedTo(JAVASCRIPT, JAVASCRIPT, JAVASCRIPT, AnyToken.NOUN));
    }

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
