package Activity;

import androidx.appcompat.app.AppCompatActivity;

import Model.User;
import Model.UserAccount;
import Retrofit.*;
import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Trace;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutorial_v1.R;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText gender;
    private EditText phone;
    private EditText address;
    private EditText password;
    private EditText email;
    private EditText description;
    private Button sign_up_btn;

    private String Name, Password, Phone, Address, Description, Gender, Email;
    private String response = "AA";
    private IUserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUIReference();

        Retrofit retrofitClient = RetrofitClient.getInstance();
        //service = retrofitClient.create(IRegisterService.class);
        IUserService service = new Retrofit.Builder()
                .baseUrl("http://149.28.24.98:9000/") // API base url
                .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format trả về
                .build()
                .create(IUserService.class);

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidate()){
                    try{
                        service.register(   name.getText().toString(),
                                            password.getText().toString(),
                                            phone.getText().toString(),
                                            address.getText().toString(),
                                            description.getText().toString(),
                                            gender.getText().toString(),
                                            email.getText().toString()
                                )
                                .enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        Toast.makeText(RegisterActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    catch (Exception ex){

                    }

                }
            }
        });
    }

    private void setUIReference() {
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        description = findViewById(R.id.description);
        sign_up_btn = findViewById(R.id.sign_up_btn);
    }

    private boolean isValidate() {
        return true;
    }
}