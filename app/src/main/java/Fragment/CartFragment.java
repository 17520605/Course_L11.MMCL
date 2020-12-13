package Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial_v1.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.CartItemAdapter;
import Adapter.CourseAdapter;
import Model.Course;
import Retrofit.ICourseService;
import Retrofit.ServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment
{
    private List<Course> model = new ArrayList<Course>();
    private View root;
    private RecyclerView courses;
    private ImageView nothing;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_cart, container, false);
        initUIs();
        Sync();
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void Sync(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String str = pref.getString("idsoncart", "default");
        if(str.compareTo("default") != 0 && !str.isEmpty()){
            String[] ids = str.split(",");
            for (String id: ids) {
                ICourseService service = ServiceClient.getInstance().create(ICourseService.class);
                service.GetCourseById("course/getbyid/" + id)
                        .enqueue(new Callback<Course>() {
                            @Override
                            public void onResponse(Call<Course> call, Response<Course> response) {
                                model.add(response.body());
                                if(response.body().get_id().compareTo(ids[ids.length-1])==0){
                                    Load();
                                }
                            }
                            @Override
                            public void onFailure(Call<Course> call, Throwable t) {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }

    }

    private void initUIs() {
        courses = root.findViewById(R.id.home_cart_courses);
        nothing = root.findViewById(R.id.home_cart_nothing);
    }

    private void Load(){
        if(model.size() == 0){

        }
        else {
            nothing.setVisibility(View.GONE);
            CartItemAdapter adapter = new CartItemAdapter(model);
            courses.setAdapter(adapter);
        }
    }
}

