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
package org.coffeescript.lang.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.psi.tree.IElementType;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.coffeescript.lang.lexer.CoffeeScriptLexerUnitTest.AnyString.NOUN;
import static org.coffeescript.lang.lexer.CoffeeScriptLexerUnitTest.AnyString.VERB;
import static org.coffeescript.lang.lexer.CoffeeScriptTokenTypes.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

//TODO: @ accessor highlighting
//TODO: extra string literals
//TODO: variable highlighting
//TODO: new reserved words  (u.utilities.key(k) for k of (u: require './utilities').utilities.functions)...
//TODO: string interpolation?
//TODO: test NOUNORVERB state
//TODO: backticks in javascript
//TODO: nested ''' in heredocs?
public class CoffeeScriptLexerUnitTest {

    @Test
    public void badCharacters() {
        assertThat(lexing("~"), tokenisedTo(BAD_CHARACTER));
    }

    @Test
    public void strings() {
        assertThat(lexing("\""), tokenisedTo(DOUBLE_QUOTE_STRING));
        assertStringHas("\\\'", STRING_LITERAL);
        assertStringHas("\\\"", STRING_LITERAL);
        assertStringHas("\\t", STRING_LITERAL);
        assertStringHas("\\\\", STRING_LITERAL);
        assertStringHas("\\n", STRING_LITERAL);
        assertStringHas("\\n", STRING_LITERAL);
        assertStringHas("\\x", BAD_CHARACTER);
        assertStringHas("\n", LINE_TERMINATOR);
        assertInitialNounOfTwoTokens("\"\"", DOUBLE_QUOTE_STRING, DOUBLE_QUOTE_STRING);
        assertInitialNounOfTwoTokens("''", SINGLE_QUOTE_STRING, SINGLE_QUOTE_STRING);
    }

    private void assertStringHas(String character, IElementType token) {
        assertThat(lexing("\"" + character), tokenisedTo(DOUBLE_QUOTE_STRING, token));
        assertThat(lexing("'" + character), tokenisedTo(SINGLE_QUOTE_STRING, token));
    }

    @Test
    public void interpolations() {
        assertThat(lexing("'a#{@n}'"), tokenisedTo(SINGLE_QUOTE_STRING, SINGLE_QUOTE_STRING, SINGLE_QUOTE_STRING));
        assertThat(lexing("\"b#{@n}\""), tokenisedTo(DOUBLE_QUOTE_STRING, DOUBLE_QUOTE_STRING, INTERPOLATION,  ACCESSOR, IDENTIFIER, INTERPOLATION, DOUBLE_QUOTE_STRING));
        assertThat(lexing("'''c#{@n}'''"), tokenisedTo(SINGLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC));
        assertThat(lexing("\"\"\"d#{@n}\"\"\""), tokenisedTo(DOUBLE_QUOTE_HEREDOC, DOUBLE_QUOTE_HEREDOC, INTERPOLATION, ACCESSOR, IDENTIFIER, INTERPOLATION, DOUBLE_QUOTE_HEREDOC));
    }

    @Test
    public void heredocs() {
        assertInitialNounOfTwoTokens("''''''", SINGLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC);
        assertThat(lexing("'''a"), tokenisedTo(SINGLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC));
        assertThat(lexing("''''"), tokenisedTo(SINGLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC));
        assertThat(lexing("'''\n"), tokenisedTo(SINGLE_QUOTE_HEREDOC, LINE_TERMINATOR));
        assertThat(lexing("'''\r"), tokenisedTo(SINGLE_QUOTE_HEREDOC, LINE_TERMINATOR));
        assertThat(lexing("'''\n\n'''"), tokenisedTo(SINGLE_QUOTE_HEREDOC, LINE_TERMINATOR, LINE_TERMINATOR, SINGLE_QUOTE_HEREDOC));
    }

    @Test
    public void doubleQuotedHeredocs() {
        assertInitialNounOfTwoTokens("\"\"\"\"\"\"", DOUBLE_QUOTE_HEREDOC, DOUBLE_QUOTE_HEREDOC);
        assertThat(lexing("\"\"\"a"), tokenisedTo(DOUBLE_QUOTE_HEREDOC, DOUBLE_QUOTE_HEREDOC));
        assertThat(lexing("\"\"\"\""), tokenisedTo(DOUBLE_QUOTE_HEREDOC, DOUBLE_QUOTE_HEREDOC));
        assertThat(lexing("\"\"\"\n"), tokenisedTo(DOUBLE_QUOTE_HEREDOC, LINE_TERMINATOR));
        assertThat(lexing("\"\"\"\r"), tokenisedTo(DOUBLE_QUOTE_HEREDOC, LINE_TERMINATOR));
        assertThat(lexing("\"\"\"\n\n\"\"\""), tokenisedTo(DOUBLE_QUOTE_HEREDOC, LINE_TERMINATOR, LINE_TERMINATOR, DOUBLE_QUOTE_HEREDOC));
    }

    static void assertInitialNounOfTwoTokens(String initialNoun, IElementType startToken, IElementType endToken) {
        assertThat(lexing(initialNoun), tokenisedTo(startToken, endToken));
        assertThat(lexing("(" + initialNoun), tokenisedTo(PARENTHESIS, startToken, endToken));
        assertThat(lexing(initialNoun + VERB), tokenisedTo(startToken, endToken, AnyToken.VERB));
        assertThat(lexing(IDENTIFIER + " " + initialNoun), tokenisedTo(IDENTIFIER, WHITESPACE, startToken, endToken));
    }

    @Test
    public void multiLineStrings() {
        assertThat(lexing("'\nx"), tokenisedTo(SINGLE_QUOTE_STRING, LINE_TERMINATOR, SINGLE_QUOTE_STRING));
        assertThat(lexing("'\rx"), tokenisedTo(SINGLE_QUOTE_STRING, LINE_TERMINATOR, SINGLE_QUOTE_STRING));
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
        assertThat(lexing("enum"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("export"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("import"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("native"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("__extends"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("__hasProp"), tokenisedTo(RESERVED_WORD));
        assertThat(lexing("__slice"), tokenisedTo(RESERVED_WORD));
    }

    @Test
    public void separators() {
        assertThat(lexing(";"), tokenisedTo(SEMI_COLON));
        assertVerb(";", SEMI_COLON);
        assertThat(lexing("x=1;y"), tokenisedTo(IDENTIFIER, ASSIGNMENT, NUMBER, SEMI_COLON, IDENTIFIER));
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

    static void assertVerbalPreNoun(String verbalPreNoun, IElementType token) {
        assertVerb(verbalPreNoun, token);
        assertPreNoun(verbalPreNoun, token);
    }

    static void assertTextualVerbalPreNoun(String textualVerbalPreNoun, IElementType token) {
        assertTextualVerb(textualVerbalPreNoun, token);
        assertPreNoun(textualVerbalPreNoun, token);
    }

    private static void assertTextualVerb(String textualVerbalVerb, IElementType token) {
        assertThat(lexing(NOUN + " " + textualVerbalVerb), tokenisedTo(AnyToken.NOUN, WHITESPACE, token));
        assertThat(lexing(NOUN + " " + textualVerbalVerb + " " + NOUN), tokenisedTo(AnyToken.NOUN, WHITESPACE, token, WHITESPACE, AnyToken.NOUN));
    }

    static void assertLastLineElement(String lastLineElement, IElementType token) {
        assertThat(lexing(lastLineElement), tokenisedTo(token));
        assertThat(lexing(NOUN + lastLineElement), tokenisedTo(AnyToken.NOUN, token));
        assertThat(lexing(NOUN + VERB + lastLineElement), tokenisedTo(AnyToken.NOUN, AnyToken.VERB, token));
    }

    static void assertNounalPreVerb(String nounalPreverb, IElementType token) {
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
            assertVerbalPreNoun("+", OPERATOR);
            assertVerbalPreNoun("-", OPERATOR);
            assertVerbalPreNoun("*", OPERATOR);
            assertVerb("&", OPERATOR);
            assertVerbalPreNoun("&&", OPERATOR);
            assertVerb("|", OPERATOR);
            assertVerbalPreNoun("||", OPERATOR);
            assertVerb("/", OPERATOR);
            assertVerbalPreNoun("%", OPERATOR);
            assertVerb("<", OPERATOR);
            assertVerb(">", OPERATOR);
            assertVerb("::", OPERATOR);
            assertVerbalPreNoun("!", OPERATOR);
            assertVerbalPreNoun("!!", OPERATOR);
            assertVerb("^", OPERATOR);
            assertVerb("~", OPERATOR);
            assertVerb("<<", OPERATOR);
            assertVerb(">>", OPERATOR);
            assertVerb(">>>", OPERATOR);
            assertVerbalPreNoun("?", OPERATOR);
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
            assertThat(lexing("foo or="), tokenisedTo(IDENTIFIER, WHITESPACE, ASSIGNMENT));
            assertThat(lexing("fooor="), tokenisedTo(IDENTIFIER, ASSIGNMENT));
        }

        @Test
        public void identifiers() {
            assertThat(lexing("x+"), tokenisedTo(IDENTIFIER, OPERATOR));
            assertThat(lexing("(x+"), tokenisedTo(PARENTHESIS, IDENTIFIER, OPERATOR));
        }

        @Test
        public void separators() {
            assertThat(lexing("x" + "."), tokenisedTo(IDENTIFIER, DOT));
            assertThat(lexing("x" + ","), tokenisedTo(IDENTIFIER, COMMA));
            assertThat(lexing("x" + "." + "x"), tokenisedTo(IDENTIFIER, DOT, IDENTIFIER));
            assertThat(lexing("x" + "," + "x"), tokenisedTo(IDENTIFIER, COMMA, IDENTIFIER));
        }

    }

    public static class NounsUnitTest {

        @Test
        public void strings() {
            assertThat(lexing("(\""), tokenisedTo(PARENTHESIS, DOUBLE_QUOTE_STRING));
            assertThat(lexing("(\"\"+"), tokenisedTo(PARENTHESIS, DOUBLE_QUOTE_STRING, DOUBLE_QUOTE_STRING, OPERATOR));
            assertThat(lexing("('"), tokenisedTo(PARENTHESIS, SINGLE_QUOTE_STRING));
            assertThat(lexing("(''+"), tokenisedTo(PARENTHESIS, SINGLE_QUOTE_STRING, SINGLE_QUOTE_STRING, OPERATOR));
        }

        @Test
        public void whitespace() {
            assertThat(lexing("foo= "), tokenisedTo(IDENTIFIER, ASSIGNMENT, WHITESPACE));
        }

        @Test
        public void identifiers() {
            assertThat(lexing("x" + VERB), tokenisedTo(IDENTIFIER, AnyToken.VERB));
            assertThat(lexing("\nx"), tokenisedTo(LINE_TERMINATOR, IDENTIFIER));
            assertThat(lexing("x=y"), tokenisedTo(IDENTIFIER, ASSIGNMENT, IDENTIFIER));
        }

    }

    public static class InitialNounUnitTest {

        @Test
        public void objectLiteral() {
            assertPreNoun("{", BRACE);
            assertInitialNoun("}", BRACE);
            assertNounalPreVerb("}", BRACE);
            assertThat(lexing("{x:1}"), tokenisedTo(BRACE, IDENTIFIER, ASSIGNMENT, NUMBER, BRACE));
            assertThat(lexing("  window:  {width: 200, height: 200}"), not(hasItem(BAD_CHARACTER)));
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
        public void javascript() {
            assertInitialNounOfTwoTokens("``", JAVASCRIPT, JAVASCRIPT);
            assertThat(lexing("` "), tokenisedTo(JAVASCRIPT, JAVASCRIPT));
            assertThat(lexing("`\n"), tokenisedTo(JAVASCRIPT, JAVASCRIPT));
            assertThat(lexing("`\n`"), tokenisedTo(JAVASCRIPT, JAVASCRIPT, JAVASCRIPT));
            assertThat(lexing("`\n`" + NOUN), tokenisedTo(JAVASCRIPT, JAVASCRIPT, JAVASCRIPT, AnyToken.NOUN));
        }

        @Test
        public void halfAssignments() {
            assertThat(lexing("foo:/ bar"), tokenisedTo(IDENTIFIER, ASSIGNMENT, OPERATOR, WHITESPACE, IDENTIFIER));
        }

        @Test
        public void regexes() {
            assertThat(lexing("foo:/x"), tokenisedTo(IDENTIFIER, ASSIGNMENT, REGULAR_EXPRESSION, REGULAR_EXPRESSION));
            assertThat(lexing("foo=/x"), tokenisedTo(IDENTIFIER, ASSIGNMENT, REGULAR_EXPRESSION, REGULAR_EXPRESSION));
            assertThat(lexing("foo(/x"), tokenisedTo(IDENTIFIER, PARENTHESIS, REGULAR_EXPRESSION, REGULAR_EXPRESSION));
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
            assertThat(lexing("(\"\")"), tokenisedTo(PARENTHESIS, DOUBLE_QUOTE_STRING, DOUBLE_QUOTE_STRING, PARENTHESIS));
            assertThat(lexing("('')"), tokenisedTo(PARENTHESIS, SINGLE_QUOTE_STRING, SINGLE_QUOTE_STRING, PARENTHESIS));
            assertNounalPreVerb(")", PARENTHESIS);
        }

        @Test
        public void accessors() {
            assertVerbalPreNoun("@", ACCESSOR);
        }

        @Test
        public void brackets() {
            assertNounalPreVerb("]", BRACKET);
            assertVerbalPreNoun("[", BRACKET);
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
            assertTextualVerbalPreNoun("while", KEYWORD);
        }

        @Test
        public void functions() {
            assertVerbalPreNoun("->", FUNCTION);
            assertVerbalPreNoun("=>", FUNCTION);
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
            assertThat(lexing("#x\n x"), tokenisedTo(COMMENT, LINE_TERMINATOR, WHITESPACE, IDENTIFIER));
        }

        @Test
        public void blockcomment() {
            assertLastLineElement("### A block comment ###", BLOCK_COMMENT);
            assertLastLineElement("###\nA block comment\n###", BLOCK_COMMENT);
            assertLastLineElement("###\nA\n# block\n# comment\n###", BLOCK_COMMENT);
            assertThat(lexing("x ### A block comment ###"), tokenisedTo(IDENTIFIER, BLOCK_COMMENT));
        }

        @Test
        public void lineTerminators() {
            assertLastLineElement("\r", LINE_TERMINATOR);
            assertLastLineElement("\n", LINE_TERMINATOR);
            assertThat(lexing(NOUN + "\n" + NOUN), tokenisedTo(AnyToken.NOUN, LINE_TERMINATOR, AnyToken.NOUN));
            assertThat(lexing(NOUN + "\r" + NOUN), tokenisedTo(AnyToken.NOUN, LINE_TERMINATOR, AnyToken.NOUN));
            assertThat(lexing(NOUN + "\n."), tokenisedTo(AnyToken.NOUN, LINE_TERMINATOR, DOT));
            assertThat(lexing(NOUN + "\r."), tokenisedTo(AnyToken.NOUN, LINE_TERMINATOR, DOT));
        }

    }

    public static Matcher<Collection> tokenisedTo(IElementType... identifier) {
        return equalTo((Collection) Arrays.asList(identifier));
    }

    public static Collection<IElementType> lexing(final CharSequence charSequence) {
        final FlexAdapter lexer = new CoffeeScriptFlexLexerWithCommunicationSkills(
                new _CoffeeScriptLexer((Reader) null));
        lexer.start(charSequence);
        ArrayList<IElementType> tokenTypes = new ArrayList<IElementType>();
        while (lexer.getCurrentPosition().getOffset() < lexer.getBufferEnd()) {
            tokenTypes.add(lexer.getTokenType());
            lexer.advance();
        }
        return tokenTypes;
    }

    static Matcher<Iterable<IElementType>> tokenisedWithoutBadCharacters() {
        return not(hasItem(BAD_CHARACTER));
    }

    public static class AnyToken {
        private static final IElementType VERB = OPERATOR;
        private static final IElementType NOUN = NUMBER;
    }

    public static class AnyString {

        public static final String VERB = "+";
        public static final String NOUN = "1";
        private static final String OPERATOR = "+";
    }
}
