package Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorial_v1.R;

import Model.ChangeProfileResponeModel;
import Model.User;
import Model.UserAccount;
import Retrofit.IUserService;
import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ActiveAccountActivity extends AppCompatActivity {

    private EditText Email_EditText;
    private Button Verify_Button;

    private UserAccount user;
    private IUserService service;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        initUIs();
        initEventHandles();

    }

    private void initUIs() {

    }

    private void initEventHandles(){

    }

}