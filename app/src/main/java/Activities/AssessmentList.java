package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.wittenPortfolio.R;

import java.util.List;
import java.util.Objects;

import adapters.AssessmentListAdapter;
import database.AppDatabase;
import entities.Assessment;

public class AssessmentList extends AppCompatActivity implements AssessmentListAdapter.OnAssessmentListener {
    private AssessmentListAdapter assessmentListAdapter;
    List<Assessment> assessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        assessments = db.AssessmentDAO().getAllAssessments();

        initRecyclerView();

    }

    private void initRecyclerView() {
        RecyclerView rcView = findViewById(R.id.assessmentRecyclerView);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcView.addItemDecoration(decoration);
        assessmentListAdapter = new AssessmentListAdapter(this, assessments, this);
        rcView.setAdapter(assessmentListAdapter);

    }

    public void toAddAssessmentView(View view) {
        Intent intent = new Intent(AssessmentList.this, AddAssessmentActivity.class);
        startActivity(intent);
    }


    @Override
    public void onAssessmentClick(int position) {
        Assessment a = assessments.get(position);
        System.out.println("title = " + a.assTitle);
        Intent intent = new Intent(AssessmentList.this, AssessmentDetail.class);
        intent.putExtra ("assessment", a);
        startActivity(intent);
    }
}