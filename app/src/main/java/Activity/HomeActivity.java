package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tutorial_v1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;
import android.view.MenuItem;

import Fragment.AccountFragment;
import Fragment.HomeFragment;
import Fragment.CategoriesFragment;
import Fragment.CartFragment;
import Fragment.NotificationFragment;
import model.UserAccount;


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
                fragment = new AccountFragment();
                break;
            case R.id.nav_btn_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_btn_categories:
                fragment = new CategoriesFragment();
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
// ==============================================================================================================
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_screen);
//
//        setUIReference();
//        alertDialog= new SpotsDialog.Builder().setContext(this).build();
//        Retrofit retrofitClient= RetrofitClient.getInstance();
//        iMyService=retrofitClient.create(IMyService.class);
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//        //get user account from sharedPreferences
//
//      /*  getSupportFragmentManager().beginTransaction().replace(R.id.container,new featuredFragment()).commit();
//        suggestionAdapter=new ArrayAdapter<>(HomeScreenActivity.this,R.layout.suggestion);*/
//        homeTB.setVisibility(GONE);
//        Spinner spinner = findViewById(R.id.spinner1);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.numbers, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String text = parent.getItemAtPosition(position).toString();
//                // Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
//                //((TextView) parent.getChildAt(0)).setText("");
//                ((TextView) parent.getChildAt(0)).setTextAppearance(android.R.style.TextAppearance_Material_Widget_Toolbar_Title);
//                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
//                ((TextView) parent.getChildAt(0)).setTextSize(21);
//
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//    BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment fragment =new Fragment();
//
//            switch (item.getItemId())
//            {
//
//              /*  case R.id.feature:
//                    homeTB.setVisibility(GONE);
//                    searchView.setVisibility(GONE);
//                    fragment=new featuredFragment();
//                    break;
//                case R.id.search_frag:
//                    homeTB.setVisibility(View.VISIBLE);
//                    fragment=new searchFragment();
//
//                    spinner.setVisibility(GONE);
//                    homeTB.setTitle("");
//                    homeTB.setVisibility(GONE);
//                    homeTB.setTitleTextColor(-1);
//                    break;
//                case R.id.my_course_frag:
//                    homeTB.setVisibility(View.VISIBLE);
//                    fragment=new mycoursesFragment();
//
//                    homeTB.setTitle("Đã tham gia");
//                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(HomeActivity.this,
//                            R.array.numbers, android.R.layout.simple_spinner_item);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner.setAdapter(adapter);
//
//                    searchView.setVisibility(GONE);
//                    homeTB.setTitleTextColor(-1);
//                    spinner.setVisibility(View.VISIBLE);
//                    break;
//                case R.id.cart_frag:
//                    homeTB.setVisibility(View.VISIBLE);
//                    fragment=new cartFragment();
//                    homeTB.setTitle("Cart");
//                    spinner.setVisibility(GONE);
//                    searchView.setVisibility(GONE);
//                    homeTB.setTitleTextColor(-1);
//                    break;*/
//                case R.id.account_frag:
//                    homeTB.setVisibility(View.VISIBLE);
//                    // Toast.makeText(HomeActivity.this, userAccount.getAva(), Toast.LENGTH_SHORT).show();
//                    fragment=new AccountFragment(userAccount);
//                    homeTB.setTitle("Tài Khoản");
//                    searchView.setVisibility(GONE);
//                    spinner.setVisibility(GONE);
//                    homeTB.setTitleTextColor(-1);
//                    break;
//            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
//            return true;
//        }
//    };
//
//    private void setUIReference() {
//        bottomNav=findViewById(R.id.bottomNav);
//        homeTB=findViewById(R.id.homeTB);
//        searchView=findViewById(R.id.searchView);
//        spinner=findViewById(R.id.spinner1);
//    }

}