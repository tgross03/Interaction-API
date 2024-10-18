package dev.edgetom.interactions;

import lombok.Getter;
import org.bukkit.event.block.Action;

/**
 * {@link ActionClass} are comprehensions of different {@link Action}.
 */
@Getter
public enum ActionClass {

    /**
     * Click with left mouse button while looking at a block or at the air
     */
    LEFT_CLICK(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK),
    /**
     * Click with right mouse button while looking at a block or at the air
     */
    RIGHT_CLICK(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK),
    /**
     * Click with left/right mouse button while looking at a block or at the air
     */
    CLICK(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK,
            Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK),
    /**
     * Click with left/right mouse button while looking at a block
     */
    BLOCK_CLICK(Action.LEFT_CLICK_BLOCK, Action.RIGHT_CLICK_BLOCK),
    /**
     * Click with left/right mouse button while looking at the air
     */
    AIR_CLICK(Action.LEFT_CLICK_AIR, Action.RIGHT_CLICK_AIR);

    private final Action[] actions;

    ActionClass(Action... actions) {
        this.actions = actions;
    }

}
