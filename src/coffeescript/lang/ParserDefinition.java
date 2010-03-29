package coffeescript.lang;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.PlainTextFileElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class ParserDefinition implements com.intellij.lang.ParserDefinition {
    private static final IFileElementType FILE_ELEMENT_TYPE = new IFileElementType(FileType.COFFEESCRIPT_LANGUAGE);

    @NotNull
    public com.intellij.lexer.Lexer createLexer(Project project) {
        return new FlexLexerAdapter();
    }

    public PsiParser createParser(Project project) {
        return new PsiParser() {
            @NotNull
            public ASTNode parse(IElementType root, PsiBuilder builder) {
                return new PlainTextFileElement(builder.getOriginalText());
            }
        };
    }

    public IFileElementType getFileNodeType() {
        return FILE_ELEMENT_TYPE;
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return TokenSet.create(Tokens.WHITESPACE);
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return TokenSet.create(Tokens.COMMENT);
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.create(Tokens.STRING_LITERAL);
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return null;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new PsiFileBase(viewProvider, FileType.COFFEESCRIPT_LANGUAGE) {
            @NotNull
            public com.intellij.openapi.fileTypes.FileType getFileType() {
                return FileType.COFFEESCRIPT_FILE_TYPE;
            }
        };
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }
}
