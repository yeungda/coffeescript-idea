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


import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import java.util.Stack;

%%

%class _CoffeeScriptLexer
%implements FlexLexer
%unicode
%public

%function advance
%type IElementType

%eof{  return;
%eof}

%{
    private Stack<Integer> stack = new Stack<Integer>();

    private void yypushState(int newState) {
        stack.push(yystate());
        yybegin(newState);
    }

    private void yypopState() {
        yybegin(stack.pop());
    }

    // For Demetra compatibility
    public void reset(CharSequence buffer, int initialState){
        zzBuffer = buffer;
        zzBufferArray = null;
        zzCurrentPos = zzMarkedPos = zzStartRead = 0;
        zzPushbackPos = 0;
        zzAtEOF = false;
        zzAtBOL = true;
        zzEndRead = buffer.length();
        yybegin(initialState);
    }
%}

WS = [\ \t]+

LINE_TERMINATOR = [\n\r]

IDENTIFIER     = [a-zA-Z\$_]([a-zA-Z_0-9$])*
NUMBER         = (0(x|X)[0-9a-fA-F]+)|([0-9]+(\.[0-9]+)?(e[+\-]?[0-9]+)?)
OPERATOR       = ([+\*&|\/\-%=<>:!?][=+])
WHITESPACE     = ([ \t]+)
COMMENT        = ((([ \t]*)?#[^\n]*)+)
BLOCK_COMMENTS = (([ \t]*)?(###)+([^]*?)(###)+)
CODE           = ((-|=)>)
ASSIGNMENT     = (:|=|or=)

CHARACTERS_IN_DOUBLE_QUOTES      = ([^#{\"\r\n\\]+)
CHARACTERS_IN_SINGLE_QUOTES      = ([^\'\r\n\\]+)
CHARACTERS_IN_HEREDOCS           = [^\r\n]
CHARACTERS_IN_JAVASCRIPT         = [^`]+
CHARACTERS_IN_REGULAR_EXPRESSION = [^#{/\\\r\n]+

REGULAR_EXPRESSION_LITERAL    = \\.
REGULAR_EXPRESSION_FLAGS      = [imgy]{0,4}
REGULAR_EXPRESSION_TERMINATOR = \/{REGULAR_EXPRESSION_FLAGS}
REGULAR_EXPRESSION_START      = \/[^ ]


%state NOUN, VERB, NOUN_OR_VERB
%state DOUBLE_QUOTE_STRING, SINGLE_QUOTE_STRING
%state DOUBLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC
%state REGULAR_EXPRESSION, REGULAR_EXPRESSION_FLAG
%state JAVASCRIPT, INTERPOLATION

%%

<YYINITIAL> {
    "case"                      |
    "default"                   |
    "do"                        |
    "function"                  |
    "var"                       |
    "void"                      |
    "with"                      |
    "const"                     |
    "let"                       |
    "enum"                      |
    "export"                    |
    "import"                    |
    "native"                    |
    "__extends"                 |
    "__hasProp"                 |
    "__slice"                   { return CoffeeScriptTokenTypes.RESERVED_WORD; }
    {LINE_TERMINATOR}           { return CoffeeScriptTokenTypes.LINE_TERMINATOR; }
}

<JAVASCRIPT> {
    "`"                         { yybegin(YYINITIAL); return CoffeeScriptTokenTypes.JAVASCRIPT; }
    {CHARACTERS_IN_JAVASCRIPT}  { return CoffeeScriptTokenTypes.JAVASCRIPT; }
}

<VERB, NOUN_OR_VERB> {
    <YYINITIAL,NOUN> "+"        |
    <YYINITIAL,NOUN> "-"        |
    <YYINITIAL,NOUN> "*"        |
    <YYINITIAL,NOUN> "%"        |
    <YYINITIAL,NOUN> "&&"       |
    <YYINITIAL,NOUN> "||"       |
    <YYINITIAL,NOUN> "?"        |
    <YYINITIAL,NOUN> "/"        |
    "++"                        |
    "&"                         |
    "|"                         |
    "--"                        |
    "<"                         |
    ">"                         |
    "^"                         |
    "~"                         |
    "<<"                        |
    ">>"                        |
    ">>>"                       |
    "::"                        |
    <YYINITIAL,NOUN> "!"        |
    <YYINITIAL,NOUN> "!!"       |
    "!="                        |
    "=="                        |
    "<="                        |
    ">="                        |
    ".."                        |
    "..."                       |
    "<-"                        { yybegin(NOUN); return CoffeeScriptTokenTypes.OPERATOR; }
    ")"                         { return CoffeeScriptTokenTypes.PARENTHESIS; }
    "or="                       |
    "="                         |
    ":"                         { yybegin(NOUN); return CoffeeScriptTokenTypes.ASSIGNMENT; }
    "."                         { yybegin(NOUN); return CoffeeScriptTokenTypes.DOT; }
    ","                         { yybegin(NOUN); return CoffeeScriptTokenTypes.COMMA; }
    "then"                      |
    "in"                        { yybegin(NOUN); return CoffeeScriptTokenTypes.KEYWORD; }
    <YYINITIAL> ";"             { yybegin(NOUN); return CoffeeScriptTokenTypes.SEMI_COLON; }
}

<YYINITIAL, NOUN, VERB, NOUN_OR_VERB, INTERPOLATION> {
    "@"                         { yybegin(NOUN); return CoffeeScriptTokenTypes.ACCESSOR; }
    "if"                        |
    "else"                      |
    "unless"                    |
    "and"                       |
    "or"                        |
    "is"                        |
    "isnt"                      |
    "while"                     |
    "not"                       { yybegin(NOUN); return CoffeeScriptTokenTypes.KEYWORD; }
    "for"                       { yybegin(NOUN); return CoffeeScriptTokenTypes.KEYWORD; }
    "("                         { yybegin(NOUN); return CoffeeScriptTokenTypes.PARENTHESIS; }
    "["                         { yybegin(NOUN); return CoffeeScriptTokenTypes.BRACKET; }
    {WS}                        { return CoffeeScriptTokenTypes.WHITESPACE; }
    {LINE_TERMINATOR}           { yybegin(NOUN_OR_VERB); return CoffeeScriptTokenTypes.LINE_TERMINATOR; }
    {BLOCK_COMMENTS}            { return CoffeeScriptTokenTypes.BLOCK_COMMENT; }
    {COMMENT}                   { return CoffeeScriptTokenTypes.COMMENT; }
    "->"                        |
    "=>"                        { yybegin(NOUN); return CoffeeScriptTokenTypes.FUNCTION; }
    "]"                         { yybegin(VERB); return CoffeeScriptTokenTypes.BRACKET; }
    "}"                         {
        if (stack.empty()) {
            yybegin(VERB); return CoffeeScriptTokenTypes.BRACE;
        } else {
            yypopState(); return CoffeeScriptTokenTypes.INTERPOLATION;
        }
    }
}

<YYINITIAL, NOUN, NOUN_OR_VERB, INTERPOLATION> {
    "new"                       |
    "return"                    |
    "try"                       |
    "catch"                     |
    "finally"                   |
    "throw"                     |
    "break"                     |
    "continue"                  |
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
    "when"                      { yybegin(NOUN); return CoffeeScriptTokenTypes.KEYWORD; }
    "this"                      |
    "null"                      { yybegin(VERB); return CoffeeScriptTokenTypes.KEYWORD; }
    "true"                      |
    "false"                     |
    "yes"                       |
    "no"                        |
    "on"                        |
    "off"                       { yybegin(VERB); return CoffeeScriptTokenTypes.BOOLEAN; }
    {IDENTIFIER}                { yybegin(NOUN_OR_VERB); return CoffeeScriptTokenTypes.IDENTIFIER; }
    {NUMBER}                    { yybegin(VERB); return CoffeeScriptTokenTypes.NUMBER; }
    "{"                         { yybegin(NOUN); return CoffeeScriptTokenTypes.BRACE; }
    ")"                         { yybegin(VERB); return CoffeeScriptTokenTypes.PARENTHESIS; }
    \'                          { yybegin(SINGLE_QUOTE_STRING); return CoffeeScriptTokenTypes.SINGLE_QUOTE_STRING; }
    \"                          { yybegin(DOUBLE_QUOTE_STRING); return CoffeeScriptTokenTypes.DOUBLE_QUOTE_STRING; }
    "'''"                       { yybegin(SINGLE_QUOTE_HEREDOC); return CoffeeScriptTokenTypes.SINGLE_QUOTE_HEREDOC; }
    "\"\"\""                    { yybegin(DOUBLE_QUOTE_HEREDOC); return CoffeeScriptTokenTypes.DOUBLE_QUOTE_HEREDOC; }
    "`"                         { yybegin(JAVASCRIPT); return CoffeeScriptTokenTypes.JAVASCRIPT; }

}

<NOUN> {
    "or="                              |
    "="                                { return CoffeeScriptTokenTypes.ASSIGNMENT; }
    {REGULAR_EXPRESSION_START}         { yypushback(1); yybegin(REGULAR_EXPRESSION); return CoffeeScriptTokenTypes.REGULAR_EXPRESSION; }
}

<REGULAR_EXPRESSION> {
    "#{"                               { yypushState(INTERPOLATION); return CoffeeScriptTokenTypes.INTERPOLATION; }
    {CHARACTERS_IN_REGULAR_EXPRESSION} { return CoffeeScriptTokenTypes.REGULAR_EXPRESSION; }
    "\\/"                              { return CoffeeScriptTokenTypes.REGULAR_EXPRESSION_LITERAL; }
    {REGULAR_EXPRESSION_LITERAL}       { return CoffeeScriptTokenTypes.REGULAR_EXPRESSION_LITERAL; }
    {REGULAR_EXPRESSION_TERMINATOR}    {
        final int length = yytext().length();
        if (length > 1) {
            yypushback(length -1);
            yybegin(REGULAR_EXPRESSION_FLAG);
        } else {
            yybegin(VERB);
        }
        return CoffeeScriptTokenTypes.REGULAR_EXPRESSION;
    }
    {LINE_TERMINATOR}                  { yybegin(YYINITIAL); return CoffeeScriptTokenTypes.BAD_CHARACTER; }
}

<REGULAR_EXPRESSION_FLAG> {
    {REGULAR_EXPRESSION_FLAGS}         { yybegin(VERB); return CoffeeScriptTokenTypes.REGULAR_EXPRESSION_FLAG; }
}

<DOUBLE_QUOTE_STRING> {
    "#{"                               { yypushState(INTERPOLATION); return CoffeeScriptTokenTypes.INTERPOLATION; }
    \"                                 { yybegin(VERB); return CoffeeScriptTokenTypes.DOUBLE_QUOTE_STRING; }
    {CHARACTERS_IN_DOUBLE_QUOTES}      { return CoffeeScriptTokenTypes.DOUBLE_QUOTE_STRING; }
}

<SINGLE_QUOTE_STRING> {
    \'                                 { yybegin(VERB); return CoffeeScriptTokenTypes.SINGLE_QUOTE_STRING; }
    {CHARACTERS_IN_SINGLE_QUOTES}      { return CoffeeScriptTokenTypes.SINGLE_QUOTE_STRING; }
}

<DOUBLE_QUOTE_STRING, SINGLE_QUOTE_STRING> {
    "\\n"                              |
    "\\t"                              |
    "\\'"                              |
    "\\\""                             |
    "\\\\"                             { return CoffeeScriptTokenTypes.STRING_LITERAL; }
    "\n"                               |
    "\r"                               { return CoffeeScriptTokenTypes.LINE_TERMINATOR; }
    \\.                                { return CoffeeScriptTokenTypes.BAD_CHARACTER; }
}

<SINGLE_QUOTE_HEREDOC> {
    "'''"                              { yybegin(VERB);  return CoffeeScriptTokenTypes.SINGLE_QUOTE_HEREDOC;}
    {CHARACTERS_IN_HEREDOCS}           { return CoffeeScriptTokenTypes.SINGLE_QUOTE_HEREDOC; }
}

<DOUBLE_QUOTE_HEREDOC> {
    "#{"                               { yypushState(INTERPOLATION); return CoffeeScriptTokenTypes.INTERPOLATION;}
    "\"\"\""                           { yybegin(VERB);  return CoffeeScriptTokenTypes.DOUBLE_QUOTE_HEREDOC;}
    {CHARACTERS_IN_HEREDOCS}           { return CoffeeScriptTokenTypes.DOUBLE_QUOTE_HEREDOC; }
}

<DOUBLE_QUOTE_HEREDOC, SINGLE_QUOTE_HEREDOC> {
    {LINE_TERMINATOR}                  { return CoffeeScriptTokenTypes.LINE_TERMINATOR; }
}

.                                      { yybegin(YYINITIAL);   return CoffeeScriptTokenTypes.BAD_CHARACTER; }