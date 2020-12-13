package Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tutorial_v1.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import Model.ChangeProfileResponeModel;
import Model.User;
import Model.UserAccount;
import Retrofit.IUserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class AccountSettingActivity extends AppCompatActivity {

    private ImageView Back_Button;

    private ImageView avatar;
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
        user = GetUserAccount();
        initUIs();
        initEventHandles();
        Sync();
    }

    private void initUIs() {
        avatar = findViewById(R.id.accountsetting_avatar_img);
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

        avatar.setOnClickListener(v -> {
            Intent intent=new Intent(AccountSettingActivity.this, UploadAvatarActivity.class);
            startActivity(intent);
        });
    }

    private void Sync(){
        service = new Retrofit.Builder()
                .baseUrl("http://149.28.24.98:9000/") // API base url
                .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format trả về
                .build()
                .create(IUserService.class);

        //==============================get Share references===============================================================
        service.login(user.mail, user.matkhau).enqueue(new Callback<User>() {
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
                    user.ava = response.body().getImage();

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
                        user.token)
                .enqueue(new Callback<ChangeProfileResponeModel>() {
                    @Override
                    public void onResponse(Call<ChangeProfileResponeModel> call, Response<ChangeProfileResponeModel> response) {
                        String message = "NULL";
                        if(response!=null && response.body()!=null) message = response.body().getMessage();
                        String code = Integer.toString(response.code()) ;
                        //Toast.makeText(AccountSettingActivity.this, code, Toast.LENGTH_LONG).show();

                        if(response.isSuccessful()){

                            if(user == null) user = new UserAccount();
                            user.hoten = response.body().getUser().getName();
                            user.sdt = response.body().getUser().getPhone();
                            user.diachia = response.body().getUser().getAddress();
                            user.mota = response.body().getUser().getDescription();
                            user.gioitinh = response.body().getUser().getGender();
                            user.ava = response.body().getUser().getImage();
                            ReloadContent();
                            Toast.makeText(AccountSettingActivity.this, "Thay đổi thành công", Toast.LENGTH_LONG).show();

                            Intent intent=new Intent(AccountSettingActivity.this,HomeActivity.class);
                            startActivity(intent);

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
            Picasso.get().load("http://149.28.24.98:9000/upload/user_image/" + user.ava)
                    .placeholder(R.drawable.noavatar)
                    .error(R.drawable.noavatar)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(avatar);
        }
    }

    private UserAccount GetUserAccount(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return new UserAccount(
                pref.getString("name", "default"),
                "",
                pref.getString("phone", "default"),
                pref.getString("image", "default"),
                pref.getString("email", "default"),
                pref.getString("id", "default"),
                pref.getString("token", "default"),
                pref.getString("gender", "default"),
                pref.getString("description", "default"),
                pref.getString("address", "default"),
                pref.getString("password", "default")
        );
    }
}