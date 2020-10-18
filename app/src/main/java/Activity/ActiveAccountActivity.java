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

    private EditText VerifyCode_EditText;
    private Button Verify_Button;

    private IUserService service;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        initUIs();
        initEventHandles();
    }

    private void initUIs() {
        VerifyCode_EditText = findViewById(R.id.code);
        Verify_Button = findViewById(R.id.login_btn);
    }

    private void initEventHandles(){
        Verify_Button.setOnClickListener(v -> {
            service = new Retrofit.Builder()
                    .baseUrl("http://149.28.24.98:9000/") // API base url
                    .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format trả về
                    .build()
                    .create(IUserService.class);
            if(VerifyCode_EditText != null && VerifyCode_EditText.getText().toString() != ""){
                service .active( "nguyenhuuminhkhai1@gmail.com", VerifyCode_EditText.getText().toString())
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response!=null && response.code() == 200){
                                    Toast.makeText(ActiveAccountActivity.this, "Kích hoạt thành công", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(ActiveAccountActivity.this, "Mã kích hoạt không hợp lệ", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(ActiveAccountActivity.this, "Loi may chu", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

}