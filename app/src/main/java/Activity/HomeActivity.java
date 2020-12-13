package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tutorial_v1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import android.os.Bundle;

import org.w3c.dom.Text;

import java.util.List;
import Fragment.SettingFragment;
import Fragment.HomeFragment;
import Fragment.SearchFragment;
import Fragment.CartFragment;
import Fragment.NotificationFragment;
import Model.UserAccount;


public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    public UserAccount userAccount = new UserAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUIReference();
        bottomNav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    private void setUIReference() {
        bottomNav = findViewById(R.id.bottomNav);
    }

    private boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = new Fragment();
        switch (item.getItemId()) {
            case R.id.nav_btn_account:
                fragment = new SettingFragment();
                break;
            case R.id.nav_btn_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_btn_search:
                fragment = new SearchFragment();
                break;
            case R.id.nav_btn_cart:
                fragment = new CartFragment();
                break;
            case R.id.nav_btn_notification:
                fragment = new NotificationFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        return true;
    }
}