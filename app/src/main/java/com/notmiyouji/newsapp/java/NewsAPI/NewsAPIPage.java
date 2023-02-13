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

package com.notmiyouji.newsapp.java.NewsAPI;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.notmiyouji.newsapp.R;
import com.notmiyouji.newsapp.java.Category.NewsAPICategory;
import com.notmiyouji.newsapp.kotlin.CheckNetworkConnection;
import com.notmiyouji.newsapp.java.Global.FavouriteNews;
import com.notmiyouji.newsapp.java.Global.MaterialAltertLoading;
import com.notmiyouji.newsapp.java.Global.NavigationPane;
import com.notmiyouji.newsapp.java.RSSURL.HomePage;
import com.notmiyouji.newsapp.java.RSSURL.SourceNewsList;
import com.notmiyouji.newsapp.java.RecycleViewAdapter.NewsAPITypeAdapter;
import com.notmiyouji.newsapp.java.Retrofit.NewsAPIKey;
import com.notmiyouji.newsapp.java.Retrofit.NewsAPPAPI;
import com.notmiyouji.newsapp.kotlin.ApplicationFlags;
import com.notmiyouji.newsapp.kotlin.NetworkConnection;
import com.notmiyouji.newsapp.kotlin.OpenActivity.CallSignInForm;
import com.notmiyouji.newsapp.kotlin.NewsAPIModels.Article;
import com.notmiyouji.newsapp.kotlin.NewsAPIModels.Country;
import com.notmiyouji.newsapp.kotlin.NewsAPIModels.News;
import com.notmiyouji.newsapp.kotlin.OpenActivity.OpenSettingsPage;
import com.notmiyouji.newsapp.kotlin.RetrofitInterface.NewsAPIInterface;
import com.notmiyouji.newsapp.kotlin.RetrofitInterface.NewsAPPInterface;
import com.notmiyouji.newsapp.kotlin.SharedSettings.GetUserLogined;
import com.notmiyouji.newsapp.kotlin.SharedSettings.LoadFollowLanguageSystem;
import com.notmiyouji.newsapp.kotlin.SharedSettings.LoadNavigationHeader;
import com.notmiyouji.newsapp.kotlin.SharedSettings.LoadThemeShared;
import com.notmiyouji.newsapp.kotlin.SharedSettings.LoadWallpaperShared;
import com.notmiyouji.newsapp.kotlin.SharedSettings.LoadWallpaperSharedLogined;
import com.notmiyouji.newsapp.kotlin.SharedSettings.SharedPreferenceSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsAPIPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Initialization variable
    public static final String API_KEY = new NewsAPIKey().getNEWSAPIKEY(); //the newsAPI key is here
    DrawerLayout drawerNewsAPI;
    NavigationView navigationView;
    LinearLayoutManager newstypeLayout;
    LinearLayoutManager newsAPIHorizontalLayout;
    RecyclerView newstypeView, newsViewHorizontal, newsViewVertical;
    Toolbar toolbar;
    NavigationPane navigationPane;
    NewsAPITypeAdapter newsAPITypeAdapter;
    Intent intent;
    List<Article> articles = new ArrayList<>(); //not include category
    NewsAdapterHorizontal newsAdapterHorizontal;
    NewsAPIInterface newsApiInterface = NewsAPIKey.getAPIClient().create(NewsAPIInterface.class);
    NewsAPPInterface newsAPPInterface = NewsAPPAPI.getAPIClient().create(NewsAPPInterface.class);
    Call<News> call;
    NewsAPICategory newsAPICategory = new NewsAPICategory();
    LoadWallpaperShared loadWallpaperShared;
    LoadWallpaperSharedLogined loadWallpaperSharedLogined;
    ExtendedFloatingActionButton filterCountry;
    TextView chooseTitle;
    EditText searchNews;
    TextInputLayout chooseHint;
    List<Country> countryList, codeList;
    SwipeRefreshLayout swipeRefreshLayout;
    LoadFollowLanguageSystem loadFollowLanguageSystem;
    LoadThemeShared loadThemeShared;
    LoadNavigationHeader loadNavigationHeader;
    GetUserLogined getUserLogined;
    LinearLayout newsapiPage, errorPage;
    CheckNetworkConnection checkNetworkConnection;
    private String countryCodeDefault = "us";

    public String getCountryCodeDefault() {
        return countryCodeDefault;
    }

    public void setCountryCodeDefault(String countryCodeDefault) {
        this.countryCodeDefault = countryCodeDefault;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadFollowLanguageSystem = new LoadFollowLanguageSystem(this);
        loadFollowLanguageSystem.loadLanguage();
        loadThemeShared = new LoadThemeShared(this);
        loadThemeShared.setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_api_page);
        ApplicationFlags applicationFlags = new ApplicationFlags(this);
        applicationFlags.setFlag();
        //Hooks
        filterCountry = findViewById(R.id.filterCountry);
        newsapiPage = findViewById(R.id.newsAPIPage);
        errorPage = findViewById(R.id.noInternetScreen);
        //Check internet connection
        NetworkConnection networkConnection = new NetworkConnection(this);
        networkConnection.observe(this, isConnected -> {
            if (isConnected) {
                newsapiPage.setVisibility(android.view.View.VISIBLE);
                filterCountry.setVisibility(android.view.View.VISIBLE);
                errorPage.setVisibility(android.view.View.GONE);
            } else {
                newsapiPage.setVisibility(android.view.View.GONE);
                filterCountry.setVisibility(android.view.View.GONE);
                errorPage.setVisibility(android.view.View.VISIBLE);
            }
        });
        navigationView = findViewById(R.id.nav_pane_newsapi);
        //From sharedPreference, if user logined saved, call navigation pane with user name header
        loadNavigationHeader = new LoadNavigationHeader(NewsAPIPage.this, navigationView);
        loadNavigationHeader.loadHeader();
        //From SharedPreference, change background for header navigation pane
        getUserLogined = new GetUserLogined(this);
        if (getUserLogined.getStatus().equals("login") || getUserLogined.getStatus().equals("google")) {
            loadWallpaperSharedLogined = new LoadWallpaperSharedLogined(navigationView, this);
            loadWallpaperSharedLogined.loadWallpaper();
        } else {
            loadWallpaperShared = new LoadWallpaperShared(navigationView, this);
            loadWallpaperShared.loadWallpaper();
        }
        drawerNewsAPI = findViewById(R.id.newsapi_page);
        toolbar = findViewById(R.id.nav_button);
        newstypeView = findViewById(R.id.news_type);
        newsViewHorizontal = findViewById(R.id.cardnews_view_horizontal);
        newsViewVertical = findViewById(R.id.cardnews_view_vertical);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        //create navigation drawer
        navigationPane = new NavigationPane(drawerNewsAPI, this, toolbar, navigationView, R.id.newsapi_menu);
        navigationPane.CallFromUser();
        //From SharedPreference, load country code
        reloadCountryCode();
        //open sign in page from navigationview
        if (getUserLogined.getStatus().equals("")) {
            CallSignInForm callSignInForm = new CallSignInForm(navigationView, this);
            callSignInForm.callSignInForm();
        }
        checkNetworkConnection = new CheckNetworkConnection();
        if (checkNetworkConnection.CheckConnection(this)) {
            //NewsCategory Type List
            LoadCategoryType(getCountryCodeDefault());
            LoadNewsAPI(getCountryCodeDefault());
        }

        //open Country Filter
        openCountryFilter();
        //Hide float button when scroll recyclerview vertical
        hideWhenScroll();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            LoadNewsAPI(getCountryCodeDefault());
            swipeRefreshLayout.setRefreshing(false);
        });
        //Search News with recyclerview filter
        searchNews = findViewById(R.id.search_input);
        searchNews.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterHorizonal(s.toString());
                //This is a feature, not a BUG
                newsAPICategory.filterVertical(s.toString());
            }
        });
    }

    private void filterHorizonal(String text) {
        List<Article> listhorizonal = new ArrayList<>();
        for (Article item : articles) {
            if (Objects.requireNonNull(item.getTitle()).toLowerCase().contains(text.toLowerCase())) {
                listhorizonal.add(item);
            }
        }
        newsAdapterHorizontal.filterList(listhorizonal);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void LoadCategoryType(String countryCodeDefault) {
        newsAPITypeAdapter = new NewsAPITypeAdapter(this, countryCodeDefault);
        newstypeLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newstypeView.setLayoutManager(newstypeLayout);
        newstypeView.setAdapter(newsAPITypeAdapter);
        newsAPITypeAdapter.notifyDataSetChanged();
    }

    private void hideWhenScroll() {
        NestedScrollView nestedScrollView = findViewById(R.id.nested_scroll_view);
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (nestedScrollView.getScrollY() == 0) {
                filterCountry.extend();
            } else {
                filterCountry.shrink();
            }
        });
    }

    private void LoadNewsAPI(String countryCodeDefault) {
        //Loading Messeage
        //final ProgressDialog mDialog = new ProgressDialog(this);
        //mDialog.setMessage(this.getString(R.string.loading_messeage));
        //mDialog.show();
        MaterialAltertLoading materialAltertLoading = new MaterialAltertLoading(this);
        MaterialAlertDialogBuilder mDialog = materialAltertLoading.getDiaglog();
        AlertDialog alertDialog = mDialog.create();
        alertDialog.show();
        //Load JSONData and apply to RecycleView Horizontal Lastest NewsCategory
        LoadJSONLastestNews(this, alertDialog, countryCodeDefault);
        //Load JSONData Business NewsCategory and apply to RecycleView Vertical Lastest NewsCategory
        newsAPICategory.LoadJSONCategory(this, alertDialog, "business", newsViewVertical, countryCodeDefault);
    }

    private void openCountryFilter() {
        filterCountry.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(NewsAPIPage.this);
            bottomSheetDialog.setContentView(R.layout.choose_feed_newsapi);
            bottomSheetDialog.show();
            MaterialAutoCompleteTextView spinner_rss;
            spinner_rss = bottomSheetDialog.findViewById(R.id.spinner_rss);
            chooseTitle = bottomSheetDialog.findViewById(R.id.choose_title);
            chooseHint = bottomSheetDialog.findViewById(R.id.hint_to_choose);
            chooseTitle.setText(R.string.choose_country_newsapi);
            chooseHint.setHint(R.string.choose_country_hint);
            call = newsAPPInterface.getListCountry();
            countryList = new ArrayList<>();
            assert call != null;
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getCountrylist() != null) {
                            if (!countryList.isEmpty()) {
                                countryList.clear();
                            }
                            countryList = response.body().getCountrylist();
                            List<Country> countryListName = countryList;
                            ArrayList<String> countryName = new ArrayList<>();
                            for (Country country : countryListName) {
                                countryName.add(country.getCountryName());
                            }
                            assert spinner_rss != null;
                            spinner_rss.setAdapter(new ArrayAdapter<>(NewsAPIPage.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, countryName));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                    Logger.getLogger("Error").warning(t.getMessage());
                }
            });
            Button okbtn = bottomSheetDialog.findViewById(R.id.btnLoad);
            assert okbtn != null;
            assert spinner_rss != null;
            okbtn.setOnClickListener(v1 -> {
                String countryName = spinner_rss.getText().toString();
                if (countryName.isEmpty()) {
                    Toast.makeText(NewsAPIPage.this, R.string.country_not_choose, Toast.LENGTH_SHORT).show();
                } else {
                    loadCountryCode(countryName);
                    bottomSheetDialog.dismiss();
                }

            });
        });
    }

    private void loadCountryCode(String countryName) {
        call = newsAPPInterface.getCountryCode(countryName);
        codeList = new ArrayList<>();
        assert call != null;
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getCountrycode() != null) {
                        if (!codeList.isEmpty()) {
                            codeList.clear();
                        }
                        codeList = response.body().getCountrycode();
                        Country country = codeList.get(0);
                        String code = country.getCountryCode();
                        //Save shared preference
                        SharedPreferenceSettings sharedPreferenceSettings = new SharedPreferenceSettings(NewsAPIPage.this);
                        assert code != null;
                        sharedPreferenceSettings.getSharedCountry(code);
                        setCountryCodeDefault(code);
                        LoadCategoryType(code);
                        //load newsapi again
                        LoadNewsAPI(code);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                Logger.getLogger("Error").warning(t.getMessage());
            }
        });
    }

    public void LoadJSONLastestNews(AppCompatActivity activity, AlertDialog mDialog, String country) {
        Thread loadSourceAPI = new Thread(() -> {
            call = newsApiInterface.getLatestNews(country, API_KEY);
            assert call != null;
            call.enqueue(new Callback<News>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getArticle() != null) {
                            if (!articles.isEmpty()) {
                                articles.clear();
                            }
                            articles = response.body().getArticle();
                            newsAPIHorizontalLayout = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                            newsViewHorizontal.setLayoutManager(newsAPIHorizontalLayout);
                            newsAdapterHorizontal = new NewsAdapterHorizontal(articles, activity);
                            newsViewHorizontal.setAdapter(newsAdapterHorizontal);
                            newsAdapterHorizontal.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                    Toast.makeText(NewsAPIPage.this, R.string.Some_things_went_wrong, Toast.LENGTH_SHORT).show();
                }
            });

            runOnUiThread(() ->
                    //close material dialog
                    newsViewHorizontal.getViewTreeObserver().addOnGlobalLayoutListener(mDialog::dismiss));
        });
        loadSourceAPI.start();
    }

    private void reloadCountryCode() {
        SharedPreferences sharedPreferences = getSharedPreferences("CountryCode", MODE_PRIVATE);
        if (!sharedPreferences.getString("code", "").equals(getCountryCodeDefault())) {
            setCountryCodeDefault(sharedPreferences.getString("code", getCountryCodeDefault()));
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerNewsAPI.isDrawerOpen(GravityCompat.START)) {
            drawerNewsAPI.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            finish();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int menuitem = item.getItemId();
        switch (menuitem) {
            case R.id.home_menu:
                intent = new Intent(NewsAPIPage.this, HomePage.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.source_menu:
                intent = new Intent(NewsAPIPage.this, SourceNewsList.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.favourite_menu:
                intent = new Intent(NewsAPIPage.this, FavouriteNews.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.settings_menu:
                OpenSettingsPage openSettingsPage = new OpenSettingsPage(NewsAPIPage.this);
                openSettingsPage.openSettings();
                break;
        }
        return true;
    }


    public void onResume() {
        super.onResume();
        if (getUserLogined.getStatus().equals("login") || getUserLogined.getStatus().equals("google")) {
            loadWallpaperSharedLogined = new LoadWallpaperSharedLogined(navigationView, this);
            loadWallpaperSharedLogined.loadWallpaper();
        } else {
            loadWallpaperShared = new LoadWallpaperShared(navigationView, this);
            loadWallpaperShared.loadWallpaper();
        }
        navigationPane = new NavigationPane(drawerNewsAPI, this, toolbar, navigationView, R.id.newsapi_menu);
        navigationPane.CallFromUser();
        loadFollowLanguageSystem = new LoadFollowLanguageSystem(this);
        loadFollowLanguageSystem.loadLanguage();
        loadThemeShared = new LoadThemeShared(this);
        loadThemeShared.setTheme();
    }
}