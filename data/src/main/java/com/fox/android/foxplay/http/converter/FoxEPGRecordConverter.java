package com.fox.android.foxplay.http.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.fox.android.foxplay.model.EPGRecordEntity;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/31/16.
 */
public class FoxEPGRecordConverter extends EasyDeserializer<EPGRecordEntity> {

    public static final int HOUR_INTERVAL = 1000 * 60 * 60;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy kk:mm:ssZ");

    @Override
    public EPGRecordEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        EPGRecordEntity epgRecordEntity = null;
        if (json != null && json.isJsonObject()) {
            epgRecordEntity = new EPGRecordEntity();
            JsonObject jsonObject = json.getAsJsonObject();
            epgRecordEntity.programName = getStringValue(jsonObject.get("programme"), null);
            String genre = getStringValue(jsonObject.get("genre"), null);
            String subGenre = getStringValue(jsonObject.get("sub_genre"), null);
            String delimeter = null;
            if (genre != null && !genre.trim().isEmpty()) {
                epgRecordEntity.description = genre;
                delimeter = " / ";
            }
            if (subGenre != null && !subGenre.trim().isEmpty()) {
                if (delimeter != null) {
                    epgRecordEntity.description += delimeter;
                }
                epgRecordEntity.description += subGenre;
            }
            String date = getStringValue(jsonObject.get("date"), null);
            String startTime = getStringValue(jsonObject.get("start_time"), null);
            String duration = getStringValue(jsonObject.get("duration"), null);
            if (date != null && startTime != null && duration != null) {
                startTime = normalizeTime(startTime);
                duration = normalizeTime(duration);
                date += " " + startTime + "+0800";//EPG API use Singapore time.
                try {
                    Date programStartTime = sdf.parse(date);
                    epgRecordEntity.startTimeInMs = programStartTime.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                epgRecordEntity.durationInMs = parseDurationToMs(duration);
            }
        }
        return epgRecordEntity;
    }

    private String normalizeTime(String time) {
        if (time.length() <= 5) {
            return time + ":00";
        }
        return time;
    }

    private long parseDurationToMs(String duration) {
        String[] split = duration.split(":");
        long durationInMs = 0;
        int startIndex = 0;
        if (split.length >= 3) {
            int hours = Integer.valueOf(split[0]);
            durationInMs += hours * HOUR_INTERVAL;
            startIndex = 1;
        }
        int minutes = Integer.valueOf(split[startIndex]);
        int seconds = Integer.valueOf(split[startIndex + 1]);
        durationInMs += minutes * 60 * 1000;
        durationInMs += seconds * 1000;
        return durationInMs;
    }
}
