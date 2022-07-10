package Activities;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.wittenPortfolio.R;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import database.AppDatabase;
import entities.Assessment;
import entities.AssessmentType;
import entities.Course;

public class AddAssessmentActivity extends AppCompatActivity {
    Integer assID;
    Integer classID;
    String assTitle;
    String className;
    String typeName;
    AssessmentType assType;
    LocalDate startDate;
    LocalDate endDate;
    AppDatabase db;
    Button courseSelectBTN;
    Button selectType;
    Button startSelectButton;
    Button endSelectButton;
    AlertDialog ad;
    AlertDialog ad2;
    String[] courses;
    String[] types;
    int selected = -1;
    int selected2 = -1;
    EditText title;
    Calendar myCalendar;
    DatePickerDialog picker;


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        db = AppDatabase.getDbInstance(this.getApplicationContext());

        courseSelectBTN = findViewById(R.id.courseSelectBTN);

        courses = db.courseDAO().getCoursesArray();
        types = getResources().getStringArray(R.array.assessment_types_array);
        title = findViewById(R.id.assessmentTitleInput);
        selectType = findViewById(R.id.typeSelectBTN);

        ad2 = new AlertDialog.Builder(AddAssessmentActivity.this).setSingleChoiceItems(types, selected2,
                (dialog, which) -> {
                    selectType.setText(types[which]);
                    selected2 = which;
                    ad2.dismiss();
                }).setTitle("Choose an Assessment Type").create();
        selectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad2.getListView().setSelection(selected2);
                ad2.show();
            }
        });

        ad = new AlertDialog.Builder(AddAssessmentActivity.this).setSingleChoiceItems(courses, selected,
                (dialog, which) -> {
                    courseSelectBTN.setText(courses[which]);
                    selected = which;
                    ad.dismiss();

                }).setTitle("Choose A Course").create();

        courseSelectBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.getListView().setSelection(selected);
                ad.show();

            }
        });

        //set up datepicker.
        startSelectButton = findViewById(R.id.startSelectBTN);
        endSelectButton = findViewById(R.id.endSelectBTN);

        startSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date picker
                picker = new DatePickerDialog(AddAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate = LocalDate.of(year, month + 1, dayOfMonth);
                        startSelectButton.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        endSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                int month = myCalendar.get(Calendar.MONTH);
                int year = myCalendar.get(Calendar.YEAR);

                //Date picker
                picker = new DatePickerDialog(AddAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate = LocalDate.of(year, month + 1, dayOfMonth);
                        endSelectButton.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });
    }


    public void addAssessmentToDatabase(View view) {
        assID = null;
        className = courseSelectBTN.getText().toString();
        classID = db.courseDAO().getClassID(className);
        assTitle = title.getText().toString();
        typeName = selectType.getText().toString();
        switch (typeName.toLowerCase()) {
            case "test":
                assType = AssessmentType.Test;
                break;
            case "project":
                assType = AssessmentType.Project;
                break;
            case "practical":
                assType = AssessmentType.Practical;
                break;
            case "paper":
                assType = AssessmentType.Paper;
                break;
            default:
                assType = AssessmentType.Test;
        }
        Assessment newAssessment = new Assessment(assID, classID, assTitle, assType, startDate, endDate);
        db.AssessmentDAO().insertAll(newAssessment);
        Toast.makeText(this, "Assessment Added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddAssessmentActivity.this, AssessmentList.class);
        startActivity(intent);
    }


}