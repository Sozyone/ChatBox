package com.milo.chatbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User's profile page activity displaying different information and settings.
 * @author Michael Loubier
 * @version 2022-01
 */
public class ProfileActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private TextView usernameTxt, emailTxt;
    private ImageView profilePic;
    private String username, email, userID;
    private Bitmap bitmap;
    private final int PICK_IMAGE = 100;

    /**
     * onCreate life cycle actions. Gets Username from FirebaseUser.
     * @param savedInstanceState bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final long ONE_MEGABYTE = 1024 * 1024;
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        assert currentUser != null;
        userID = currentUser.getUid();
        if (currentUser.isAnonymous()) {
            username = "Guest";
            email = "guest@mail.com";
        } else {
            username = currentUser.getDisplayName();
            email = currentUser.getEmail();
        }

        StorageReference profilePicRef = FirebaseStorage.getInstance().getReference()
                .child("users").child(username).child("profilePic");

        profilePicRef.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(bytes -> Glide.with(ProfileActivity.this)
                        .load(bytes).into(profilePic))
                .addOnFailureListener(exception -> Glide.with(ProfileActivity.this)
                        .load(R.drawable.logo).into(profilePic));
        InitializeUI();
    }

    /**
     * Initializes the UI showing views and manages onClick listeners.
     */
    private void InitializeUI() {
        profilePic = findViewById(R.id.profilePic);
        profilePic.setOnClickListener(view -> selectImage());
        usernameTxt = findViewById(R.id.user_username);
        usernameTxt.setText(username);
        emailTxt = findViewById(R.id.user_email);
        emailTxt.setText(email);

        RadioGroup radioGroup = findViewById(R.id.RGgroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.lightRadioBtn)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            if (checkedId == R.id.darkRadioBtn)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        });

        Button updateProfileBtn = findViewById(R.id.updateProfileBtn);
        updateProfileBtn.setOnClickListener(view -> updateProfile());
    }

    private void selectImage() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(imageUri).build();

            currentUser.updateProfile(profileChangeRequest).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                    Glide.with(this).load(imageUri).into(profilePic);
            });

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                Toast.makeText(this, "Profile picture updated!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] picData = bytes.toByteArray();

            FirebaseStorage.getInstance().getReference().child("users").child(username)
                    .child("profilePic").putBytes(picData).addOnSuccessListener(taskSnapshot -> {
                        String url = taskSnapshot.getStorage().getDownloadUrl().toString();
                        UserObject user = new UserObject(userID, url);
                        FirebaseDatabase.getInstance().getReference().child("users").child(username).setValue(user);
            });
        }
    }

    /**
     * Update profile button actions. Pops a dialog window up and asks for new username/password.
     */
    public void updateProfile() {
        AlertDialog.Builder updateDialog = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText newUsername = new EditText(this);
        newUsername.setHint("New username");
        layout.addView(newUsername);

        final EditText newEmail = new EditText(this);
        newEmail.setHint("New email");
        layout.addView(newEmail);

        updateDialog.setView(layout);
        updateDialog.setIcon(R.drawable.profile);
        updateDialog.setTitle("Update profile");
        updateDialog.setPositiveButton("Ok", (dialog, whichButton) -> {
            if (!newUsername.getText().toString().isEmpty()) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newUsername.getText().toString()).build();
                currentUser.updateProfile(profileUpdates);
                Toast.makeText(this,"Username updated",Toast.LENGTH_SHORT).show();
                usernameTxt.setText(newUsername.getText().toString());
            }
            if (!newEmail.getText().toString().isEmpty()) {
                currentUser.updateEmail(newEmail.getText().toString());
                Toast.makeText(this,"Email updated",Toast.LENGTH_SHORT).show();
                emailTxt.setText(newEmail.getText().toString());
            }
            if (newEmail.getText().toString().isEmpty() && newUsername.getText().toString().isEmpty())
                Toast.makeText(this,"Profile NOT updated",Toast.LENGTH_SHORT).show();
        });
        updateDialog.setNegativeButton("Cancel", (dialog, whichButton) ->
                Toast.makeText(this,"Profile NOT updated",Toast.LENGTH_SHORT).show());
        updateDialog.show();
    }

    /**
     * Create the user menu.
     * @param menu of options.
     * @return true.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Menu option actions. Sign out and delete account.
     * @param item clicked.
     * @return true.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chat:
                startActivity(new Intent(ProfileActivity.this, ChatActivity.class));
                return true;
            case R.id.menu_profile:
                recreate();
                return true;
            case R.id.menu_sign_out:
                AuthUI.getInstance().signOut(this).addOnCompleteListener(task -> {
                    Toast.makeText(ProfileActivity.this, "Signed out.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                });
                return true;
            case R.id.menu_delete:
                AuthUI.getInstance().delete(this).addOnCompleteListener(task -> {
                    Toast.makeText(ProfileActivity.this, "Account Deleted.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                });
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
