package com.example.hotel_reservation.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.hotel_reservation.R;
import com.example.hotel_reservation.models.Room;
import com.example.hotel_reservation.services.RoomsService;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookingPage extends AppCompatActivity {


    Button bookRoom, back;
    TextView name, description, details;

    RoomsService roomService;
    Room room;
    EditText fromDate,fromTime,toDate,toTime;

    MaterialTimePicker.Builder timePickerBuilder;
    CalendarConstraints.Builder constraintsBuilder;
    MaterialDatePicker.Builder<Pair<Long, Long>> datePickerBuilder;

    TextInputLayout fromDateLayout,fromTimeLayout,toDateLayout,toTimeLayout;

    Button cashButton,cardButton;

    private final SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        constraintsBuilder = new CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now());
        datePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()
                .setCalendarConstraints(constraintsBuilder.build());
        datePickerBuilder.setTitleText("Select Booking Date")
                .setSelection(Pair.create(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                )).build();

        timePickerBuilder  = new MaterialTimePicker.Builder();
        timePickerBuilder.setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Appointment Ending Date")
                .setPositiveButtonText("Confirm")
                .build();


        fromDateLayout  = findViewById(R.id.fromDateLayout);
        fromTimeLayout  = findViewById(R.id.fromTimeLayout);
        toDateLayout  = findViewById(R.id.toDateLayout);
        toTimeLayout  = findViewById(R.id.toTimeLayout);
        timeInstance();
        dateInstance();

        cashButton = findViewById(R.id.cashButton);
        cardButton = findViewById(R.id.cardButton);
        cashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                cardButton.setEnabled(true);
            }
        });
        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                cashButton.setEnabled(true);
            }
        });


        roomService = new RoomsService(this);
        bookRoom = findViewById(R.id.bookRoomButton);
        back = findViewById(R.id.backButton);
        back.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        name = findViewById(R.id.roomName);
        description = findViewById(R.id.roomDescription);
        details = findViewById(R.id.roomDetails);

        bookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(!isEditTextValueValid()) {return;}
                Snackbar.make(bookRoom, "Room booked", Snackbar.LENGTH_SHORT).show();
            }
        });

        try {
            room = roomService.getRoomById(getIntent().getIntExtra("id", 0));
            name.setText(room.getName());
            description.setText(room.getDescription());
            details.setText(room.getId() + " " + room.getBuildingName() + " " + room.getFloorNumber());
        } catch (Error e) {
            Toast.makeText(this, "Room Not Found", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private boolean isEditTextValueValid() {
        boolean isValid = true;
        String fromDate = String.valueOf(this.fromDate.getText());
        String fromTime = String.valueOf(this.fromTime.getText());
        String toDate = String.valueOf(this.toDate.getText());
        String toTime = String.valueOf(this.toTime.getText());


        if(fromDate.isEmpty() || fromDate.isBlank() || toDate.isBlank() || toDate.isEmpty()){
            fromDateLayout.setError("Set a date first");
            toDateLayout.setError("Set a date first");
            return false;
        }
        fromDateLayout.setError(null);
        toDateLayout.setError(null);
        if(fromTime.isEmpty() || fromTime.isBlank() ){
            fromTimeLayout.setError("Set a from time first");
            return false;
        }
        fromTimeLayout.setError(null);
        if(toTime.isEmpty() || toTime.isBlank() ){
            toTimeLayout.setError("Set a to time first");
            return false;
        }
        toTimeLayout.setError(null);

        return isValid;
    }

    private void dateInstance() {
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        toDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now());
                    MaterialDatePicker.Builder<Pair<Long, Long>> datePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()
                            .setCalendarConstraints(constraintsBuilder.build());
                    datePickerBuilder.setTitleText("Select Booking Date")
                            .setSelection(Pair.create(
                                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                                    MaterialDatePicker.todayInUtcMilliseconds()
                            ))
                            .build();
                    MaterialDatePicker<Pair<Long, Long>> materialDatePicker = datePickerBuilder.build();
                    materialDatePicker.showNow(getSupportFragmentManager(),"date1");
                    materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                        @Override
                        public void onPositiveButtonClick(Pair<Long, Long> selection) {
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(selection.first);

                            fromDate.setText(formatter.format(calendar.getTime()));
                            calendar.setTimeInMillis(selection.second);
                            toDate.setText(formatter.format(calendar.getTime()));
                        }
                    });


                }
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MaterialDatePicker<Pair<Long, Long>> materialDatePicker = datePickerBuilder.build();
                materialDatePicker.showNow(getSupportFragmentManager(),"date2");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(selection.first);

                        fromDate.setText(formatter.format(calendar.getTime()));
                        calendar.setTimeInMillis(selection.second);
                        toDate.setText(formatter.format(calendar.getTime()));
                    }
                });
            }
        });
        fromDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now());
                    MaterialDatePicker.Builder<Pair<Long, Long>> datePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()
                            .setCalendarConstraints(constraintsBuilder.build());
                    datePickerBuilder.setTitleText("Select Booking Date")
                            .setSelection(Pair.create(
                                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                                    MaterialDatePicker.todayInUtcMilliseconds()
                            ))
                            .build();
                    MaterialDatePicker<Pair<Long, Long>> materialDatePicker = datePickerBuilder.build();
                    materialDatePicker.showNow(getSupportFragmentManager(),"date1");
                    materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                        @Override
                        public void onPositiveButtonClick(Pair<Long, Long> selection) {
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(selection.first);

                            fromDate.setText(formatter.format(calendar.getTime()));
                            calendar.setTimeInMillis(selection.second);
                            toDate.setText(formatter.format(calendar.getTime()));
                        }
                    });


                }
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MaterialDatePicker<Pair<Long, Long>> materialDatePicker = datePickerBuilder.build();
                materialDatePicker.showNow(getSupportFragmentManager(),"date2");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(selection.first);

                        fromDate.setText(formatter.format(calendar.getTime()));
                        calendar.setTimeInMillis(selection.second);
                        toDate.setText(formatter.format(calendar.getTime()));
                    }
                });
            }
        });

    }

    private void timeInstance() {
        fromTime = findViewById(R.id.fromTime);
        fromTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    MaterialTimePicker materialTimePicker = timePickerBuilder.build();
                    materialTimePicker.showNow(getSupportFragmentManager(),"fragment_tag_from");
                    setUpClickListener("fragment_tag_from",fromTime);
                }
            }
        });
        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker materialTimePicker = timePickerBuilder.build();
                materialTimePicker.showNow(getSupportFragmentManager(),"fragment_tag_from");
                setUpClickListener("fragment_tag_from",fromTime);
            }
        });
        toTime = findViewById(R.id.toTime);
        toTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    MaterialTimePicker materialTimePicker = timePickerBuilder.build();
                    materialTimePicker.showNow(getSupportFragmentManager(),"fragment_tag_to");
                    setUpClickListener("fragment_tag_to",toTime);
                }
            }
        });
        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker materialTimePicker = timePickerBuilder.build();
                materialTimePicker.showNow(getSupportFragmentManager(),"fragment_tag_to");
                setUpClickListener("fragment_tag_to",toTime);
            }
        });
    }

    private void setUpClickListener(String tag,EditText time) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment instanceof MaterialTimePicker) {
            MaterialTimePicker materialTimePicker = (MaterialTimePicker) fragment;
            materialTimePicker.clearOnPositiveButtonClickListeners();
            materialTimePicker.addOnPositiveButtonClickListener(
                    dialog -> {
                        int newHour = materialTimePicker.getHour();
                        int newMinute = materialTimePicker.getMinute();
                        BookingPage.this.onTimeSet(newHour, newMinute,time);
                    });
        }
    }

    private void onTimeSet(int newHour, int newMinute,EditText time) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, newHour);
        cal.set(Calendar.MINUTE, newMinute);
        cal.setLenient(false);
        String format = formatter.format(cal.getTime());
        time.setText(format);
    }

}