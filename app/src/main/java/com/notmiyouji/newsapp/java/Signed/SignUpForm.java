package com.notmiyouji.newsapp.java.Signed;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.notmiyouji.newsapp.R;
import com.notmiyouji.newsapp.java.Retrofit.NewsAPPAPI;
import com.notmiyouji.newsapp.kotlin.ApplicationFlags;
import com.notmiyouji.newsapp.kotlin.LoginedModel.CheckNickName;
import com.notmiyouji.newsapp.kotlin.LoginedModel.Register;
import com.notmiyouji.newsapp.kotlin.RetrofitInterface.NewsAPPInterface;
import com.notmiyouji.newsapp.kotlin.SharedSettings.LoadFollowLanguageSystem;

import retrofit2.Call;
import retrofit2.Response;

public class SignUpForm extends AppCompatActivity {

    Button signupbtn, signinbtn;
    TextInputEditText fullname, email, password, confirmpassword, username;
    LoadFollowLanguageSystem loadFollowLanguageSystem;
    NewsAPPInterface newsAPPInterface = NewsAPPAPI.getAPIClient().create(NewsAPPInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadFollowLanguageSystem = new LoadFollowLanguageSystem(this);
        loadFollowLanguageSystem.loadLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_form);
        ApplicationFlags applicationFlags = new ApplicationFlags(this);
        applicationFlags.setFlag();

        fullname = findViewById(R.id.fullname_input);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        confirmpassword = findViewById(R.id.Repassword_input);
        username = findViewById(R.id.recovey_code);
        signinbtn = findViewById(R.id.ResendCodeBtn);
        ImageButton backButton = findViewById(R.id.BackPressed);
        backButton.setOnClickListener(v -> {
            onBackPressed();
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            finish();
        });
        //Sign Up form only access from Sign In form
        signinbtn.setOnClickListener(v -> {
            onBackPressed();
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            finish();
        });
        //sign up button
        signupbtn = findViewById(R.id.VerifiedButton);
        signupbtn.setOnClickListener(v -> {
            //disable button
            signupbtn.setEnabled(false);
            //check input
            checkInput();
            //if all input is not null, sign up
            if (!fullname.getText().toString().isEmpty()
                    && !email.getText().toString().isEmpty()
                    && !password.getText().toString().isEmpty()
                    && !confirmpassword.getText().toString().isEmpty()
                    && !username.getText().toString().isEmpty()) {
                //if password and confirm password is not same, show error
                if (!password.getText().toString().equals(confirmpassword.getText().toString())) {
                    password.setError(getString(R.string.password_is_not_same));
                    confirmpassword.setError(getString(R.string.password_is_not_same));
                    Toast.makeText(this, R.string.password_is_not_same, Toast.LENGTH_SHORT).show();
                    signupbtn.setEnabled(true);
                }
                //regex email
                else if (!email.getText().toString().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    email.setError(getString(R.string.email_is_not_valid));
                    Toast.makeText(this, R.string.email_is_not_valid, Toast.LENGTH_SHORT).show();
                    signupbtn.setEnabled(true);
                }
                //if password not containt al least 6 character, show error
                else if (password.getText().toString().length() < 6) {
                    password.setError(getString(R.string.password_must_be_at_least_6_character));
                    Toast.makeText(this, R.string.password_must_be_at_least_6_character, Toast.LENGTH_SHORT).show();
                    signupbtn.setEnabled(true);
                }
                //password must contain at least 1 number, 1 uppercase, 1 special character
                else if (!password.getText().toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$")) {
                    password.setError(getString(R.string.password_must_contain_at_least_1_number_1_uppercase_1_special_character));
                    Toast.makeText(this, R.string.password_must_contain_at_least_1_number_1_uppercase_1_special_character, Toast.LENGTH_SHORT).show();
                    signupbtn.setEnabled(true);
                } else {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setTitle(R.string.sign_up);
                    builder.setMessage(R.string.make_sure_email_correct);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                        //check nickname
                        checkNickname();
                    });
                    builder.setNegativeButton(R.string.no, (dialog, which) -> {
                        dialog.dismiss();
                        signupbtn.setEnabled(true);
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        finish();
    }

    public void onResume() {
        super.onResume();
        loadFollowLanguageSystem = new LoadFollowLanguageSystem(this);
        loadFollowLanguageSystem.loadLanguage();
    }

    private void checkInput() {
        //if all input is null, textinputlayout will show error
        if (fullname.getText().toString().isEmpty()) {
            fullname.setError("Full name is required");
            signupbtn.setEnabled(true);
        }
        if (email.getText().toString().isEmpty()) {
            email.setError("Email is required");
            signupbtn.setEnabled(true);
        }
        if (password.getText().toString().isEmpty()) {
            password.setError("Password is required");
            signupbtn.setEnabled(true);
        }
        if (confirmpassword.getText().toString().isEmpty()) {
            confirmpassword.setError("Confirm password is required");
            signupbtn.setEnabled(true);
        }
        if (username.getText().toString().isEmpty()) {
            username.setError("Username is required");
            signupbtn.setEnabled(true);
        }
    }

    private void checkNickname() {
        //Use Retrofit to check nickname
        Call<CheckNickName> call = newsAPPInterface.checkNickname(username.getText().toString(), email.getText().toString());
        call.enqueue(new retrofit2.Callback<CheckNickName>() {
            @Override
            public void onResponse(Call<CheckNickName> call, Response<CheckNickName> response) {
                if (response.body().getNickname().equals(username.getText().toString())) {
                    username.setError(getString(R.string.nickname_is_already_used));
                    signupbtn.setEnabled(true);
                }
                if (response.body().getEmail().equals(email.getText().toString())) {
                    email.setError(getString(R.string.email_is_already_used));
                    signupbtn.setEnabled(true);
                } else {
                    //if password and confirm password is same, sign up
                    //sign up function
                    //lowercase email
                    RegisterAccount(fullname.getText().toString(),
                            email.getText().toString().toLowerCase(),
                            password.getText().toString(),
                            username.getText().toString());
                    //gotoVerifyEmail(fullname.getText().toString(), email.getText().toString().toLowerCase(), password.getText().toString(), username.getText().toString());
                }

            }

            @Override
            public void onFailure(Call<CheckNickName> call, Throwable t) {
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void RegisterAccount(String fullname, String email, String password, String username) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //We need make sure no duplicate email in firebase
//        firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                if (task.getResult().getSignInMethods().size() == 0) {
//                    //Email not exist, create new account
//
//                } else {
//                    //Email exist, show error
//                    Toast.makeText(SignUpForm.this, R.string.email_is_already_used, Toast.LENGTH_SHORT).show();
//                    signupbtn.setEnabled(true);
//                }
//            }
//        });
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                //Send email verification
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                firebaseUser.sendEmailVerification().addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(SignUpForm.this, "NewsApp")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(getString(R.string.sign_up))
                                .setContentText(getString(R.string.a_confirmation_email_has_been_sent_to_your_mailbox))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        Toast.makeText(SignUpForm.this, R.string.a_confirmation_email_has_been_sent_to_your_mailbox, Toast.LENGTH_SHORT).show();
                        //Create notification
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(SignUpForm.this);
                        notificationManager.notify(1, builder.build());
                        gotoVerifyEmail(fullname, email, password, username);
                    } else {
                        Toast.makeText(SignUpForm.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void gotoVerifyEmail(String fullname, String email, String password, String username) {
        //First, save it to database
        Call<Register> call = newsAPPInterface.register(fullname, email, password, username);
        call.enqueue(new retrofit2.Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                //Save successfully, go to verify account activity
                if (response.isSuccessful()) {
                    Register register = response.body();
                    //go to verify account activity
                    Intent intent = new Intent(SignUpForm.this, VerifyAccountForm.class);
                    intent.putExtra("user_id", register.getUserId());
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("username", username);
                    ActivityOptions.makeSceneTransitionAnimation(SignUpForm.this).toBundle();
                    SignUpForm.this.finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
            }
        });
    }
}