package menu.domain;

import menu.domain.menu.Category;
import menu.util.StandardRandomGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuRecommendation {

    private static People people;
    private List<String> shuffledCategories;

    public MenuRecommendation(String input) {
        String[] split = validate(input);
        List<Person> result = Arrays.stream(split)
                .map(Person::new)
                .collect(Collectors.toList());
        people = new People(result);
    }

    private String[] validate(String input) {
        String[] split = input.split(",");
        for (String s : split) {
            if (!s.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
                throw new IllegalArgumentException("올바른 입력이 아닙니다. 쉽표로 구분하여 입력해주세요");
            }
        }
        return split;
    }
    public static People getPeople() {
        return people;
    }

    public void addUnavailableMenus(List<String> inputs) {
        people.addUnavailableMeneus(inputs);
    }

    public People getRecommendations(StandardRandomGenerator generator) {
        List<String> categories = Arrays.stream(Category.values())
                .map(Category::getTitle)
                .collect(Collectors.toList());

        shuffledCategories = calculateShuffledCategories(generator, categories);
        people.setShuffledCategories(shuffledCategories, generator);

        return people;
    }

    public List<String> getShuffledCategories() {
        return shuffledCategories;
    }

    private List<String> calculateShuffledCategories(StandardRandomGenerator generator, List<String> categories) {

        List<String> result = new ArrayList<>();
        for (Day value : Day.values()) {
            String generated = generator.generate(categories);
            while (!result.isEmpty() && !validateCount(result, generated)) {
                generated = generator.generate(categories);
            }
            result.add(generated);
        }
        return result;
    }

    private boolean validateCount(List<String> result, String generated) {
        return result.stream().filter(m -> m.equals(generated)).count() <= 2;
    }
}
