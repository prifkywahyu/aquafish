package com.mobile.aquafish.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.aquafish.FragmentMain;
import com.mobile.aquafish.InputFilterMinMax;
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
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<FeederModel> call, @NotNull Response<FeederModel> response) {
                if (response.body() != null) {
                    first.setVisibility(View.GONE);
                    createFeed.setEnabled(false);

                    String getStartHr = response.body().getStartHour();
                    int start = Integer.valueOf(getStartHr);
                    String getStartMn = response.body().getStartMin();
                    String getEndHr = response.body().getEndHour();
                    String getEndMn = response.body().getEndMin();
                    String getDelay = response.body().getDelay();
                    Log.d(TAG, "Feed schedule successfully received data");

                    if (start >= 0 && start <= 9) {
                        startOne.setText("0" + getStartHr);
                        startTwo.setText(getStartMn);
                        endOne.setText(getEndHr);
                        endTwo.setText(getEndMn);
                        weightDelay.setText(getDelay);
                    }
                    else {
                        startOne.setText(getStartHr);
                        startTwo.setText(getStartMn);
                        endOne.setText(getEndHr);
                        endTwo.setText(getEndMn);
                        weightDelay.setText(getDelay);
                    }
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

        TextView title = view.findViewById(R.id.createTitle);
        title.setText(R.string.create_feed);
        final EditText gram = view.findViewById(R.id.feedGram);
        final EditText secondHr = view.findViewById(R.id.secondHour);
        final EditText secondMn = view.findViewById(R.id.secondMinute);

        final EditText firstHr = view.findViewById(R.id.firstHour);
        firstHr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int parseValue = 0;

                try {
                    parseValue = Integer.parseInt(firstHr.getText().toString());
                    int out = parseValue+8;
                    secondHr.setText(String.valueOf(out));
                } catch (NumberFormatException e) {
                    firstHr.setText(String.valueOf(parseValue));
                    secondHr.setText(String.valueOf(parseValue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final EditText firstMn = view.findViewById(R.id.firstMinute);
        firstMn.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        firstMn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                secondMn.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
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
        saved.setText(R.string.save);
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<FeederModel> feederData = apiInterface.postSchedule(firstHr.getText().toString(), firstMn.getText().toString(),
                        secondHr.getText().toString(), secondMn.getText().toString(), gram.getText().toString());
                feederData.enqueue(new Callback<FeederModel>() {
                    @Override
                    public void onResponse(@NotNull Call<FeederModel> call, @NotNull Response<FeederModel> response) {
                        if (response.body() != null) {
                            dialog.dismiss();

                            Log.d(TAG, "Successfully sent data to database.");
                            Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), "Success to create scheduler!", Toast.LENGTH_SHORT).show();

                            final Handler refresh = new Handler();
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    getFeedingData();
                                    refresh.postDelayed(this, 2500);
                                }
                            }; refresh.postDelayed(runnable, 2500);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<FeederModel> call, @NotNull Throwable throwable) {
                        Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, throwable.toString());
                    }
                });
            }
        });
    }

    public void dialogUpdate() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.schedule_popup, null);
        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView title = view.findViewById(R.id.createTitle);
        title.setText(R.string.update_feed);
        final EditText gram = view.findViewById(R.id.feedGram);
        final EditText secondHr = view.findViewById(R.id.secondHour);
        final EditText secondMn = view.findViewById(R.id.secondMinute);

        final EditText firstHr = view.findViewById(R.id.firstHour);
        firstHr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int parseValue = 0;

                try {
                    parseValue = Integer.parseInt(firstHr.getText().toString());
                    int out = parseValue+8;
                    secondHr.setText(String.valueOf(out));
                } catch (NumberFormatException e) {
                    firstHr.setText(String.valueOf(parseValue));
                    secondHr.setText(String.valueOf(parseValue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final EditText firstMn = view.findViewById(R.id.firstMinute);
        firstMn.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        firstMn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                secondMn.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FeederModel> feederGet = apiInterface.getFeederData();
        feederGet.enqueue(new Callback<FeederModel>() {
            @Override
            public void onResponse(@NotNull Call<FeederModel> call, @NotNull Response<FeederModel> response) {
                if (response.body() != null) {
                    String getStartHr = response.body().getStartHour();
                    String getStartMn = response.body().getStartMin();
                    String getEndHr = response.body().getEndHour();
                    String getEndMn = response.body().getEndMin();
                    String getDelay = response.body().getDelay();
                    Log.d(TAG, "Feed schedule successfully received data");

                    firstHr.setText(getStartHr);
                    firstMn.setText(getStartMn);
                    secondHr.setText(getEndHr);
                    secondMn.setText(getEndMn);
                    gram.setText(getDelay);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeederModel> call, @NotNull Throwable throwable) {
                Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, throwable.toString());
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
        saved.setText(R.string.update);
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<FeederModel> feederData = apiInterface.postSchedule(firstHr.getText().toString(), firstMn.getText().toString(),
                        secondHr.getText().toString(), secondMn.getText().toString(), gram.getText().toString());
                feederData.enqueue(new Callback<FeederModel>() {
                    @Override
                    public void onResponse(@NotNull Call<FeederModel> call, @NotNull Response<FeederModel> response) {
                        if (response.body() != null) {
                            dialog.dismiss();

                            Log.d(TAG, "Successfully update data to database.");
                            Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), "Success to update scheduler!", Toast.LENGTH_SHORT).show();

                            final Handler refresh = new Handler();
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    getFeedingData();
                                    refresh.postDelayed(this, 2500);
                                }
                            }; refresh.postDelayed(runnable, 2500);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<FeederModel> call, @NotNull Throwable throwable) {
                        Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, throwable.toString());
                    }
                });
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
                dialogUpdate();
                break;
        }
    }
}
