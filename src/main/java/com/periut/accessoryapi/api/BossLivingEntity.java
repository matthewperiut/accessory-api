package com.periut.accessoryapi.api;

public interface BossLivingEntity
{
    void setBoss(boolean boss);
    boolean isBoss();
    int getHP();
    int getMaxHP();
    String getName();
    default String getCustomTitle() {
        return "";
    }
}
