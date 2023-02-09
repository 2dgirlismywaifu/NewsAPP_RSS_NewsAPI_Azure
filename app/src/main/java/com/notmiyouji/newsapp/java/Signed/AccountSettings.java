package com.notmiyouji.newsapp.java.Signed;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.notmiyouji.newsapp.R;
import com.notmiyouji.newsapp.java.UpdateInformation.BirthdayController;
import com.notmiyouji.newsapp.java.UpdateInformation.FullNameController;
import com.notmiyouji.newsapp.java.UpdateInformation.GenderController;
import com.notmiyouji.newsapp.java.UpdateInformation.UserNameController;
import com.notmiyouji.newsapp.kotlin.ApplicationFlags;
import com.notmiyouji.newsapp.kotlin.LoadImageURL;
import com.notmiyouji.newsapp.kotlin.SharedSettings.GetUserLogined;
import com.notmiyouji.newsapp.kotlin.SharedSettings.LoadFollowLanguageSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccountSettings extends AppCompatActivity {
    TextView fullName, username, chooseTitle;
    ShapeableImageView avatar;
    TextView fullnameView,usernameView, birthdayView, genderView;
    RelativeLayout changeFullName, changeUserName, changeBirthDay, changeGender, showRecoveryCode, changePassword, changeAvatar;
    LoadFollowLanguageSystem loadFollowLanguageSystem;
    GetUserLogined getUserLogined;
    BottomSheetDialog bottomSheetDialog;
    MaterialAutoCompleteTextView materialAutoCompleteTextView;
    TextInputEditText fullname_input, username_input;
    Button okbtn;
    Intent intent;
    String status;
    TextInputLayout chooseHint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadFollowLanguageSystem = new LoadFollowLanguageSystem(this);
        loadFollowLanguageSystem.loadLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        ApplicationFlags applicationFlags = new ApplicationFlags(this);
        applicationFlags.setFlag();

        //Set textView
        fullName = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        //View to edit
        fullnameView = findViewById(R.id.fullname_view);
        usernameView = findViewById(R.id.username_view);
        birthdayView = findViewById(R.id.birth_view);
        genderView = findViewById(R.id.gender_view);
        getUserLogined = new GetUserLogined(this);
        status = getUserLogined.getStatus();
        //////////////////////////////////////////////////////////////
        //Get view from shared preferences
        fullName.setText(getUserLogined.getFullname());
        username.setText("@" + getUserLogined.getUsername());
        fullnameView.setText(getUserLogined.getFullname());
        usernameView.setText("@" + getUserLogined.getUsername());
        birthdayView.setText(getUserLogined.getBirthday());
        genderView.setText(getUserLogined.getGender());
        //////////////////////////////////////////////////////////////
        //Load avatar
        avatar = findViewById(R.id.avatar_user_logined);
        LoadImageURL loadImageURL = new LoadImageURL(getUserLogined.getAvatar());
        loadImageURL.loadImageUser(avatar);
        //back button
        ImageButton backButton = findViewById(R.id.BackPressed);
        backButton.setOnClickListener(v -> {
            onBackPressed();
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            finish();
        });
        //Change Fullname
        changeFullName = findViewById(R.id.fullname_line);
        changeFullName.setOnClickListener(v -> {
            //go to change fullname
            if (status.equals("login")) {
                //go to change fullname
                bottomSheetDialog = new BottomSheetDialog(this);
                bottomSheetDialog.setContentView(R.layout.update_fullname);
                bottomSheetDialog.show();
                fullname_input = bottomSheetDialog.findViewById(R.id.fullname_input);
                chooseTitle = bottomSheetDialog.findViewById(R.id.choose_title);
                chooseHint = bottomSheetDialog.findViewById(R.id.hint_to_choose);
                chooseTitle.setText(R.string.enter_your_new_fullname);
                chooseHint.setHint(R.string.enter_your_new_fullname);
                okbtn = bottomSheetDialog.findViewById(R.id.btnLoad);
                assert okbtn != null;
                okbtn.setOnClickListener(v1 -> {
                    //Update fullname
                    String fullname = fullname_input.getText().toString();
                    if (fullname.isEmpty()) {
                        Toast.makeText(this, R.string.please_enter_your_new_fullname, Toast.LENGTH_SHORT).show();
                    } else {
                        fullName.setText(fullname);
                        //Update fullname
                        FullNameController fullNameController = new FullNameController
                                (getUserLogined.getUserID(),fullname,this);
                        fullNameController.updateFullName();
                        bottomSheetDialog.dismiss();
                    }
                });
            } else {
                //SSO can't edit
                Toast.makeText(this, R.string.single_sign_on_can_not_edit, Toast.LENGTH_SHORT).show();
            }
        });
        //Change Username
        changeUserName = findViewById(R.id.username_line);
        changeUserName.setOnClickListener(v -> {
            //go to change username
            if (status.equals("login")) {
                //go to change username
                bottomSheetDialog = new BottomSheetDialog(this);
                bottomSheetDialog.setContentView(R.layout.update_username);
                bottomSheetDialog.show();
                username_input = bottomSheetDialog.findViewById(R.id.username_input);
                chooseTitle = bottomSheetDialog.findViewById(R.id.choose_title);
                chooseHint = bottomSheetDialog.findViewById(R.id.hint_to_choose);
                chooseTitle.setText(R.string.enter_your_new_username);
                chooseHint.setHint(R.string.enter_your_new_username);
                okbtn = bottomSheetDialog.findViewById(R.id.btnLoad);
                assert okbtn != null;
                okbtn.setOnClickListener(v1 -> {
                    //Update username
                    String username = username_input.getText().toString();
                    if (username.isEmpty()) {
                        Toast.makeText(this, R.string.please_enter_your_new_username, Toast.LENGTH_SHORT).show();
                    } else {
                        //Update fullname
                        usernameView.setText("@" + username);
                        UserNameController userNameController = new UserNameController
                                (getUserLogined.getUserID(),username,getUserLogined.getEmail(),this);
                        userNameController.checkUserName();
                        bottomSheetDialog.dismiss();
                    }
                });
            } else {
                //SSO can't edit
                Toast.makeText(this, R.string.single_sign_on_can_not_edit, Toast.LENGTH_SHORT).show();
            }
        });
        //Change Birthday
        changeBirthDay = findViewById(R.id.birthday_line);
        changeBirthDay.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getDay(getUserLogined.getBirthday()));
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                //Set Birthday
                //Open Android DatePicker
                @SuppressLint("DefaultLocale")
                String dateString = String.format("%d-%02d-%02d", year, (month+1), dayOfMonth);
                BirthdayController birthdayController = new BirthdayController
                        (getUserLogined.getUserID(),dateString,this);
                if (status.equals("login")) {
                    birthdayController.updateBirthday();
                } else {
                    //for SSO account
                    birthdayController.updateBirthdaySSO();
                }

                //Update Birthday
                birthdayView.setText(dateString);
                //Update Birthday to database
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            //Date Picker Dialog show date like textView
            datePickerDialog.show();

        });
        //Change Gender
        changeGender = findViewById(R.id.gender_line);
        changeGender.setOnClickListener(v-> {
            //Open BottomSheetDialog
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.choose_gender);
            bottomSheetDialog.show();
            materialAutoCompleteTextView = bottomSheetDialog.findViewById(R.id.spinner_gender);
            chooseTitle = bottomSheetDialog.findViewById(R.id.choose_title);
            chooseHint = bottomSheetDialog.findViewById(R.id.hint_to_choose);
            chooseTitle.setText(R.string.choose_gender);
            chooseHint.setHint(R.string.select_your_gender);
            materialAutoCompleteTextView.setAdapter(new ArrayAdapter<>(this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, genderList()));
            okbtn = bottomSheetDialog.findViewById(R.id.btnLoad);
            assert okbtn != null;
            okbtn.setOnClickListener(v1 -> {
                bottomSheetDialog.dismiss();
                genderView.setText(materialAutoCompleteTextView.getText().toString());
                GenderController genderController = new GenderController
                        (getUserLogined.getUserID(),materialAutoCompleteTextView.getText().toString(),this);
                if (status.equals("login")) {
                    genderController.updateGender();
                } else {
                    //for SSO account
                    genderController.updateGenderSSO();
                }
            });

        });
        //Show Recovery Code
        showRecoveryCode = findViewById(R.id.view_recovery_code);
        showRecoveryCode.setOnClickListener(v -> {
            if (status.equals("login")) {
                //go to change fullname
                intent = new Intent(this, ViewRecoveryCode.class);
                startActivity(intent);
            } else {
                //SSO can't edit
                Toast.makeText(this, R.string.single_sign_on_can_not_edit, Toast.LENGTH_SHORT).show();
            }

        });
        //Change Password
        changePassword = findViewById(R.id.change_password_action);
        changePassword.setOnClickListener(v -> {
            if (status.equals("login")) {
                intent = new Intent(this, ChangePassword.class);
                startActivity(intent);
            } else {
                //SSO can't edit
                Toast.makeText(this, R.string.single_sign_on_can_not_edit, Toast.LENGTH_SHORT).show();
            }
        });
        //Change Avatar
        changeAvatar = findViewById(R.id.change_avatar_action);
        changeAvatar.setOnClickListener(v -> {
            if (status.equals("login")) {
                //open material dialog to tell user about go gravatar change your avatar
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle(R.string.change_avatar_account);
                String message = getString(R.string.we_recommend_you_to_change_your_avatar_at_gravatar_com) +
                        getString(R.string.gravatar_is_a_free_service_for_providing_globally_unique_avatars_for_your_email_address) +
                        getString(R.string.you_can_change_your_avatar_at_gravatar_com_and_it_will_be_automatically_updated_in_all_of_your_applications_that_use_gravatar) +
                        getString(R.string.sign_up_at_gravatar_com_and_upload_your_avatar);
                builder.setMessage(message);
                builder.setPositiveButton(R.string.go_gravatar, (dialog, which) -> {
                    //Go to gravatar
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://gravatar.com"));
                    startActivity(intent);
                });
                builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();

            } else {
                //SSO can't edit
                Toast.makeText(this, R.string.single_sign_on_can_not_edit, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //create list gender
    private List<String> genderList() {
        List<String> gender = new ArrayList<>();
        gender.add(getString(R.string.male));
        gender.add(getString(R.string.female));
        gender.add(getString(R.string.other_gender));
        return gender;
    }

    private Date getDay(String date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date getDate = null;
        try {
            getDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDate;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        finish();
    }
    @Override
    public void onResume() {
        super.onResume();
        loadFollowLanguageSystem = new LoadFollowLanguageSystem(this);
        loadFollowLanguageSystem.loadLanguage();
    }
}