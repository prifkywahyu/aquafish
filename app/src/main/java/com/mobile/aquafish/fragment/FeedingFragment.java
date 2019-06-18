package com.mobile.aquafish.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.mobile.aquafish.FragmentMain;
import com.mobile.aquafish.R;

import java.util.Objects;

public class FeedingFragment extends Fragment {

    public static final String[] selectWeight = {"5 gram", "7 gram", "9 gram"};
    public static final String[] selectType = {"07:00", "08:00"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View forView = inflater.inflate(R.layout.feed_fragment, container, false);

        setHasOptionsMenu(true);
        ((FragmentMain) Objects.requireNonNull(getActivity())).setTitleActionBar("Feed Schedule");

        return forView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.feed_add, menu);
        for (int we = 0; we < menu.size(); we++) {
            Drawable drawing = menu.getItem(we).getIcon();
            if (drawing != null) {
                drawing.mutate();
            }
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_add:
                dialogCreate();
                return true;
        }

        return false;
    }

    public void dialogCreate() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.schedule_create, null);
        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();

        Spinner weight = view.findViewById(R.id.spinnerWeight);
        ArrayAdapter<String> getSelectWeight = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, selectWeight);
        getSelectWeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight.setAdapter(getSelectWeight);
        weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Spinner type = view.findViewById(R.id.spinnerTime);
        ArrayAdapter<String> getSelectType = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, selectType);
        getSelectType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(getSelectType);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Button abort = view.findViewById(R.id.abortFeed);
        abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button saved = view.findViewById(R.id.saveFeed);
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
