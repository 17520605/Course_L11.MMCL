package Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tutorial_v1.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import Activity.AccountSettingActivity;
import Activity.ActiveAccountActivity;
import Activity.ChangePasswordActivity;
import Activity.LoginActivity;
import Model.User;
import Model.UserAccount;
import Retrofit.IUserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingFragment extends Fragment
{

    private UserAccount user;
    private IUserService service;

    private View rootView;
    private TextView Name;
    private ImageView Active_ImageView;
    private ImageView avatar;
    private ImageView NonActive_ImageView;
    private ImageView logout;
    private ImageView accountSetting;
    private ImageView contract;
    private ImageView changepassword;
    private ImageView courseOfYouInjon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account, container, false);
        user = GetUserAccount();
        initUIs();
        initEventHandles();

        Name = rootView.findViewById(R.id.accountFrag_user_name);

        Sync();

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Update UI from the child-activity.
    }

    private void initUIs() {
        avatar = rootView.findViewById(R.id.accountFrag_user_avatar);
        accountSetting = rootView.findViewById(R.id.account_setting);
        contract = rootView.findViewById(R.id.contact_support);
        Active_ImageView = rootView.findViewById(R.id.active_btn);
        NonActive_ImageView = rootView.findViewById(R.id.nonactive_btn);
        courseOfYouInjon = rootView.findViewById(R.id.course_buy);
        changepassword = rootView.findViewById(R.id.home_account_changepassword_btn);
        logout = rootView.findViewById(R.id.account_account_logout_btn);
    }

    private void initEventHandles(){
        accountSetting.setOnClickListener(v -> {
            Intent intent = new Intent( getActivity(), AccountSettingActivity.class);
            getActivity().startActivity(intent);
        });
        courseOfYouInjon.setOnClickListener(v -> {
            Intent intent = new Intent( getActivity(), AccountSettingActivity.class);
            getActivity().startActivity(intent);
        });
        NonActive_ImageView.setOnClickListener(v -> {
            Intent intent = new Intent( getActivity(), ActiveAccountActivity.class);
            getActivity().startActivity(intent);
        });

        changepassword.setOnClickListener(v -> {
            Intent intent = new Intent( getActivity(), ChangePasswordActivity.class);
            getActivity().startActivity(intent);
        });

        logout.setOnClickListener(v -> {
            SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            pref.edit().clear().commit();
            Intent intent = new Intent( getActivity(), LoginActivity.class);
            getActivity().startActivity(intent);
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
                    user.ava = response.body().getImage();
                    ReloadContent();
                }
                else{
                    Toast.makeText(getActivity(), "Co van khi lay du lieu", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Loi ket noi may chu", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ReloadContent(){
        // reload tat ca UI theo data user
        if(user != null){
            this.Name.setText(user.hoten);
            if(user.token == ""){
                Active_ImageView.setVisibility(View.GONE);
                NonActive_ImageView.setVisibility(View.VISIBLE);
            }
            else {
                Active_ImageView.setVisibility(View.VISIBLE);
                NonActive_ImageView.setVisibility(View.GONE);
            }

            Picasso.get().load("http://149.28.24.98:9000/upload/user_image/" + user.ava)
                    .placeholder(R.drawable.noavatar)
                    .error(R.drawable.noavatar)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(avatar);
        }
    }

    private UserAccount GetUserAccount(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
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

