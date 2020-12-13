package Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial_v1.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import Activity.CourseActivity;
import Model.Course;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private List<Course> data;

    public CartItemAdapter(List<Course> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.cart_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NumberFormat formatter = new DecimalFormat("#,###");
        final Course course = data.get(position);
        Picasso.get().load("http://149.28.24.98:9000/upload/course_image/" + course.getImage())
                .placeholder(R.drawable.noavatar)
                .error(R.drawable.noavatar)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.image);
                holder.title.setText(course.getName());
//                String newprice = formatter.format(price);
//                coursePrice.setText(newprice+ "đ");
                holder.price.setText(formatter.format(course.getPrice()).compareTo("0")  == 0 ? "FREE" : formatter.format(course.getPrice())+ " đ" );
                holder.image.setOnClickListener(v -> {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CourseActivity.class);
                    intent.putExtra("COURSE_ID", course.get_id());
                    context.startActivity(intent);
                });

                holder.remove.setOnClickListener(v -> {
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(v.getContext().getApplicationContext());
                    String str = pref.getString("idsoncart", "default");
                    if(str.compareTo("default")!= 0){
                        String ids = pref.getString("idsoncart", "default");
                        ids = ids.replace (course.get_id()+",", "");
                        SharedPreferences.Editor editor = pref.edit();
                        editor.remove("idsoncart");
                        editor.putString("idsoncart", ids);
                        editor.commit();
                    }
                    Toast.makeText(v.getContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                    holder.view.setVisibility(View.GONE);
                });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView price;
        public ImageView remove;
        public View view;
        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.image = itemView.findViewById(R.id.home_cart_item_image);
            this.title = itemView.findViewById(R.id.home_cart_item_title);
            this.price = itemView.findViewById(R.id.home_cart_item_price);
            this.remove = itemView.findViewById(R.id.home_cart_item_remove);
        }
    }
}
