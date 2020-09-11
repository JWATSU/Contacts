package contacts;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ContactHandler
{
    private final Scanner scanner = new Scanner(System.in);
    private final List<Contact> contacts = new ArrayList<>();
    private final Map<String, Runnable> mainMenuChoices;

    public ContactHandler()
    {
        mainMenuChoices = new TreeMap<>();
        mainMenuChoices.put("add", this::addContact);
        mainMenuChoices.put("list", this::listMenu);
        mainMenuChoices.put("search", this::searchForContact);
        mainMenuChoices.put("count", this::displayCountOfContacts);
    }

    public void startMenu()
    {
        while (true)
        {
            System.out.printf("\n[menu] Enter action (%s, exit): ", String.join(", ", mainMenuChoices.keySet()));
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("exit") || !mainMenuChoices.containsKey(choice))
            {
                break;
            }
            mainMenuChoices.get(choice).run();
        }
    }

    private void searchForContact()
    {

    }

    private void displayInfoAboutContact()
    {
        if (!contacts.isEmpty())
        {
            printListOfContacts();
            System.out.print("Enter index to show info: ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            boolean legalIndex = index >= 0 && index < contacts.size();
            if (legalIndex)
            {
                Contact contact = contacts.get(index);
                System.out.println(contact);
            }
        } else
        {
            System.out.println("There are no records to display.");
        }
    }

    private void addContact()
    {
        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine().toLowerCase();
        Contact contact;
        if ("person".equals(type))
        {
            contact = new Person();
        } else if ("organization".equals(type))
        {
            contact = new Organization();
        } else
        {
            System.out.println("Not a valid type.");
            return;
        }
        for (String value : contact.getEditableFields())
        {
            System.out.print("Enter the " + value + ": ");
            contact.updateEditableField(value, scanner.nextLine());
        }

        contacts.add(contact);
        System.out.println("The record was added.");
    }

    /**
     * @return true if the Contact was changed, otherwise false
     */
    private boolean editContact(Contact contact)
    {
        String editableFields = String.join(", ", contact.getEditableFields());
        boolean contactUpdated = false;

        System.out.print("Select a field (" + editableFields + "): ");
        String field = scanner.nextLine().toLowerCase();
        boolean contains = Arrays.asList(contact.getEditableFields()).contains(field);
        if (contains)
        {
            System.out.println("Enter new " + field + ": ");
            String newValue = scanner.nextLine();
            if (contact.updateEditableField(field, newValue))
            {
                contactUpdated = true;
            }
        } else
        {
            System.out.println("Invalid field.");
        }
        return contactUpdated;
    }

    private void listMenu()
    {
        printListOfContacts();
        if (contacts.isEmpty())
        {
            return;
        }
        System.out.print("\n[list] Enter action ([number], back): ");
        String userChoice = scanner.nextLine();
        if (!userChoice.matches("\\d+"))
        {
            return;
        }
        int index = Integer.parseInt(userChoice) - 1;
        boolean isLegalIndex = index >= 0 && index < contacts.size();
        if (isLegalIndex)
        {
            Contact contact = contacts.get(index);
            modifyContactMenu(contact);
        } else
        {
            System.out.println("Invalid index.");
        }
    }

    private void modifyContactMenu(Contact contact)
    {
        boolean continueLoop = true;
        while (continueLoop)
        {
            System.out.println(contact);
            System.out.print("\n[record] Enter action (edit, delete, menu): ");
            String userChoice = scanner.nextLine().toLowerCase();
            switch (userChoice)
            {
                case "edit":
                    if (editContact(contact))
                    {
                        System.out.println("Saved");
                    }
                    break;
                case "delete":
                    deleteContact(contact);
                    System.out.println("Contact removed.");
                    break;
                case "menu":
                    continueLoop = false;
                    break;
                default:
                    System.out.println("Illegal action.");
            }
        }
    }

    private void deleteContact(Contact contact)
    {
        contacts.remove(contact);
    }

    private void printListOfContacts()
    {
        if (contacts.isEmpty())
        {
            System.out.println("There are no records to display.");
        } else
        {
            for (int i = 0; i < contacts.size(); i++)
            {
                System.out.println(i + 1 + ". " + contacts.get(i).getFullName());
            }
        }
    }

    private void displayCountOfContacts()
    {
        System.out.println("The Phone Book has " + contacts.size() + " records.");
    }
}
