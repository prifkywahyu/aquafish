package com.mobile.aquafish.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobile.aquafish.FragmentMain;
import com.mobile.aquafish.MainActivity;
import com.mobile.aquafish.R;
import com.mobile.aquafish.SharedPrefMain;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    AppCompatActivity activity;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView email, about;
    Button logout;
    SharedPrefMain prefMain;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View forView = inflater.inflate(R.layout.fragment_profile, container, false);

        ((FragmentMain) Objects.requireNonNull(getActivity())).setTitleActionBar("More Options");
        email = forView.findViewById(R.id.personName);
        about = forView.findViewById(R.id.aboutUs);
        about.setText("Hi! Let us introduced to you all, we are Aquafish, an application for water quality monitoring and automatic feeding to simplify your pet maintenance." +
                "\n\nAquafish are built by two students, they are Muhammad Naufal and Rifky Wahyu Pratama. Both of them, want to create an efficiency and effective pet caring without worried about your time, job and etc." +
                "\n\nOwner of pet can enjoy the time, doing job with a happy feeling. Hope we can help your problem with pet." +
                "\n\nWith love, Aquafish Team.");

        logout = forView.findViewById(R.id.buttonSignOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout();
            }
        });

        activity = (AppCompatActivity) getActivity();
        prefMain = new SharedPrefMain(activity);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        getPersonName();

        return forView;
    }

    public void getPersonName() {
        String bro = prefMain.getAquaName();
        email.setText(bro);
    }

    public void dialogLogout() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_logout, null);
        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();

        Button cancel = view.findViewById(R.id.cancelState);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button logout = view.findViewById(R.id.loggedOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                prefMain.deleteValue();
                Intent back = new Intent(getContext(), MainActivity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }
}
