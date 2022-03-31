# spring-petclinic
springbootを使用したサンプルアプリケーション

### 環境
OSはどれでも問題ないと思います。
windowsの場合は、各コマンドはpowershellで実行するようにしてください。

### 必要ソフトウェア
dockerをインストールしている必要があります、

### 前準備
本プロジェクトのルートディレクトリで以下のコマンドを実行して、ビルドに使用するdockerイメージを作成する
```shell
docker build -t spring/petclinic:1.0 .
```
### ビルド
初回
```shell
docker run --name petclinic-build -p 8888:8888 -it -v /$(pwd):/workspaces/mizuide_backend spring/petclinic:1.0 /bin/bash
/bin/bash -c 'cd /workspaces/mizuide_backend;./mvnw package -Dmaven.test.skip=true'
exit
```
2回目以降
```shell
docker start petclinic-build
docker exec -it petclinic-build /bin/bash -c 'cd /workspaces/mizuide_backend;./mvnw package -Dmaven.test.skip=true'
```
実行完了後、target以下にjarファイルが作成される
### 実行
上記ビルドコマンドを実行後に以下のコマンドを実行することで、APIが利用できるようになる。
（フロント側についてはフロント側のREADME.mdを参照してください）
```shell
docker start petclinic-build
docker exec -it petclinic-build /bin/bash -c 'cd /workspaces/mizuide_backend;java -jar target/*.jar'
```

### ビルドを行わずにソースコードの変更を確認する
実行可能jarのビルドには時間がかかるため、開発中の変更を即時反映して確認したい場合は以下のコマンドを実行する。
```shell
docker start petclinic-build
docker exec -it petclinic-build /bin/bash -c 'cd /workspaces/mizuide_backend;./mvnw spring-boot:run'
```
コマンド実行後localhost:8080にアクセスすることで、現在のソースコードを反映したアプリケーションが確認できる。

### CORS認証について
各contotollerについて、CORSを設定しており、デフォルトでは開発環境で動かすことを想定してlocalhost:3000としています。
変更したい場合は[application.properties](src/main/resources/application.properties)を開き`front_fqdn`の値を編集してください。