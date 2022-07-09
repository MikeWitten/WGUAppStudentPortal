package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import dao.AssessmentDAO;
import dao.CourseDAO;
import dao.MentorDAO;
import dao.TermDAO;
import entities.Assessment;
import entities.Course;
import entities.Mentor;
import entities.Term;

@Database(entities = {Assessment.class, Course.class, Mentor.class, Term.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract AssessmentDAO AssessmentDAO();

    public abstract CourseDAO courseDAO();

    public abstract MentorDAO mentorDAO();

    public abstract TermDAO termDAO();

    public static AppDatabase getDbInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "student_portal_db").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
