package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wittenPortfolio.R;

import java.time.format.DateTimeFormatter;
import java.util.List;

import entities.Assessment;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.MyViewHolder> {

    private Context context;
    private List<Assessment> assessmentList;
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public AssessmentListAdapter(Context context) {
        this.context = context;
    }

    public void setAssessmentList(List<Assessment> assessmentList) {
        this.assessmentList = assessmentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssessmentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.assessment_recycler_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentListAdapter.MyViewHolder holder, int position) {
        holder.columnAssTitle.setText(this.assessmentList.get(position).assTitle);
        holder.columnAssType.setText(this.assessmentList.get(position).assType.name());
        holder.columnAssStart.setText(this.assessmentList.get(position).startDate.format(formatter));
        holder.columnAssEnd.setText(this.assessmentList.get(position).endDate.format(formatter));


    }

    @Override
    public int getItemCount() {
        return this.assessmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView columnAssTitle;
        TextView columnAssType;
        TextView columnAssStart;
        TextView columnAssEnd;

        public MyViewHolder(View view) {
            super(view);
            columnAssTitle = view.findViewById(R.id.columnAssTitle);
            columnAssType = view.findViewById(R.id.columnAssType);
            columnAssStart = view.findViewById(R.id.columnAssStart);
            columnAssEnd = view.findViewById(R.id.columnAssEnd);
        }
    }
}
