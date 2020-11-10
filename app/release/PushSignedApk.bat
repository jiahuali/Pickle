java -jar signapk.jar  platform.x509.pem platform.pk8 app-release.apk tmp\SignedTest.apk
adb root
adb remount
adb push tmp\SignedTest.apk /system/app/Fota/SignedTest.apk
pause