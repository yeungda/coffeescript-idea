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

import com.intellij.ide.highlighter.HtmlFileHighlighter;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.editor.XmlHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.OptionsBundle;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.coffeescript.CoffeeScriptIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;

public class CoffeeScriptColorsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] ATTRS = new AttributesDescriptor[]{
        new AttributesDescriptor("Number", CoffeeScriptSyntaxHighlighter.NUMBER),
        new AttributesDescriptor("Operator", CoffeeScriptSyntaxHighlighter.OPERATOR),
        new AttributesDescriptor("Assignment", CoffeeScriptSyntaxHighlighter.ASSIGNMENT),
        new AttributesDescriptor("Comment", CoffeeScriptSyntaxHighlighter.COMMENT),
        new AttributesDescriptor("Block comment", CoffeeScriptSyntaxHighlighter.BLOCK_COMMENT),
        new AttributesDescriptor("String", CoffeeScriptSyntaxHighlighter.STRING),
        new AttributesDescriptor("String literal", CoffeeScriptSyntaxHighlighter.STRING_LITERAL),
        new AttributesDescriptor("Keyword", CoffeeScriptSyntaxHighlighter.KEYWORD),
        new AttributesDescriptor("Reserved keyword", CoffeeScriptSyntaxHighlighter.RESERVED_WORD),
    };

    @NotNull
    public String getDisplayName() {
        return "CoffeeScript";
    }

    public Icon getIcon() {
        return CoffeeScriptIcons.ICON;
    }

    @NotNull
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRS;
    }

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    public CoffeeScriptSyntaxHighlighter getHighlighter() {
        return new CoffeeScriptSyntaxHighlighter();
    }

    @NotNull
    public String getDemoText() {
        return "###\n" +
                "Some examples\n" +
                "###\n" +
                "class Animal\n" +
                "  constructor: (@name) -> \n" +
                "  move: (meters) -> alert @name + \" moved \" + meters + \"m.\"\n" +
                "\n" +
                "class Snake extends Animal\n" +
                "  move: -> \n" +
                "    alert \"Slithering...\"\n" +
                "    super 5\n" +
                "\n" +
                "# Assignment:\n" +
                "number   = 42\n" +
                "opposite = true\n" +
                "\n" +
                "# Conditions:\n" +
                "number = -42 if opposite\n" +
                "\n" +
                "# Functions:\n" +
                "square = (x) -> x * x\n" +
                "\n" +
                "# Arrays:\n" +
                "list = [1, 2, 3, 4, 5]\n" +
                "\n" +
                "# Objects:\n" +
                "math =\n" +
                "  root:   Math.sqrt\n" +
                "  square: square\n" +
                "  cube:   (x) -> x * square x\n" +
                "\n" +
                "# Splats:\n" +
                "race = (winner, runners...) ->\n" +
                "  print winner, runners\n" +
                "\n" +
                "# Existence:\n" +
                "alert \"I knew it!\" if elvis?\n" +
                "\n" +
                "# Array comprehensions:\n" +
                "cubes = math.cube num for num in list";
    }

    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }
}