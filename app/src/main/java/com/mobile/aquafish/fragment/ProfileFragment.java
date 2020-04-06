package com.mobile.aquafish.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobile.aquafish.FragmentMain;
import com.mobile.aquafish.MainActivity;
import com.mobile.aquafish.R;
import com.mobile.aquafish.SharedPreferences;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private TextView email;
    private SharedPreferences prefMain;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View forView = inflater.inflate(R.layout.fragment_profile, container, false);

        ((FragmentMain) Objects.requireNonNull(getActivity())).setTitleActionBar("More Options");
        email = forView.findViewById(R.id.personName);
        TextView about = forView.findViewById(R.id.aboutUs);
        about.setText(R.string.introduce);

        Button logout = forView.findViewById(R.id.buttonSignOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout();
            }
        });

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        prefMain = new SharedPreferences(activity);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Objects.requireNonNull(firebaseUser).getEmail();
        getPersonName();

        return forView;
    }

    private void getPersonName() {
        String bro = prefMain.getAquaName();
        email.setText(bro);
    }

    private void dialogLogout() {
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