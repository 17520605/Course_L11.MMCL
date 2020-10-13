package Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.tutorial_v1.R;

class LoadingActivity {
    private Activity activity;
    private AlertDialog dialog;
    LoadingActivity(Activity myActivity){
        activity = myActivity;
    }
    void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_login,null));
        dialog = builder.create();
        dialog.show();
    }

    void dismissDialog(){
        dialog.dismiss();
    }
}
