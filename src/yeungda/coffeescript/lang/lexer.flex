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

WS=[\ \t]+

IDENTIFIER    = [a-zA-Z\$_]([a-zA-Z_0-9$])*
NUMBER        = (0(x|X)[0-9a-fA-F]+)|([0-9]+(\.[0-9]+)?(e[+\-]?[0-9]+)?)
INTERPOLATION = \$([a-zA-Z_@]\w*(\.\w+)*)
OPERATOR      = ([+\*&|\/\-%=<>:!?][=+])
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
REGULAR_EXPRESSION = [^/\\\r\n]+
REGULAR_EXPRESSION_LITERAL = \\.
REGULAR_EXPRESSION_FLAGS = [imgy]{0,4}
REGULAR_EXPRESSION_TERMINATOR = \/{REGULAR_EXPRESSION_FLAGS}
HEREDOCS   = .
JAVASCRIPT = [^`]+
%state NOUN, DOUBLE_QUOTE_STRING, SINGLE_QUOTE_STRING, REGULAR_EXPRESSION, VERB, REGULAR_EXPRESSION_FLAG, NOUN_OR_VERB, JAVASCRIPT, HEREDOCS

%%

<YYINITIAL> {
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
    ";"                         { return Tokens.SEMI_COLON; }
    {LINE_TERMINATOR}           { return Tokens.LINE_TERMINATOR; }
    "`"                         { yybegin(JAVASCRIPT); return Tokens.JAVASCRIPT; }
}

<JAVASCRIPT> {
    "`"                         { yybegin(YYINITIAL); return Tokens.JAVASCRIPT; }
    {JAVASCRIPT}                { return Tokens.JAVASCRIPT; }
}

<VERB, NOUN_OR_VERB> {
    "+"                         |
    "++"                        |
    "*"                         |
    "&"                         |
    "|"                         |
    "/"                         |
    "-"                         |
    "--"                        |
    "%"                         |
    "<"                         |
    ">"                         |
    "::"                        |
    "!"                         |
    "!="                        |
    "=="                        |
    "<="                        |
    ">="                        |
    ".."                        |
    "..."                       |
    "?"                         { yybegin(NOUN); return Tokens.OPERATOR; }
    ")"                         { return Tokens.PARENTHESIS; }
    "="                         |
    ":"                         { yybegin(NOUN); return Tokens.ASSIGNMENT; }
    "."                         { yybegin(NOUN); return Tokens.DOT; }
    ","                         { yybegin(NOUN); return Tokens.COMMA; }
    "then"                      |
    "in"                        { yybegin(NOUN); return Tokens.KEYWORD; }
}

<YYINITIAL, NOUN, VERB, NOUN_OR_VERB> {
    "@"                         { yybegin(NOUN); return Tokens.ACCESSOR; }
    "if"                        |
    "else"                      |
    "unless"                    |
    "and"                       |
    "or"                        |
    "is"                        |
    "isnt"                      |
    "not"                       { yybegin(NOUN); return Tokens.KEYWORD; }
    "for"                       { yybegin(NOUN); return Tokens.KEYWORD; }
    "("                         { yybegin(NOUN); return Tokens.PARENTHESIS; }
    "["                         { yybegin(NOUN); return Tokens.BRACKET; }
    {WS}                        { return Tokens.WHITESPACE; }
    {LINE_TERMINATOR}           { yybegin(YYINITIAL); return Tokens.LINE_TERMINATOR; }
    {COMMENT}                   { return Tokens.COMMENT; }
    "->"                        |
    "=>"                        { yybegin(NOUN); return Tokens.FUNCTION; }
    "]"                         { yybegin(VERB); return Tokens.BRACKET; }
}

<YYINITIAL, NOUN, NOUN_OR_VERB> {
    "new"                       |
    "return"                    |
    "try"                       |
    "catch"                     |
    "finally"                   |
    "throw"                     |
    "break"                     |
    "continue"                  |
    "while"                     |
    "delete"                    |
    "instanceof"                |
    "typeof"                    |
    "switch"                    |
    "super"                     |
    "extends"                   |
    "class"                     |
    "of"                        |
    "by"                        |
    "where"                     |
    "when"                      { yybegin(NOUN); return Tokens.KEYWORD; }
    "true"                      |
    "false"                     |
    "yes"                       |
    "no"                        |
    "on"                        |
    "off"                       { yybegin(VERB); return Tokens.BOOLEAN; }
    {IDENTIFIER}                { yybegin(NOUN_OR_VERB); return Tokens.IDENTIFIER; }
    {NUMBER}                    { yybegin(VERB); return Tokens.NUMBER; }
    "{"                         { yybegin(NOUN); return Tokens.BRACE; }
    "}"                         { yybegin(VERB); return Tokens.BRACE; }
    ")"                         { yybegin(VERB); return Tokens.PARENTHESIS; }
    \"                          { yybegin(DOUBLE_QUOTE_STRING); return Tokens.STRING; }
    \'\'\'                      { yybegin(HEREDOCS); return Tokens.HEREDOCS; }
    \'                          { yybegin(SINGLE_QUOTE_STRING); return Tokens.STRING; }
}

<NOUN> {
    "="                         { return Tokens.ASSIGNMENT; }
    "/"                         { yybegin(REGULAR_EXPRESSION); return Tokens.REGULAR_EXPRESSION; }
}

<REGULAR_EXPRESSION> {
    {REGULAR_EXPRESSION}       { return Tokens.REGULAR_EXPRESSION; }
    "\\/"                      { return Tokens.REGULAR_EXPRESSION_LITERAL; }
    {REGULAR_EXPRESSION_LITERAL} { return Tokens.REGULAR_EXPRESSION_LITERAL; }
    {REGULAR_EXPRESSION_TERMINATOR} {
        final int length = yytext().length();
        if (length > 1) {
            yypushback(length -1);
            yybegin(REGULAR_EXPRESSION_FLAG);
        } else {
            yybegin(VERB);
        }
        return Tokens.REGULAR_EXPRESSION;
    }
    {LINE_TERMINATOR}          { yybegin(YYINITIAL); return Tokens.BAD_CHARACTER; }
}

<REGULAR_EXPRESSION_FLAG> {
    {REGULAR_EXPRESSION_FLAGS}       { yybegin(VERB); return Tokens.REGULAR_EXPRESSION_FLAG; }
}
<DOUBLE_QUOTE_STRING> {
    \"                             { yybegin(VERB); return Tokens.STRING; }
    "\\\""                         { return Tokens.STRING_LITERAL; }
    {CHARACTERS_IN_DOUBLE_QUOTES}  { return Tokens.STRING; }
}

<SINGLE_QUOTE_STRING> {
    \'                             { yybegin(VERB); return Tokens.STRING; }
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

<HEREDOCS> {
    "'''"                           { yybegin(VERB); return Tokens.HEREDOCS; }
    {HEREDOCS}                      { return Tokens.HEREDOCS; }
    {LINE_TERMINATOR}               { return Tokens.HEREDOCS; }
}
.                                  { yybegin(YYINITIAL);   return Tokens.BAD_CHARACTER; }