package com.example.redalert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MoodSymptomActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private Spinner moodSpinner, symptomSpinner;
    private Button btnSave, btnCapture;
    private ImageView imagePreview;

    private Bitmap capturedImageBitmap = null;

    private final String[] moods = {"Happy", "Sad", "Irritable", "Tired", "Energetic"};
    private final String[] symptoms = {"Cramps", "Headache", "Bloating", "Acne", "None"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_symptom);

        moodSpinner = findViewById(R.id.moodSpinner);
        symptomSpinner = findViewById(R.id.symptomSpinner);
        btnSave = findViewById(R.id.btnSaveMoodSymptom);
        btnCapture = findViewById(R.id.btnCaptureImage);
        imagePreview = findViewById(R.id.imagePreview);

        moodSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moods));
        symptomSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, symptoms));

        btnCapture.setOnClickListener(v -> requestCameraPermission());

        btnSave.setOnClickListener(v -> saveMoodSymptomToFirestore());
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            launchCamera();
        }
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            capturedImageBitmap = (Bitmap) data.getExtras().get("data");
            imagePreview.setImageBitmap(capturedImageBitmap);
        }
    }

    private void saveMoodSymptomToFirestore() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String mood = moodSpinner.getSelectedItem().toString();
        String symptom = symptomSpinner.getSelectedItem().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("mood", mood);
        data.put("symptom", symptom);
        data.put("timestamp", System.currentTimeMillis());

        if (capturedImageBitmap != null) {
            uploadImageToFirebase(user.getUid(), capturedImageBitmap, data);
        } else {
            saveToFirestore(user.getUid(), data);
        }
    }

    private void uploadImageToFirebase(String uid, Bitmap bitmap, Map<String, Object> data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] imageData = baos.toByteArray();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child("users/" + uid + "/selfies/" + System.currentTimeMillis() + ".jpg");

        UploadTask uploadTask = storageRef.putBytes(imageData);

        uploadTask.addOnSuccessListener(taskSnapshot ->
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    data.put("imageUrl", uri.toString());
                    saveToFirestore(uid, data);
                })
        ).addOnFailureListener(e ->
                Toast.makeText(this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }

    private void saveToFirestore(String uid, Map<String, Object> data) {
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .collection("mood_symptoms")
                .add(data)
                .addOnSuccessListener(docRef -> Toast.makeText(this, "Entry saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            launchCamera();
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
