# Extended Function Library

## 概要
Extended Function Libraryはjava.util.functionパッケージを基に機能を拡張した関数ライブラリです。本ライブラリ下の関数には以下の機能が実装されています。  

### defaultメソッドの拡張
* 合成関数の作成
* 引数の部分適用
* 引数の順序交換
* 関数の型変換
* ラムダやメソッド参照によるインスタンス生成

### 末尾再帰最適化と再帰ラムダ式
* TailCall, VoidTailCallによる末尾再帰関数の最適化
* 関数をラムダ式で再帰的に定義するためインターフェース

### 非null関数
* アノテーションによりnullを扱わないことが保証された関数