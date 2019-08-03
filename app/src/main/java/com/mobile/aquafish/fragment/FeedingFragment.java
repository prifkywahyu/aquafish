package com.mobile.aquafish.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.aquafish.FragmentMain;
import com.mobile.aquafish.R;
import com.mobile.aquafish.model.FeederModel;
import com.mobile.aquafish.rest.ApiClient;
import com.mobile.aquafish.rest.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedingFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = FeedingFragment.class.getSimpleName();
    public static final String[] selectWeight = {"5 gram", "7 gram", "9 gram"};
    public static final String[] selectType = {"07:00", "08:00"};
    Button createFeed, updateFeed;
    LinearLayout first, second, third, fourth;
    TextView startOne, startTwo, endOne, endTwo, weightDelay, writeStart, writeEnd, writeWeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_feed, container, false);

        startOne = mView.findViewById(R.id.startHour);
        startTwo = mView.findViewById(R.id.startMin);
        endOne = mView.findViewById(R.id.endHour);
        endTwo = mView.findViewById(R.id.endMin);
        weightDelay = mView.findViewById(R.id.delay);

        writeStart = mView.findViewById(R.id.startSelect);
        writeEnd = mView.findViewById(R.id.endSelect);
        writeWeight = mView.findViewById(R.id.weightType);
        second = mView.findViewById(R.id.startTime);
        third = mView.findViewById(R.id.endTime);
        fourth = mView.findViewById(R.id.weightSelect);

        first = mView.findViewById(R.id.blankFeed);
        createFeed = mView.findViewById(R.id.addFeed);
        createFeed.setOnClickListener(this);
        updateFeed = mView.findViewById(R.id.updateFeed);
        updateFeed.setOnClickListener(this);
        ((FragmentMain) Objects.requireNonNull(getActivity())).setTitleActionBar("Feed Schedule");
        getFeedingData();

        return mView;
    }

    private void getFeedingData() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<FeederModel> modelCall = apiInterface.getFeederData();
        modelCall.enqueue(new Callback<FeederModel>() {
            @Override
            public void onResponse(@NotNull Call<FeederModel> call, @NotNull Response<FeederModel> response) {
                if (response.body() != null) {
                    first.setVisibility(View.GONE);
                    createFeed.setEnabled(false);

                    String getStartHr = response.body().getStartHour();
                    String getStartMn = response.body().getStartMin();
                    String getEndHr = response.body().getEndHour();
                    String getEndMn = response.body().getEndMin();
                    String getDelay = response.body().getDelay();
                    Log.d(TAG, "Feed schedule successfully received data");

                    startOne.setText(getStartHr);
                    startTwo.setText(getStartMn);
                    endOne.setText(getEndHr);
                    endTwo.setText(getEndMn);
                    weightDelay.setText(getDelay);
                }
                else {
                    writeStart.setVisibility(View.GONE);
                    writeEnd.setVisibility(View.GONE);
                    writeWeight.setVisibility(View.GONE);
                    second.setVisibility(View.GONE);
                    third.setVisibility(View.GONE);
                    fourth.setVisibility(View.GONE);
                    updateFeed.setEnabled(false);
                }
            }

            @Override
            public void onFailure (@NotNull Call <FeederModel> call, @NotNull Throwable t) {
                Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }

    public void dialogCreate() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.schedule_popup, null);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addFeed:
                dialogCreate();
                break;
            case R.id.updateFeed:
                break;
        }
    }
}
