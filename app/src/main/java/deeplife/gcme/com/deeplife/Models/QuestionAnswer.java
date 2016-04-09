package deeplife.gcme.com.deeplife.Models;

/**
 * Created by rog on 11/12/2015.
 */
public class QuestionAnswer {
    String id;
    String disciple_id;
    String question_id;
    String answer;
    String Build_Phase;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisciple_id() {
        return disciple_id;
    }

    public void setDisciple_id(String disciple_id) {
        this.disciple_id = disciple_id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getBuild_Phase() {
        return Build_Phase;
    }

    public void setBuild_Phase(String build_Phase) {
        Build_Phase = build_Phase;
    }

}
