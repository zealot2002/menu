package com.zzy.home.model.bean;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.io.Serializable;


public class GoodsSuggestion implements SearchSuggestion,Serializable {

    private String name;
    private boolean mIsHistory = true;

    public GoodsSuggestion(String suggestion) {
        this.name = suggestion;    }

    public GoodsSuggestion(Parcel source) {
        this.name = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return name;
    }

    public static final Creator<GoodsSuggestion> CREATOR = new Creator<GoodsSuggestion>() {
        @Override
        public GoodsSuggestion createFromParcel(Parcel in) {
            return new GoodsSuggestion(in);
        }

        @Override
        public GoodsSuggestion[] newArray(int size) {
            return new GoodsSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}