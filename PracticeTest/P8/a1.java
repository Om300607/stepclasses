import java.util.*;

abstract class Question {
    int id;
    String text;
    Question(int id, String text) {
        this.id = id;
        this.text = text;
    }
    abstract boolean isCorrect(String answer);
}

class MultipleChoiceQuestion extends Question {
    List<String> options;
    String correct;
    MultipleChoiceQuestion(int id, String text, List<String> options, String correct) {
        super(id, text);
        this.options = options;
        this.correct = correct;
    }
    boolean isCorrect(String answer) {
        return correct.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {
    boolean correct;
    TrueFalseQuestion(int id, String text, boolean correct) {
        super(id, text);
        this.correct = correct;
    }
    boolean isCorrect(String answer) {
        return Boolean.parseBoolean(answer) == correct;
    }
}

class Examination {
    String title;
    List<Question> questions = new ArrayList<>();
    Examination(String title) {
        this.title = title;
    }
    void addQuestion(Question q) {
        questions.add(q);
    }
    Question find(int id) {
        for (Question q : questions) {
            if (q.id == id) return q;
        }
        return null;
    }
}

enum AttemptState {
    STARTED, SUBMITTED
}

class Attempt {
    String student;
    Examination exam;
    AttemptState state;
    Map<Integer, String> answers = new LinkedHashMap<>();

    Attempt(String student, Examination exam) {
        this.student = student;
        this.exam = exam;
        this.state = AttemptState.STARTED;
        System.out.println("Examination '" + exam.title + "' started by " + student + ".");
    }

    void answer(int questionId, String answer) {
        if (state == AttemptState.SUBMITTED) {
            System.out.println("Cannot change answers after submission.");
            return;
        }
        if (exam.find(questionId) == null) {
            System.out.println("Question " + questionId + " does not exist.");
            return;
        }
        answers.put(questionId, answer);
        System.out.println("Question " + questionId + " answered with '" + answer + "'.");
    }

    int score() {
        int s = 0;
        for (Map.Entry<Integer, String> e : answers.entrySet()) {
            Question q = exam.find(e.getKey());
            if (q != null && q.isCorrect(e.getValue())) s++;
        }
        return s;
    }

    void submit() {
        if (state == AttemptState.SUBMITTED) {
            System.out.println("Examination already submitted.");
            return;
        }
        state = AttemptState.SUBMITTED;
        System.out.println("Examination '" + exam.title + "' submitted successfully.");
        System.out.println("Result for '" + exam.title + "' attempt: " + score() + "/" + exam.questions.size() + " correct.");
    }
}

public class a1 {
    public static void main(String[] args) {
        Examination exam = new Examination("Math Quiz");
        exam.addQuestion(new MultipleChoiceQuestion(1, "2 + 2 = ?", Arrays.asList("A. 4", "B. 5", "C. 6"), "A"));
        exam.addQuestion(new MultipleChoiceQuestion(2, "3 x 3 = ?", Arrays.asList("A. 6", "B. 9", "C. 12"), "B"));
        exam.addQuestion(new TrueFalseQuestion(3, "10 is an even number", true));

        Attempt attempt = new Attempt("Student", exam);
        attempt.answer(1, "A");
        attempt.answer(2, "C");
        attempt.submit();
        attempt.answer(2, "B");
    }
}