# AutomaticCreatingPluginUpdate

## プラグイン説明
プラグイン開発を楽にする <br>
簡易的なサーバーを起動することにより、1クリックリロードを可能にする

## 使い方
- 前提プラグインをサーバーに入れる
- 本プラグインをサーバーに入れる
- 自作プラグインをサーバーにコピー
- http://localhost:{ポート番号}/plugin?name={プラグイン名}を実行
- マイクラ内に現れる「[プラグインリロード通知] プラグイン名{プラグイン名} [リロード]」のリロードをクリック

## 前提プラグイン
- [PluginManager](https://github.com/Lenni0451/SpigotPluginManager) - プラグインをリロードに使用

## configファイル
- Port - 通信を受け付けるポートを指定する
- ReloadCommand - プラグインをリロードするためのコマンドを指定

## 使用例
[使用例](https://github.com/Ringoame196/KotlinSpigotPluginTemplate/blob/master/build.gradle.kts)のtasks.named("build")の箇所

## 開発環境
- Minecraft Version : 1.20.1
- Kotlin Version : 1.6.10

## プロジェクト情報
- プロジェクトパス : Ringoame196/AutomaticCreatingPluginUpdate.git
- 開発者名 : Ringoame196
- 開発開始日 : 2024-07-20
