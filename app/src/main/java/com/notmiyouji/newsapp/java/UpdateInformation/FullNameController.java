package com.notmiyouji.newsapp.java.UpdateInformation;

import static com.notmiyouji.newsapp.java.Retrofit.NewsAPPAPI.getAPIClient;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.notmiyouji.newsapp.R;
import com.notmiyouji.newsapp.kotlin.RetrofitInterface.NewsAPPInterface;
import com.notmiyouji.newsapp.kotlin.SharedSettings.SaveUserLogined;
import com.notmiyouji.newsapp.kotlin.UpdateModel.FullName;

import retrofit2.Call;

public class FullNameController {
    String userId;
    String fullName;
    AppCompatActivity activity;
    NewsAPPInterface newsAPPInterface = getAPIClient().create(NewsAPPInterface.class);

    public FullNameController(String userId, String fullName, AppCompatActivity activity) {
        this.userId = userId;
        this.fullName = fullName;
        this.activity = activity;
    }
    public void updateFullName() {
        //Retrofit call update fullname request
        Call<FullName> call = newsAPPInterface.updateFullNameAccount(userId, fullName);
        call.enqueue(new retrofit2.Callback<FullName>() {
            @Override
            public void onResponse(Call<FullName> call, retrofit2.Response<FullName> response) {
                if (response.isSuccessful()) {
                    FullName fullName = response.body();
                    if (fullName.getStatus().equals("pass")) {
                        //Save fullname to shared preference
                        SaveUserLogined saveUserLogined = new SaveUserLogined(activity);
                        saveUserLogined.saveFullname(fullName.getFullname());
                        Toast.makeText(activity, R.string.fullname_updated, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, R.string.fullname_already_exists, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<FullName> call, Throwable t) {

            }
        });

    }
}