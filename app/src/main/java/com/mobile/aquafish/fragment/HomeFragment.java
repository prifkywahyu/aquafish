package com.mobile.aquafish.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.aquafish.FragmentMain;
import com.mobile.aquafish.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class HomeFragment extends Fragment {
    TextView date, timeText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View forView = inflater.inflate(R.layout.home_fragment, container, false);

        setHasOptionsMenu(true);
        date = forView.findViewById(R.id.dateText);
        timeText = forView.findViewById(R.id.timeText);
        ((FragmentMain) Objects.requireNonNull(getActivity())).setTitleActionBar("Aqua Home");

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date.setText(currentDate);

        return forView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_reload, menu);
        for (int in = 0; in < menu.size(); in++) {
            Drawable draw = menu.getItem(in).getIcon();
            if (draw != null) {
                draw.mutate();
            }
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
