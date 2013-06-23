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

package org.coffeescript.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.coffeescript.lang.lexer.CoffeeScriptFlexLexerWithCommunicationSkills;
import org.coffeescript.lang.lexer._CoffeeScriptLexer;
import org.coffeescript.lang.lexer.CoffeeScriptTokenTypes;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class CoffeeScriptSyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Map<IElementType, TextAttributesKey> TOKENS_TO_STYLES;

    @NotNull
    public Lexer getHighlightingLexer() {
        return new CoffeeScriptFlexLexerWithCommunicationSkills(new _CoffeeScriptLexer((Reader) null));
    }

    static final TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.NUMBER",
        SyntaxHighlighterColors.NUMBER.getDefaultAttributes()
    );

    static final TextAttributesKey OPERATOR = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.OPERATOR",
        SyntaxHighlighterColors.OPERATION_SIGN.getDefaultAttributes()
    );

    static final TextAttributesKey ASSIGNMENT = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.ASSIGNMENT",
         SyntaxHighlighterColors.OPERATION_SIGN.getDefaultAttributes()
    );

    static final TextAttributesKey COMMENT = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.COMMENT",
        SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes()
    );

    static final TextAttributesKey BLOCK_COMMENT = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.BLOCK_COMMENT",
        SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes()
    );

    static final TextAttributesKey DOUBLE_QUOTE_STRING = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.DOUBLE_QUOTE_STRING",
        SyntaxHighlighterColors.STRING.getDefaultAttributes()
    );

    static final TextAttributesKey SINGLE_QUOTE_STRING = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.SINGLE_QUOTE_STRING",
        SyntaxHighlighterColors.STRING.getDefaultAttributes()
    );

    static final TextAttributesKey DOUBLE_QUOTE_HEREDOC = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.DOUBLE_QUOTE_HEREDOC",
        SyntaxHighlighterColors.STRING.getDefaultAttributes()
    );

    static final TextAttributesKey SINGLE_QUOTE_HEREDOC = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.SINGLE_QUOTE_HEREDOC",
        SyntaxHighlighterColors.STRING.getDefaultAttributes()
    );

    static final TextAttributesKey STRING_LITERAL = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.STRING",
        SyntaxHighlighterColors.VALID_STRING_ESCAPE.getDefaultAttributes()
    );

    static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.KEYWORD",
        SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()
    );

    static final TextAttributesKey RESERVED_WORD = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.BAD_KEYWORD",
        HighlighterColors.BAD_CHARACTER.getDefaultAttributes()
    );

    static final TextAttributesKey INTERPOLATION = TextAttributesKey.createTextAttributesKey(
        "COFFEESCRIPT.INTERPOLATION",
        SyntaxHighlighterColors.OPERATION_SIGN.getDefaultAttributes()
    );

    static {
        TOKENS_TO_STYLES = new HashMap<IElementType, TextAttributesKey>();
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.NUMBER, NUMBER);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.OPERATOR, OPERATOR);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.COMMENT, COMMENT);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.BLOCK_COMMENT, BLOCK_COMMENT);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.ASSIGNMENT, ASSIGNMENT);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.DOUBLE_QUOTE_STRING, DOUBLE_QUOTE_STRING);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.SINGLE_QUOTE_STRING, SINGLE_QUOTE_STRING);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.IDENTIFIER, HighlighterColors.TEXT);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.WHITESPACE, HighlighterColors.TEXT);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.BAD_CHARACTER, HighlighterColors.BAD_CHARACTER);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.STRING_LITERAL, STRING_LITERAL);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.LINE_TERMINATOR, HighlighterColors.TEXT);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.KEYWORD, KEYWORD);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.RESERVED_WORD, RESERVED_WORD);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.BRACE, SyntaxHighlighterColors.BRACES);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.BRACKET, SyntaxHighlighterColors.BRACKETS);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.COMMA, SyntaxHighlighterColors.COMMA);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.SEMI_COLON, SyntaxHighlighterColors.JAVA_SEMICOLON);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.DOT, SyntaxHighlighterColors.DOT);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.PARENTHESIS, SyntaxHighlighterColors.PARENTHS);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.ACCESSOR, SyntaxHighlighterColors.KEYWORD);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.BOOLEAN, SyntaxHighlighterColors.NUMBER);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.REGULAR_EXPRESSION, SyntaxHighlighterColors.STRING);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.REGULAR_EXPRESSION_LITERAL, SyntaxHighlighterColors.STRING);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.JAVASCRIPT, SyntaxHighlighterColors.STRING);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.FUNCTION, KEYWORD);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.REGULAR_EXPRESSION_FLAG, SyntaxHighlighterColors.STRING);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.DOUBLE_QUOTE_HEREDOC, DOUBLE_QUOTE_HEREDOC);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.SINGLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC);
        TOKENS_TO_STYLES.put(CoffeeScriptTokenTypes.INTERPOLATION, INTERPOLATION);
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (!TOKENS_TO_STYLES.containsKey(tokenType)) {
            throw new UnsupportedOperationException(tokenType.toString());
        }
        return pack(TOKENS_TO_STYLES.get(tokenType));
    }
}
