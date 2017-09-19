package beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Question {
    @XmlElement(name = "question")
    private String question;
    @XmlElement(name = "duration")
    private int duartion;
    @XmlElement(name = "answers")
    private List<String> answers;
    @XmlElement(name = "correctIndex")
    private int correctIndex;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getDuartion() {
        return duartion;
    }

    public void setDuartion(int duartion) {
        this.duartion = duartion;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }
}
