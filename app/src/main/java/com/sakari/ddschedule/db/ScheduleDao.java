package com.sakari.ddschedule.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sakari.ddschedule.model.ScheduleModel;

import java.util.List;

@Dao
public interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ScheduleModel> schedules);

    @Query("DELETE FROM schedule_table")
    void deleteAll();

    @Query("SELECT * FROM schedule_table " +
            "WHERE groups IN (SELECT group_id FROM group_table WHERE isSelected = 1) " +
            "AND scheduled_start_time BETWEEN :start_timestamp AND :end_timestamp " +
            "ORDER BY scheduled_start_time ASC")
    LiveData<List<ScheduleModel>> getSchedules(long start_timestamp, long end_timestamp);

    @Query("SELECT * FROM schedule_table " +
            "WHERE groups IN (SELECT group_id FROM group_table WHERE isSelected = 1) " +
            "AND scheduled_start_time BETWEEN :start_timestamp AND :end_timestamp " +
            "AND streamer_id NOT IN (SELECT streamer_id FROM liver_table WHERE isBlocked=1) " +
            "ORDER BY scheduled_start_time ASC")
    LiveData<List<ScheduleModel>> getSchedules_filter(long start_timestamp, long end_timestamp);

    @Query("SELECT * FROM schedule_table " +
            "WHERE scheduled_start_time BETWEEN :start_timestamp AND :end_timestamp " +
            "ORDER BY scheduled_start_time ASC")
    LiveData<List<ScheduleModel>> getAllSchedules(long start_timestamp, long end_timestamp);

    @Query("SELECT * FROM schedule_table " +
            "WHERE groups IN (SELECT group_id FROM group_table WHERE isSelected = 1) " +
            "AND ((scheduled_start_time - :now_time) >= 0) " +
            "AND ((scheduled_start_time - :now_time) <= :interval_time) " +
            "ORDER BY scheduled_start_time,streamer_name ASC")
    List<ScheduleModel> getNotificationSchedules(long now_time, long interval_time);

    @Query("SELECT * FROM schedule_table " +
            "WHERE groups IN (SELECT group_id FROM group_table WHERE isSelected = 1) " +
            "AND ((scheduled_start_time - :now_time) >= 0) " +
            "AND ((scheduled_start_time - :now_time) <= :interval_time) " +
            "AND streamer_id NOT IN (SELECT streamer_id FROM liver_table WHERE isBlocked=1) " +
            "ORDER BY scheduled_start_time,streamer_name ASC")
    List<ScheduleModel> getNotificationSchedules_filter(long now_time, long interval_time);

}
