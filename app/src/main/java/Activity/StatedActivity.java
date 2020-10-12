package Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tutorial_v1.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StatedActivity extends AppCompatActivity {
    Button get_stated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_stated = (Button) findViewById(R.id.get_stated);
        get_stated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
    }
    public void openNewActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}