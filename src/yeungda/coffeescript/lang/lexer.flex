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


REGEX_START        = \/[^\/ ]
REGEX_INTERPOLATION= ([^\\]\$[a-zA-Z_@]|[^\\]\$\{.*[^\\]\})
REGEX_FLAGS        = [imgy]{0,4}
REGEX_ESCAPE       = \\[^\$]

%state DOUBLE_QUOTE_STRING, SINGLE_QUOTE_STRING

%%

<YYINITIAL> {
    {IDENTIFIER}                { return Tokens.IDENTIFIER; }
    {WS}                        { return Tokens.WHITESPACE; }
    {NUMBER}                    { return Tokens.NUMBER; }
    {OPERATOR}                  { return Tokens.OPERATOR; }
    {COMMENT}                   { return Tokens.COMMENT; }
    \"                          { yybegin(DOUBLE_QUOTE_STRING); }
    \'                          { yybegin(SINGLE_QUOTE_STRING); }
}

<DOUBLE_QUOTE_STRING> {
    \"                             { yybegin(YYINITIAL); return Tokens.STRING; }
    "\\\""                         { }
    .                              { }
}

<SINGLE_QUOTE_STRING> {
    \'                             { yybegin(YYINITIAL); return Tokens.STRING; }
    "\\'"                          { }
    .                              { }
}

.                                  { yybegin(YYINITIAL);   return Tokens.BAD_CHARACTER; }