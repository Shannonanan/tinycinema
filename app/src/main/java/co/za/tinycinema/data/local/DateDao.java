package co.za.tinycinema.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;

import java.util.Date;
import java.util.List;

@Dao
public interface DateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertDate(DateSavedEntity saveMovie);

//    @Query("SELECT * FROM dateSaved WHERE date >= :date")
//    List<DateSavedEntity> checkDate(Date date);

    @Query("SELECT COUNT(id) FROM dateSaved WHERE date >= :date")
    int checkDate(Date date);
}
