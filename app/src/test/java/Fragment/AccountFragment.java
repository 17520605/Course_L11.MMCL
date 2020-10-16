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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import Activity.UserInfoActivity;
import Model.UserAccount;
import de.hdodenhof.circleimageview.CircleImageView;


public class AccountFragment extends Fragment {

    private CircleImageView circleImageView;
    TextView Name;
    String URLDefault="http://149.28.24.98:9000/upload/user_image/";
    UserAccount userAccount;

    public AccountFragment(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        final View rootView= inflater.inflate(R.layout.fragment_account, container, false);
        circleImageView=rootView.findViewById(R.id.accountFrag_user_avatar);
        String avurl=URLDefault+userAccount.getAva();
        Picasso.get().load(avurl).placeholder(R.drawable.account_fragment_useravatar).error(R.drawable.account_fragment_useravatar).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(circleImageView);
        //Cập nhật thông tin cá nhân
        Name=rootView.findViewById(R.id.accountFrag_user_name);
        Name.setText(userAccount.getHoten());
        ImageView account_setting=rootView.findViewById(R.id.account_setting);
        account_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), UserInfoActivity.class);
                intent.putExtra("userAcc", userAccount);
                startActivityForResult(intent,1);
            }
        });
        //Cập nhật ảnh đại diện
        ImageView UpdateAvatar=rootView.findViewById(R.id.accountFrag_user_avatar);
        //Khóa học bạn tạo
        ImageView course_created=rootView.findViewById(R.id.course_created);
        //Khóa học bạn mua
        ImageView course_buy=rootView.findViewById(R.id.course_buy);
        //Cập nhật mật khẩu
        ImageView change_password=rootView.findViewById(R.id.change_password);
        //Lịch sử thanh toán
        ImageView payment_history=rootView.findViewById(R.id.payment_history);
        //Hổ trợ
        ImageView contact_support=rootView.findViewById(R.id.contact_support);
        //Đăng xuất
        ImageView Logout=rootView.findViewById(R.id.log_out_account);


        return rootView;
    }

    private  boolean flag=false;



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            //Update UI from the child-activity.
        }
    }
