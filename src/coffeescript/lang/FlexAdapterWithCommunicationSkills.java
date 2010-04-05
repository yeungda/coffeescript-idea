/* Copyright 2010 David Yeung

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package coffeescript.lang;

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
