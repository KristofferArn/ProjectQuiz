package services;

import beans.Question;
import beans.Quiz;
import beans.Time;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.*;

@Path("/quizzes/")
public class QuizService {
    private static Map<Integer, Quiz> quizzes = new HashMap<>();
    private static int idTraker = 0;

    //For some start values
    static {
        Quiz quiz = new Quiz();
        quiz.setName("The Best Quiz");
        quiz.setAuthor("Joakim Sæther");
        List<Question> questions = new ArrayList<>();
        Question question = new Question();
        question.setQuestion("What is 2+2?");
        ArrayList<String> answers = new ArrayList<>();
        answers.add("2");
        answers.add("Potato");
        answers.add("4");
        question.setAnswers(answers);
        question.setCorrectIndex(2);
        question.setDuartion(5000);
        questions.add(question);
        quiz.setQuestions(questions);
        Time time = new Time();
        time.setMonth(4);
        time.setDay(30);
        time.setHour(20);
        time.setMinute(30);
        quiz.setStartTime(time);
        quizzes.put(idTraker, quiz);
    }

    @GET
    @Path("/{quizId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Quiz getQuiz(@PathParam("quizId") int quizId) {
        Quiz quiz = quizzes.get(quizId);
        if (quiz != null) {
            return quiz;
        } else {
            throw new NotFoundException();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Quiz> getQuizzes() {
        return quizzes.values();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addQuiz(Quiz quiz) {
        quiz.setId(idTraker);
        quizzes.put(quiz.getId(), quiz);
        idTraker++;
    }

    @DELETE
    @Path("/{quizId}")
    public void deleteQuiz(@PathParam("quizId") int id) {
        quizzes.remove(id);
    }


    @PUT
    @Path("/{quizId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateKunde(@PathParam("quizId") int id, Quiz quiz) {
        if (quiz == null) {
            throw new NullPointerException("Quiz object can not be null.");
        }

        Quiz found = quizzes.get(id);
        if (found == null) {
            throw new NotFoundException("Can not find a quiz with id of " + id + ".");
        }

        // Get new data.
        String name = quiz.getName();
        Time startTime = quiz.getStartTime();
        List<Question> questions = quiz.getQuestions();

        // Update with new information if available.
        found.setName((name != null) ? name : found.getName());
        found.setStartTime((startTime != null) ? startTime : found.getStartTime());
        found.setQuestions((questions != null) ? questions : found.getQuestions());

        quizzes.put(id, found);
    }
}
