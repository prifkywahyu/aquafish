package com.mobile.aquafish.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobile.aquafish.FragmentMain;
import com.mobile.aquafish.LiveMonitor;
import com.mobile.aquafish.MainActivity;
import com.mobile.aquafish.R;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView email;
    CardView live, notify, about;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View forView = inflater.inflate(R.layout.profile_fragment, container, false);

        setHasOptionsMenu(true);
        ((FragmentMain) Objects.requireNonNull(getActivity())).setTitleActionBar("More Options");
        live = forView.findViewById(R.id.cardLive);
        notify = forView.findViewById(R.id.cardNotify);
        about = forView.findViewById(R.id.cardGroup);
        email = forView.findViewById(R.id.personName);
        methodCard();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        getPersonName();

        return forView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.more_settings, menu);
        for (int us = 0; us < menu.size(); us++) {
            Drawable drawer = menu.getItem(us).getIcon();
            if (drawer != null) {
                drawer.mutate();
            }
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_set:
                dialogLogout();
                return true;
        }

        return false;
    }

    public void getPersonName() {
        String bro = getString(R.string.emailOne);
        String men = getString(R.string.emailTwo);

        if (Objects.equals(firebaseUser.getEmail(), bro)) {
            email.setText(R.string.personOne);
        }
        if (Objects.equals(firebaseUser.getEmail(), men)) {
            email.setText(R.string.personTwo);
        }
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
                Intent back = new Intent(getContext(), MainActivity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }

    private void methodCard() {
        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent live = new Intent(getContext(), LiveMonitor.class);
                live.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(live);
            }
        });

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Currently notifications not ready", Toast.LENGTH_SHORT).show();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Currently the feature not available", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
