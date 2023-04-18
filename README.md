# sample-kmm-library
天気予報を取得するライブラリ

# コマンド
```sh
% ./gradlew publishToMavenLocal 
```
ローカル端末にMavenリポジトリを公開する。
アプリ側のsettings.gradle#dependencyResolutionManagement.repositoriesに`mavenLocal()`を追加する必要がある

```sh
% ./gradlew publishGprPublicationToGitHubPackagesRepository 
```
Github PackagesにMavenリポジトリを公開する。
対象のGithubリポジトリがprivateの場合は、gradle.propertiesもしくは.envにGithubユーザー名とPersonalAccessTokenを設定し、
settings.gradle#dependencyResolutionManagement.repositoriesに個別のmavenリポジトリを定義する必要がある
FYI: https://docs.github.com/ja/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#using-a-published-package
```gradle
// 例
...
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/wataru-taniuchi/sample-kmm-library")
            credentials {
                username = getProperty("gpr.user") ?: System.getenv("USERNAME")
                password = getProperty("gpr.key") ?: System.getenv("TOKEN")
            }
        }
    }
}
```

```sh
% ./gradlew createSwiftPackage 
```
SwiftPackageManager向けにzipファイルを作成する。
SwiftPMとしてXcodeにimportさせるには新しくSwiftPM用のGithubリポジトリを用意する必要がある

# KMM導入検証
##実装までにやったこと
1. Android Studioのインストール
2. Android Studioにプラグインを追加する（https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile）
3. New ProjectでKMM Libraryのプロジェクトを作成する
4. APIの実行処理を実装
5. テストコードを実装し、問題なくAPIが成功することを確認
6. Github PackageにMavenリポジトリを公開
7. Package.swiftを追加しSwiftPMとして公開
8. Androidアプリ側のsettings.gradleにGithubリポジトリURLをMavenリポジトリとして設定
9. app/build.gradleに`implementation 'com.wataru_taniuchi:shared:0.0.2'`を追加する
10. XcodeでGithubアカウントを認証し、SwiftPMとしてGithubリポジトリを追加する


## iOSにおける考慮
- suspend関数はメインスレッドでしか処理できない
- ビルドのキャッシュが残ってアプリクラッシュが起こるためクリーンが大事
- API実行の単体テストがタイムアウトしてしまう
- Ktorで使用されるネットラークライブラリはNSURLSessionが使われる
- ネットワーク越しにライブラリを読み込むにはGithubリポジトリを作る
- コード補完がうまく動作していない
- `./gradlew assembleXCFramework`で.xcframeworkファイルを生成できる
- SwiftPM形式で出力するには一手間必要（ライブラリ入れれば簡単になるが、Xcodeにimportするとエラーが出る）


## Androidにおける考慮
- KMM側で使っているライブラリを、アプリ側にも入れる必要がある（依存ライブラリとしてライブラリ側に設定できる？）
- Ktorで使用されるネットラークライブラリはOKHttpが使われる
- ネットワーク越しにライブラリを読み込むにはGithubリポジトリを作ってGithub Packagesで公開する
- `./gradlew assembleRelease`で.aarファイルを生成できる

## 結論
API疎通を含めた基本的なロジックは、KMMを導入することでiOS/Androidで共通化でき、実装や修正にかかる手間が極端に減らせる。
ただし、端末側の機能を用いるようなロジックには都度検証が必要なことや、言語の最新バージョンなどで新しく追加された最先端すぎる技術などは共通化しづらい。
