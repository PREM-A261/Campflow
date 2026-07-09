# Campflow 🍽️

Campflow is a native Android food-ordering app built with Java and Firebase. It lets users browse a menu by category, search for dishes, save favorites, manage a cart, check out, and track their order — all backed by Firebase Authentication, Firestore, and Realtime Database.

## Features

- **Browse & Search** — Home screen with banner carousel, category grid, and a searchable food catalog
- **Authentication** — Email/password sign up and login via Firebase Auth
- **Favorites** — Save and manage favorite dishes
- **Cart** — Add items, adjust quantities, and view order totals
- **Checkout & Payment** — Dedicated payment flow with an order confirmation screen
- **Order History & Tracking** — View past orders and track order status step-by-step
- **Profile** — User profile screen with account details and preferences
- **Dark Mode** — Toggleable light/dark theme
- **Navigation Drawer** — Quick access to Home, Orders, Payment Method, Settings, and Sign Out

## Tech Stack

- **Language:** Java
- **Platform:** Android (min SDK 24, target SDK 35, compile SDK 36)
- **UI:** Material Components, ConstraintLayout, RecyclerView, ViewPager2
- **Backend:** Firebase
  - Firebase Authentication
  - Cloud Firestore
  - Firebase Realtime Database
  - Firebase Analytics
- **Build System:** Gradle (Kotlin DSL) with version catalogs (`libs.versions.toml`)

## Project Structure

```
app/src/main/java/com/example/campflow/
├── MainActivity.java              # Home screen: banners, categories, search
├── LoginActivity.java             # User login
├── SignupActivity.java            # User registration
├── ProfileActivity.java           # User profile
├── CartActivity.java              # Shopping cart
├── CartManager.java               # Cart state management
├── CartAdapter.java
├── CategoryItemsActivity.java     # Items within a selected category
├── CategoryAdapter.java
├── ProductDetailActivity.java     # Individual food item detail
├── FavoriteActivity.java          # Saved favorites
├── FavoriteManager.java
├── FavoriteAdapter.java
├── OrderActivity.java             # Order list/history
├── OrderDetailActivity.java       # Single order + tracking
├── OrderAdapter.java
├── Order.java                     # Order model
├── FoodItem.java                  # Food item model
├── Category.java                  # Category model
├── BannerAdapter.java             # Home banner carousel
├── PaymentActivity.java           # Checkout/payment
├── SuccessActivity.java           # Order success screen
└── utils/
    └── SearchManager.java         # Search logic
```

## Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (recent stable version)
- JDK 17
- A [Firebase](https://firebase.google.com/) project

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/Campflow.git
   cd Campflow
   ```

2. **Connect Firebase**
   - Create a project in the [Firebase Console](https://console.firebase.google.com/).
   - Register an Android app with the package name `com.example.cafeteria`.
   - Download the generated `google-services.json` and place it in `app/`.
   - Enable **Authentication** (Email/Password), **Cloud Firestore**, and **Realtime Database** in your Firebase project.

   > ⚠️ A `google-services.json` is included in this repo for development convenience, but you should replace it with your own Firebase project credentials before building — never ship a production app with shared/demo Firebase keys.

3. **Open in Android Studio**
   - Open the project root folder in Android Studio.
   - Let Gradle sync and download dependencies.

4. **Run the app**
   - Select a device or emulator (API 24+).
   - Click **Run** ▶️, or from the command line:
     ```bash
     ./gradlew installDebug
     ```

### Building

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

## Dependencies

Key libraries (see `gradle/libs.versions.toml` for exact versions):

| Library | Purpose |
|---|---|
| AndroidX AppCompat, Material, ConstraintLayout | Core UI components |
| AndroidX RecyclerView | Lists and grids |
| Firebase BoM (Auth, Firestore, Realtime Database, Analytics) | Backend services |

## Testing

- Unit tests: `app/src/test/java/com/example/campflow/`
- Instrumented tests: `app/src/androidTest/java/com/example/campflow/`

Run with:
```bash
./gradlew test               # unit tests
./gradlew connectedAndroidTest # instrumented tests
```

## Contributing

Contributions are welcome! Please open an issue to discuss any major changes before submitting a pull request.

## License

No license has been specified for this project yet. Add a `LICENSE` file to clarify usage terms.
