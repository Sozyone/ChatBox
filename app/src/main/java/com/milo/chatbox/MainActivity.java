package com.milo.chatbox;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Launcher activity. Let's the user sign in with a pre-built UI from Firebase.
 * No need for a login or a register activity/layout.
 * @author Michael Loubier
 * @version 2022-01
 */
public class MainActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    String username;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(), this::onSignInResult
    );

    /**
     * Makes the user sign in then sends them to the chat.
     * @param savedInstanceState bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null)
            sendToRegister();
        else
            sendToChat();
    }

    /**
     * Sends the user to the chat and welcomes them.
     */
    private void sendToChat() {
        if (currentUser.isAnonymous())
            username = "Guest";
        else
            username = currentUser.getDisplayName();

        Toast.makeText(this, "Welcome " + username, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, ChatActivity.class));
    }

    /**
     * If the user isn't already signed in, sends them to the register UI with different providers.
     */
    public void sendToRegister() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.AnonymousBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build());
        Intent signInIntent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers).setLogo(R.drawable.logo)
                .setTosAndPrivacyPolicyUrls(
                        "https://example.com/terms.html",
                        "https://example.com/privacy.html").build();
        signInLauncher.launch(signInIntent);
    }

    /**
     * Sign in results (success or failure).
     * @param result from authentication.
     */
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        if (result.getResultCode() == RESULT_OK) {
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            assert currentUser != null;
            username = currentUser.getDisplayName();
            UserObject user = new UserObject(currentUser.getUid());
            FirebaseDatabase.getInstance().getReference().child("users").child(username).setValue(user);
            sendToChat();
        } else
            Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
    }
}