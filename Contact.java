package contacts;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Contact
{
    private String phoneNumber;
    private final LocalDateTime creationDate;
    private LocalDateTime lastEditDate;
    private final boolean isPerson;

    public Contact(String phoneNumber, boolean isPerson)
    {
        this.phoneNumber = phoneNumber;
        creationDate = LocalDateTime.now();
        lastEditDate = LocalDateTime.now();
        this.isPerson = isPerson;
    }

    abstract String getFullName();

    public String getPhoneNumber()
    {
        return Objects.requireNonNullElse(phoneNumber, "[no data]");
    }

    public void setPhoneNumber(String newPhoneNumber)
    {
        if (Validator.validatePhoneNumber(newPhoneNumber))
        {
            this.phoneNumber = newPhoneNumber;
        } else
        {
            this.phoneNumber = "[no number]";
        }
    }

    public boolean hasPhoneNumber()
    {
        return phoneNumber != null;
    }

    public boolean isPerson()
    {
        return isPerson;
    }

    public LocalDateTime getCreationDate()
    {
        return creationDate;
    }

    public LocalDateTime getLastEditDate()
    {
        return lastEditDate;
    }

    public void setLastEditDate(LocalDateTime lastEditDate)
    {
        this.lastEditDate = lastEditDate;
    }

    @Override
    public String toString()
    {
        return "Number: " + getPhoneNumber() + "\n" +
                "Time created: " + creationDate.truncatedTo(ChronoUnit.MINUTES) + "\n" +
                "Time last edit: " + lastEditDate.truncatedTo(ChronoUnit.MINUTES);
    }
}
