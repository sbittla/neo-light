# Neo-Light

## Android Studio Installation and Build Guide

### 🧰 Prerequisites

Before you begin, ensure you have the following installed and set up:

- A computer running **Windows**, **macOS**, or **Linux**
- [Java Development Kit (JDK) 8 or later](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Android Studio](https://developer.android.com/studio)
- Stable internet connection (for downloading SDKs and dependencies)

---

### 📥 Step 1: Download and Install Android Studio

- Download the latest version from the official website:
  👉 [https://developer.android.com/studio](https://developer.android.com/studio)
- Follow the installation instructions for your operating system:

  - [Windows Installation Guide](https://developer.android.com/studio/install#windows)
  - [macOS Installation Guide](https://developer.android.com/studio/install#mac)
  - [Linux Installation Guide](https://developer.android.com/studio/install#linux)

---

### ⚙️ Step 2: Set Up Android Studio

#### Configure Android SDK and AVD

- Launch Android Studio
- It will prompt you to install:
  - **Android SDK**
  - **Android SDK Platform Tools**
  - **Android Emulator**
  - **Build Tools**

You can always manage SDKs later via:
**Preferences / Settings** > **Appearance & Behavior** > **System Settings** > **Android SDK**

---

### 📂 Step 3: Open the Project

1. Open Android Studio
2. Go to **File** > **Open...**
3. Navigate to the root folder of the project (contains `build.gradle`)
4. Click **OK** to open the project

---

### 🔨 Step 4: Build Your Application

- In the top menu, go to **Build** > **Make Project**
- Android Studio will sync and build the project automatically

---

### ▶️ Step 5: Run the App

#### Option A: Using Android Emulator

1. Open **AVD Manager** (📱 icon in the toolbar)
2. Create a virtual device (e.g., Pixel 5 with Android 12)
3. Click the green **Run** ▶️ button

#### Option B: Using a Physical Android Device

1. Enable **Developer Options** and **USB Debugging**
   [Guide for Enabling Developer Mode](https://developer.android.com/studio/debug/dev-options)
2. Connect your phone via USB
3. Click **Run** ▶️ to install and launch the app

---

### 📦 Step 6: Generate a Signed Android App Bundle (AAB)

To release your app on the Play Store:

1. Go to **Build** > **Generate Signed Bundle / APK**
2. Select **Android App Bundle**, then click **Next**
3. Choose the **Release** build variant
4. Provide:

   - **Keystore path**
   - **Keystore password**
   - **Key alias**
   - **Key password**
5. Click **Finish** to generate your `.aab` file

[More on App Signing](https://developer.android.com/studio/publish/app-signing)

---

## 🧱 App Architecture – Neo-Light

### 📁 Project Structure

Neo-Light/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/                  # Kotlin/Java source code
│   │       ├── res/                   # Layouts, drawables, strings, etc.
│   │       ├── assets/                # Static files like PDFs
│   │       └── AndroidManifest.xml    # App manifest
│   └── build.gradle                   # Module-level Gradle config
│
├── build.gradle                       # Project-level

---

### 🔄 Component Flow

```text
+-----------------------------------+
|         AndroidManifest.xml       |
+-----------------------------------+
| - Declares components (Activities,|
|   Services, Permissions)          |
| - Defines main entry point        |
| - Sets app-level configurations   |
+-----------------------------------+
               |
               v
+-----------------------------------+
|            Activity               |
+-----------------------------------+
| - Controls the UI layer           |
| - Handles lifecycle methods       |
| - Captures user interactions      |
+-----------------------------------+
               |
               v
+-----------------------------------+
|          Jetpack Compose          |
+-----------------------------------+
| - Declarative UI toolkit          |
| - Replaces XML layout             |
| - Uses Composable functions       |
| - Fully integrated with Kotlin    |
+-----------------------------------+
               |
               v
+-----------------------------------+
|     Fetching Data from Assets     |
+-----------------------------------+
| - Reads static data (e.g., PDFs)  |
|   from `assets/` directory        |
| - Used for configuration or info  |
+-----------------------------------+
---
```
