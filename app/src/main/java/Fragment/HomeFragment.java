package Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.tutorial_v1.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.CategoryAdapter;
import Adapter.CourseAdapter;
import Model.Category;
import Model.Course;
import Retrofit.IUserService;
import Retrofit.ICategoryService;
import Retrofit.ICourseService;
import Retrofit.ServiceClient;
import Model.User;
import Model.UserAccount;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment
{
    private UserAccount user;
    private IUserService userService;

    private boolean isLoaded1 = false;
    private boolean isLoaded2 = false;
    private boolean isLoaded3 = false;
    private boolean isLoaded4 = false;

    private ImageSlider imageSlider;
    private View rootView;
    private TextView fullname;
    private RecyclerView categories;
    private RecyclerView freecourses;
    private RecyclerView bestcourses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider= rootView.findViewById(R.id.image_slider);
        List<SlideModel> imageList=new ArrayList<>();

        imageList.add(new SlideModel("https://images.unsplash.com/photo-1510915228340-29c85a43dcfe?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80", "“The authority of those who teach is often an obstacle to those who want to learn.”-Marcus Tullius Cicero", true));
        imageList.add(new SlideModel("https://learnworthy.net/wp-content/uploads/2019/12/Why-programming-is-the-skill-you-have-to-learn.jpg","“The ink of the scholar is more holy than the blood of the martyr”― Anonymous, Qurʾan"));
        imageList.add(new SlideModel("https://images.unsplash.com/photo-1510915361894-db8b60106cb1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80", "“The more that you read, the more things you will know. The more that you learn, the more places you’ll go.” – Dr.  Seus"));

        imageSlider.setImageList(imageList,true);
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
        categories = rootView.findViewById(R.id.home_categories_rcv);
        freecourses = rootView.findViewById(R.id.home_freecourses_rcv);
        bestcourses = rootView.findViewById(R.id.home_bestcourses_rcv);
    }

    private void Sync(){
        AlertDialog alertDialog;
        alertDialog= new SpotsDialog.Builder().setContext(this.getActivity()).build();
        //alertDialog.setTitle("Load...");
        alertDialog.show();
        userService = new Retrofit.Builder()
                .baseUrl("http://149.28.24.98:9000/") // API base url
                .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format trả về
                .build()
                .create(IUserService.class);
        //==============================get Share references===============================================================
        userService.login(user.mail, user.matkhau).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(user == null) user = new UserAccount();
                    user.hoten = response.body().getName();
                    ReloadContent();
                    isLoaded1 = true;
                    if(isLoaded1 && isLoaded2 && isLoaded3 && isLoaded4){
                        alertDialog.dismiss();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Co van khi lay du lieu", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                isLoaded1 = true;
                if(isLoaded1 && isLoaded2 && isLoaded3 && isLoaded4){
                    alertDialog.dismiss();
                }
                Toast.makeText(getActivity(), "Loi ket noi may chu", Toast.LENGTH_LONG).show();
            }
        });

        ICategoryService categoryService = ServiceClient.getInstance().create(ICategoryService.class);
        categoryService.GetAllCategories("category/get-all-category")
                       .enqueue(new Callback<List<Category>>() {
                           @Override
                           public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                               Toast.makeText(getActivity(), String.valueOf(response.body().size()), Toast.LENGTH_LONG).show();
                               CategoryAdapter adapter = new CategoryAdapter(response.body());
                               //categories.setHasFixedSize(true);
                               categories.setAdapter(adapter);
                               isLoaded2 = true;
                               if(isLoaded1 && isLoaded2 && isLoaded3 && isLoaded4){
                                   alertDialog.dismiss();
                               }
                           }
                           @Override
                           public void onFailure(Call<List<Category>> call, Throwable t) {
                               isLoaded2 = true;
                               if(isLoaded1 && isLoaded2 && isLoaded3 && isLoaded4){
                                   alertDialog.dismiss();
                               }
                               Toast.makeText(getActivity(), "Error. Load categories failed", Toast.LENGTH_LONG).show();
                           }
                       });
        ICourseService courseService = ServiceClient.getInstance().create(ICourseService.class);
        courseService.GetFreeCourses()
                .enqueue(new Callback<List<Course>>() {
                    @Override
                    public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                        CourseAdapter adapter = new CourseAdapter(response.body());
                        categories.setHasFixedSize(true);
                        freecourses.setAdapter(adapter);
                        isLoaded3 = true;
                        if(isLoaded1 && isLoaded2 && isLoaded3 && isLoaded4){
                            alertDialog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Course>> call, Throwable t) {
                        isLoaded3 = true;
                        if(isLoaded1 && isLoaded2 && isLoaded3 && isLoaded4){
                            alertDialog.dismiss();
                        }
                        Toast.makeText(getActivity(), "Error. Load categories failed", Toast.LENGTH_LONG).show();
                    }
                });

        courseService.GetTopCourses()
                .enqueue(new Callback<List<Course>>() {
                    @Override
                    public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                        CourseAdapter adapter = new CourseAdapter(response.body());
                        categories.setHasFixedSize(true);
                        bestcourses.setAdapter(adapter);
                        isLoaded4 = true;
                        if(isLoaded1 && isLoaded2 && isLoaded3 && isLoaded4){
                            alertDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Course>> call, Throwable t) {
                        isLoaded4 = true;
                        if(isLoaded1 && isLoaded2 && isLoaded3 && isLoaded4){
                            alertDialog.dismiss();
                        }
                        Toast.makeText(getActivity(), "Error. Load categories failed", Toast.LENGTH_LONG).show();
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

