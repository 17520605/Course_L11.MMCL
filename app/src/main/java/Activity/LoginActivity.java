package Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;
import com.example.tutorial_v1.R;

import org.json.JSONException;
import org.json.JSONObject;

import Model.UserAccount;
import Retrofit.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {

    private Button login_btn;
    private TextView regisTextView,forgotPassword;
    private EditText email, password;
    private CompositeDisposable compositeDisposable =new CompositeDisposable();
    private IMyService iMyService;
    private String TaiKhoan, MatKhau;
    private AlertDialog alertDialog;
    private UserAccount userAccount;
    private SharedPreferences sharedPreferences;
    private boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_login);

        setUIReference();

        Retrofit retrofitClient= RetrofitClient.getInstance();
        iMyService=retrofitClient.create(IMyService.class);
        alertDialog= new SpotsDialog.Builder().setContext(this).build();

        regisTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckValidInput())
                    Login();
            }
        });
    }

    private void Login() {
        login_btn.setClickable(false);
        login_btn.setEnabled(false);

        try {
            // alertDialog.show();
            iMyService.loginUser(TaiKhoan, MatKhau)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<String>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(Response<String> stringResponse) {
                            if(stringResponse.isSuccessful()){
                                if(stringResponse.body().toString().contains("name"))
                                {
                                    String responseString=stringResponse.body().toString();
                                    try {
                                        JSONObject jo=new JSONObject(responseString);
                                        userAccount=new UserAccount(jo.getString("name"),"",
                                                jo.getString("phone"),
                                                jo.getString("image"),
                                                jo.getString("email"),
                                                stringResponse.headers().get("Auth-token"),
                                                jo.getString("gender"),
                                                jo.getString("description"),
                                                jo.getString("address"),
                                                MatKhau,
                                                jo.getString("_id")
                                        );
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("name",userAccount.getHoten());
                                        editor.putString("phone",userAccount.getSdt());
                                        editor.putString("image",userAccount.getAva());
                                        editor.putString("email",userAccount.getMail());
                                        editor.putString("token",userAccount.getToken());
                                        editor.putString("gender",userAccount.getGioitinh());
                                        editor.putString("description",userAccount.getMota());
                                        editor.putString("address",userAccount.getDiachia());
                                        editor.putString("password",MatKhau);
                                        editor.putString("id",userAccount.getID());
                                        editor.commit();

                                        Toast.makeText(LoginActivity.this, userAccount.getToken(), Toast.LENGTH_LONG).show();

                                        flag=true;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    flag=false;
                                }}
                            else{
                                flag=false;
                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            alertDialog.dismiss();
                                        }
                                    }, 500);
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            login_btn.setClickable(true);
                            login_btn.setEnabled(true);
                        }
                        @Override
                        public void onComplete() {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            alertDialog.dismiss();
                                        }
                                    }, 500);
                            if(flag==true) {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("userAcc", userAccount);
                                intent.putExtra("change",0);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                login_btn.setClickable(true);
                                login_btn.setEnabled(true);
                            }
                        }
                    });
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void setUIReference() {
        login_btn=findViewById(R.id.login_btn);
        regisTextView=findViewById(R.id.regisTextView);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        forgotPassword=findViewById(R.id.forgotPassword);
    }

    private boolean CheckValidInput() {
        Boolean valid = true;
        TaiKhoan=email.getText().toString();
        MatKhau=password.getText().toString();
        if(TaiKhoan.isEmpty() ||TaiKhoan.length() < 6 || TaiKhoan.length() >40 )
        {
            email.setError("Từ 6 đến 40 ký tự");
            valid = false;
        } else {
            email.setError(null);
        }
        if(MatKhau.isEmpty() || MatKhau.length() <8 ||MatKhau.length()>16 )
        {
            password.setError("Mật khẩu có 8 đến 16 ký tự");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }
}