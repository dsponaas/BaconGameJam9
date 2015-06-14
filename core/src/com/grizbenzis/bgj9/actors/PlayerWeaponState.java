package com.grizbenzis.bgj9.actors;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

/**
 * Created by sponaas on 6/12/15.
 */
public enum PlayerWeaponState implements State<PlayerWeapon> {

    READY() {
        @Override
        public void enter(PlayerWeapon weapon) {}

        @Override
        public void update(PlayerWeapon weapon) {}

        @Override
        public void exit(PlayerWeapon weapon) {}

        @Override
        public boolean onMessage(PlayerWeapon weapon, Telegram telegram) { return false; }
    },

    CHARGING() {
        @Override
        public void enter(PlayerWeapon weapon) {
            weapon.resetChargeTimer();
        }

        @Override
        public void update(PlayerWeapon weapon) {
            weapon.incrementChargeTimer();
        }

        @Override
        public void exit(PlayerWeapon weapon) {}

        @Override
        public boolean onMessage(PlayerWeapon weapon, Telegram telegram) { return false; }
    },

    COOLDOWN() {
        @Override
        public void enter(PlayerWeapon weapon) {
            weapon.resetCooldownTimer();
        }

        @Override
        public void update(PlayerWeapon weapon) {
            weapon.decrementCooldownTimer();
        }

        @Override
        public void exit(PlayerWeapon weapon) {}

        @Override
        public boolean onMessage(PlayerWeapon weapon, Telegram telegram) { return false; }
    };

}
