package Missions;

import Unit.UnitWrapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Mission implements Comparator<UnitWrapper> {

    protected MissionManager mm;

    private float priority;

    public Mission(float priority, MissionManager missionManager) {
        this.priority = priority;
        this.mm = missionManager;
    }

    private UnitWrapper unit;

    public abstract void onDelete();

    public abstract void onStart();

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

    public abstract boolean checkCompleted();

    public UnitWrapper getUnit() {
        return unit;
    }

    public void setUnit(UnitWrapper unit) {
        this.unit = unit;
    }


}
