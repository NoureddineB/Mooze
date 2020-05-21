package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.mooze.Fragment.BottomSheet;
import com.project.mooze.Fragment.MainFragment;
import com.project.mooze.Model.Hours.ClosingHour;
import com.project.mooze.Model.Hours.Hours;
import com.project.mooze.Model.Hours.OpeningDay;
import com.project.mooze.Model.Hours.OpeningHour;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;



public class OrderActivity extends AppCompatActivity implements BottomSheet.BottomSheetListener {

    private Disposable disposable;
    private int restaurentid;
    private TextView restaurant_name;
    private TextView restaurant_adress;
    private TextView restaurant_current_state;
    private TextView time_text;
    private TextView text_discount;
    private TextView text_discount_state;
    private TextView text_countdown;
    private ImageView imageView;
    private Button button;
    private int selectedTime = 0;
    private Date dateCompareOne;
    private Date dateCompareTwo;
    private boolean isOpen;
    private Date date;
    public List<OpeningDay> openingDay;
    public List<OpeningHour> openingHours;
    public List<ClosingHour> closingHours;
    boolean takeaway;
    long selected;
    long current;
    public static final String restoID2 = "ID2";
    private SharedPreferences preferences;
    public static final String inputFormat = "HH:mm";
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);


    //OPENING HOURS PROBLEM


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        restaurant_name = findViewById(R.id.text_name);
        restaurant_adress = findViewById(R.id.text_adress);
        restaurant_current_state = findViewById(R.id.text_current_state);
        text_discount_state = findViewById(R.id.text_state);
        text_countdown = findViewById(R.id.text_countdown);
        button = findViewById(R.id.button_order);
        restaurentid = getIntent().getIntExtra(MainFragment.restoID,0);
        preferences = getSharedPreferences("PREFS",0);
        time_text = findViewById(R.id.time_text);
        text_discount = findViewById(R.id.text_discount);
        imageView = findViewById(R.id.imageView7);
        Log.e("TAGERrestoid", String.valueOf(restaurentid));
        getRestaurent();
        getHours();
        openBottomSheet();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("RESTOID", restaurentid);
        editor.apply();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void getHours(){
        this.disposable = MoozeStreams.getHours(restaurentid).subscribeWith(createHours());

    }

    private DisposableObserver<Hours> createHours(){
        return new DisposableObserver<Hours>() {
            @Override
            public void onNext(Hours hours) {
                openingDay = new ArrayList<>();
                closingHours = new ArrayList<>();
                openingHours = new ArrayList<>();
                openingDay = hours.getOpeningDays();
                checkOpeningDay(openingDay);





            }
            @Override
            public void onError(Throwable e) {
                Log.e("TAGERRORYEA", "Error" + e);


            }



            @Override
            public void onComplete() {




            }
        };
    }

    private void getRestaurent(){
        this.disposable = MoozeStreams.getRestaurent(restaurentid).subscribeWith(create());

    }

    private DisposableObserver<Restaurent> create(){
        return new DisposableObserver<Restaurent>() {
            @Override
            public void onNext(Restaurent restaurents) {
                updateUI(restaurents);
                if (restaurents.getDiscount() != null) {
                    String endstr = restaurents.getDiscount().getEndDate();
                    text_discount.setText(restaurents.getDiscount().getDescription());
                    text_countdown.setText(endstr);
                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    try {
                        Date endDate = format.parse(endstr);
                        if (date.after(endDate)){
                            text_discount_state.setText("Expiré");
                            text_discount.setText("Aucune Promotions");
                            imageView.setBackgroundColor(Color.RED);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


            }
            @Override
            public void onError(Throwable e) {
                Log.e("TAGERRORYEA", "Error" + e);


            }



            @Override
            public void onComplete() {




            }
        };
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    public void openRestaurantActivity(View view){
        if (selectedTime == 0 || selected < current){
            Toast.makeText(this,"Choissisez une heure",Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("COUNTDOWN", selectedTime);
            editor.putBoolean("TAKEAWAY",takeaway);
            editor.apply();
            Intent intent = new Intent(OrderActivity.this, RestaurantActivity.class);
            intent.putExtra(restoID2, restaurentid);
            startActivity(intent);
        }
    }

    private void updateUI(Restaurent restaurent){
        restaurant_name.setText(restaurent.getName());
        restaurant_adress.setText(restaurent.getAddress());
    }


    public void getTime(View v) {
        final Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        current = mcurrentTime.getTimeInMillis();
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(OrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time_text.setText( selectedHour + ":" + selectedMinute);
                final Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                selected = calendar.getTimeInMillis();
                selectedTime = (int) (selected - current);

            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public void checkOpeningDay(List<OpeningDay> openingDay) {
        List<String> openingdays = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String today = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());

        for (int i = 0; i < openingDay.size();i++){
            openingdays.add(openingDay.get(i).getDay());
        }
        if (openingdays.contains(today)){
            String hours = new SimpleDateFormat("HH:mm",Locale.ENGLISH).format(date.getTime());
            String openinghouur = openingDay.get(openingdays.indexOf(today)).getOpeningHours().get(0).getHour();
            String closinghouur = openingDay.get(openingdays.indexOf(today)).getClosingHours().get(0).getHour();
           compareDates(hours,closinghouur,openinghouur);
        }else {
            restaurant_current_state.setText("FERMER");
            restaurant_current_state.setTextColor(Color.RED);
            button.setClickable(false);
            button.setBackgroundColor(Color.GRAY);
        }
    }

    private void compareDates(String hours,String closingHours, String openingHours){
        Calendar now = GregorianCalendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        date = parseDate(hour + ":" + minute);
        dateCompareOne = parseDate(closingHours);
        dateCompareTwo = parseDate(openingHours);
        if (openingHours.substring(0,1).equals("9")){
            openingHours = "0"+openingHours;
        }



        try {
            if (isTimeBetweenTwoTime(openingHours,closingHours,hours+ ":" + "00")) {
                restaurant_current_state.setText("OUVERT");
                restaurant_current_state.setTextColor(Color.GREEN);
                button.setClickable(true);
                isOpen = true;

            }else {
                restaurant_current_state.setText("FERMER");
                restaurant_current_state.setTextColor(Color.RED);
                button.setClickable(false);
                button.setBackgroundColor(Color.GRAY);
                isOpen = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Date parseDate(String date) {
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }


    private void openBottomSheet(){
        BottomSheet bottomSheet = new BottomSheet();
        bottomSheet.setCancelable(false);
        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
    }
    public static boolean isTimeBetweenTwoTime(String argStartTime, String argEndTime, String argCurrentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        //
        if (argStartTime.matches(reg) && argEndTime.matches(reg)
                && argCurrentTime.matches(reg)) {
            boolean valid = false;
            // Start Time
            java.util.Date startTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            java.util.Date currentTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            java.util.Date endTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            //
            if (currentTime.compareTo(endTime) < 0) {

                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();

            }

            if (startTime.compareTo(endTime) < 0) {

                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();

            }
            //
            if (currentTime.before(startTime)) {

                System.out.println(" Time is Lesser ");

                valid = false;
            } else {

                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();

                }

                System.out.println("Comparing , Start Time /n " + startTime);
                System.out.println("Comparing , End Time /n " + endTime);
                System.out
                        .println("Comparing , Current Time /n " + currentTime);

                if (currentTime.before(endTime)) {
                    System.out.println("RESULT, Time lies b/w");
                    valid = true;
                } else {
                    valid = false;
                    System.out.println("RESULT, Time does not lies b/w");
                }

            }
            return valid;

        } else {
            throw new IllegalArgumentException(
                    "Not a valid time, expecting HH:MM:SS format");
        }

    }
    @Override
    public void onButtonClicked(boolean takeaway) {
        this.takeaway = takeaway;
    }

}


