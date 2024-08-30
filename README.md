# Minesweeper

## 專案介紹

就是個踩地雷遊戲，可運行在Android及IOS環境上。

## 使用套件及開發框架

* [**Kotlin Multiplatform**](https://kotlinlang.org/docs/multiplatform.html) - 可用jetpack
  compose開發多平台開發框架。
* [**Decompose**](https://github.com/arkivanov/Decompose) - 簡化compose可以將ui及邏輯代碼分離，並優化了頁面導向及生命週期綁定。
* [**Compottie**](https://github.com/alexzhirkevich/compottie) - compose使用Lottie動畫的引擎。

## 目錄結構

* **androidApp** - Android app專案目錄
* **gradle** - gradle資源
  * **libs.versions.toml** - 引用第三方程式庫資訊
* **iosApp** - Ios app專案目錄
* **shared** - compose共用資源目錄
  * **androidMain** - Android compose用程式目錄
  * **commonMain** - 共用compose程式目錄
    * **composeResources** - compose共用資源
    * **kotlin** - 共用compose程式
  * **iosMain** - Ios compose用程式目錄