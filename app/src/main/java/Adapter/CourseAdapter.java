package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.tutorial_v1.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import Activity.CourseActivity;
import Activity.ForgotPasswordActivity;
import Activity.LoginActivity;
import Model.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<Course> data;

    public CourseAdapter(List<Course> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.course_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Course course = data.get(position);
        Picasso.get().load("http://149.28.24.98:9000/upload/course_image/" + course.getImage())
                .placeholder(R.drawable.noavatar)
                .error(R.drawable.noavatar)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.image);
        holder.title.setText(course.getName());
        holder.fee.setText(course.getPrice().compareTo("0")  == 0 ? "FREE" : course.getPrice() + " đ" );
//        NumberFormat formatter = new DecimalFormat("#,###");
//        double price=(double)data.get(position).getPrice();
//        String formattedNumber1 = formatter.format(price);
//        if(formattedNumber1.equals("0")) holder.fee.setText("Miễn phí");
//        else
//            holder.fee.setText(formattedNumber1+" đ");
        holder.total.setText(course.getVote().getTotalVote());
        holder.rating.setRating(Float.valueOf(course.getVote().getEVGVote()));
        holder.image.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, CourseActivity.class);
            intent.putExtra("COURSE_ID", course.get_id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public RatingBar rating;
        public TextView total;
        public TextView fee;
        public TextView discount;
        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.course_image);
            this.title = itemView.findViewById(R.id.course_title);
            this.rating = itemView.findViewById(R.id.course_rating);
            this.total = itemView.findViewById(R.id.course_totalVote);
            this.fee = itemView.findViewById(R.id.course_fee);
            this.discount = itemView.findViewById(R.id.course_discount);
        }
    }
}
