package agenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class Termination {

    private final LocalDate terminationDate;
    private final long numberOfOccurrences;

    public LocalDate terminationDateInclusive() {
        if (terminationDate != null) {
            return terminationDate;
        }
        throw new IllegalStateException("No termination date defined.");
    }

    public long numberOfOccurrences() {
        return numberOfOccurrences;
    }


    /**
     * Constructs a fixed termination event ending at a given date
     * @param start the start time of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param terminationInclusive the date when this event ends
     * @see ChronoUnit#between(Temporal, Temporal)
     */
    public Termination(LocalDate start, ChronoUnit frequency, LocalDate terminationInclusive) {
        this.terminationDate = terminationInclusive;
        this.numberOfOccurrences = ChronoUnit.DAYS.between(start, terminationInclusive) / frequency.getDuration().toDays() + 1;
    }

    /**
     * Constructs a fixed termination event ending after a number of iterations
     * @param start the start time of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param numberOfOccurrences the number of occurrences of this repetitive event
     */
    public Termination(LocalDate start, ChronoUnit frequency, long numberOfOccurrences) {
        this.numberOfOccurrences = numberOfOccurrences;
        this.terminationDate = start.plus((numberOfOccurrences - 1) * frequency.getDuration().toDays(), ChronoUnit.DAYS);
    }

}
