package it.unibo.oop.relario.model.quest;

import it.unibo.oop.relario.model.entities.living.MainCharacter;
import it.unibo.oop.relario.model.inventory.InventoryItem;

public final class QuestFactoryImpl implements QuestFactory {

    private QuestImpl createQuest(final String name, final String description, final ObjectiveStrategy... objectives) {
        final QuestImpl quest = new QuestImpl(name, description);
        for (final ObjectiveStrategy objective : objectives) {
            quest.addObjective(objective);
        }
        return quest;
    }

    @Override
    public Quest createCollectItemQuest(final InventoryItem item, final MainCharacter player) {
        return createQuest("", "", new CollectItemObjective(player, item));
    }

    @Override
    public Quest createDefeatEnemyQuest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createDefeatEnemyQuest'");
    }

    @Override
    public Quest createSolvePuzzleQuest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createSolvePuzzleQuest'");
    }

}
