package com.gcme.deeplife.Models;

/**
 * Created by BENGEOS on 2/19/16.
 */
public class ReportItem {
    private String reportID,Text;
    private int Value;

    public ReportItem(String reportID, String text, int value) {
        this.reportID = reportID;
        Text = text;
        Value = value;
    }

    public ReportItem(String reportID) {
        this.reportID = reportID;
        Value = 0;
    }

    public int getValue() {
        return Value;
    }

    public String getReportID() {
        return reportID;
    }

    public String getText() {
        return Text;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public void setText(String text) {
        Text = text;
    }

    public void setValue(int value) {
        Value = value;
    }
}
