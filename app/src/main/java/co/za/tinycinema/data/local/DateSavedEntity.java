package co.za.tinycinema.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import static co.za.tinycinema.data.local.DateSavedEntity.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class DateSavedEntity {

    public static final String TABLE_NAME = "dateSaved";

    @PrimaryKey(autoGenerate = true)
    int id;

    private Date date;

    public DateSavedEntity(int id, Date date) {
        this.id = id;
        this.date = date;
    }
    @Ignore
    public DateSavedEntity(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
