package yeungda.coffeescript.lang;


import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%class Lexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

WHITE_SPACE_CHAR=[\ \n\r\t]

WS={WHITE_SPACE_CHAR}+

IDENTIFIER    = [a-zA-Z\$_]([a-zA-Z_0-9$])*
NUMBER        = (0(x|X)[0-9a-fA-F]+)|([0-9]+(\.[0-9]+)?(e[+\-]?[0-9]+)?)
INTERPOLATION = \$([a-zA-Z_@]\w*(\.\w+)*)
OPERATOR      = ([+\*&|\/\-%=<>:!?]+)
WHITESPACE    = ([ \t]+)
COMMENT       = (((\n?[ \t]*)?#[^\n]*)+)
CODE          = ((-|=)>)
MULTI_DENT    = ((\n([ \t]*))+)(\.)?
LAST_DENT     = \n([ \t]*)
ASSIGNMENT    = (:|=)

CHARACTERS_IN_DOUBLE_QUOTES  = ([^\"\r\n\\]+)
CHARACTERS_IN_SINGLE_QUOTES  = ([^\'\r\n\\]+)
LINE_TERMINATOR = [\n\r]

REGEX_START        = \/[^\/ ]
REGEX_INTERPOLATION= ([^\\]\$[a-zA-Z_@]|[^\\]\$\{.*[^\\]\})
REGEX_FLAGS        = [imgy]{0,4}
REGEX_ESCAPE       = \\[^\$]

%state DOUBLE_QUOTE_STRING, SINGLE_QUOTE_STRING

%%

<YYINITIAL> {
    "if"        |
    "else"      |
    "new"       |
    "return"    |
    "try"       |
    "catch"     |
    "finally"   |
    "throw"     |
    "break"     |
    "continue"  |
    "for"       |
    "in"        |
    "while"     |
    "delete"    |
    "instanceof"|
    "typeof"    |
    "switch"    |
    "super"     |
    "extends"   |
    "class"     |
    "and"       |
    "or"        |
    "is"        |
    "isnt"      |
    "not"       |
    "then"      |
    "unless"    |
    "of"        |
    "by"        |
    "where"     |
    "when"                      { return Tokens.KEYWORD; }

    "true"      |
    "false"     |
    "yes"       |
    "no"        |
    "on"        |
    "off"                      { return Tokens.BOOLEAN; }

    "case"      |
    "default"   |
    "do"        |
    "function"  |
    "var"       |
    "void"      |
    "with"      |
    "const"     |
    "let"       |
    "debugger"  |
    "enum"      |
    "export"    |
    "import"    |
    "native"    |
    "__extends" |
    "__hasProp"                 { return Tokens.RESERVED_WORD; }
    "("                         |
    ")"                         { return Tokens.PARENTHESIS; }
    "{"                         |
    "}"                         { return Tokens.BRACE; }
    "["                         |
    "]"                         { return Tokens.BRACKET; }
    ";"                         { return Tokens.SEMI_COLON; }
    ","                         { return Tokens.COMMA; }
    "."                         { return Tokens.DOT; }
    "@"                         { return Tokens.ACCESSOR; }
    {IDENTIFIER}                { return Tokens.IDENTIFIER; }
    {WS}                        { return Tokens.WHITESPACE; }
    {NUMBER}                    { return Tokens.NUMBER; }
    {OPERATOR}                  { return Tokens.OPERATOR; }
    {COMMENT}                   { return Tokens.COMMENT; }
    \"                          { yybegin(DOUBLE_QUOTE_STRING); return Tokens.STRING; }
    \'                          { yybegin(SINGLE_QUOTE_STRING); return Tokens.STRING; }
}

<DOUBLE_QUOTE_STRING> {
    \"                             { yybegin(YYINITIAL); return Tokens.STRING; }
    "\\\""                         { return Tokens.STRING_LITERAL; }
    {CHARACTERS_IN_DOUBLE_QUOTES}  { return Tokens.STRING; }
}

<SINGLE_QUOTE_STRING> {
    \'                             { yybegin(YYINITIAL); return Tokens.STRING; }
    "\\'"                          { return Tokens.STRING_LITERAL; }
    {CHARACTERS_IN_SINGLE_QUOTES}  { return Tokens.STRING; }
}

<DOUBLE_QUOTE_STRING, SINGLE_QUOTE_STRING> {
    "\\n"                          |
    "\\\\"                         { return Tokens.STRING_LITERAL; }
    "\n"                           |
    "\r"                           { return Tokens.LINE_TERMINATOR; }
    \\.                            { return Tokens.BAD_CHARACTER; }
}

.                                  { yybegin(YYINITIAL);   return Tokens.BAD_CHARACTER; }