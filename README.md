
# RedAlert Android App

## Project Overview

RedAlert is a user-centric Android mobile application designed primarily for health and wellness management, specifically tailored for period tracking, mood and symptom logging, cycle prediction, and personalized wellness tips.

## Technology Stack

- **Language:** Java
- **Development Environment:** Android Studio
- **Database:** Firebase Firestore
- **Authentication:** Firebase Authentication
- **Cloud Storage:** Firebase Storage

## Project Structure

- **app/** - Main source code and resources for the application.
- **gradle/** - Gradle wrapper scripts and configuration.
- **README.md** - Overview and essential project details.
- **google-services.json** - Firebase integration configuration file.

### Source Code Directories
- `src/main/java`: Java source code.
- `src/main/res`: XML layouts and resources.
- `src/androidTest`: Android instrumentation tests.
- `src/test`: Unit tests.

## Key Functionalities

### User Authentication
- Secure login and signup with Firebase Authentication.
- Persistent user sessions and personalized user experience.

### Period Tracking & Predictions
- Allows users to log and manage menstrual cycle data.
- Period duration, start and end dates management.
- Predicts menstrual cycles, ovulation, fertile windows, and luteal phases displayed on a calendar.

### Mood and Symptom Tracker
- Users can log their daily moods and symptoms.
- Data visualization through a calendar highlighting saved mood and symptom dates.

### Wellness Tips
- Daily wellness advice tailored to the user’s current menstrual cycle stage.

### Profile Management
- Stores user-specific data (name, age, cycle length) using SharedPreferences.

## Firebase Integration

### Firebase Authentication
- Manages user sign-up, sign-in, and account security.

### Firebase Firestore
- Stores user-specific period, mood, and symptom data.

### Firebase Storage (optional)
- Used for storing user-uploaded data if implemented.

## Setup and Execution

1. Clone the repository or unzip the provided project.
2. Open the project in Android Studio.
3. Replace `google-services.json` with your Firebase project's configuration file.
4. Sync Gradle files (`File > Sync Project with Gradle Files`).
5. Run the application on an emulator or physical device.

## Testing and Troubleshooting

### Running Tests
- Execute unit tests from the `src/test/java` directory.
- Perform instrumentation tests from the `src/androidTest/java` directory.

### Troubleshooting Common Issues
- Ensure your Firebase setup matches the provided `google-services.json` file.
- Verify Gradle dependencies are properly configured and synced.

## Future Enhancements

- Detailed analytics and reporting.
- Community-driven health advice and forums.
- Multimedia support using Firebase Storage.

## Conclusion

RedAlert provides a comprehensive yet intuitive interface for managing menstrual health, empowering users with precise data, personalized predictions, and valuable wellness information.
