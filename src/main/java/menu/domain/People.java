package menu.domain;

import menu.util.MenuRandomGenerator;

import java.util.Iterator;
import java.util.List;

public class People implements Iterable<Person> {
    private final List<Person> people;

    public People(List<Person> people) {
        this.people = validatePeopleSize(people);
    }

    private List<Person> validatePeopleSize(List<Person> people) {
        if (people.size() < 2 || people.size() > 5) {
            throw new IllegalArgumentException("코치는 최소 2명, 최대 5명까지 식사를 함께 합니다.");
        }
        return people;
    }

    public void addUnavailableMenus(List<String> menus) {
        int index = 0;
        for (Person person : people) {
            person.addUnavailableMenus(menus.get(index++));
        }
    }

    public void setShuffledCategories(List<String> shuffledCategory, MenuRandomGenerator menuRandomGenerator) {
        for (String category : shuffledCategory) {
            for (Person person : people) {
                person.recommendMenu(category, menuRandomGenerator);
            }
        }
    }

    @Override
    public Iterator<Person> iterator() {
        return people.iterator();
    }
}
