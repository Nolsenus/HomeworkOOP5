import java.io.IOException;

public class App {
    private final ContactBook cb;
    private final ContactBookUI cbui;

    public App(ContactBook cb, ContactBookUI cbui) {
        this.cb = cb;
        this.cbui = cbui;
    }

    public App(ContactBook cb) {
        this(cb, new ConsoleUI());
    }

    public App(ContactBookUI cbui) {
        this(new ContactBook(), cbui);
    }

    public App() {
        this(new ContactBook(), new ConsoleUI());
    }

    private void showContactBook() {
        cbui.showString(cb.toString());
    }

    private void addContact() {
        try {
            Contact contact = cbui.enterContact("Введите контакт, который хотите добавить: ");
            cb.add(contact);
            cbui.showString("Контакт добавлен.");
        } catch (NotANumberException | ContactNameOccupiedException e) {
            cbui.showString(e.getMessage());
        }
    }

    private void addContactNumber() {
        String name = cbui.enterString("Введите имя контакта, к которому добавить номер: ");
        try {
            PhoneNumber number = cbui.enterNumber("Введите номер, который хотите добавить: ");
            cb.addContactNumber(name, number);
            cbui.showString("Номер добавлен к контакту.");
        } catch (Exception e) {
            cbui.showString(e.getMessage());
        }
    }

    private void changeContactName() {
        String oldName = cbui.enterString("Введите имя контакта, которое хотите заменить: ");
        String newName = cbui.enterString("Введите имя, которое хотите чтобы носил этот контакт: ");
        try {
            cb.updateContactName(oldName, newName);
            cbui.showString("Имя контакта заменено.");
        } catch (Exception e) {
            cbui.showString(e.getMessage());
        }
    }

    private void changeContactFirstNumber() {
        String name = cbui.enterString("Введите имя контакта, в котором хотите заменить первый номер: ");
        try {
            PhoneNumber number = cbui.enterNumber("Введите новый первый номер контакта: ");
            cb.updateContactFirstNumber(name, number);
            cbui.showString("Номер заменён.");
        } catch (Exception e) {
            cbui.showString(e.getMessage());
        }
    }

    private void changeContactNumber() {
        String name = cbui.enterString("Введите имя контакта, в котором хотите заменить номер: ");
        try {
            PhoneNumber oldNum = cbui.enterNumber("Введите номер, который хотите заменить: ");
            PhoneNumber newNum = cbui.enterNumber("Введите новый номер: ");
            cb.updateContactNumber(name, oldNum, newNum);
            cbui.showString("Номер заменён.");
        } catch (Exception e) {
            cbui.showString(e.getMessage());
        }
    }

    private void removeContactNumber() {
        String name = cbui.enterString("Введите имя контакта, из которого хотите удалить номер: ");
        try {
            PhoneNumber number = cbui.enterNumber("Введите номер, который хотите удалить: ");
            cb.removeContactNumber(name, number);
            cbui.showString("Номер удалён.");
        } catch (Exception e) {
            cbui.showString(e.getMessage());
        }
    }

    private void removeContact() {
        String name = cbui.enterString("Введите имя контакта, который хотите удалить: ");
        try {
            cb.removeContact(name);
            cbui.showString("Контакт удалён.");
        } catch (ContactDoesNotExistException e) {
            cbui.showString(e.getMessage());
        }
    }

    private void export() {
        String filename = cbui.enterString("Введите название файла, куда хотите сохранить справочник: ");
        boolean overwrite = cbui.enterBoolean("Если файл с таким именем существует, " +
                "хотите ли вы перезаписать его?(Да/что-либо кроме да): ");
        boolean allNumbersOnSeparateLines =
                cbui.enterBoolean("Вы хотите чтобы каждый номер был на отдельной строке?(Да/Нет): ");
        try {
            cb.exportToTxt(filename, allNumbersOnSeparateLines, overwrite);
            cbui.showString("Файл создан.");
        } catch (IOException e) {
            cbui.showString(e.getLocalizedMessage());
        }
    }

    private void importFromFile() {
        String filepath = cbui.enterString("Введите полный путь до текстового файла" +
                "(например: C:/Program Files/test/test.txt): ");
        try {
            cb.importFromTxt(filepath);
        } catch (Exception e) {
            cbui.showString(e.getLocalizedMessage());
        }
    }

    private void executeCommand(int command) {
        switch (command) {
            case 1: showContactBook(); break;
            case 2: addContact(); break;
            case 3: addContactNumber(); break;
            case 4: changeContactName(); break;
            case 5: changeContactFirstNumber(); break;
            case 6: changeContactNumber(); break;
            case 7: removeContactNumber(); break;
            case 8: removeContact(); break;
            case 9: export(); break;
            default: importFromFile();
        }
    }

    public void work() {
        cbui.showMenu();
        while (true) {
            boolean isGoodCommand = false;
            while (!isGoodCommand) {
                try {
                    int command = cbui.getCommand();
                    if (command == 0) {
                        return;
                    }
                    isGoodCommand = true;
                    executeCommand(command);
                } catch (NotACommandException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
