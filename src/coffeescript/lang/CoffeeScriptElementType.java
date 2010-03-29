package coffeescript.lang;

class CoffeeScriptElementType extends com.intellij.psi.tree.IElementType {
    public CoffeeScriptElementType(String debugName) {
        super(debugName, FileType.COFFEESCRIPT_LANGUAGE);
    }
}
