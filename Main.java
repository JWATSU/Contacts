package contacts;


public class Main
{
    public static void main(String[] args)
    {
        String filePath = "";
        if (args.length != 0)
        {
            filePath = args[0];
        }
        ContactHandler contactHandler = new ContactHandler(filePath);
        contactHandler.init();
    }
}
