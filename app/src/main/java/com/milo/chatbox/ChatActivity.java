package com.milo.chatbox;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Chat room activity displaying the GUI with all messages...
 * @author Michael Loubier
 * @version 2022-01
 */
public class ChatActivity extends AppCompatActivity {
    private DatabaseReference dbReference;
    private String username;

    /**
     * onCreate life cycle actions. Gets Username from FirebaseUser.
     * @param savedInstanceState bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        if (currentUser.isAnonymous())
            username = "Guest";
        else
            username = currentUser.getDisplayName();

        dbReference = FirebaseDatabase
                .getInstance("https://chatbox-5e03d-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Messages");
        InitializeUI();
    }

    /**
     * Initializes the chat list adapter for displaying chat messages.
     * Initializes the send floating action button with its on click listener.
     */
    private void InitializeUI() {
        ListView listOfMessages = findViewById(R.id.list_of_messages);
        FirebaseListAdapter<MessageObject> adapter = new FirebaseListAdapter<MessageObject>(this,
                MessageObject.class, R.layout.object_message, dbReference) {
            @Override
            protected void populateView(View v, MessageObject message, int position) {
                TextView msgUser = v.findViewById(R.id.message_user);
                TextView msgText = v.findViewById(R.id.message_text);
                TextView msgTime = v.findViewById(R.id.message_time);
                msgText.setText(message.getText());
                msgUser.setText(message.getUser());
                msgTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", message.getTime()));
            }
        };
        listOfMessages.setAdapter(adapter);

        FloatingActionButton sendBtn = findViewById(R.id.fab);
        sendBtn.setOnClickListener(view -> { EditText txtInput = findViewById(R.id.input);
            dbReference.push().setValue(new MessageObject(txtInput.getText().toString(), username));
            txtInput.setText("");
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        });
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
                recreate();
                return true;
            case R.id.menu_profile:
                startActivity(new Intent(ChatActivity.this, ProfileActivity.class));
                return true;
            case R.id.menu_sign_out:
                AuthUI.getInstance().signOut(this).addOnCompleteListener(task -> {
                    Toast.makeText(ChatActivity.this, "Signed out.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChatActivity.this, MainActivity.class));
                });
                return true;
            case R.id.menu_delete:
                AuthUI.getInstance().delete(this).addOnCompleteListener(task -> {
                    Toast.makeText(ChatActivity.this, "Account Deleted.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChatActivity.this, MainActivity.class));
                });
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}