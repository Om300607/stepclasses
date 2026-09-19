// Problem 4: Universal Media Launcher

interface Playable {
    String play();
    String play(int fromSecond);   // overload: same name, different parameter list
    String pause();
}

abstract class MediaFile {
    private static int counter = 1000;
    private final String fileId;

    protected MediaFile() {
        fileId = "MF-" + (++counter);
    }

    public abstract String getFormatInfo();

    public String getFileId() {
        return fileId;
    }
}

class AudioFile extends MediaFile implements Playable {
    private final String title;

    public AudioFile(String title) {
        super();
        this.title = title;
    }

    @Override
    public String getFormatInfo() {
        return "Audio file, ID: " + getFileId();
    }

    @Override
    public String play() {
        return "Playing audio: " + title;
    }

    @Override
    public String play(int fromSecond) {
        return "Playing audio: " + title + " from "
                + (fromSecond / 60) + ":" + String.format("%02d", fromSecond % 60);
    }

    @Override
    public String pause() {
        return "Paused audio: " + title;
    }
}

// Not a MediaFile: no fileId, no format info - it only CAN play
class Podcast implements Playable {
    private final String showName;
    private final int episodeNumber;

    public Podcast(String showName, int episodeNumber) {
        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }

    @Override
    public String play() {
        return "Streaming episode " + episodeNumber + " of " + showName;
    }

    @Override
    public String play(int fromSecond) {
        return "Streaming episode " + episodeNumber + " of " + showName
                + " from " + (fromSecond / 60) + ":" + String.format("%02d", fromSecond % 60);
    }

    @Override
    public String pause() {
        return "Paused episode " + episodeNumber + " of " + showName;
    }
}

public class a4 {

    static void launchAll(Playable[] items) {
        for (Playable item : items) {
            System.out.println(item.play());
        }
    }

    public static void main(String[] args) {
        AudioFile a = new AudioFile("Morning Jazz");
        System.out.println(a.play());
        System.out.println(a.play(30));
        System.out.println(a.getFormatInfo());

        Podcast p = new Podcast("Tech Talk", 12);
        System.out.println(p.play());

        Playable ref = a; // upcasting: AudioFile stored as the Playable interface type
        System.out.println(ref.play());

        launchAll(new Playable[]{ref, p});
    }
}