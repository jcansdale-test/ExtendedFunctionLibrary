package raystark.eflib.function.recursive;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raystark.eflib.function.P3;

/**
 * 再帰的に定義された型T1, 型T2, 型T3の述語です。
 *
 * <p>このインターフェースは{@link RP3#test(T1, T2, T3, RP3)}を関数メソッドに持つ関数型インターフェースです。
 *
 * <p>このインターフェースは三変数述語{@link P3}を再帰的ラムダ式で定義するためのインターフェースです。
 * このインターフェース自体のインスタンスからは部分適用等の{@link P3}で定義されるdefaultメソッドにはアクセスできません。
 * {@link RP3#of}メソッドを使うことで再帰的ラムダ式から{@link P3}型の関数オブジェクトを作成できます。
 *
 * @param <T1> 第一引数の型
 * @param <T2> 第二引数の型
 * @param <T3> 第三引数の型
 */
@FunctionalInterface
public interface RP3<T1, T2, T3> {

    /**
     * 三変数述語を再帰的に定義します。
     *
     * <p>selfにこの関数自身を渡した場合、三変数述語として引数をこの関数に適用します。
     * selfに自身以外を渡した場合の動作は未定義です。
     *
     * <p>再帰的ラムダ式では{@link TailCall#call}に渡すSupplierの中でselfを参照し、{@link RP3#test(T1, T2, T3)}メソッドを呼び出してください。
     *
     * @param t1 第一引数
     * @param t2 第二引数
     * @param t3 第三引数
     * @param self this参照
     * @return 再帰関数の末尾呼び出し
     * @see RP3#test(T1, T2, T3)
     * @see TailCall#call
     */
    @NotNull
    BooleanTailCall test(@Nullable T1 t1, @Nullable T2 t2, @Nullable T3 t3, @NotNull RP3<T1, T2, T3> self);

    /**
     * 指定された引数でこの述語を評価します。
     *
     * <p>この実装では{@link RP3#test(T1, T2, T3, RP3)}メソッドに引数と関数自身を渡します。
     *
     * @param t1 第一引数
     * @param t2 第二引数
     * @param t3 第三引数
     * @return 再帰関数の末尾呼び出し
     * @see TailCall#call
     */
    @NotNull
    default BooleanTailCall test(@Nullable T1 t1, @Nullable T2 t2, @Nullable T3 t3) {
        return test(t1, t2, t3, this);
    }

    /**
     * 再帰的ラムダ式から関数オブジェクトを生成するファクトリメソッドです。
     *
     * <p>このメソッドは引数に渡された再帰的ラムダ式をP3型に変換して返します。
     * {@link TailCall#evaluate}の実行は{@link P3#test}の実行まで遅延されます。
     *
     * <p>再帰的ラムダ式の型が推論されない場合は次のように明示的に型を指定してください。
     * <pre>{@code
     *  P3<T1, T2, T3> p3 = RP3.<T1, T2, T3>of((t1, t2, t3, self) -> {
     *      if(someCondition) return complete(someValue);
     *      return call(() -> self.apply(modify(t1), modify(t2), modify(t3));
     *  }.and(SomeClass2::someMethod);
     * }</pre>
     *
     * @param rp3 再帰的ラムダ式
     * @param <T1> 第一引数の型
     * @param <T2> 第二引数の型
     * @param <T3> 第三引数の型
     * @return ラムダ式のP3変換
     */
    @NotNull
    static <T1, T2, T3> P3<T1, T2, T3> of(@NotNull RP3<T1, T2, T3> rp3) {
        return (t1, t2, t3) -> rp3.test(t1, t2, t3).evaluate();
    }
}