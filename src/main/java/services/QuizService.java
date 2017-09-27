package services;

import beans.Player;
import beans.Question;
import beans.Quiz;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/quizzes/")
public class QuizService {
    private static Map<Integer, Quiz> quizzes = new HashMap<>();
    private static int idTraker = 0;

    @GET
    @Path("/{quizId}")
    @Produces(MediaType.APPLICATION_JSON)
    public static Quiz getQuiz(@PathParam("quizId") int quizId) {
        Quiz quiz = quizzes.get(quizId);
        if (quiz != null) {
            return quiz;
        } else {
            throw new NotFoundException();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static Collection<Quiz> getQuizzes() {
        return quizzes.values();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public static void addQuiz(Quiz quiz) {
        quiz.setId(idTraker);
        quizzes.put(quiz.getId(), quiz);
        idTraker++;
    }

    @DELETE
    @Path("/{quizId}")
    public static void deleteQuiz(@PathParam("quizId") int id) {
        quizzes.remove(id);
    }

    @PATCH
    @Path("/{quizId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public static void patchQuiz(@PathParam("quizId") int id, Quiz quiz) {
        updateQuiz(id, quiz, true);
    }

    @PUT
    @Path("/{quizId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public static void putQuiz(@PathParam("quizId") int id, Quiz quiz) {
        updateQuiz(id, quiz, false);
    }

    //Used for both put and patch
    private static void updateQuiz(int id, Quiz quiz, boolean patch) {
        if (quiz == null) {
            throw new NullPointerException("Quiz object can not be null.");
        }

        Quiz found = quizzes.get(id);
        if (found == null) {
            throw new NotFoundException("Can not find a quiz with id of " + id + ".");
        }

        // Get new data.
        String name = quiz.getName();
        String author = quiz.getAuthor();
        Date startTime = quiz.getStartTime();
        List<Question> questions = quiz.getQuestions();
        List<Player> players = quiz.getPlayers();

        // No remove mode, means to not remove any data only add.
        if (patch) {
            // Update with new information if available.
            found.setName((name != null) ? name : found.getName());
            found.setAuthor((author != null) ? author : found.getAuthor());
            found.setStartTime((startTime != null) ? startTime : found.getStartTime());

            List<Question> foundQuestions = found.getQuestions();
            if (questions != null && foundQuestions != null) {
                // Add new questions and update existing ones.
                for (Question question : questions) {
                    if (!foundQuestions.contains(question)) {
                        foundQuestions.add(question);
                    } else {
                        int index = foundQuestions.indexOf(question);
                        foundQuestions.set(index, question);
                    }
                }
            } else if (questions != null) {
                found.setQuestions(questions);
            }

            List<Player> foundPlayerList = found.getPlayers();
            if (players != null && found.getPlayers() != null) {
                // Add new players and update existing ones.
                for (Player player : players) {
                    if (!foundPlayerList.contains(player)) {
                        foundPlayerList.add(player);
                    } else {
                        int index = foundPlayerList.indexOf(player);
                        foundPlayerList.set(index, player);
                    }
                }
            } else if (players != null) {
                found.setPlayers(players);
            }
        } else {
            found = quiz;
            found.setId(id);
        }

        quizzes.put(id, found);
    }
}
