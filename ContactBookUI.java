public interface ContactBookUI {

//    Команды:
//    0 - выход;
//    1 - показать справочник;
//    2 - добавить контакт;
//    3 - добавить номер в контакт;
//    4 - изменить имя контакта;
//    5 - изменить первый номер в контакте;
//    6 - изменить указанный номер в контакте;
//    7 - удалить номер из контакта;
//    8 - удалить контакт из справочника;
//    9 - экспортировать справочник в указанном формате.
//    10 - импортировать справочник из файла

    void showString(String message);
    void showMenu();
    int getCommand() throws NotACommandException;
    Contact enterContact(String caption) throws NotANumberException;
    PhoneNumber enterNumber(String caption) throws NotANumberException;
    String enterString(String caption);
    boolean enterBoolean(String caption);
}
