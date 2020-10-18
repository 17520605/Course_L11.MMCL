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




public class AccountSettingActivity extends AppCompatActivity {

    private ImageView Back_Button;
    private ImageView Avatar_ImageView;
    private EditText Name_EditText;
    private EditText Phone_EditText;
    private EditText Email_EditText;
    private EditText Address_EditText;
    private EditText Description_EditText;
    private EditText Gender_EditText;
    private Button Change_Button;

    private UserAccount user;
    private IUserService service;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        initUIs();
        initEventHandles();
        Sync();
    }

    private void initUIs() {
        Back_Button = findViewById(R.id.btn_back);
       // Avatar_ImageView = findViewById(R.id.imageView19);
        Name_EditText = findViewById(R.id.input_name);
        Phone_EditText = findViewById(R.id.input_phone);
        Email_EditText = findViewById(R.id.input_email);
        Address_EditText = findViewById(R.id.input_address);
        Description_EditText = findViewById(R.id.input_description);
        Gender_EditText = findViewById(R.id.input_gender);
        Change_Button = findViewById(R.id.update_btn);
        Email_EditText.setEnabled(false);
    }

    private void initEventHandles(){
        Change_Button.setOnClickListener(v -> {
            ChangeInfo();
        });
    }

    private void Sync(){
        service = new Retrofit.Builder()
                .baseUrl("http://149.28.24.98:9000/") // API base url
                .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format trả về
                .build()
                .create(IUserService.class);

        //==============================get Share references===============================================================
        service.login("nguyenngockhai25@gmail.com", "K1234567").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(user == null) user = new UserAccount();

                    user.hoten = response.body().getName();
                    user.sdt = response.body().getPhone();
                    user.mail = response.body().getEmail();
                    user.diachia = response.body().getAddress();
                    user.mota = response.body().getDescription();
                    user.gioitinh = response.body().getGender();

                    ReloadContent();
                }
                else{
                   // Toast.makeText(getActivity(), "Co van khi lay du lieu", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //Toast.makeText(getActivity(), "Loi ket noi may chu", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ChangeInfo(){

        service = new Retrofit.Builder()
                .baseUrl("http://149.28.24.98:9000/") // API base url
                .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format trả về
                .build()
                .create(IUserService.class);

        //==============================get Share references===============================================================
        service.change( Name_EditText.getText().toString(),
                        Phone_EditText.getText().toString(),
                        Address_EditText.getText().toString(),
                        Description_EditText.getText().toString(),
                        Gender_EditText.getText().toString(),
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVmODI4Nzk3YjRlMzgwN2JmNDBmZWM3MiIsInJvbGUiOiJzdHVkZW50IiwiYWN0aXZlIjoxLCJpYXQiOjE2MDMwMzAyNDN9.QKnyZON9N7tr5xhlsvoGC2Bp9SYtpZfaFQ47eaukf48")
                .enqueue(new Callback<ChangeProfileResponeModel>() {
                    @Override
                    public void onResponse(Call<ChangeProfileResponeModel> call, Response<ChangeProfileResponeModel> response) {
                        String message = "NULL";
                        if(response!=null && response.body()!=null) message = response.body().getMessage();
                        String code = Integer.toString(response.code()) ;
                        Toast.makeText(AccountSettingActivity.this, code, Toast.LENGTH_LONG).show();

                        if(response.isSuccessful()){

                            if(user == null) user = new UserAccount();
                            user.hoten = response.body().getUser().getName();
                            user.sdt = response.body().getUser().getPhone();
                            user.diachia = response.body().getUser().getAddress();
                            user.mota = response.body().getUser().getDescription();
                            user.gioitinh = response.body().getUser().getGender();
                            ReloadContent();
                            Toast.makeText(AccountSettingActivity.this, "Thay đổi thành công", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(AccountSettingActivity.this, "That bai", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangeProfileResponeModel> call, Throwable t) {
                        //Toast.makeText(getActivity(), "Loi ket noi may chu", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void ReloadContent(){
        if(user != null){
            Name_EditText.setText(user.hoten);
            Phone_EditText.setText(user.sdt);
            Email_EditText.setText(user.mail);
            Address_EditText.setText(user.diachia);
            Description_EditText.setText(user.mota);
            Gender_EditText.setText(user.gioitinh);
        }
    }
}