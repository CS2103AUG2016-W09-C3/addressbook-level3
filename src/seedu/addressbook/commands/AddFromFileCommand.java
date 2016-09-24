package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddFromFileCommand extends Command {

    public static final String COMMAND_WORD = "addfile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Adds the given text file of people into the address book. \n\t"
            + "Each line in the file should follow the same syntax as the add command:\n"
            + AddCommand.MESSAGE_USAGE;
    
    public static final String MESSAGE_SUCCESS = "File loaded: %1$s\nPeople loaded:\n%2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "There exists duplicate people in the file.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found.";
    public static final String MESSAGE_CANNOT_READ_FILE = "Unable to load file.";

    private final Person toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddFromFileCommand(String name,
                      String phone, boolean isPhonePrivate,
                      String email, boolean isEmailPrivate,
                      String address, boolean isAddressPrivate,
                      Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Person(
                new Name(name),
                new Phone(phone, isPhonePrivate),
                new Email(email, isEmailPrivate),
                new Address(address, isAddressPrivate),
                new UniqueTagList(tagSet)
        );
    }

    public AddFromFileCommand(Person toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyPerson getPerson() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            addressBook.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }
    }

}
