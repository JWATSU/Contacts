package contacts;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Validator
{
    public static boolean validatePhoneNumber(String phoneNumber)
    {
        if (phoneNumber.matches("\\+?\\(?[a-zA-Z0-9]+\\)?([\\-\\s]([a-zA-Z0-9]{2,}))+|" +
                "\\+?[a-zA-Z0-9]+[\\s\\-]\\(?[a-zA-Z0-9]{2,}\\)?([\\s\\-]([a-zA-Z0-9]{2,}))*|" +
                "\\+?\\(?[a-zA-Z0-9]+\\)?"))
        {
            return true;
        }
        return false;
    }

    public static boolean validateBirthDate(String birthday)
    {
        try
        {
            LocalDate.parse(birthday);
        } catch (DateTimeParseException e)
        {
            return false;
        }
        return true;
    }
}
