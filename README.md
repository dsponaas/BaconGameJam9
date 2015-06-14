# BaconGameJam9

Build Jar
./gradlew desktop:dist

Mac Build
java -jar packr.jar -platform mac -jdk "https://bitbucket.org/alexkasko/openjdk-unofficial-builds/downloads/openjdk-1.7.0-u80-unofficial-macosx-x86_64-bundle.zip" -executable DepthChargeGame -appjar desktop-1.0.jar -mainclass "com/grizbenzis/bgj9/desktop/DesktopLauncher" -minimizejre "soft" -vmargs "-Xmx1G" -outdir out

Windows Build

Linux Build
