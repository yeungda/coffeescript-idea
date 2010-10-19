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

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.coffeescript.lang.lexer.CoffeeScriptElementType;

public class CoffeeScriptTokenTypes {
    public static final IElementType WHITESPACE = TokenType.WHITE_SPACE;
    public static final IElementType NUMBER = new CoffeeScriptElementType("NUMBER");
    public static final IElementType OPERATOR = new CoffeeScriptElementType("OPERATOR");
    public static final IElementType COMMENT = new CoffeeScriptElementType("COMMENT");
    public static final IElementType BLOCK_COMMENT = new CoffeeScriptElementType("BLOCK_COMMENT");
    public static final IElementType ASSIGNMENT = new CoffeeScriptElementType("ASSIGNMENT");
    public static final IElementType IDENTIFIER = new CoffeeScriptElementType("IDENTIFIER");
    public static final IElementType STRING = new CoffeeScriptElementType("STRING");
    public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    public static final IElementType STRING_LITERAL = new CoffeeScriptElementType("STRING_LITERAL");
    public static final IElementType LINE_TERMINATOR = new CoffeeScriptElementType("LINE_TERMINATOR");
    public static final IElementType KEYWORD = new CoffeeScriptElementType("KEYWORD");
    public static final IElementType RESERVED_WORD = TokenType.BAD_CHARACTER;
    public static final IElementType PARENTHESIS = new CoffeeScriptElementType("PARENTHESIS");
    public static final IElementType BRACE = new CoffeeScriptElementType("BRACE");
    public static final IElementType BRACKET = new CoffeeScriptElementType("BRACKET");
    public static final IElementType SEMI_COLON = new CoffeeScriptElementType("SEMI_COLON");
    public static final IElementType COMMA = new CoffeeScriptElementType("COMMA");
    public static final IElementType DOT = new CoffeeScriptElementType("DOT");
    public static final IElementType ACCESSOR = new CoffeeScriptElementType("ACCESSOR");
    public static final IElementType BOOLEAN = new CoffeeScriptElementType("BOOLEAN");
    public static final IElementType REGULAR_EXPRESSION = new CoffeeScriptElementType("REGULAR_EXPRESSION");
    public static final IElementType REGULAR_EXPRESSION_LITERAL = new CoffeeScriptElementType("REGULAR_EXPRESSION_LITERAL");
    public static final IElementType JAVASCRIPT = new CoffeeScriptElementType("JAVASCRIPT");
    public static final IElementType FUNCTION = new CoffeeScriptElementType("FUNCTION");
    public static final IElementType REGULAR_EXPRESSION_FLAG = new CoffeeScriptElementType("REGULAR_EXPRESSION_FLAG");
    public static final IElementType HEREDOCS = new CoffeeScriptElementType("HEREDOCS");
}
