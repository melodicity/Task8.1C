package com.example.task81c;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declare views
    TextView tvWelcome;
    EditText etUsername;
    Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Array of valid accounts
        Account[] accounts = {
                new Account("Daniel"),
                new Account("Fred"),
                new Account("admin")
        };

        // Initialise views
        tvWelcome = findViewById(R.id.tvWelcome);
        etUsername = findViewById(R.id.etUsername);
        btnGo = findViewById(R.id.btnGo);

        // Create a SpannableString to apply different font sizes to tvWelcome
        SpannableString spannableString = new SpannableString(getString(R.string.welcome));
        spannableString.setSpan(new AbsoluteSizeSpan(42, true), 0, 8, SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(56, true), 9, 20, SpannableString.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the formatted text to the TextView
        tvWelcome.setText(spannableString);
        
        // Set on click listener for go button
        btnGo.setOnClickListener(v -> {
            // Check username
            String username = etUsername.getText().toString();

            // Validate input exists
            if (username.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                return;
            }

            // Match input with an account
            boolean accountExists = false;
            for (Account account : accounts) {
                if (account.getName().equals(username)) {
                    accountExists = true;
                    break;
                }
            }

            if (accountExists) {
                // Account found, proceed to Chat Activity
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                etUsername.setText("");
            } else {
                // No matching account found
                Toast.makeText(MainActivity.this, "No account found with username: " + username, Toast.LENGTH_SHORT).show();
            }
        });
    }
}