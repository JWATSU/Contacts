package contacts;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class ContactHandler
{
    private final Scanner scanner = new Scanner(System.in);
    private final List<Contact> contacts = new ArrayList<>();
    private final Map<String, Runnable> mainMenuChoices = new TreeMap<>();
    private final String filePath;

    public ContactHandler(String filePath)
    {
        this.filePath = filePath;
    }

    public void init()
    {
        mainMenuChoices.put("add", this::addContact);
        mainMenuChoices.put("list", this::listMenu);
        mainMenuChoices.put("search", this::searchForContact);
        mainMenuChoices.put("count", this::displayCountOfContacts);

        if (!filePath.isEmpty())
        {
            try
            {
                File file = new File(filePath);
                if (file.exists() && !file.isDirectory())
                {
                    ArrayList<Contact> deserializedList = (ArrayList<Contact>) SerializationUtils.deserialize(filePath);
                    contacts.addAll(deserializedList);
                } else
                {
                    file.createNewFile();
                }
            } catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        startMenu();
    }

    private void saveState()
    {
        try
        {
            SerializationUtils.serialize(contacts, filePath);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void startMenu()
    {
        while (true)
        {
            System.out.printf("\n[menu] Enter action (%s, exit): ", String.join(", ", mainMenuChoices.keySet()));
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("exit"))
            {
                break;
            } else if (!mainMenuChoices.containsKey(choice))
            {
                System.out.println("Invalid choice.");
                continue;
            }
            mainMenuChoices.get(choice).run();
        }
    }

    private void searchForContact()
    {
        if (contacts.isEmpty())
        {
            System.out.println("There are no records to search for.");
            return;
        }
        System.out.print("Enter search query: ");
        String searchQuery = scanner.nextLine().toLowerCase();
        List<Contact> contactsFound = new ArrayList<>();
        for (Contact contact : contacts)
        {
            if (contact.toString().toLowerCase().contains(searchQuery))
            {
                contactsFound.add(contact);
            }
        }
        if (contactsFound.isEmpty())
        {
            System.out.println("Found 0 results.");
            return;
        }
        System.out.println("Found " + contactsFound.size() + " results:");
        printListOfContacts(contactsFound);
        searchMenu();
    }

    private void searchMenu()
    {
        System.out.print("\n[search] Enter action ([number], back, again): ");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("again"))
        {
            searchForContact();
        } else if (choice.equals("back"))
        {
            return;
        } else if (choice.matches("\\d+"))
        {
            int index = Integer.parseInt(choice) - 1;
            boolean isLegalIndex = index >= 0 && index < contacts.size();
            if (isLegalIndex)
            {
                Contact contact = contacts.get(index);
                modifyContactMenu(contact);
            } else
            {
                System.out.println("Invalid index.");
            }
        } else
        {
            System.out.println("Invalid action.");
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
        if (!filePath.isEmpty())
        {
            saveState();
        }
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
            System.out.print("Enter new " + field + ": ");
            String newValue = scanner.nextLine();
            if (contact.updateEditableField(field, newValue))
            {
                contactUpdated = true;
                contact.setLastEditDate(LocalDateTime.now());
            }
        } else
        {
            System.out.println("Invalid field.");
        }
        if (contactUpdated && !filePath.isEmpty())
        {
            saveState();
        }
        return contactUpdated;
    }

    private void listMenu()
    {
        printListOfContacts(contacts);
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
                    return;
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

    private void printListOfContacts(List<Contact> listToPrint)
    {
        if (contacts.isEmpty())
        {
            System.out.println("There are no records to display.");
        } else
        {
            for (int i = 0; i < listToPrint.size(); i++)
            {
                System.out.println(i + 1 + ". " + listToPrint.get(i).getFullName());
            }
        }
    }

    private void displayCountOfContacts()
    {
        System.out.println("The Phone Book has " + contacts.size() + " records.");
    }


}
