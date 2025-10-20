Files to add to your Android project repository (root)
=====================================================

This ZIP contains helper Gradle files and a Codemagic configuration to help you build the app.
Steps to use:
1. Download and unzip this archive.
2. Upload the contained files to the ROOT of your GitHub repository (where 'app/' and 'settings.gradle' live).
   Files included:
     - gradlew                   (Unix shell script) -- make executable
     - gradlew.bat               (Windows batch script)
     - gradle/wrapper/gradle-wrapper.properties
     - codemagic.yaml            (Codemagic build config)
3. Commit and push to GitHub.
4. On Codemagic, press 'Check for configuration files' then 'Start new build'.
   The codemagic.yaml tries to use ./gradlew if present, otherwise it will attempt to install gradle on the runner.
Notes:
- The gradle-wrapper.jar is NOT included in this archive. If Codemagic requires it, you can create it locally by running 'gradle wrapper' in your project, or let the CI use system-gradle (the codemagic.yaml attempts installation).
- If you prefer, I can also add the full gradle-wrapper.jar to this ZIP (larger file) — tell me and I'll include it.
