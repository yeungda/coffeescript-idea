package yeungda.coffeescript.lang;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import com.intellij.lexer.LexerPosition;

class FlexAdapterWithCommunicationSkills extends FlexAdapter {
    private CharSequence charSequence = "not started";

    public FlexAdapterWithCommunicationSkills(FlexLexer lexer) {
        super(lexer);
    }

    @Override
    public String toString() {
        return "lexer with charsequence: " + charSequence.toString();
    }

    @Override
    public LexerPosition getCurrentPosition() {
        try {
            return super.getCurrentPosition();
        } catch (Error error) {
            throw new Error("Failed for input: " + toString(), error);
        }
    }

    @Override
    public void advance() {
        try {
            super.advance();
        } catch (Error error) {
            throw new Error("Failed for input: " + toString(), error);
        }
//        System.out.printf("[%2d][%2d],%n", getState(), getTokenEnd());
    }

    @Override
    public void start(CharSequence buffer, int startOffset, int endOffset, int initialState) {
        charSequence = buffer;
        super.start(buffer,startOffset, endOffset, initialState);
    }

    @Override
    public void restore(LexerPosition position) {
        System.out.println("restoring position offset [" + position.getOffset() + "] state [" + position.getState() + "]");
        super.restore(position);
    }
}
