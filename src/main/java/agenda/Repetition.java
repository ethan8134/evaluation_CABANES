package agenda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Repetition {
    private final ChronoUnit myFrequency;
    private Termination termination;
    private final List<LocalDate> exceptions = new ArrayList<>();

    public ChronoUnit getFrequency() {
        return myFrequency;
    }

    /**
     * Stores the frequency of this repetition, one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     */

    public Repetition(ChronoUnit myFrequency) {
        this.myFrequency = myFrequency;
    }

    /**
     * Les exceptions à la répétition
     * @param date un date à laquelle l'événement ne doit pas se répéter
     */
    public void addException(LocalDate date) {
        exceptions.add(date);
    }

    /**
     * La terminaison d'une répétition (optionnelle)
     * @param termination la terminaison de la répétition
     */
    public void setTermination(Termination termination) {
        this.termination = termination;

    }

    public Termination getTermination() {
        return termination;
    }

    public boolean isInDay(LocalDate aDay, LocalDate startDate, List<LocalDate> eventExceptions) {
        if (termination != null && aDay.isAfter(termination.terminationDateInclusive())) {
            return false;
        }

        if (eventExceptions.contains(aDay) || exceptions.contains(aDay)) {
            return false;
        }

        long diff = ChronoUnit.DAYS.between(startDate, aDay);
        boolean isRepetitionDay = diff >= 0 && diff % myFrequency.getDuration().toDays() == 0;

        if (termination != null && termination.terminationDateInclusive().isEqual(aDay)) {
            return true;
        }

        return isRepetitionDay;
    }
}
