package entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity(tableName = "assessments")
public class Assessment {

    @PrimaryKey (autoGenerate = true)
    public Integer assID;
    @ColumnInfo
    public Integer classID;
    @ColumnInfo
    public String assTitle;
    @ColumnInfo
    public AssessmentType assType;
    @ColumnInfo
    public LocalDate startDate;
    @ColumnInfo
    public LocalDate endDate;

    public Assessment(Integer assID, Integer classID, String assTitle, AssessmentType assType,
                      LocalDate startDate, LocalDate endDate) {
        this.assID = assID;
        this.classID = classID;
        this.assTitle = assTitle;
        this.assType = assType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
