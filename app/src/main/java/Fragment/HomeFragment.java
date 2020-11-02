package Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.tutorial_v1.R;

import Retrofit.IUserService;
import model.User;
import model.UserAccount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment
{
    private UserAccount user;
    private IUserService service;

    private View rootView;
    private TextView fullname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        user = GetUserAccount();
        initUIs();
        Sync();
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initUIs() {
        fullname = rootView.findViewById(R.id.home_home_fullname_txt);
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
            this.fullname.setText(user.hoten);
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

