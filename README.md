"# Pitica - Food Discovery App

Pitica is a local food discovery Android application designed to help users browse, search, and manage food listings. Built with Java and Firebase, the app features a clean UI and efficient data management.

## 🚀 Key Features

* **Real-time Database:** Powered by Firebase Firestore for seamless, real-time updates.
* **Smart Search:** On-device, real-time filtering of food items to find what you want instantly.
* **Efficient Local Storage:** Uses a custom local file system approach to store and retrieve food images, optimizing performance and avoiding cloud storage costs.
* **Shopping Cart:** Intuitive cart management for adding and reviewing items.
* **Modern UI:** Built using Material Design components for a professional look and feel.

## 🛠 Tech Stack

* **Language:** Java
* **Backend:** Firebase Firestore (No-SQL Database)
* **Architecture:** Model-View-Controller (MVC) pattern
* **UI Components:** RecyclerView, MaterialCardView, ShapeableImageView
* **Storage:** Local Internal Storage (for images)

## 📂 Project Structure

* `MainActivity.java`: The main hub of the application, managing the display of food items and the search logic.
* `PostFoodActivity.java`: Handles user input and the local file-saving process for new food entries.
* `FoodAdapter.java`: Custom RecyclerView adapter that handles local image loading and item click events.
* `FoodItem.java`: The data model class used for Firestore synchronization.
* `CartManager.java`: Singleton helper for managing the shopping cart session.

## ⚙️ Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone [your-repo-url]

    Setup Firebase:

        Create a project in the Firebase Console.

        Add your Android app to the project using the package name com.example.pitica.

        Download the google-services.json file and place it in your app/ folder.

        Enable Firestore in your Firebase console.

    Build:

        Open the project in Android Studio.

        Sync your Gradle files.

        Run the app on an emulator or physical device.

💡 Technical Design Choice: Local Image Storage

To ensure high performance and zero-cost scaling during the development phase, Pitica uses local internal storage for images. When a user uploads a photo, the app saves the file to the device's internal memory and stores the absolute file path in Firestore. This allows for instant loading without the overhead of cloud storage SDKs.
