package Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wittenPortfolio.R;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import database.AppDatabase;
import entities.Assessment;
import entities.AssessmentType;
import entities.Course;

public class AssessmentDetail extends AppCompatActivity {
    Assessment assessment;
    EditText titleText;
    TextView assIdTv;
    Button spinnerCourseBtn;
    Button spinnerAssTypeBtn;
    Button startDateSelector;
    Button endDateSelector;
    Button editBtn;
    Button cancelBtn;
    Button saveBtn;
    Button alarmBtn;
    TextView alarmConfirmationTv;
    AppDatabase db;
    Integer assID;
    Integer classID;
    String assTitle;
    Course tempCourse;
    String className;
    String typeName;
    AssessmentType assType;
    LocalDate startDate;
    LocalDate endDate;
    String[] courses;
    String[] types;
    List<Course> courseList;
    Course currentCourse = null;
    AlertDialog courseDialog;
    AlertDialog typeDialog;
    int selected = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        //enable back button.
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //Get instance of the database.
        db = AppDatabase.getDbInstance(this.getApplicationContext());
        //Get Object from intent extra.
        assessment = getIntent().getExtras().getParcelable("assessment");
        //set the fields to represent the assessment object.
        setFields();
    }

    private void setFields() {
        //Title
        titleText = findViewById(R.id.assTitleET);
        titleText.setText(assessment.assTitle);
        titleText.setEnabled(false);

        //ID
        assIdTv = findViewById(R.id.assIDTV);
        assIdTv.setText(assessment.assID.toString());
        assID = assessment.assID;

        //Course ID.  Populate Course Spinner
        populateCourseSpinner();

        //Assessment Type.  Populate Type spinner
        populateAssessmentType();
    }

    private void populateAssessmentType() {
        //Set the assessment type to default
        assType = assessment.assType;

        //Populate the spinner with an array from resource files.
        spinnerAssTypeBtn = findViewById(R.id.spinnerAssTypeBTN);
        spinnerAssTypeBtn.setText(assessment.assType.name());
        types = getResources().getStringArray(R.array.assessment_types_array);
        //Create a dialog.
        typeDialog = new AlertDialog.Builder(AssessmentDetail.this).setSingleChoiceItems(types, selected,
                (dialog, which) -> {
                    spinnerAssTypeBtn.setText(types[which]);
                    selected = which;
                    typeDialog.dismiss();
                }).setTitle("Ass Types").create();
        //Set an on click listener.
        spinnerAssTypeBtn.setOnClickListener(v -> {
            typeDialog.getListView().setSelection(selected);
            typeDialog.show();
        });
        spinnerAssTypeBtn.setClickable(false);
        selected = -1;
    }

    private void populateCourseSpinner() {
        //Find the current course association.
        courseList = db.courseDAO().getAllCourses();
        for (Course c : courseList) {
            if (assessment.classID == c.courseID) {
                currentCourse = c;
            }
        }
        if (currentCourse == null) {
            System.out.println("current course not found");
            return;
        }
        //Set a default value for 'Class ID'
        classID = currentCourse.courseID;
        //populate spinner by database generated array.
        spinnerCourseBtn = findViewById(R.id.spinnerCourseBTN);
        spinnerCourseBtn.setText(currentCourse.courseName);
        courses = db.courseDAO().getCoursesArray();
        //Create a dialog.
        courseDialog = new AlertDialog.Builder(AssessmentDetail.this).setSingleChoiceItems(courses, selected,
                (dialog, which) -> {
                    spinnerCourseBtn.setText(courses[which]);
                    selected = which;
                    courseDialog.dismiss();
                }).setTitle(currentCourse.courseName).create();
        //Set an on click listener.
        spinnerCourseBtn.setOnClickListener(v -> {
            courseDialog.getListView().setSelection(selected);
            courseDialog.show();
        });
        spinnerCourseBtn.setClickable(false);
        selected = -1;
    }


    public void makeEditable(View view) {
        titleText.setEnabled(true);
        spinnerCourseBtn.setClickable(true);
        spinnerAssTypeBtn.setClickable(true);


        //Replace the edit button with a cancel button
        editBtn = findViewById(R.id.editBTN);
        editBtn.setVisibility(View.GONE);
        cancelBtn = findViewById(R.id.cancelBTN);
        cancelBtn.setVisibility(View.VISIBLE);
    } //FIXME

    public void saveChanges(View view) {

    }//FIXME

    public void cancelChanges(View view) {
    } //FIXME
}