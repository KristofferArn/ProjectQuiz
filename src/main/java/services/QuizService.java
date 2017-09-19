package services;

import beans.Quiz;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Path("/quizzes/")
public class QuizService {
    private static Map<Integer, Quiz> quizzes = new HashMap<Integer, Quiz>();

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
    public Collection<Quiz> getKunder() {
        return quizzes.values();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addQuiz(Quiz quiz) {
        if (quizzes.get(quiz.getId()) == null) {
            quizzes.put(quiz.getId(), quiz);
        }
    }

    @DELETE
    @Path("/{quizId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteQuiz(Quiz quiz) {
        quizzes.remove(quiz.getId());
    }

    @PUT
    @Path("/{quizId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateKunde(Quiz quiz) {
        if (quizzes.get(quiz.getId()) == null) {
            throw new NotFoundException();
        } else {
            quizzes.put(quiz.getId(), quiz);
        }
    }
}
