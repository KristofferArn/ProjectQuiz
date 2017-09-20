package services;

import beans.Question;
import beans.Quiz;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.*;

@Path("/quizzes/")
public class QuizService {
    private static Map<Integer, Quiz> quizzes = new HashMap<>();

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
        quizzes.putIfAbsent(quiz.getId(), quiz);
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
        LocalDateTime startTime = quiz.getStartDate();
        List<Question> questions = quiz.getQuestions();

        // Update with new information if available.
        found.setName((name != null) ? name : found.getName());
        found.setStartDate((startTime != null) ? startTime : found.getStartDate());
        found.setQuestions((questions != null) ? questions : found.getQuestions());

        quizzes.put(id, found);
    }
}
