package agenda;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Event {

    /**
     * The myTitle of this event
     */
    private String myTitle;
    
    /**
     * The starting time of the event
     */
    private LocalDateTime myStart;

    /**
     * The durarion of the event 
     */
    private Duration myDuration;

    private Repetition repetition;
    private List<LocalDate> exceptions = new ArrayList<>();


    /**
     * Constructs an event
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     */
    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
    }

    public void setRepetition(ChronoUnit frequency) {
        this.repetition = new Repetition(frequency);
    }

    public void addException(LocalDate date) {
        if (repetition != null) {
            repetition.addException(date);
        } else {
            throw new IllegalStateException("Cet évènement n'a pas de répétitions définies.");
        }
    }

    public void setTermination(LocalDate terminationInclusive) {
        if (repetition != null) {
            repetition.setTermination(new Termination(myStart.toLocalDate(), repetition.getFrequency(), terminationInclusive));
        } else {
            throw new IllegalStateException("Cet évènement n'a pas de répétitions définies.");
        }
    }

    public void setTermination(long numberOfOccurrences) {
        if (repetition != null) {
            repetition.setTermination(new Termination(myStart.toLocalDate(), repetition.getFrequency(), numberOfOccurrences));
        } else {
            throw new IllegalStateException("Cet évènement n'a pas de répétitions définies.");
        }
    }

    public int getNumberOfOccurrences() {
        if (repetition != null) {
            return (int) repetition.getTermination().numberOfOccurrences();
        }
        throw new IllegalStateException("Cet évènement n'a pas de répétitions définies.");
    }

    public LocalDate getTerminationDate() {
        if (repetition != null) {
            return repetition.getTermination().terminationDateInclusive();
        }
        throw new IllegalStateException("Cet évènement n'a pas de répétitions définies.");
    }

    /**
     * Tests if an event occurs on a given day
     *
     * @param aDay the day to test
     * @return true if the event occurs on that day, false otherwise
     */
    public boolean isInDay(LocalDate aDay) {
        LocalDate startDate = myStart.toLocalDate();
        LocalDate endDate = myStart.plus(myDuration).toLocalDate();

        if ((aDay.isEqual(startDate) || aDay.isAfter(startDate)) && (aDay.isBefore(endDate) || aDay.isEqual(endDate))) {
            return true;
        }

        if (repetition != null) {
            return repetition.isInDay(aDay, myStart.toLocalDate(), exceptions);
        }

        return false;
    }

    /**
     * @return the myTitle
     */
    public String getTitle() {
        return myTitle;
    }

    /**
     * @return the myStart
     */
    public LocalDateTime getStart() {
        return myStart;
    }


    /**
     * @return the myDuration
     */
    public Duration getDuration() {
        return myDuration;
    }

    @Override
    public String toString() {
        return "Event{title='%s', start=%s, duration=%s}".formatted(myTitle, myStart, myDuration);
    }
}
