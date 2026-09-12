public class a3 {
    public static String validateFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            String extension = filename.substring(dotIndex + 1);
            if (extension.equalsIgnoreCase("pdf") || 
                extension.equalsIgnoreCase("docx") || 
                extension.equalsIgnoreCase("zip")) {
                return "Accepted";
            }
        }
        return "Rejected — invalid file type";
    }

    public static void main(String[] args) {
        System.out.println(validateFileExtension("Assignment1.PDF"));
        System.out.println(validateFileExtension("notes.txt"));
    }
}