package utils;

import java.io.Serializable;

/**
 * Created by Aisha on 2/14/2018.
 */

public class Idioms implements Serializable {

    private String idiomName;
    private String idiomMeaning;
    private String idiomExample;
    private String idiomCategory;
    private Long uploadDate;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;

    private int randomNo;
    private boolean pushNotification;
    private String notificationText;


    private String idiomsUID;
    private boolean importantIdiom;
    private String idiomDateName;


    public String getIdiomDateName() {
        return idiomDateName;
    }

    public void setIdiomDateName(String idiomDateName) {
        this.idiomDateName = idiomDateName;
    }


    public String getIdiomsUID() {
        return idiomsUID;
    }

    public void setIdiomsUID(String idiomsUID) {
        this.idiomsUID = idiomsUID;
    }

    public String getIdiomName() {
        return idiomName;
    }

    public void setIdiomName(String idiomName) {
        this.idiomName = idiomName;
    }

    public String getIdiomMeaning() {
        return idiomMeaning;
    }

    public void setIdiomMeaning(String idiomMeaning) {
        this.idiomMeaning = idiomMeaning;
    }

    public String getIdiomExample() {
        return idiomExample;
    }

    public void setIdiomExample(String idiomExample) {
        this.idiomExample = idiomExample;
    }

    public String getIdiomCategory() {
        return idiomCategory;
    }

    public void setIdiomCategory(String idiomCategory) {
        this.idiomCategory = idiomCategory;
    }

    public Long getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Long uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getRandomNo() {
        return randomNo;
    }

    public void setRandomNo(int randomNo) {
        this.randomNo = randomNo;
    }

    public boolean isPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public boolean isImportantIdiom() {
        return importantIdiom;
    }

    public void setImportantIdiom(boolean importantIdiom) {
        this.importantIdiom = importantIdiom;
    }
}
