package deeplife.gcme.com.deeplife.Models;

/**
 * Created by BENGEOS on 2/19/16.
 */
public class ReportItem {
    private String id,Report_ID,Category,Question,Value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getReport_ID() {
        return Report_ID;
    }

    public void setReport_ID(String report_ID) {
        Report_ID = report_ID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }
}
