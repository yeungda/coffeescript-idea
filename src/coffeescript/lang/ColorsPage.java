package coffeescript.lang;

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
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;

public class ColorsPage implements ColorSettingsPage {
  private static final AttributesDescriptor[] ATTRS = new AttributesDescriptor[] {
      new AttributesDescriptor("Number", coffeescript.lang.SyntaxHighlighter.NUMBER),
      new AttributesDescriptor("Operator", coffeescript.lang.SyntaxHighlighter.OPERATOR),
      new AttributesDescriptor("Assignment", coffeescript.lang.SyntaxHighlighter.ASSIGNMENT),
      new AttributesDescriptor("Comment", coffeescript.lang.SyntaxHighlighter.COMMENT),
      new AttributesDescriptor("Block comment", coffeescript.lang.SyntaxHighlighter.BLOCK_COMMENT),
      new AttributesDescriptor("String", coffeescript.lang.SyntaxHighlighter.STRING),
      new AttributesDescriptor("String literal", coffeescript.lang.SyntaxHighlighter.STRING_LITERAL),
      new AttributesDescriptor("Keyword", coffeescript.lang.SyntaxHighlighter.KEYWORD),
      new AttributesDescriptor("Reserved keyword", coffeescript.lang.SyntaxHighlighter.RESERVED_WORD),
  };

  @NotNull
  public String getDisplayName() {
    return "CoffeeScript";
  }

  public Icon getIcon() {
    return coffeescript.icon.Icons.ICON;
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
  public SyntaxHighlighter getHighlighter() {
    return new coffeescript.lang.SyntaxHighlighter();
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
           "\n"  +
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