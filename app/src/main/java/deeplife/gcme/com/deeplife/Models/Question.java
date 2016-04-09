package deeplife.gcme.com.deeplife.Models;

/**
 * Created by rog on 11/7/2015.
 */
public class Question {

    String id;
    String category;
    String description;

    String note;
    String mandatory;

    public Question(){

    }

    public Question(String id, String catagory, String desc){
        this.id = id;
        this.category = catagory;
        this.description = desc;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String catagory) {
        this.category = catagory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
