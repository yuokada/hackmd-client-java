package io.github.yuokada.hackmd.core;

import java.net.URL;
import java.util.List;

/** Detailed view of a note returned by GET /notes/{noteId}. */
// {
//  "id": "PSHaPUbqQbmNuL_3sKA9DQ",
//  "title": "AWS x lambda",
//  "tags": [
//    "aws",
//    "lambda",
//    "sam",
//    "serverless"
//  ],
//  "createdAt": 1577982358138,
//  "titleUpdatedAt": 1686798210662,
//  "tagsUpdatedAt": 1686798210628,
//  "publishType": "view",
//  "publishedAt": null,
//  "permalink": null,
//  "publishLink": "https://hackmd.io/@uokada/rkA9tqsyL",
//  "shortId": "rkA9tqsyL",
//  "content": "# AWS x lambda\n\n###### tags: `aws`, `lambda`, `sam`, `serverless`\n\n##
// Overview\n\n- [Serverless FrameworkのExampleを見て、サーバーレスの様々な実装を学ぼう ｜
// Developers\\.IO](https://dev.classmethod.jp/server-side/serverless/serverless-framework-example-laean/)\n- [サーバーレスアーキテクチャ再考 \\- ゆううきブログ](https://blog.yuuk.io/entry/2019/rethinking-serverless-architecture)\n\n    - [Lambda パフォーマンスチューニング ｜ Developers\\.IO](https://dev.classmethod.jp/developers-io-cafe/lambda-performance-tuning/)\n- [サーバーレスを始めよう](https://aws.amazon.com/jp/serverless/patterns/start-serverless/)\n- [サーバーレスパターン](https://aws.amazon.com/jp/serverless/patterns/serverless-pattern/)\n\n\n**とりあえず、このドキュメントを読むところから始めよう。**\n[サーバーレスを始めよう](https://aws.amazon.com/jp/serverless/patterns/start-serverless/)\n\n## AWS Lambda公式での紹介\n[サーバーレスアプリケーション開発者用ツール](https://aws.amazon.com/jp/serverless/developer-tools/#Frameworks)  \n[サーバーレスを始めよう](https://aws.amazon.com/jp/serverless/patterns/start-serverless/)  \n\n- SAMは公式のフレームワーク。そのためクラウドまたぎで何かを実装するには向いていない。\n    - PDF: [AWS-Blackbelt_SAM_rev.pdf](chrome-extension://mhjfbmdgcfjbbpaeojofohoefgiehjai/index.html)\n- serverlessはクラウドをまたいでうまくやってくれるツール。\n\n## Serverless Framework\n\n[serverless/serverless: Serverless Framework – Build web, mobile and IoT applications with serverless architectures using AWS Lambda, Azure Functions, Google CloudFunctions & more\\! –](https://github.com/serverless/serverless)\n\n- [Serverless Frameworkの使い方まとめ \\- Qiita](https://qiita.com/horike37/items/b295a91908fcfd4033a2)\n- [Serverless FrameworkのExampleを見て、サーバーレスの様々な実装を学ぼう ｜ Developers\\.IO](https://dev.classmethod.jp/server-side/serverless/serverless-framework-example-laean/)\n- [serverless/examples: Serverless Examples – A collection of boilerplates and examples of serverless architectures built with the Serverless Framework on AWS Lambda, Microsoft Azure, Google Cloud Functions, and more\\.](https://github.com/serverless/examples)\n\n### Install\n\n```\n~ ❯❯❯ nvm install v12.14.0\n~ ❯❯❯ nvm use --delete-prefix v12.14.0\nNow using node v12.14.0 (npm v6.13.4)\n~ ❯❯❯ which npm\n/usr/local/opt/nvm/versions/node/v12.14.0/bin/npm\n~ ❯❯❯ npm install -g serverless\n~ ❯❯❯ serverless -v\nFramework Core: 1.60.4\nPlugin: 3.2.6\nSDK: 2.2.1\nComponents Core: 1.1.2\nComponents CLI: 1.4.0\n```\n\n### Examples on serverless\n\n[serverless/examples: Serverless Examples – A collection of boilerplates and examples of serverless architectures built with the Serverless Framework on AWS Lambda, Microsoft Azure, Google Cloud Functions, and more\\.](https://github.com/serverless/examples)\n\n- [Simple HTTP Endpoint \\| AWS Lambda Function Example in Go](https://serverless.com/examples/aws-golang-simple-http-endpoint/)\n\n```\n$ serverless install -u https://github.com/serverless/examples/tree/master/aws-golang-simple-http-endpoint -n  aws-golang-simple-http-endpoint\n```\n\n- [Simple HTTP Endpoint \\| AWS Lambda Function Example in Python](https://serverless.com/examples/aws-python-simple-http-endpoint/)\n- [REST API With DynamoDB \\| AWS Lambda Function Example in Python](https://serverless.com/examples/aws-python-rest-api-with-dynamodb/)\n- [REST API With PynamoDB \\| AWS Lambda Function Example in Python](https://serverless.com/examples/aws-python-rest-api-with-pynamodb/)\n\n## Repositories\n\n- [awslabs/aws\\-sam\\-cli\\-app\\-templates](https://github.com/awslabs/aws-sam-cli-app-templates)\n- [aws\\-sam\\-java\\-rest/OrderRequestStreamHandler\\.java at master · aws\\-samples/aws\\-sam\\-java\\-rest](https://github.com/aws-samples/aws-sam-java-rest/blob/master/src/main/java/com/amazonaws/handler/OrderRequestStreamHandler.java)\n- [deepakekkati/LambdaS3Event: This lambda is triggered when a file is uploaded into a bucket and copies to another bucket](https://github.com/deepakekkati/LambdaS3Event)\n\n## Firecracker\n\n- [firecracker\\-microvm/firecracker: Secure and fast microVMs for serverless computing\\.](https://github.com/firecracker-microvm/firecracker)\n- [Firecracker – サーバーレスコンピューティングのための軽量な仮想化機能 \\| Amazon Web Services ブログ](https://aws.amazon.com/jp/blogs/news/firecracker-lightweight-virtualization-for-serverless-computing/)\n\n## Provisioned Concurrency\n\n- [LambdaのProvisioned Concurrencyは3rd Partyツールからも既に\\(というかGA時点から\\)使えます \\#reinvent \\| Developers\\.IO](https://dev.classmethod.jp/articles/provisoned-concurrency-can-be-used-3rd-party-tool-now-md/)\n- [\\[速報\\]コールドスタート対策のLambda定期実行とサヨナラ！！ LambdaにProvisioned Concurrencyの設定が追加されました　 \\#reinvent \\| Developers\\.IO](https://dev.classmethod.jp/articles/lambda-support-provisioned-concurrency/)\n\n\n- [lambda provisioned concurrency \\- Twitter検索 / Twitter](https://twitter.com/search?q=lambda%20provisioned%20concurrency&src=typed_query)\n\n\n## スケジューリング機能\n\n- [【AWS】lambdaファンクションを定期的に実行する \\- Qiita](https://qiita.com/Toshinori_Hayashi/items/5b0a72dc64ced91717c0)\n- [Rate または Cron を使用したスケジュール式 \\- AWS Lambda](https://docs.aws.amazon.com/ja_jp/lambda/latest/dg/services-cloudwatchevents-expressions.html)\n\n## Java performance\n\n[AWS LambdaのJavaは遅い？ \\- Qiita](https://qiita.com/moritalous/items/632333088948aad7f8c9)\n\n\n## AWS API Gateway \n\n- [Amazon API Gateway のドキュメント](https://docs.aws.amazon.com/ja_jp/apigateway/?id=docs_gateway)\n- [Amazon API Gateway とは \\- Amazon API Gateway](https://docs.aws.amazon.com/ja_jp/apigateway/latest/developerguide/welcome.html)\n\n## Slides\n\n- [Serverlessの今とこれから / Everything will be Serverless \\- Speaker Deck](https://speakerdeck.com/marcyterui/everything-will-be-serverless)\n- [サーバーレスの基本とCI/CD構築 & 運用 〜システムは動いてからが本番だ〜](https://www.slideshare.net/FujiiGenki/cicd-189075972)\n- [ISPがサーバレスに手を出した / ISP Challenges Serverless \\- Speaker Deck](https://speakerdeck.com/georgeorge/isp-challenges-serverless?slide=57)\n- [Serverless時代のJavaEEコンテナ \\- Quarkus \\- ブログなんだよもん](https://koduki.hatenablog.com/entry/2019/04/15/063038)\n- [Serverless Tech/事例セミナー（2019年3月27日 実施） レポート Vol\\.1 \\| Amazon Web Services ブログ](https://aws.amazon.com/jp/blogs/news/serverless-seminar-report20190327-1/)\n- [Webアプリエンジニアに贈る アプリケーション開発におけるサーバーレス流の考え方 / The concept of serverless in application development \\- Speaker Deck](https://speakerdeck.com/wadayusuke/the-concept-of-serverless-in-application-development)\n- [Ruby on Lambdaで変わる大規模サービスの裏側 / Ruby on Lambda makes a change to Eight's backend \\- Speaker Deck](https://speakerdeck.com/sansanbuildersbox/ruby-on-lambda-makes-a-change-to-eights-backend)\n\n### Monitoring\n\n- [開発者におくるサーバーレスモニタリング](https://www.slideshare.net/AmazonWebServicesJapan/ss-122211649)\n- [ServerlessConf Tokyo2018 サーバーレスなシステムのがんばらない運用監視](https://www.slideshare.net/takanorig/serverlessconf-tokyo2018)\n- [サーバレスアプリケーションの監視・運用 \\- Speaker Deck](https://speakerdeck.com/kazutomo/sabaresuapurikesiyonfalsejian-shi-yun-yong)\n\n## Articles\n\n- [Serverless: 15% slower and 8x more expensive](https://einaregilsson.com/serverless-15-percent-slower-and-eight-times-more-expensive/)\n- [サーバーレスアプリケーションの最も危険なリスク10選 \\- Qiita](https://qiita.com/yuuhu04/items/ad38d6d35d358a90a60f)\n\n\n## Quarkus\n\n- [Serverless時代のJavaEEコンテナ \\- Quarkus \\- ブログなんだよもん](https://koduki.hatenablog.com/entry/2019/04/15/063038)",
//  "lastChangedAt": 1589826104565,
//  "lastChangeUser": {
//    "name": "カントク",
//    "userPath": "uokada",
//    "photo": "https://pbs.twimg.com/profile_images/1615717189/reonald_normal.jpg",
//    "biography": null
//  },
//  "userPath": "uokada",
//  "teamPath": null,
//  "readPermission": "guest",
//  "writePermission": "signed_in"
// }
public record Note(
    String id,
    String title,
    String content,
    List<String> tags,
    long createdAt,
    long titleUpdatedAt,
    long tagsUpdatedAt,
    NotePublishType publishType,
    Long publishedAt,
    URL permalink,
    URL publishLink,
    String shortId,
    long lastChangedAt,
    UserSummary lastChangeUser,
    String userPath,
    String teamPath,
    NotePermissionRole readPermission,
    NotePermissionRole writePermission) {}
