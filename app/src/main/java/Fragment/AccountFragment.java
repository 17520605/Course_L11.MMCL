package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tutorial_v1.R;

import Activity.AccountSettingActivity;
import Model.UserAccount;

public class AccountFragment extends Fragment
{
    private View rootView;
    private UserAccount userAccount;

    private TextView Name;


    ImageView accountSetting;
    public AccountFragment(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        setUIReference();
        setUIEventHandles();
        //circleImageView=rootView.findViewById(R.id.accountFrag_user_avatar);
       // String avurl=URLDefault+userAccount.getAva();
        //Picasso.get().load(avurl).placeholder(R.drawable.account_fragment_useravatar).error(R.drawable.account_fragment_useravatar).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(circleImageView);
        //Cập nhật thông tin cá nhân
        Name = rootView.findViewById(R.id.accountFrag_user_name);
        Name.setText(userAccount.getHoten());
//        ImageView account_setting=rootView.findViewById(R.id.account_setting);
//        account_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getContext(), UserInfoActivity.class);
//                intent.putExtra("userAcc", userAccount);
//                startActivityForResult(intent,1);
//            }
//        });
//
        return rootView;
    }

    private  boolean flag=false;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Update UI from the child-activity.
    }

    private void setUIReference() {
        accountSetting = rootView.findViewById(R.id.account_setting);
    }

    private void setUIEventHandles(){
        accountSetting.setOnClickListener(v -> {
            //Toast.makeText(getActivity(), "Lỗi AAAAAAAAAA", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent( getActivity(), AccountSettingActivity.class);
            getActivity().startActivity(intent);
        });
    }
}