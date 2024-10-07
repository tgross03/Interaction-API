package dev.edgetom.iteminteractions;

import lombok.Getter;
import org.bukkit.event.block.Action;

@Getter
public enum ActionClass {

    LEFT_CLICK(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK),
    RIGHT_CLICK(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK),
    CLICK(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK,
            Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK),
    BLOCK_CLICK(Action.LEFT_CLICK_BLOCK, Action.RIGHT_CLICK_BLOCK);


    private final Action[] actions;

    ActionClass(Action... actions) {
        this.actions = actions;
    }

}
