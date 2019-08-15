package com.mobile.aquafish;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mobile.aquafish.model.UserModel;
import com.mobile.aquafish.rest.ApiClient;
import com.mobile.aquafish.rest.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRegister extends AppCompatActivity implements View.OnClickListener {

    private static final Pattern PATTERN_CODE =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // at least 1 digit
                    "(?=.*[a-z])" +         // at least 1 lower case
                    "(?=.*[A-Z])" +         // at least 1 upper case
                    "(?=\\S+$)" +           // no white spaces
                    ".{8,20}" +             // 8 until 20 characters
                    "$");

    private static final String TAG = MainRegister.class.getSimpleName();
    TextInputLayout nameBox, mailBox, codeBox, aquaBox;
    TextInputEditText name, mail, code, aqua;
    Button register, toLogin;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        register = findViewById(R.id.buttonRegister);
        register.setOnClickListener(this);
        toLogin = findViewById(R.id.buttonToLogin);
        toLogin.setOnClickListener(this);
        nameBox = findViewById(R.id.checkName);
        mailBox = findViewById(R.id.checkMail);
        codeBox = findViewById(R.id.checkPass);
        aquaBox = findViewById(R.id.checkCode);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        auth = FirebaseAuth.getInstance();

        name = findViewById(R.id.name_slave);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputName = Objects.requireNonNull(name.getText()).toString().trim();

                if (inputName.isEmpty()) {
                    nameBox.setErrorEnabled(true);
                    nameBox.setError("Field can not be empty");
                    register.setClickable(false);
                } else {
                    register.setClickable(true);
                    register.setEnabled(true);
                    nameBox.setError(null);
                    nameBox.setErrorEnabled(false);
                }
            }
        });

        aqua = findViewById(R.id.code_slave);
        aqua.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputAqua = Objects.requireNonNull(aqua.getText()).toString().trim();

                if (inputAqua.isEmpty()) {
                    aquaBox.setErrorEnabled(true);
                    aquaBox.setError("Field can not be empty");
                    register.setClickable(false);
                } else {
                    register.setClickable(true);
                    register.setEnabled(true);
                    aquaBox.setError(null);
                    aquaBox.setErrorEnabled(false);
                }
            }
        });

        mail = findViewById(R.id.email_slave);
        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputMail = Objects.requireNonNull(mail.getText()).toString().trim();

                if (inputMail.isEmpty()) {
                    mailBox.setErrorEnabled(true);
                    mailBox.setError("Field can not be empty");
                    register.setClickable(false);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(inputMail).matches()) {
                    mailBox.setErrorEnabled(true);
                    mailBox.setError("Email format is incorrect");
                    register.setClickable(false);
                } else {
                    register.setClickable(true);
                    register.setEnabled(true);
                    mailBox.setError(null);
                    mailBox.setErrorEnabled(false);
                }
            }
        });

        code = findViewById(R.id.password_slave);
        code.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        code.setTypeface(ResourcesCompat.getFont(this, R.font.lato_regular));
        code.setTransformationMethod(new PasswordTransformationMethod());
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputCode = Objects.requireNonNull(code.getText()).toString().trim();

                if (inputCode.isEmpty()) {
                    codeBox.setErrorEnabled(true);
                    codeBox.setError("Field can not be empty");
                    register.setClickable(false);
                } else if (!PATTERN_CODE.matcher(inputCode).matches()) {
                    codeBox.setErrorEnabled(true);
                    codeBox.setError("Password format is incorrect");
                    register.setClickable(false);
                } else {
                    register.setClickable(true);
                    register.setEnabled(true);
                    codeBox.setError(null);
                    codeBox.setErrorEnabled(false);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        String forName = Objects.requireNonNull(name.getText()).toString().trim();
        String forAqua = Objects.requireNonNull(aqua.getText()).toString().trim();
        String forMail = Objects.requireNonNull(mail.getText()).toString().trim();
        String forPass = Objects.requireNonNull(code.getText()).toString().trim();

        switch (v.getId()) {
            case R.id.buttonToLogin:
                Intent backed = new Intent(MainRegister.this, MainActivity.class);
                startActivity(backed);
                finish();
                break;
            case R.id.buttonRegister:
                if (forName.isEmpty() & forAqua.isEmpty() & forMail.isEmpty() & forPass.isEmpty()) {
                    nameBox.setErrorEnabled(true);
                    nameBox.setError("Field can not be empty");
                    aquaBox.setErrorEnabled(true);
                    aquaBox.setError("Field can not be empty");
                    mailBox.setErrorEnabled(true);
                    mailBox.setError("Field can not be empty");
                    codeBox.setErrorEnabled(true);
                    codeBox.setError("Field can not be empty");
                } else if (forName.isEmpty()) {
                    nameBox.setErrorEnabled(true);
                    nameBox.setError("Field can not be empty");
                } else if (forAqua.isEmpty()) {
                    aquaBox.setErrorEnabled(true);
                    aquaBox.setError("Field can not be empty");
                } else if (forMail.isEmpty()) {
                    mailBox.setErrorEnabled(true);
                    mailBox.setError("Field can not be empty");
                } else if (forPass.isEmpty()) {
                    codeBox.setErrorEnabled(true);
                    codeBox.setError("Field can not be empty");
                } else if (nameBox.isErrorEnabled() | aquaBox.isErrorEnabled() | mailBox.isErrorEnabled() | codeBox.isErrorEnabled()) {
                    register.setClickable(false);
                } else {
                    register.setClickable(true);
                    register.setEnabled(true);
                    sendToDatabase();
                    thirdPartySaved();
                }
                break;
        }
    }

    public void thirdPartySaved() {
        (auth.createUserWithEmailAndPassword(Objects.requireNonNull(mail.getText()).toString().trim(),
                Objects.requireNonNull(code.getText()).toString().trim()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User success to register");
                        } else {
                            Log.e("Register Error", Objects.requireNonNull(task.getException()).toString());
                        }
                    }
                });
    }

    public void sendToDatabase() {
        dialog = ProgressDialog.show(MainRegister.this, null, "Loading to send data",
                true, false);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserModel> userModelCall = apiInterface.postUserData(Objects.requireNonNull(name.getText()).toString().trim(),
                Objects.requireNonNull(mail.getText()).toString().trim(), Objects.requireNonNull(aqua.getText()).toString().trim());
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                if (response.body() != null) {
                    dialog.dismiss();
                    Log.d(TAG, "User success to register");
                    Toast.makeText(MainRegister.this, "User has been registered and can login", Toast.LENGTH_SHORT).show();

                    name.getText().clear();
                    aqua.getText().clear();
                    mail.getText().clear();
                    Objects.requireNonNull(code.getText()).clear();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
                Toast.makeText(MainRegister.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }
}