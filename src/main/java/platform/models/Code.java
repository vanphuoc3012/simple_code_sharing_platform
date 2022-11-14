package platform.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
public class Code implements Cloneable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonView(View.CodeAndDate.class)
    private String code;

    @JsonProperty
    @JsonView(View.CodeAndDate.class)
    private String date;

    @NotNull
    private int time;

    @NotNull
    private int views;

    @JsonIgnore
    private boolean restrictTime = true;

    @JsonIgnore
    private boolean restrictViews = true;

    public boolean isRestrictTime() {
        return restrictTime;
    }

    public void setRestrictTime(boolean restrictTime) {
        this.restrictTime = restrictTime;
    }

    public boolean isRestrictViews() {
        return restrictViews;
    }

    public void setRestrictViews(boolean restrictViews) {
        this.restrictViews = restrictViews;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss.nn");
        this.date = date.format(formatter);
    }



    public void setDate(String date) {
        this.date = date;
    }

    public String idReturnJson() {
        return "{ \"id\" : \""+id+"\" }";
    }


    public int timeAlive() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss.nn");
        LocalDateTime timeCreate = LocalDateTime.parse(this.getDate(), formatter);
        int duration = (int) Duration.between(timeCreate, LocalDateTime.now()).toSeconds();
        return this.getTime()-duration;
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", views=" + views +
                '}';
    }


}
