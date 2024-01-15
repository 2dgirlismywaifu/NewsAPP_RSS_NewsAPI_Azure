/*
 * Copyright By @2dgirlismywaifu (2023) .
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.notmiyouji.newsapp.java.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.notmiyouji.newsapp.R;
import com.notmiyouji.newsapp.java.activity.MaterialAltertLoading;
import com.notmiyouji.newsapp.java.category.RssUrlCategory;

import java.util.HashMap;
import java.util.Map;

public class NewsTypeAdapter extends RecyclerView.Adapter<NewsTypeAdapter.NewsTypeHolder> {

    private final AppCompatActivity activity;
    private final String userId;

    public NewsTypeAdapter(AppCompatActivity activity, String userId) {
        this.activity = activity;
        this.userId = userId;
    }

    @NonNull
    @Override
    public NewsTypeAdapter.NewsTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.news_type, parent, false);
        return new NewsTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsTypeAdapter.NewsTypeHolder holder, int position) {
        Map<String, String> data = newstype(activity);
        holder.newsType.setText(data.keySet().toArray()[position].toString());
        holder.newsType.setOnClickListener(v -> {
            MaterialAltertLoading materialAltertLoading = new MaterialAltertLoading(activity);
            MaterialAlertDialogBuilder mDialog = materialAltertLoading.getDialog();
            AlertDialog alertDialog = mDialog.create();
            alertDialog.show();
            String category = data.get(data.keySet().toArray()[position].toString());
            RssUrlCategory rssURLCategory = new RssUrlCategory(activity, activity.findViewById(R.id.cardnews_view_vertical), alertDialog, userId);
            rssURLCategory.startLoad(category);
        });
    }

    @Override
    public int getItemCount() {
        return newstype(activity).size();
    }


    public HashMap<String, String> newstype(AppCompatActivity activity) {
        HashMap<String, String> data = new HashMap<>();
        Context context = activity.getBaseContext();
        data.put(context.getString(R.string.breakingnews_type), "BreakingNews");
        data.put(context.getString(R.string.worldnews_type), "World");
        data.put(context.getString(R.string.news_type), "News");
        data.put(context.getString(R.string.sportnews_type), "Sport");
        data.put(context.getString(R.string.lawnews_type), "Law");
        data.put(context.getString(R.string.educationnews_type), "Education");
        data.put(context.getString(R.string.healthnews_type), "Health");
        data.put(context.getString(R.string.lifestylenews_type), "LifeStyle");
        data.put(context.getString(R.string.travelnews_type), "Travel");
        data.put(context.getString(R.string.sciencenews_type), "Science");
        return data;
    }

    public static class NewsTypeHolder extends RecyclerView.ViewHolder {
        Button newsType;

        public NewsTypeHolder(@NonNull View itemView) {
            super(itemView);
            newsType = itemView.findViewById(R.id.news_type_text);
        }
    }
}