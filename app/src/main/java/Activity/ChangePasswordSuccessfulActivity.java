package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorial_v1.R;

public class ChangePasswordSuccessfulActivity extends AppCompatActivity {
    Button backtologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepasswordsuccessful);
        InitUIs();

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePasswordSuccessfulActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void InitUIs() {
        backtologin=findViewById(R.id.changepasswordsuccessfull_backtologin_btn);
    }
}