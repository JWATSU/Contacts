package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Contact implements Serializable
{
    private String phoneNumber;
    private final LocalDateTime creationDate;
    private LocalDateTime lastEditDate;
    private static final long serialVersionUID = 2L;

    public Contact()
    {
        creationDate = LocalDateTime.now();
        lastEditDate = LocalDateTime.now();
    }

    public abstract String getFullName();

    public abstract String[] getEditableFields();

    public abstract boolean updateEditableField(String fieldToUpdate, String newValue);

    public String getPhoneNumber()
    {
        return Objects.requireNonNullElse(phoneNumber, "[no data]");
    }

    public boolean setPhoneNumber(String newPhoneNumber)
    {
        if (validatePhoneNumber(newPhoneNumber))
        {
            this.phoneNumber = newPhoneNumber;
            return true;
        } else
        {
            System.out.println("Bad phone number!");
            this.phoneNumber = "[no data]";
            return false;
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

    public static boolean validatePhoneNumber(String phoneNumber)
    {
        return phoneNumber.matches("\\+?\\(?[a-zA-Z0-9]+\\)?([\\-\\s]([a-zA-Z0-9]{2,}))+|" +
                "\\+?[a-zA-Z0-9]+[\\s\\-]\\(?[a-zA-Z0-9]{2,}\\)?([\\s\\-]([a-zA-Z0-9]{2,}))*|" +
                "\\+?\\(?[a-zA-Z0-9]+\\)?");
    }

    @Override
    public String toString()
    {
        return "Number: " + getPhoneNumber() + "\n" +
                "Time created: " + creationDate.truncatedTo(ChronoUnit.MINUTES) + "\n" +
                "Time last edit: " + lastEditDate.truncatedTo(ChronoUnit.MINUTES);
    }

}
