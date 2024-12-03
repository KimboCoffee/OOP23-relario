package it.unibo.oop.relario.model.quest;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    
    private final String name;
    private final String description;
    private final List<ObjectiveStrategy> objectives; // non necessario

    public Quest(final String name, final String description) {
        this.name = name;
        this.description = description;
        this.objectives = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void addObjective(final ObjectiveStrategy objective) {
        this.objectives.add(objective);
    }

    public boolean isCompleted() {
        return this.objectives.stream().allMatch(obj -> obj.check());
    }

    /*public List<Entity> getKeyItems() {
        this.objectives.stream().map(obj -> obj.getKeyItem()).toList();
    }*/

}
