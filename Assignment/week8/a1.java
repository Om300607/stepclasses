import java.util.*;

public class a1 {
    interface Action {
        void run();
    }

    static void attempt(String prefix, Action action) {
        try {
            action.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(prefix + e.getMessage() + ".");
        }
    }

    enum State {
        OPEN, JUDGING, PUBLISHED
    }

    interface Track {
        String getName();
        double finalScore(Score score);
    }

    static class InnovationTrack implements Track {
        @Override
        public String getName() {
            return "Innovation";
        }

        @Override
        public double finalScore(Score s) {
            return s.getIdea() * 0.5 + s.getExecution() * 0.3 + s.getPresentation() * 0.2;
        }
    }

    static class OpenTrack implements Track {
        @Override
        public String getName() {
            return "Open";
        }

        @Override
        public double finalScore(Score s) {
            return (s.getIdea() + s.getExecution() + s.getPresentation()) / 3.0;
        }
    }

    static class Student {
        private final String name;

        Student(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Student)) {
                return false;
            }
            return name.equals(((Student) o).name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    static class Score {
        private final Judge judge;
        private final double idea;
        private final double execution;
        private final double presentation;

        Score(Judge judge, double idea, double execution, double presentation) {
            check(idea, "Idea");
            check(execution, "Execution");
            check(presentation, "Presentation");
            this.judge = judge;
            this.idea = idea;
            this.execution = execution;
            this.presentation = presentation;
        }

        private static void check(double value, String label) {
            if (value < 0 || value > 10) {
                throw new IllegalArgumentException(label + " rating must be between 0 and 10");
            }
        }

        Judge getJudge() {
            return judge;
        }

        double getIdea() {
            return idea;
        }

        double getExecution() {
            return execution;
        }

        double getPresentation() {
            return presentation;
        }
    }

    static class Project {
        private final String title;
        private final Team team;
        private Score score;

        Project(String title, Team team) {
            this.title = title;
            this.team = team;
        }

        String getTitle() {
            return title;
        }

        Team getTeam() {
            return team;
        }

        Score getScore() {
            return score;
        }

        void setScore(Score score) {
            this.score = score;
        }
    }

    static class Team {
        private final String name;
        private final Track track;
        private final List<Student> members;
        private Project project;

        Team(String name, Track track, List<Student> members) {
            this.name = name;
            this.track = track;
            this.members = new ArrayList<>(members);
        }

        String getName() {
            return name;
        }

        Track getTrack() {
            return track;
        }

        List<Student> getMembers() {
            return Collections.unmodifiableList(members);
        }

        Project getProject() {
            return project;
        }

        void setProject(Project project) {
            this.project = project;
        }
    }

    static class Judge {
        private final String name;

        Judge(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        void score(Hackathon hackathon, String projectTitle, double idea, double execution, double presentation) {
            hackathon.recordScore(this, projectTitle, idea, execution, presentation);
        }
    }

    static class Hackathon {
        private final String name;
        private State state = State.OPEN;
        private final List<Team> teams = new ArrayList<>();
        private final Set<Student> registeredStudents = new HashSet<>();
        private final Map<String, Project> projects = new LinkedHashMap<>();

        Hackathon(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        State getState() {
            return state;
        }

        Team findTeam(String teamName) {
            for (Team team : teams) {
                if (team.getName().equals(teamName)) {
                    return team;
                }
            }
            return null;
        }

        Team registerTeam(String teamName, Track track, List<Student> members) {
            if (state != State.OPEN) {
                throw new IllegalStateException("Registration is closed");
            }
            if (findTeam(teamName) != null) {
                throw new IllegalArgumentException("Team name " + teamName + " is already taken");
            }
            if (members.size() < 2 || members.size() > 4) {
                throw new IllegalArgumentException("A team must have 2 to 4 members");
            }
            if (new HashSet<>(members).size() != members.size()) {
                throw new IllegalArgumentException("A student cannot be listed twice in a team");
            }
            for (Student student : members) {
                if (registeredStudents.contains(student)) {
                    throw new IllegalArgumentException(student.getName() + " is already registered in another team");
                }
            }
            Team team = new Team(teamName, track, members);
            teams.add(team);
            registeredStudents.addAll(members);
            System.out.println("Team " + teamName + " registered (" + members.size() + " members, " + track.getName() + " track).");
            return team;
        }

        void submitProject(String teamName, String title) {
            if (state == State.PUBLISHED) {
                throw new IllegalStateException("Results have already been published");
            }
            Team team = findTeam(teamName);
            if (team == null) {
                throw new IllegalArgumentException("Team " + teamName + " is not registered");
            }
            if (team.getProject() != null) {
                throw new IllegalStateException(teamName + " has already submitted a project");
            }
            if (projects.containsKey(title)) {
                throw new IllegalArgumentException("Project title '" + title + "' is already used");
            }
            Project project = new Project(title, team);
            team.setProject(project);
            projects.put(title, project);
            System.out.println("Project '" + title + "' submitted by " + teamName + ".");
        }

        void recordScore(Judge judge, String title, double idea, double execution, double presentation) {
            if (state == State.PUBLISHED) {
                throw new IllegalStateException("Results have already been published");
            }
            Project project = projects.get(title);
            if (project == null) {
                throw new IllegalArgumentException("Project '" + title + "' was not found");
            }
            Score score = new Score(judge, idea, execution, presentation);
            project.setScore(score);
            state = State.JUDGING;
            double finalScore = project.getTeam().getTrack().finalScore(score);
            System.out.println(String.format(Locale.US, "Score recorded for '%s'. Final score: %.2f.", title, finalScore));
        }

        void publish() {
            if (state == State.PUBLISHED) {
                throw new IllegalStateException("Results have already been published");
            }
            state = State.PUBLISHED;
            System.out.println("Results published.");
        }
    }

    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon("Code Sprint");
        Judge judge = new Judge("Judge 1");
        Track innovation = new InnovationTrack();
        Track open = new OpenTrack();

        attempt("Registration failed: ", () -> hackathon.registerTeam("ByteBusters", innovation,
                Arrays.asList(new Student("Asha"), new Student("Ravi"), new Student("Neha"))));
        attempt("Registration failed: ", () -> hackathon.registerTeam("SoloCoder", open,
                Arrays.asList(new Student("Kiran"))));
        attempt("Submission failed: ", () -> hackathon.submitProject("ByteBusters", "SmartAttend"));
        attempt("Scoring failed: ", () -> judge.score(hackathon, "SmartAttend", 8, 7, 9));
        attempt("Publish failed: ", () -> hackathon.publish());
        attempt("Rescore rejected: ", () -> judge.score(hackathon, "SmartAttend", 10, 7, 9));
    }
}