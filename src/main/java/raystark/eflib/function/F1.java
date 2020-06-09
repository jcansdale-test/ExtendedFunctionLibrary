package raystark.eflib.function;

import org.jetbrains.annotations.NotNull;
import java.util.function.Function;

/**
 * 型T1から型Rへの一変数関数です。
 *
 * <p>このインターフェースは{@link F1#apply}を関数メソッドに持つ関数型インターフェースです。
 *
 * <p>このインターフェースは{@link Function}に対応します。{@link Function}に変換する場合次のイディオムが使えます。
 *
 * <pre>{@code
 *  F1<String, Integer> f = Integer::parseInt;
 *  Function<String, Integer> f2 = f::apply;
 * }</pre>
 *
 * <p>{@link Function}と等価なメソッドに加えて型を変換するメソッド、ラムダやメソッド参照からインスタンスを作成するメソッドが定義されています。
 *
 * @param <T1> 第一引数の型
 * @param <R> 戻り値の型
 */
@FunctionalInterface
public interface F1<T1, R> {
    /**
     * 一変数関数として、引数をこの関数に適用します。
     *
     * @param t1 第一引数
     * @return 適用結果
     */
    R apply(T1 t1);

    /**
     * 入力をこの関数を適用し、一変数関数としての結果を関数afterに適用する合成関数を返します。
     *
     * <p>いずれかの関数の評価時に例外がスローされた場合、その例外は呼び出し元に中継されます。
     *
     * @param after この関数が適用された後に適用される関数
     * @param <V> 合成関数の出力の型
     * @return 合成関数
     */
    @NotNull
    default <V> F1<T1, V> then1(@NotNull F1<? super R, ? extends V> after) {
        return t1 -> after.apply(this.apply(t1));
    }

    /**
     * 第一引数への入力を関数beforeに適用し、その結果をこの関数の第一引数に適用する合成関数を返します。
     *
     * <p>いずれかの関数の評価時に例外がスローされた場合、その例外は呼び出し元に中継されます。
     *
     * @param before この関数に適用される前に第一引数に適用される関数
     * @param <V1>　合成関数の第一引数の型
     * @return 合成関数
     */
    @NotNull
    default <V1> F1<V1, R> compose1(@NotNull F1<? super V1, ? extends T1> before) {
        return v1 -> apply(before.apply(v1));
    }

    /**
     * 引数をこの関数に適用した結果を生成するSupplierを返します。
     *
     * <p>この関数の評価時に例外がスローされた場合、その例外は呼び出し元に中継されます。
     *
     * @param t1 第一引数
     * @return Supplier
     */
    @NotNull
    default S<R> asS(T1 t1) {
        return () -> apply(t1);
    }

    /**
     * この関数の結果を関数afterが消費するConsumerを返します。
     *
     * <p>この関数の評価時に例外がスローされた場合、その例外は呼び出し元に中継されます。
     *
     * @param after この関数の結果を消費するConsumer
     * @return Consumer
     */
    @NotNull
    default C1<T1> asC1(@NotNull C1<? super R> after) {
        return t1 -> after.accept(apply(t1));
    }

    /**
     * ラムダやメソッド参照から関数オブジェクトを生成するファクトリメソッドです。
     *
     * <p>このメソッドは引数をそのまま返します。オーバーロードされているメソッドを参照する場合は次のようにラムダの引数等で明示的に型を指定してください。
     *
     * <pre>{@code
     *  F1<String, Integer> f1 = F1.of((String s) -> Integer.parseInt(s)).then1(num -> num*2);
     *  F1<String, Integer> f2 = F1.<String, Integer>of(Integer::parseInt).then1(num -> num*2);
     * }</pre>
     *
     * @param f1 ラムダやメソッド参照で記述された関数
     * @param <T1> 第一引数の型
     * @param <R> 戻り値の型
     * @return 引数に渡された関数
     */
    @NotNull
    static <T1, R> F1<T1, R> of(@NotNull F1<T1, R> f1) {
        return f1;
    }
}
