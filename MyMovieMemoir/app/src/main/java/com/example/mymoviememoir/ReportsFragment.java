package com.example.mymoviememoir;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.entity.Person;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportsFragment extends Fragment {
    private BarChart chart;
    private PieChart pieChart;
    String movieYear;
    NetworkConnection networkConnection = null;

    public static ReportsFragment newInstance() {
        ReportsFragment fragment = new ReportsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ReportsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reports, container, false);
        networkConnection = new NetworkConnection();
        chart = view.findViewById(R.id.barChart);
        pieChart = view.findViewById(R.id.pieChart);
        final Spinner yearSpinner = view.findViewById(R.id.yearSpinner);

        Button btnPieGo = view.findViewById(R.id.btn_pieGo);
        Button btnBarGo = view.findViewById(R.id.btn_barGo);
        final EditText startDateText = view.findViewById(R.id.start_DateTxt);
        final EditText endDateText = view.findViewById(R.id.end_DateTxt);

        int personID = Person.getPersonid();
        final String personIDString = String.valueOf(personID);

        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                mcurrentDate.set(Calendar.MONTH, Calendar.JANUARY);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, 9);
                mcurrentDate.set(Calendar.YEAR, 2019);
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        datepicker.setMaxDate(System.currentTimeMillis());
                        selectedmonth = selectedmonth + 1;

                        String sMonth = "";
                        if (selectedmonth < 10) {
                            sMonth = "0" + String.valueOf(selectedmonth);
                        } else {
                            sMonth = String.valueOf(selectedmonth);
                        }
                        startDateText.setText("" + selectedyear + "-" + sMonth + "-" + selectedday);

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                mcurrentDate.set(Calendar.MONTH, Calendar.JANUARY);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, 9);
                mcurrentDate.set(Calendar.YEAR, 2019);
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        datepicker.setMaxDate(System.currentTimeMillis());
                        selectedmonth = selectedmonth + 1;

                        String sMonth = "";
                        if (selectedmonth < 10) {
                            sMonth = "0" + String.valueOf(selectedmonth);
                        } else {
                            sMonth = String.valueOf(selectedmonth);
                        }
                        endDateText.setText("" + selectedyear + "-" + sMonth + "-" + selectedday);

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        btnPieGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = startDateText.getText().toString();
                String endDate = endDateText.getText().toString();

                GetMoviesWatchedTask getMoviesWatchedTask = new GetMoviesWatchedTask();
                //validate form
                if (validateLogin(startDate, endDate)) {
                    getMoviesWatchedTask.execute(personIDString, startDate, endDate);
                }
            }

            private boolean validateLogin(String startDate, String endDate) {
                if (startDate == null || startDate.trim().length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Start date is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (endDate == null || endDate.trim().length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "End date is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

        });

        btnBarGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year1 = movieYear;

                GetTotalMoviesWatchedPerMonthTask getTotalMoviesWatchedPerMonthTask = new GetTotalMoviesWatchedPerMonthTask();
                //validate form
                if (validateLogin(year1)) {
                    getTotalMoviesWatchedPerMonthTask.execute(personIDString, year1);
                }
            }

            private boolean validateLogin(String year) {
                if (year == null || year.trim().length() == 0 || year.equals("Select Year")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Year is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long
                    id) {
                if (position > 0) {
                    movieYear = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


    private class GetMoviesWatchedTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String user = params[0];
            String startDate = params[1];
            String endDate = params[2];
            Log.i("user id 1 ", user);
            return networkConnection.getMoviesWatchedperPostcode(user, startDate, endDate);
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            Type movieListType = new TypeToken<ArrayList<ReportsFragment.TotalMoviesPostcodes>>() {
            }.getType();
            ArrayList<ReportsFragment.TotalMoviesPostcodes> moviesList = gson.fromJson(result, movieListType);

            List<PieEntry> pieChartEntries = new ArrayList<>();
            String postcode = null;
            double sum = 0;
            int total;

            for (int j = 0; j < moviesList.size(); j++) {
                total = Integer.parseInt(moviesList.get(j).getTotalMovies());
                sum += total;
            }

            for (int i = 0; i < moviesList.size(); i++) {
                postcode = moviesList.get(i).getPostcode();

                int total1 = Integer.parseInt(moviesList.get(i).getTotalMovies());
                float per = (float) ((total1 * 100) / sum);

                pieChartEntries.add(new PieEntry(per, postcode));
            }

            PieDataSet set = new PieDataSet(pieChartEntries, "Postcode");

            Description description = pieChart.getDescription();
            set.setColors(ColorTemplate.COLORFUL_COLORS);
            set.setSliceSpace(2f);
            set.setValueTextColor(Color.WHITE);
            set.setValueTextSize(12f);
            set.setValueFormatter(new PercentFormatter());
            description.setEnabled(false);

            PieData data1 = new PieData(set);
            data1.setValueFormatter(new PercentFormatter(pieChart));
            pieChart.setData(data1);
            pieChart.invalidate();
        }
    }

    public class TotalMoviesPostcodes {
        private String postcode;
        private String totalMoviesWatched;

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getTotalMovies() {
            return totalMoviesWatched;
        }

        public void setTotalMovies(String totalMovies) {
            this.totalMoviesWatched = totalMovies;
        }

        @Override
        public String toString() {
            return "TotalMoviesPostcodes [postcode=" + postcode + ", total=" + totalMoviesWatched + "]";
        }

    }

    private class GetTotalMoviesWatchedPerMonthTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String user = params[0];
            String year = params[1];
            return networkConnection.getTotalMoviesWatchedPerMonth(user, year);
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            Type movieListType = new TypeToken<ArrayList<ReportsFragment.TotalMoviesMonth>>() {
            }.getType();
            ArrayList<ReportsFragment.TotalMoviesMonth> moviesList = gson.fromJson(result, movieListType);

            ArrayList<Integer> noOfMovies = new ArrayList<Integer>();
            for (int i = 0; i < moviesList.size(); i++) {
                int no = Integer.parseInt(moviesList.get(i).totalMoviesWatched);
                noOfMovies.add(no);
            }

            List<BarEntry> entries = new ArrayList<>();

            XAxis xAxis = chart.getXAxis();

            ArrayList<String> months = new ArrayList<String>();
            for (int i = 0; i < moviesList.size(); i++) {
                String name = moviesList.get(i).getMonth();
                months.add(name);
            }

            ArrayList<String> monthsTemporary = new ArrayList<String>();
            monthsTemporary.add("Jan");
            monthsTemporary.add("Feb");
            monthsTemporary.add("Mar");
            monthsTemporary.add("Apr");
            monthsTemporary.add("May");
            monthsTemporary.add("Jun");
            monthsTemporary.add("Jul");
            monthsTemporary.add("Aug");
            monthsTemporary.add("Sept");
            monthsTemporary.add("Oct");
            monthsTemporary.add("Nov");
            monthsTemporary.add("Dec");

            for (String mon : months)
            {
                monthsTemporary.remove(mon);
            }

            for (String monthsTemp : monthsTemporary)
            {
                noOfMovies.add(0);
                months.add(monthsTemp);
            }

            ArrayList<String> monthsTemporaryForSorting = new ArrayList<String>();
            monthsTemporaryForSorting.add("Jan");
            monthsTemporaryForSorting.add("Feb");
            monthsTemporaryForSorting.add("Mar");
            monthsTemporaryForSorting.add("Apr");
            monthsTemporaryForSorting.add("May");
            monthsTemporaryForSorting.add("Jun");
            monthsTemporaryForSorting.add("Jul");
            monthsTemporaryForSorting.add("Aug");
            monthsTemporaryForSorting.add("Sept");
            monthsTemporaryForSorting.add("Oct");
            monthsTemporaryForSorting.add("Nov");
            monthsTemporaryForSorting.add("Dec");

            ArrayList<String> months2 = new ArrayList<String>();
            ArrayList<Integer> noOfMovies2 = new ArrayList<Integer>();

            for(String mon : monthsTemporaryForSorting)
            {
                for(int i=0 ; i <= 11; i++)
                {
                    if(mon.equals(months.get(i)))
                    {
                        noOfMovies2.add(noOfMovies.get(i));
                        months2.add(months.get(i));
                    }
                }

            }

            for (int j = 0; j < noOfMovies2.size(); j++) {
                float value = (float) (noOfMovies2.get(j));
                entries.add(new BarEntry(j, value));
            }

            xAxis.setCenterAxisLabels(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.WHITE);
            xAxis.setTextSize(12);
            xAxis.setAxisLineColor(Color.WHITE);
            xAxis.setGranularityEnabled(true);
            xAxis.setCenterAxisLabels(false);
            IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months2);
            xAxis.setGranularity(1f);
            xAxis.setLabelCount(12);
            xAxis.setLabelRotationAngle(-90);
            xAxis.setValueFormatter(formatter);

            BarDataSet bardataset = new BarDataSet(entries, "No Of Movies watched");
            bardataset.setValueTextColor(Color.WHITE);
            bardataset.setValueTextSize(12f);
            BarData data = new BarData(bardataset);
            data.setBarWidth(0.3f);
            chart.getDescription().setEnabled(false);
            bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
            chart.animateXY(2000, 2000);
            chart.setFitBars(true);
            chart.setData(data);
            chart.invalidate();
        }
    }


    public class TotalMoviesMonth {
        private String month;
        private String totalMoviesWatched;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getTotalMovies() {
            return totalMoviesWatched;
        }

        public void setTotalMovies(String totalMovies) {
            this.totalMoviesWatched = totalMovies;
        }

        @Override
        public String toString() {
            return "TotalMoviesPostcodes [month=" + month + ", totalMoviesWatched=" + totalMoviesWatched + "]";
        }

    }

    public class PercentFormatter extends ValueFormatter
    {

        public DecimalFormat mFormat;
        private PieChart pieChart;
        private boolean percentSignSeparated;

        public PercentFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0");
            percentSignSeparated = true;
        }

        // Can be used to remove percent signs if the chart isn't in percent mode
        public PercentFormatter(PieChart pieChart) {
            this();
            this.pieChart = pieChart;
        }

        // Can be used to remove percent signs if the chart isn't in percent mode
        public PercentFormatter(PieChart pieChart, boolean percentSignSeparated) {
            this(pieChart);
            this.percentSignSeparated = percentSignSeparated;
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value) + (percentSignSeparated ? " %" : "%");
        }

        @Override
        public String getPieLabel(float value, PieEntry pieEntry) {
            if (pieChart != null && pieChart.isUsePercentValuesEnabled()) {
                // Converted to percent
                return getFormattedValue(value);
            } else {
                // raw value, skip percent sign
                return mFormat.format(value);
            }
        }

    }

}
