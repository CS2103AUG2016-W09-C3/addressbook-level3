package seedu.addressbook.data.person;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.DuplicateDataException;

import java.util.*;

/**
 * A list of persons. Does not allow null elements or duplicates.
 *
 * @see Person#equals(Object)
 * @see Utils#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Person> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePersonException extends DuplicateDataException {
        protected DuplicatePersonException() {
            super("Operation would result in duplicate persons");
        }
    }

    /**
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class PersonNotFoundException extends Exception {}

    private final List<Person> internalList = new ArrayList<>();
    private final List<Person> sortedInternalList = new ArrayList<>();
    
    
    public void sort() {
		ArrayList<String> personName = new ArrayList<String>();
		int size = 0;
		for (size=0; size<internalList.size(); size++){
			personName.add(internalList.get(size).getName().toString());
		}
		
		HashMap<String, Person> sortedList = new HashMap<String, Person>();
		for (int i=0; i<internalList.size(); i++){
			sortedList.put(personName.get(i), internalList.get(i));
		}
		
		List<String> keys = new ArrayList<String>(sortedList.keySet());
		Collections.sort(keys);
		
		HashMap<String, Person> finallySortedList = new HashMap<String, Person>();
		for (int i=0; i<internalList.size(); i++){
			finallySortedList.put(keys.get(i), sortedList.get(keys.get(i)));
			sortedInternalList.add(finallySortedList.get(keys.get(i)));
		}
		
		internalList.clear();
		for (int i=0; i<sortedInternalList.size(); i++){
			internalList.add(sortedInternalList.get(i));
		} 
	}

    /**
     * Constructs empty person list.
     */
    public UniquePersonList() {}

    /**
     * Constructs a person list with the given persons.
     */
    public UniquePersonList(Person... persons) throws DuplicatePersonException {
        final List<Person> initialTags = Arrays.asList(persons);
        if (!Utils.elementsAreUnique(initialTags)) {
            throw new DuplicatePersonException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     * @param persons a collection of persons
     * @throws DuplicatePersonException if the {@code persons} contains duplicate persons
     */
    public UniquePersonList(Collection<Person> persons) throws DuplicatePersonException {
        if (!Utils.elementsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }
        internalList.addAll(persons);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public UniquePersonList(UniquePersonList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * Unmodifiable java List view with elements cast as immutable {@link ReadOnlyPerson}s.
     * For use with other methods/libraries.
     * Any changes to the internal list/elements are immediately visible in the returned list.
     */
    public List<ReadOnlyPerson> immutableListView() {
        return Collections.unmodifiableList(internalList);
    }


    /**
     * Checks if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Person toAdd) throws DuplicatePersonException {
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public void remove(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Clears all persons in list.
     */
    public void clear() {
        internalList.clear();
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && this.internalList.equals(
                        ((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
