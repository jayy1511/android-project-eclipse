# 🚨 RedAlert – Women’s Health Tracking App

RedAlert is a user-friendly Android app designed to empower women by tracking their menstrual cycles, moods, reminders, and more. The app features a clean UI, modern components, and stores personalized data securely using Firebase – just like popular apps such as Flo.

---

## ✅ Features Completed So Far

### 🔐 Authentication
- Implemented Firebase Authentication using Email/Password.
- Users can securely sign up and log in.
- Sessions are persisted and checked on app launch.
- Logout option available.

### 📅 Cycle Calendar View
- Integrated `MaterialCalendarView` for a visually intuitive calendar.
- Users can select their period **start date**.
- Automatically highlights the next 3 days to mark period duration.
- Period tracking information is stored and fetched using Firestore.

### ☁️ Firebase Firestore Integration
- User-specific period data is saved to `users/{uid}/periods` in Firestore.
- When logged in, each user's data is securely loaded.
- Ensures isolation of data between accounts (multi-user setup like Flo).
- Data persists across logins and device sessions.

### 🛠️ Tools & Technologies Used
- Java
- Android Studio
- Firebase Authentication
- Firebase Firestore (NoSQL)
- Volley for API calls
- Material Calendar View (`com.prolificinteractive:material-calendarview:1.4.3`)
- Clean UI with ConstraintLayout

---

## 📥 Getting Started

To run this project locally:

1. **Clone the repo**
   ```bash
   git clone https://github.com/your-username/android-project-eclipse.git
   cd android-project-eclipse
   ```

2. **Open in Android Studio**

3. **Add your `google-services.json`**
   Place it inside the `/app` directory after setting up your Firebase project.

4. **Sync Gradle & Run**
   Make sure all dependencies are downloaded and the project builds successfully.

---

## 🔮 Planned Features (Next Steps)
- Add period **end date** input instead of assuming 3 days
- Mood & Symptom Tracker
- Cycle stage insights (e.g., ovulation, PMS alerts)
- Daily wellness tips based on cycle stage
- Custom reminder messages
- Light/Dark theme switcher

---

## 👩‍💻 Contributors

- **You** – Developer, Firebase Setup, UI/UX
- [Optional: Add team members or links]

---

