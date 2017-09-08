package io.github.alamo18.updater.model.json;

import android.os.Parcel;
import android.os.Parcelable;

public class Repo implements Parcelable {

    private String name;
    private String jsonurl;
    private String dev;

    public Repo() {}

    private Repo(Parcel in) {
        name = in.readString();
        jsonurl = in.readString();
        dev = in.readString();
    }

    public static final Creator<Repo> CREATOR = new Creator<Repo>() {
        @Override
        public Repo createFromParcel(Parcel in) {
            return new Repo(in);
        }

        @Override
        public Repo[] newArray(int size) {
            return new Repo[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getJsonLocation() {
        return jsonurl;
    }

    public String getDev() {
        return dev;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(jsonurl);
        parcel.writeString(dev);
    }
}
