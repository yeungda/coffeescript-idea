package yeungda.coffeescript.lang;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

public class Tokens {
    public static final IElementType WHITESPACE = TokenType.WHITE_SPACE;
    public static final IElementType NUMBER = new CoffeeScriptElementType("NUMBER");
    public static final IElementType OPERATOR = new CoffeeScriptElementType("OPERATOR");
    public static final IElementType COMMENT = new CoffeeScriptElementType("COMMENT");
    public static final IElementType ASSIGNMENT = new CoffeeScriptElementType("ASSIGNMENT");
    public static final IElementType IDENTIFIER = new CoffeeScriptElementType("IDENTIFIER");
    public static final IElementType STRING = new CoffeeScriptElementType("STRING");
    public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    public static final IElementType STRING_LITERAL = new CoffeeScriptElementType("STRING_LITERAL");
    public static final IElementType LINE_TERMINATOR = new CoffeeScriptElementType("LINE_TERMINATOR");
    public static final IElementType KEYWORD = new CoffeeScriptElementType("KEYWORD");
    public static final IElementType RESERVED_WORD = TokenType.BAD_CHARACTER;
    public static final IElementType PARENTHESIS = new CoffeeScriptElementType("PARENTHESIS");
    public static final IElementType BRACE = new CoffeeScriptElementType("BRACES");
    public static final IElementType BRACKET = new CoffeeScriptElementType("BRACKETS");
    public static final IElementType SEMI_COLON = new CoffeeScriptElementType("SEMI_COLON");
    public static final IElementType COMMA = new CoffeeScriptElementType("COLON");
    public static final IElementType DOT = new CoffeeScriptElementType("DOT");
    public static final IElementType ACCESSOR = new CoffeeScriptElementType("ACCESSOR");
}
