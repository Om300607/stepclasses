class DischargeSummary {
    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        this.patientId = patientId;
        if (medicationCodes == null) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.medicationCodes = new String[medicationCodes.length];
        for (int i = 0; i < medicationCodes.length; i++) {
            if (!isValidCode(medicationCodes[i])) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.medicationCodes[i] = medicationCodes[i];
        }
    }

    private boolean isValidCode(String code) {
        return code != null && code.length() == 5 && code.startsWith("MED-") && Character.isUpperCase(code.charAt(4));
    }

    public String[] getMedicationCodes() {
        String[] copy = new String[medicationCodes.length];
        System.arraycopy(medicationCodes, 0, copy, 0, medicationCodes.length);
        return copy;
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length) {
            throw new IllegalArgumentException();
        }
        String[] newCodes = getMedicationCodes();
        newCodes[index] = newCode;
        return new DischargeSummary(this.patientId, newCodes);
    }
}

class CriticalCareDischargeSummary extends DischargeSummary {
    private final int icuDays;

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }
}

public class a5 {
    public static String processNightlyBatch(DischargeSummary[] summaries) {
        int processed = 0;
        int nullSkipped = 0;
        int critical = 0;
        int routine = 0;

        for (DischargeSummary ds : summaries) {
            if (ds == null) {
                nullSkipped++;
            } else {
                processed++;
                if (ds instanceof CriticalCareDischargeSummary) {
                    critical++;
                } else {
                    routine++;
                }
            }
        }
        
        return processed + " processed | " + nullSkipped + " null skipped | " + 
               critical + " critical-care | " + routine + " routine";
    }

    public static void main(String[] args) {
        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println(d.getMedicationCodes()[0]);

        DischargeSummary[] batch = {
            new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
            null,
            new DischargeSummary("MT002", new String[]{"MED-Y"})
        };
        System.out.println(processNightlyBatch(batch));
    }
}