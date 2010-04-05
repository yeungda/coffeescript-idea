/* Copyright 2010 David Yeung

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package coffeescript.lang;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class SyntaxHighlighter extends SyntaxHighlighterBase {
    private static final Map<IElementType, TextAttributesKey> TOKENS_TO_STYLES;

    @NotNull
    public Lexer getHighlightingLexer() {
        return new FlexAdapterWithCommunicationSkills(new coffeescript.lang.Lexer((Reader) null));
    }

    static final TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey(
            "COFFEESCRIPT.NUMBER",
            SyntaxHighlighterColors.NUMBER.getDefaultAttributes()
    );

    private static final TextAttributesKey OPERATOR = TextAttributesKey.createTextAttributesKey(
            "COFFEESCRIPT.OPERATOR",
            SyntaxHighlighterColors.OPERATION_SIGN.getDefaultAttributes()
    );

    private static final TextAttributesKey ASSIGNMENT = TextAttributesKey.createTextAttributesKey(
            "COFFEESCRIPT.ASSIGNMENT",
            SyntaxHighlighterColors.OPERATION_SIGN.getDefaultAttributes()
    );

    private static final TextAttributesKey COMMENT = TextAttributesKey.createTextAttributesKey(
            "COFFEESCRIPT.COMMENT",
            SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes()
    );

    private static final TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey(
            "COFFEESCRIPT.STRING",
            SyntaxHighlighterColors.STRING.getDefaultAttributes()
    );

    private static final TextAttributesKey STRING_LITERAL = TextAttributesKey.createTextAttributesKey(
            "COFFEESCRIPT.STRING",
            SyntaxHighlighterColors.VALID_STRING_ESCAPE.getDefaultAttributes()
    );

    private static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey(
            "COFFEESCRIPT.KEYWORD",
            SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()
    );

    private static final TextAttributesKey RESERVED_WORD = TextAttributesKey.createTextAttributesKey(
            "COFFEESCRIPT.BAD_KEYWORD",
            HighlighterColors.BAD_CHARACTER.getDefaultAttributes()
    );

    static {
        TOKENS_TO_STYLES = new HashMap<IElementType, TextAttributesKey>();
        TOKENS_TO_STYLES.put(Tokens.NUMBER, NUMBER);
        TOKENS_TO_STYLES.put(Tokens.OPERATOR, OPERATOR);
        TOKENS_TO_STYLES.put(Tokens.COMMENT, COMMENT);
        TOKENS_TO_STYLES.put(Tokens.ASSIGNMENT, ASSIGNMENT);
        TOKENS_TO_STYLES.put(Tokens.STRING, STRING);
        TOKENS_TO_STYLES.put(Tokens.IDENTIFIER, HighlighterColors.TEXT);
        TOKENS_TO_STYLES.put(Tokens.WHITESPACE, HighlighterColors.TEXT);
        TOKENS_TO_STYLES.put(Tokens.BAD_CHARACTER, HighlighterColors.BAD_CHARACTER);
        TOKENS_TO_STYLES.put(Tokens.STRING_LITERAL, STRING_LITERAL);
        TOKENS_TO_STYLES.put(Tokens.LINE_TERMINATOR, HighlighterColors.TEXT);
        TOKENS_TO_STYLES.put(Tokens.KEYWORD, KEYWORD);
        TOKENS_TO_STYLES.put(Tokens.RESERVED_WORD, RESERVED_WORD);
        TOKENS_TO_STYLES.put(Tokens.BRACE, SyntaxHighlighterColors.BRACES);
        TOKENS_TO_STYLES.put(Tokens.BRACKET, SyntaxHighlighterColors.BRACKETS);
        TOKENS_TO_STYLES.put(Tokens.COMMA, SyntaxHighlighterColors.COMMA);
        TOKENS_TO_STYLES.put(Tokens.SEMI_COLON, SyntaxHighlighterColors.JAVA_SEMICOLON);
        TOKENS_TO_STYLES.put(Tokens.DOT, SyntaxHighlighterColors.DOT);
        TOKENS_TO_STYLES.put(Tokens.PARENTHESIS, SyntaxHighlighterColors.PARENTHS);
        TOKENS_TO_STYLES.put(Tokens.ACCESSOR, SyntaxHighlighterColors.KEYWORD);
        TOKENS_TO_STYLES.put(Tokens.BOOLEAN, SyntaxHighlighterColors.NUMBER);
        TOKENS_TO_STYLES.put(Tokens.REGULAR_EXPRESSION, SyntaxHighlighterColors.STRING);
        TOKENS_TO_STYLES.put(Tokens.REGULAR_EXPRESSION_LITERAL, SyntaxHighlighterColors.STRING);
        TOKENS_TO_STYLES.put(Tokens.JAVASCRIPT, SyntaxHighlighterColors.STRING);
        TOKENS_TO_STYLES.put(Tokens.FUNCTION, KEYWORD);
        TOKENS_TO_STYLES.put(Tokens.REGULAR_EXPRESSION_FLAG, SyntaxHighlighterColors.STRING);
        TOKENS_TO_STYLES.put(Tokens.HEREDOCS, SyntaxHighlighterColors.STRING);
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (!TOKENS_TO_STYLES.containsKey(tokenType)) {
            throw new UnsupportedOperationException(tokenType.toString());
        }
        return pack(TOKENS_TO_STYLES.get(tokenType));
    }

}
