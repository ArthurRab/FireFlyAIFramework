package Missions;

import Unit.UnitWrapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Mission implements Comparator<UnitWrapper> {

    private boolean completed = false;
    private MissionManager mm;


    private float priority;

    public Mission(float priority, MissionManager missionManager) {
        this.priority = priority;
        this.mm = missionManager;
    }

    private UnitWrapper worker;

    public void onDelete() {
        if (!completed)
            mm.addMission(this);
    }

    public void onStart() {

    }

    public boolean isAllowedToAcceptThisMission(UnitWrapper unit) {
        return true;
    }


    public UnitWrapper chooseUnit(List<UnitWrapper> u) {
        if (u.isEmpty()) {
            return null;
        }


        UnitWrapper best = Collections.max(u, this);

        return best;
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public UnitWrapper getUnit() {
        return worker;
    }

    public void setUnit(UnitWrapper unit) {
        this.worker = worker;
    }


}
