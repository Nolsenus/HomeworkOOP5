import java.util.Scanner;

public class ConsoleUI implements ContactBookUI {

    private final Scanner in;

    public ConsoleUI() {
        in = new Scanner(System.in);
    }

    @Override
    public void showMenu() {
        System.out.println("Команды:");
        System.out.println("0 - выход;");
        System.out.println("1 - показать справочник;");
        System.out.println("2 - добавить контакт;");
        System.out.println("3 - добавить номер в контакт;");
        System.out.println("4 - изменить имя контакта;");
        System.out.println("5 - изменить первый номер в контакте;");
        System.out.println("6 - изменить указанный номер в контакте;");
        System.out.println("7 - удалить номер из контакта;");
        System.out.println("8 - удалить контакт из справочника;");
        System.out.println("9 - экспортировать справочник в указанном формате;");
        System.out.println("10 - импортировать справочник из указанного файла.");
    }

    @Override
    public int getCommand() throws NotACommandException {
        System.out.println("Введите номер команды: ");
        String commString = in.nextLine();
        boolean isGood = true;
        int num = 0;
        try {
            num = Integer.parseInt(commString);
            if (num < 0 || num > 10) {
                isGood = false;
            }
        } catch (NumberFormatException e) {
            isGood = false;
        }
        if (isGood) {
            return num;
        }
        throw new NotACommandException("Некорректная команда.");
    }

    @Override
    public void showString(String message) {
        System.out.println(message);
    }

    @Override
    public Contact enterContact(String caption) throws NotANumberException {
        System.out.println(caption);
        String name = enterString("Введите имя контакта: ");
        PhoneNumber number = enterNumber("Введите номер контакта: ");
        return new Contact(name, number);
    }

    @Override
    public PhoneNumber enterNumber(String caption) throws NotANumberException {
        System.out.print(caption);
        String number = in.nextLine();
        return new PhoneNumber(number);
    }

    @Override
    public String enterString(String caption) {
        System.out.print(caption);
        return in.nextLine();
    }

    @Override
    public boolean enterBoolean(String caption) {
        System.out.print(caption);
        return in.nextLine().equalsIgnoreCase("да");
    }
}
