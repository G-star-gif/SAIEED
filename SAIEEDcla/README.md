SAIEEDcla - Android project (Kotlin + Jetpack Compose)
=====================================

What you received
- A ready-to-import Android Studio project (folder: app/)
- Written in Kotlin using Jetpack Compose
- Light design, Arabic + English language toggle
- Stores settings locally (SharedPreferences)

Important note
- This environment cannot build an APK (no Android SDK here).
- You can obtain an APK **without installing Android Studio** by using a cloud CI/build service or by asking someone to run the build for you.
- Below are two recommended options to produce an installable APK (debug or release).

Option A — Build with Codemagic (no local installs)
--------------------------------------------------
1. Create a new Git repository (GitHub/GitLab/Bitbucket) and push the project files.
2. Go to https://codemagic.io and sign up (free tier available).
3. Connect your repository and create a new workflow for Android.
4. Use the default Gradle task: `assembleDebug` or `assembleRelease` (for release you must provide a signing key).
5. Start the build — when finished, download the generated APK from Codemagic UI.

Option B — Build with GitHub Actions (cloud, no Android Studio on your PC)
-------------------------------------------------------------------------
1. Push this project to GitHub.
2. Create a GitHub Actions workflow that runs `./gradlew assembleDebug` on an ubuntu-latest runner.
3. The runner will download required SDK components and build the APK. Example workflows are widely documented online.
4. Download the APK artifact from the Actions run page.

Option C — Build locally without Android Studio (requires SDK/Gradle)
---------------------------------------------------------------------
1. Install Java 11+ and Android SDK command-line tools + Gradle (or use SDKMAN).
2. Run `./gradlew assembleDebug` (you may have to run `gradle wrapper` first to create the wrapper).
3. The debug APK will appear under: app/build/outputs/apk/debug/app-debug.apk

Notes about signing
- Debug APKs are signed with a debug key and can be installed on devices for testing.
- If you want a release APK to distribute, create a keystore and follow Android docs to sign the release build. Codemagic and GitHub Actions both support providing keystores as secure secrets for signing.

Next steps I can do for you (choose any)
- Provide a ready GitHub repo (I can prepare the repo structure and a .gitignore).
- Add a GitHub Actions workflow file in this project that builds the APK automatically.
- Prepare a step-by-step video or screenshots for using Codemagic with this repo.
- Help you tweak UI text, labels, or add an icon/logo you provide.

Thank you — app name set to: SAIEEDcla
