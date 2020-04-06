package com.mobile.aquafish;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobile.aquafish.model.UserModel;
import com.mobile.aquafish.rest.Client;
import com.mobile.aquafish.rest.Interface;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String tagged = MainActivity.class.getSimpleName();
    private static final Pattern passwordPattern =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // at least 1 digit
                    "(?=.*[a-z])" +         // at least 1 lower case
                    "(?=.*[A-Z])" +         // at least 1 upper case
                    "(?=\\S+$)" +           // no white spaces
                    ".{8,20}" +             // 8 until 20 characters
                    "$");

    TextInputLayout boxMail, boxPass;
    TextInputEditText email, pass;
    Button login, toRegister;
    FirebaseAuth auth;
    ProgressDialog dialog;
    SharedPreferences prefMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.buttonLogin);
        login.setOnClickListener(this);
        toRegister = findViewById(R.id.buttonToRegister);
        toRegister.setOnClickListener(this);
        boxMail = findViewById(R.id.checkOne);
        boxPass = findViewById(R.id.checkTwo);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        auth = FirebaseAuth.getInstance();
        prefMain = new SharedPreferences(this);

        email = findViewById(R.id.emailMain);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputMail = Objects.requireNonNull(email.getText()).toString().trim();

                if(inputMail.isEmpty()) {
                    boxMail.setErrorEnabled(true);
                    boxMail.setError("Field can not be empty");
                    login.setClickable(false);
                } else if(!Patterns.EMAIL_ADDRESS.matcher(inputMail).matches()) {
                    boxMail.setErrorEnabled(true);
                    boxMail.setError("Email format is incorrect");
                    login.setClickable(false);
                } else {
                    login.setClickable(true);
                    login.setEnabled(true);
                    boxMail.setError(null);
                    boxMail.setErrorEnabled(false);
                }
            }
        });

        pass = findViewById(R.id.passwordMain);
        pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pass.setTypeface(ResourcesCompat.getFont(this, R.font.font_regular));
        pass.setTransformationMethod(new PasswordTransformationMethod());
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputPass = Objects.requireNonNull(pass.getText()).toString().trim();

                if(inputPass.isEmpty()) {
                    boxPass.setErrorEnabled(true);
                    boxPass.setError("Field can not be empty");
                    login.setClickable(false);
                } else if(!passwordPattern.matcher(inputPass).matches()) {
                    boxPass.setErrorEnabled(true);
                    boxPass.setError("Password format is incorrect");
                    login.setClickable(false);
                } else {
                    login.setClickable(true);
                    login.setEnabled(true);
                    boxPass.setError(null);
                    boxPass.setErrorEnabled(false);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            Intent intent = new Intent(MainActivity.this, FragmentMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        String forMail = Objects.requireNonNull(email.getText()).toString().trim();
        String forPass = Objects.requireNonNull(pass.getText()).toString().trim();

        switch(v.getId()) {
            case R.id.buttonToRegister:
                Intent enroll = new Intent(MainActivity.this, MainRegister.class);
                startActivity(enroll);
                finish();
                break;
            case R.id.buttonLogin:
                if(forMail.isEmpty() & forPass.isEmpty()) {
                    boxMail.setErrorEnabled(true);
                    boxMail.setError("Field can not be empty");
                    boxPass.setErrorEnabled(true);
                    boxPass.setError("Field can not be empty");
                } else if(forMail.isEmpty()) {
                    boxMail.setErrorEnabled(true);
                    boxMail.setError("Field can not be empty");
                } else if(forPass.isEmpty()) {
                    boxPass.setErrorEnabled(true);
                    boxPass.setError("Field can not be empty");
                } else if(boxMail.isErrorEnabled() | boxPass.isErrorEnabled()) {
                    login.setClickable(false);
                } else {
                    login.setClickable(true);
                    login.setEnabled(true);
                    methodForLogin();
                }
                break;
        }
    }

    public void methodForLogin() {
        final String getMail = Objects.requireNonNull(email.getText()).toString().trim();

        dialog = ProgressDialog.show(MainActivity.this, null, "Loading get data",
                true, false);

        (auth.signInWithEmailAndPassword(Objects.requireNonNull(email.getText()).toString().trim(),
                Objects.requireNonNull(pass.getText()).toString().trim()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();

                if(task.isSuccessful()) {
                    getFromDatabase();

                    Intent logSuccess = new Intent(MainActivity.this, FragmentMain.class);
                    logSuccess.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    logSuccess.putExtra("emailUser", getMail);
                    startActivity(logSuccess);
                    finish();
                } else {
                    Log.e("Login Error", Objects.requireNonNull(task.getException()).toString());
                    Toast.makeText(MainActivity.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getFromDatabase() {
        Interface getInterface = Client.getClient().create(Interface.class);
        Call<UserModel> userCall = getInterface.getUser(Objects.requireNonNull(email.getText()).toString().trim());
        userCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                if (response.body() != null) {
                    String codeAqua = response.body().getCode();
                    String nameAqua = response.body().getName();
                    Log.d(tagged, "User success to login");

                    prefMain.saveStringCode(SharedPreferences.AQUA_CODE, codeAqua);
                    prefMain.saveStringName(SharedPreferences.AQUA_NAME, nameAqua);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(tagged, t.toString());
            }
        });
    }
}