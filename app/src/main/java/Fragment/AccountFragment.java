package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tutorial_v1.R;

import Activity.AccountSettingActivity;
import Model.User;
import Model.UserAccount;
import Retrofit.IUserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment
{
    private View rootView;
    private UserAccount user = new UserAccount();
    private IUserService service;
    private TextView Name;


    private ImageView accountSetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

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

        accountSetting = rootView.findViewById(R.id.account_setting);
    }

    private void initEventHandles(){
        accountSetting.setOnClickListener(v -> {
            Intent intent = new Intent( getActivity(), AccountSettingActivity.class);
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
        service.login("nguyenngockhai25@gmail.com", "K1234567").enqueue(new Callback<User>() {
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
            this.Name.setText(user.hoten);
        }
    }

}

