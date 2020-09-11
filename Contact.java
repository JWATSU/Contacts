package contacts;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Contact
{
    private String phoneNumber;
    private final LocalDateTime creationDate;
    private LocalDateTime lastEditDate;

    public Contact()
    {
        creationDate = LocalDateTime.now();
        lastEditDate = LocalDateTime.now();
    }

    public abstract String getFullName();

    public abstract String[] getEditableFields();

    public abstract void updateEditableField(String fieldToUpdate, String newValue);

    public abstract String getEditableFieldValue(String field);


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
            System.out.println("Bad phone number!");
            this.phoneNumber = "[no number]";
        }
    }

    public boolean hasPhoneNumber()
    {
        return phoneNumber != null;
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
