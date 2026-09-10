import java.util.Arrays;
import java.util.regex.Pattern;

public class DischargeSummary {
    private static final Pattern MED_CODE_PATTERN;

    static {
        MED_CODE_PATTERN = Pattern.compile("MED-[A-Z]");
    }

    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (medicationCodes == null) {
            throw new IllegalArgumentException("medicationCodes cannot be null");
        }
        for (String code : medicationCodes) {
            if (code == null || !MED_CODE_PATTERN.matcher(code).matches()) {
                throw new IllegalArgumentException("Invalid medication code: " + code);
            }
        }
        this.patientId = patientId;
        this.medicationCodes = Arrays.copyOf(medicationCodes, medicationCodes.length);
    }

    public String getPatientId() {
        return patientId;
    }

    public String[] getMedicationCodes() {
        return Arrays.copyOf(medicationCodes, medicationCodes.length);
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        String[] updated = Arrays.copyOf(medicationCodes, medicationCodes.length);
        updated[index] = newCode;
        return new DischargeSummary(patientId, updated);
    }

    static String processNightlyBatch(DischargeSummary[] summaries) {
        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        for (DischargeSummary summary : summaries) {
            if (summary == null) {
                nullSkipped++;
                continue;
            }
            if (summary instanceof CriticalCareDischargeSummary) {
                criticalCare++;
            } else {
                routine++;
            }
            processed++;
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + criticalCare + " critical-care | " + routine + " routine";
    }

    public static void main(String[] args) {
        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        System.out.println(d.getMedicationCodes()[0]);

        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println(d.getMedicationCodes()[0]);

        String result = processNightlyBatch(new DischargeSummary[]{
                new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
                null,
                new DischargeSummary("MT002", new String[]{"MED-Y"})
        });
        System.out.println(result);
    }
}

final class CriticalCareDischargeSummary extends DischargeSummary {
    private final int icuDays;

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}