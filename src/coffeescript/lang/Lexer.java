/* The following code was generated by JFlex 1.4.3 on 3/29/10 10:21 PM */

package coffeescript.lang;


import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 3/29/10 10:21 PM from the specification file
 * <tt>/Users/dyeung/Projects/coffeescript/src/coffeescript/lang/lexer.flex</tt>
 */
class Lexer implements FlexLexer {
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int REGULAR_EXPRESSION = 8;
  public static final int VERB = 10;
  public static final int REGULAR_EXPRESSION_FLAG = 12;
  public static final int HEREDOCS = 18;
  public static final int DOUBLE_QUOTE_STRING = 4;
  public static final int SINGLE_QUOTE_STRING = 6;
  public static final int YYINITIAL = 0;
  public static final int NOUN = 2;
  public static final int JAVASCRIPT = 16;
  public static final int NOUN_OR_VERB = 14;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  6,  6,  7,  7, 
     8,  8,  9, 9
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\1\1\20\2\0\1\27\22\0\1\31\1\15\1\24\1\21"+
    "\1\2\1\62\1\63\1\25\1\71\1\66\1\62\1\16\1\67\1\12"+
    "\1\10\1\30\1\4\11\3\1\23\1\70\1\65\1\17\1\22\1\62"+
    "\1\13\6\7\11\2\1\61\7\2\1\6\2\2\1\72\1\26\1\73"+
    "\1\0\1\60\1\35\1\37\1\54\1\36\1\41\1\11\1\42\1\55"+
    "\1\53\1\50\1\2\1\74\1\44\1\56\1\47\1\46\1\57\1\2"+
    "\1\52\1\40\1\45\1\43\1\51\1\14\1\5\1\34\1\2\1\32"+
    "\1\64\1\33\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\6\0\1\1\3\0\1\2\1\3\1\4\2\5\1\4"+
    "\1\6\1\7\1\4\1\6\1\2\1\10\1\11\1\12"+
    "\1\13\1\10\1\14\1\15\1\4\1\16\17\4\2\2"+
    "\1\17\1\20\1\21\1\22\1\23\2\4\1\24\2\25"+
    "\1\6\5\4\1\26\1\27\1\2\1\26\1\30\1\31"+
    "\1\2\1\32\1\33\1\2\3\6\2\34\7\2\3\6"+
    "\1\35\1\36\1\1\2\4\1\37\1\40\2\41\3\0"+
    "\3\4\1\42\2\4\2\0\10\4\1\43\11\4\1\44"+
    "\1\45\1\44\3\4\1\45\1\44\10\4\1\46\3\4"+
    "\1\47\1\50\1\51\1\32\1\6\5\0\1\44\1\0"+
    "\1\44\1\1\1\4\1\44\1\0\2\5\1\0\1\5"+
    "\7\4\1\52\32\4\1\32\3\0\1\1\1\4\1\53"+
    "\13\4\1\54\6\4\1\32\1\0\1\1\12\4\1\32"+
    "\1\0\14\4";

  private static int [] zzUnpackAction() {
    int [] result = new int[253];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\75\0\172\0\267\0\364\0\u0131\0\u016e\0\u01ab"+
    "\0\u01e8\0\u0225\0\u0262\0\u029f\0\u02dc\0\u0319\0\u0356\0\u0393"+
    "\0\u03d0\0\u0262\0\u040d\0\u0262\0\u03d0\0\u044a\0\u0487\0\u0262"+
    "\0\u04c4\0\u0262\0\u0262\0\u0262\0\u0501\0\u0262\0\u053e\0\u057b"+
    "\0\u05b8\0\u05f5\0\u0632\0\u066f\0\u06ac\0\u06e9\0\u0726\0\u0763"+
    "\0\u07a0\0\u07dd\0\u081a\0\u0857\0\u0894\0\u08d1\0\u090e\0\u0262"+
    "\0\u0262\0\u0262\0\u0262\0\u0262\0\u094b\0\u0988\0\u03d0\0\u044a"+
    "\0\u0262\0\u09c5\0\u0a02\0\u0a3f\0\u0a7c\0\u0ab9\0\u0af6\0\u0b33"+
    "\0\u0262\0\u0b70\0\u0bad\0\u0bea\0\u0262\0\u0c27\0\u0c64\0\u0ca1"+
    "\0\u0cde\0\u0d1b\0\u0d58\0\u0d95\0\u0dd2\0\u0e0f\0\u0e4c\0\u0e89"+
    "\0\u0ec6\0\u0f03\0\u0f40\0\u0f7d\0\u0fba\0\u08d1\0\u090e\0\u0ff7"+
    "\0\u0262\0\u0262\0\u1034\0\u1071\0\u10ae\0\u10eb\0\u0262\0\u0262"+
    "\0\u1128\0\u1165\0\u11a2\0\u11df\0\u121c\0\u1259\0\u1296\0\u0262"+
    "\0\u12d3\0\u1310\0\u044a\0\u134d\0\u138a\0\u13c7\0\u1404\0\u1441"+
    "\0\u147e\0\u14bb\0\u14f8\0\u1535\0\u02dc\0\u1572\0\u15af\0\u15ec"+
    "\0\u1629\0\u1666\0\u16a3\0\u16e0\0\u171d\0\u175a\0\u1797\0\u02dc"+
    "\0\u02dc\0\u17d4\0\u1811\0\u184e\0\u188b\0\u18c8\0\u1905\0\u1942"+
    "\0\u197f\0\u19bc\0\u19f9\0\u1a36\0\u1a73\0\u1ab0\0\u0262\0\u1aed"+
    "\0\u1b2a\0\u1b67\0\u0262\0\u0262\0\u0262\0\u1ba4\0\u1be1\0\u1c1e"+
    "\0\u1c5b\0\u0f40\0\u1c98\0\u1cd5\0\u0262\0\u1d12\0\u1d4f\0\u1d8c"+
    "\0\u1dc9\0\u1905\0\u1e06\0\u1e43\0\u1e80\0\u1e80\0\u11df\0\u1ebd"+
    "\0\u1efa\0\u1f37\0\u1f74\0\u1fb1\0\u1fee\0\u202b\0\u0262\0\u2068"+
    "\0\u20a5\0\u20e2\0\u211f\0\u215c\0\u2199\0\u21d6\0\u2213\0\u2250"+
    "\0\u228d\0\u22ca\0\u2307\0\u2344\0\u2381\0\u23be\0\u23fb\0\u2438"+
    "\0\u2475\0\u188b\0\u24b2\0\u24ef\0\u252c\0\u2569\0\u25a6\0\u25e3"+
    "\0\u2620\0\u265d\0\u269a\0\u26d7\0\u2714\0\u2751\0\u278e\0\u0262"+
    "\0\u27cb\0\u2808\0\u2845\0\u2882\0\u28bf\0\u28fc\0\u2939\0\u2976"+
    "\0\u29b3\0\u29f0\0\u2a2d\0\u02dc\0\u2a6a\0\u2aa7\0\u2ae4\0\u2b21"+
    "\0\u2b5e\0\u2b9b\0\u2bd8\0\u2c15\0\u0262\0\u2c52\0\u2c8f\0\u2ccc"+
    "\0\u2d09\0\u2d46\0\u2d83\0\u2dc0\0\u2dfd\0\u2e3a\0\u2e77\0\u0262"+
    "\0\u2eb4\0\u2ef1\0\u2f2e\0\u2f6b\0\u2fa8\0\u2fe5\0\u3022\0\u305f"+
    "\0\u309c\0\u30d9\0\u3116\0\u3153\0\u3190";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[253];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\13\1\14\1\15\1\16\1\17\3\15\1\13\1\20"+
    "\1\21\1\22\1\23\1\13\1\24\1\25\1\26\1\27"+
    "\2\13\1\30\1\31\1\13\1\32\1\24\1\14\1\33"+
    "\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43"+
    "\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53"+
    "\1\15\1\54\3\15\1\55\1\15\1\24\1\56\1\57"+
    "\1\13\1\60\1\13\1\61\1\62\1\63\1\64\1\15"+
    "\1\13\1\14\1\15\1\16\1\17\3\15\1\13\1\65"+
    "\1\21\1\22\1\66\1\13\1\24\1\67\1\70\1\27"+
    "\2\13\1\30\1\31\1\13\1\71\1\72\1\14\1\33"+
    "\1\34\1\35\1\36\1\73\1\40\1\41\1\74\1\75"+
    "\1\44\1\15\1\46\1\47\1\76\1\77\1\15\1\53"+
    "\1\15\1\54\5\15\1\24\1\56\1\57\1\13\1\60"+
    "\2\13\1\62\1\63\1\64\1\15\20\100\1\32\3\100"+
    "\1\101\1\100\1\102\1\32\45\100\20\103\1\32\4\103"+
    "\1\101\1\102\1\32\45\103\20\104\1\105\5\104\1\106"+
    "\1\105\1\107\44\104\1\13\1\14\6\13\1\110\1\111"+
    "\1\112\1\22\1\13\1\113\1\114\1\115\1\70\1\27"+
    "\1\113\1\116\3\13\1\71\1\24\1\14\1\13\1\34"+
    "\3\13\1\117\2\13\1\120\1\121\1\13\1\122\1\123"+
    "\1\124\1\125\11\13\1\24\1\126\1\127\1\130\1\131"+
    "\1\132\1\61\1\62\1\63\1\64\21\13\1\0\13\13"+
    "\1\133\13\13\1\133\4\13\2\133\17\13\1\14\1\15"+
    "\1\16\1\17\3\15\1\110\1\65\1\112\1\22\1\66"+
    "\1\113\1\114\1\115\1\70\1\27\1\113\1\116\1\30"+
    "\1\31\1\13\1\71\1\24\1\14\1\33\1\34\1\35"+
    "\1\36\1\73\1\40\1\41\1\74\1\75\1\44\1\15"+
    "\1\134\1\47\1\76\1\135\1\15\1\53\1\15\1\54"+
    "\5\15\1\24\1\126\1\127\1\130\1\131\1\132\1\61"+
    "\1\62\1\63\1\64\1\15\35\136\1\137\37\136\20\140"+
    "\1\32\4\140\1\141\1\140\1\32\45\140\76\0\1\14"+
    "\17\0\1\27\7\0\1\14\45\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\24\15\12\0\1\15"+
    "\3\0\2\16\3\0\1\142\1\143\66\0\2\16\2\144"+
    "\1\0\1\142\1\143\65\0\3\15\1\145\2\15\1\0"+
    "\1\15\2\0\1\15\17\0\1\15\1\0\6\15\1\146"+
    "\2\15\1\147\12\15\12\0\1\15\22\0\1\150\54\0"+
    "\6\15\1\0\1\15\2\0\1\15\17\0\1\15\1\0"+
    "\12\15\1\151\2\15\1\152\6\15\12\0\1\15\1\0"+
    "\1\153\17\0\1\27\7\0\1\153\43\0\20\27\1\153"+
    "\54\27\25\0\1\154\51\0\6\15\1\0\1\155\2\0"+
    "\1\15\17\0\1\15\1\0\24\15\12\0\1\15\2\0"+
    "\6\15\1\0\1\15\2\0\1\15\17\0\1\15\1\0"+
    "\1\15\1\156\4\15\1\157\1\15\1\160\13\15\12\0"+
    "\1\15\2\0\6\15\1\0\1\15\2\0\1\15\17\0"+
    "\1\15\1\0\11\15\1\161\12\15\12\0\1\15\2\0"+
    "\6\15\1\0\1\15\2\0\1\162\17\0\1\15\1\0"+
    "\5\15\1\163\16\15\12\0\1\15\2\0\6\15\1\0"+
    "\1\164\2\0\1\15\17\0\1\15\1\0\10\15\1\165"+
    "\13\15\12\0\1\15\2\0\6\15\1\0\1\15\2\0"+
    "\1\15\17\0\1\15\1\0\1\15\1\166\3\15\1\167"+
    "\2\15\1\170\1\15\1\171\11\15\12\0\1\15\2\0"+
    "\6\15\1\0\1\15\2\0\1\15\17\0\1\15\1\0"+
    "\11\15\1\172\12\15\12\0\1\15\2\0\6\15\1\0"+
    "\1\173\2\0\1\15\17\0\1\15\1\0\24\15\12\0"+
    "\1\15\2\0\6\15\1\0\1\15\2\0\1\15\17\0"+
    "\1\174\1\0\14\15\1\175\1\176\6\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\4\15\1\177\4\15\1\200\2\15\1\201\7\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\202\2\0\1\15"+
    "\17\0\1\15\1\0\1\15\1\203\3\15\1\204\2\15"+
    "\1\205\13\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\2\15\1\206\1\15"+
    "\1\201\4\15\1\207\6\15\1\210\3\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\1\15\1\211\6\15\1\212\13\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\213\2\0\1\15\17\0\1\15"+
    "\1\0\24\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\201\1\0\14\15\1\214\7\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\22\15\1\215\1\15\12\0\1\15"+
    "\63\0\1\24\75\0\1\24\12\0\3\15\1\216\2\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\6\15"+
    "\1\146\15\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\15\15\1\152\6\15"+
    "\12\0\1\15\31\217\1\0\43\217\2\0\6\15\1\0"+
    "\1\15\2\0\1\15\17\0\1\15\1\0\1\15\1\220"+
    "\4\15\1\157\1\15\1\221\13\15\12\0\1\15\2\0"+
    "\6\15\1\0\1\222\2\0\1\15\17\0\1\15\1\0"+
    "\24\15\12\0\1\15\2\0\6\15\1\0\1\15\2\0"+
    "\1\15\17\0\1\15\1\0\1\15\1\166\6\15\1\170"+
    "\1\15\1\171\11\15\12\0\1\15\2\0\6\15\1\0"+
    "\1\202\2\0\1\15\17\0\1\15\1\0\5\15\1\204"+
    "\2\15\1\205\13\15\12\0\1\15\2\0\6\15\1\0"+
    "\1\15\2\0\1\15\17\0\1\15\1\0\2\15\1\206"+
    "\1\15\1\201\4\15\1\207\12\15\12\0\1\15\20\100"+
    "\1\0\3\100\1\0\1\100\2\0\45\100\20\223\1\0"+
    "\3\223\3\224\16\223\1\224\1\223\1\224\25\223\20\103"+
    "\1\0\4\103\3\0\45\103\20\104\1\0\5\104\3\0"+
    "\44\104\20\225\1\0\54\225\34\0\1\226\13\0\1\226"+
    "\4\0\2\226\26\0\1\227\130\0\1\230\42\0\1\24"+
    "\7\0\1\150\71\0\1\24\73\0\1\24\75\0\1\24"+
    "\2\0\1\150\75\0\1\24\120\0\1\231\73\0\1\232"+
    "\75\0\1\233\100\0\1\234\73\0\1\235\70\0\1\236"+
    "\66\0\1\237\1\0\1\235\4\0\1\235\37\0\1\24"+
    "\4\0\1\24\111\0\1\240\13\0\1\240\4\0\2\240"+
    "\20\0\6\15\1\0\1\15\2\0\1\15\17\0\1\174"+
    "\1\0\14\15\1\175\1\241\6\15\12\0\1\15\2\0"+
    "\6\15\1\0\1\15\2\0\1\15\17\0\1\15\1\0"+
    "\2\15\1\206\1\15\1\201\4\15\1\242\12\15\12\0"+
    "\1\15\35\136\1\0\37\136\25\0\1\243\52\0\2\244"+
    "\73\0\2\245\5\0\1\246\3\0\1\246\61\0\2\247"+
    "\2\0\1\247\1\0\1\247\24\0\2\247\1\0\2\247"+
    "\11\0\1\247\22\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\7\15\1\250\11\15\1\251\2\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\2\15\1\252\21\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\5\15\1\253\16\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\7\15"+
    "\1\254\14\15\12\0\1\15\2\0\6\15\1\0\1\255"+
    "\2\0\1\15\17\0\1\15\1\0\12\15\1\256\11\15"+
    "\12\0\1\15\25\0\1\257\51\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\2\15\1\200\21\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\2\15\1\260\4\15\1\261\14\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\1\15\1\262\22\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\11\15\1\263\12\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\3\15"+
    "\1\201\20\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\12\15\1\220\11\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\21\15\1\264\2\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\4\15\1\265\1\15\1\266\7\15\1\267\5\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\6\15\1\270\15\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\11\15\1\271\12\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\14\15"+
    "\1\201\7\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\11\15\1\272\12\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\6\15\1\273\15\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\7\15\1\165\14\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\21\15"+
    "\1\274\2\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\201\1\0\5\15\1\275\16\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\12\15\1\276\1\15\1\277\7\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\4\15\1\200\17\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\201\17\0\1\15"+
    "\1\0\24\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\7\15\1\300\14\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\6\15\1\301\15\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\7\15\1\201\14\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\11\15"+
    "\1\302\12\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\2\15\1\303\21\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\21\15\1\251\2\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\14\15\1\165\7\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\12\15"+
    "\1\304\11\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\7\15\1\305\14\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\306\2\0\1\15"+
    "\17\0\1\15\1\0\24\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\307\2\0\1\15\17\0\1\15\1\0\15\15"+
    "\1\310\6\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\7\15\1\250\14\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\7\15\1\261\14\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\11\15\1\311\12\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\6\15"+
    "\1\266\15\15\12\0\1\15\34\0\1\312\13\0\1\312"+
    "\4\0\2\312\26\0\1\24\124\0\1\313\75\0\1\235"+
    "\77\0\1\314\41\0\1\315\130\0\1\235\76\0\1\236"+
    "\61\0\1\316\13\0\1\316\4\0\2\316\20\0\6\15"+
    "\1\0\1\317\2\0\1\15\17\0\1\15\1\0\12\15"+
    "\1\276\1\15\1\277\7\15\12\0\1\15\25\0\1\320"+
    "\52\0\2\244\4\0\1\143\66\0\2\245\72\0\6\15"+
    "\1\0\1\321\2\0\1\15\17\0\1\15\1\0\24\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\10\15\1\322\13\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\201\2\0\1\15\17\0\1\15"+
    "\1\0\24\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\20\15\1\165\3\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\15\15\1\165\6\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\11\15\1\201\2\15\1\252\7\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\6\15\1\252\15\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\165\2\0\1\15\17\0\1\15\1\0\24\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\1\323\23\15\12\0\1\15\2\0"+
    "\6\15\1\0\1\15\2\0\1\15\17\0\1\15\1\0"+
    "\2\15\1\324\21\15\12\0\1\15\2\0\6\15\1\0"+
    "\1\15\2\0\1\15\17\0\1\15\1\0\2\15\1\173"+
    "\4\15\1\325\14\15\12\0\1\15\2\0\6\15\1\0"+
    "\1\170\2\0\1\15\17\0\1\15\1\0\24\15\12\0"+
    "\1\15\2\0\6\15\1\0\1\15\2\0\1\15\17\0"+
    "\1\15\1\0\1\15\1\326\22\15\12\0\1\15\2\0"+
    "\6\15\1\0\1\327\2\0\1\15\17\0\1\15\1\0"+
    "\24\15\12\0\1\15\2\0\6\15\1\0\1\15\2\0"+
    "\1\15\17\0\1\15\1\0\5\15\1\330\16\15\12\0"+
    "\1\15\2\0\6\15\1\0\1\15\2\0\1\15\17\0"+
    "\1\15\1\0\2\15\1\275\21\15\12\0\1\15\2\0"+
    "\6\15\1\0\1\15\2\0\1\15\17\0\1\15\1\0"+
    "\1\331\23\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\1\15\1\332\22\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\262\2\0\1\15"+
    "\17\0\1\15\1\0\24\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\333\2\0\1\15\17\0\1\15\1\0\24\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\200\2\0\1\15"+
    "\17\0\1\15\1\0\24\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\2\15"+
    "\1\334\21\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\10\15\1\202\13\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\12\15\1\335\11\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\6\15\1\334\15\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\7\15"+
    "\1\336\14\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\3\15\1\165\20\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\5\15\1\337\16\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\1\15\1\340\22\15\12\0\1\15\2\0\3\15"+
    "\1\341\2\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\24\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\1\15\1\342\22\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\7\15\1\325\14\15\12\0\1\15"+
    "\34\0\1\343\13\0\1\343\4\0\2\343\27\0\1\235"+
    "\74\0\1\344\132\0\1\235\61\0\1\345\13\0\1\345"+
    "\4\0\2\345\20\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\11\15\1\201\12\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\11\15\1\346\12\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\14\15"+
    "\1\173\7\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\15\15\1\201\6\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\2\15\1\201\21\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\12\15\1\347\11\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\5\15"+
    "\1\350\16\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\7\15\1\252\14\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\17\15\1\351\4\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\7\15\1\352\14\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\6\15"+
    "\1\353\15\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\10\15\1\354\13\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\13\15\1\260\10\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\1\15\1\355\22\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\14\15"+
    "\1\317\7\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\24\15\12\0\1\201"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\7\15\1\356\14\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\2\15"+
    "\1\357\21\15\12\0\1\15\34\0\1\360\13\0\1\360"+
    "\4\0\2\360\56\0\1\361\36\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\3\15\1\324\20\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\11\15\1\362\12\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\6\15\1\173\15\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\17\15"+
    "\1\363\4\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\12\15\1\364\11\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\6\15\1\365\15\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\4\15\1\201\17\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\11\15"+
    "\1\366\12\15\12\0\1\15\2\0\6\15\1\0\1\367"+
    "\2\0\1\15\17\0\1\15\1\0\24\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\23\15\1\370\12\0\1\15\40\0\1\235\36\0"+
    "\6\15\1\0\1\15\2\0\1\15\17\0\1\15\1\0"+
    "\5\15\1\252\16\15\12\0\1\15\2\0\6\15\1\0"+
    "\1\211\2\0\1\15\17\0\1\15\1\0\24\15\12\0"+
    "\1\15\2\0\6\15\1\0\1\15\2\0\1\15\17\0"+
    "\1\15\1\0\10\15\1\371\13\15\12\0\1\15\2\0"+
    "\6\15\1\0\1\15\2\0\1\15\17\0\1\201\1\0"+
    "\24\15\12\0\1\15\2\0\6\15\1\0\1\15\2\0"+
    "\1\15\17\0\1\15\1\0\1\274\23\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\11\15\1\372\12\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\14\15"+
    "\1\373\7\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\11\15\1\165\12\15"+
    "\12\0\1\15\2\0\6\15\1\0\1\15\2\0\1\15"+
    "\17\0\1\15\1\0\3\15\1\374\20\15\12\0\1\15"+
    "\2\0\6\15\1\0\1\15\2\0\1\15\17\0\1\15"+
    "\1\0\10\15\1\375\13\15\12\0\1\15\2\0\6\15"+
    "\1\0\1\15\2\0\1\15\17\0\1\15\1\0\2\15"+
    "\1\165\21\15\12\0\1\15\2\0\6\15\1\0\1\15"+
    "\2\0\1\15\17\0\1\15\1\0\21\15\1\165\2\15"+
    "\12\0\1\15";

  private static int [] zzUnpackTrans() {
    int [] result = new int[12749];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  private static final char[] EMPTY_BUFFER = new char[0];
  private static final int YYEOF = -1;
  private static java.io.Reader zzReader = null; // Fake

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\6\0\1\1\3\0\1\11\6\1\1\11\1\1\1\11"+
    "\3\1\1\11\1\1\3\11\1\1\1\11\21\1\5\11"+
    "\4\1\1\11\7\1\1\11\3\1\1\11\23\1\2\11"+
    "\4\1\2\11\1\1\3\0\3\1\1\11\2\1\2\0"+
    "\42\1\1\11\3\1\3\11\2\1\5\0\1\11\1\0"+
    "\4\1\1\0\2\1\1\0\10\1\1\11\33\1\3\0"+
    "\2\1\1\11\23\1\1\0\1\11\12\1\1\11\1\0"+
    "\14\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[253];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** this buffer may contains the current text array to be matched when it is cheap to acquire it */
  private char[] zzBufferArray;

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;


  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 146) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart(){
    return zzStartRead;
  }

  public final int getTokenEnd(){
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end,int initialState){
    zzBuffer = buffer;
    zzBufferArray = com.intellij.util.text.CharArrayUtil.fromSequenceWithoutCopying(buffer);
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzPushbackPos = 0;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
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

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBufferArray != null ? zzBufferArray[zzStartRead+pos]:zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;
    char[] zzBufferArrayL = zzBufferArray;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL.charAt(zzCurrentPosL++);
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL.charAt(zzCurrentPosL++);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 35: 
          { return Tokens.RESERVED_WORD;
          }
        case 45: break;
        case 19: 
          { yybegin(VERB); return Tokens.BRACKET;
          }
        case 46: break;
        case 34: 
          { yybegin(NOUN); return Tokens.FUNCTION;
          }
        case 47: break;
        case 25: 
          { yybegin(YYINITIAL); return Tokens.BAD_CHARACTER;
          }
        case 48: break;
        case 2: 
          { yybegin(YYINITIAL);   return Tokens.BAD_CHARACTER;
          }
        case 49: break;
        case 29: 
          { return Tokens.PARENTHESIS;
          }
        case 50: break;
        case 33: 
          { return Tokens.HEREDOCS;
          }
        case 51: break;
        case 26: 
          { final int length = yytext().length();
        if (length > 1) {
            yypushback(length -1);
            yybegin(REGULAR_EXPRESSION_FLAG);
        } else {
            yybegin(VERB);
        }
        return Tokens.REGULAR_EXPRESSION;
          }
        case 52: break;
        case 42: 
          { yybegin(HEREDOCS); return Tokens.HEREDOCS;
          }
        case 53: break;
        case 24: 
          { return Tokens.REGULAR_EXPRESSION;
          }
        case 54: break;
        case 23: 
          { yybegin(VERB); return Tokens.STRING;
          }
        case 55: break;
        case 14: 
          { yybegin(JAVASCRIPT); return Tokens.JAVASCRIPT;
          }
        case 56: break;
        case 1: 
          { yybegin(VERB); return Tokens.REGULAR_EXPRESSION_FLAG;
          }
        case 57: break;
        case 22: 
          { return Tokens.STRING;
          }
        case 58: break;
        case 43: 
          { yybegin(VERB);  return Tokens.HEREDOCS;
          }
        case 59: break;
        case 39: 
          { return Tokens.BAD_CHARACTER;
          }
        case 60: break;
        case 4: 
          { yybegin(NOUN_OR_VERB); return Tokens.IDENTIFIER;
          }
        case 61: break;
        case 16: 
          { yybegin(NOUN); return Tokens.SEMI_COLON;
          }
        case 62: break;
        case 38: 
          { yypushback(1); yybegin(REGULAR_EXPRESSION); return Tokens.REGULAR_EXPRESSION;
          }
        case 63: break;
        case 12: 
          { yybegin(NOUN); return Tokens.BRACE;
          }
        case 64: break;
        case 8: 
          { return Tokens.LINE_TERMINATOR;
          }
        case 65: break;
        case 28: 
          { yybegin(NOUN); return Tokens.ASSIGNMENT;
          }
        case 66: break;
        case 32: 
          { yybegin(YYINITIAL); return Tokens.JAVASCRIPT;
          }
        case 67: break;
        case 7: 
          { yybegin(NOUN); return Tokens.ACCESSOR;
          }
        case 68: break;
        case 6: 
          { yybegin(NOUN); return Tokens.OPERATOR;
          }
        case 69: break;
        case 15: 
          { yybegin(VERB); return Tokens.PARENTHESIS;
          }
        case 70: break;
        case 9: 
          { return Tokens.COMMENT;
          }
        case 71: break;
        case 27: 
          { yybegin(NOUN); return Tokens.DOT;
          }
        case 72: break;
        case 41: 
          { return Tokens.REGULAR_EXPRESSION_LITERAL;
          }
        case 73: break;
        case 13: 
          { yybegin(VERB); return Tokens.BRACE;
          }
        case 74: break;
        case 37: 
          { yybegin(VERB); return Tokens.BOOLEAN;
          }
        case 75: break;
        case 21: 
          { yybegin(YYINITIAL); return Tokens.LINE_TERMINATOR;
          }
        case 76: break;
        case 20: 
          { return Tokens.ASSIGNMENT;
          }
        case 77: break;
        case 5: 
          { yybegin(VERB); return Tokens.NUMBER;
          }
        case 78: break;
        case 11: 
          { yybegin(SINGLE_QUOTE_STRING); return Tokens.STRING;
          }
        case 79: break;
        case 36: 
          { yybegin(NOUN); return Tokens.KEYWORD;
          }
        case 80: break;
        case 44: 
          { yybegin(VERB); return Tokens.KEYWORD;
          }
        case 81: break;
        case 18: 
          { yybegin(NOUN); return Tokens.BRACKET;
          }
        case 82: break;
        case 30: 
          { yybegin(NOUN); return Tokens.COMMA;
          }
        case 83: break;
        case 40: 
          { return Tokens.STRING_LITERAL;
          }
        case 84: break;
        case 3: 
          { return Tokens.WHITESPACE;
          }
        case 85: break;
        case 17: 
          { yybegin(NOUN); return Tokens.PARENTHESIS;
          }
        case 86: break;
        case 10: 
          { yybegin(DOUBLE_QUOTE_STRING); return Tokens.STRING;
          }
        case 87: break;
        case 31: 
          { return Tokens.JAVASCRIPT;
          }
        case 88: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
            return null;
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
