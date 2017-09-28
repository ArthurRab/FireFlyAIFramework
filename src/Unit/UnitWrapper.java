package Unit;

import Missions.Mission;

public abstract class UnitWrapper<U> {
    private Mission mission;
    private U unit;

    public UnitWrapper(U unit) {

    }


    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        deleteMission();
        this.mission = mission;
        mission.setUnit(this);
    }

    public boolean hasMission() {
        return mission != null;
    }


    public boolean willTakeMission(Mission m) {
        return hasMission() || m.getPriority() > mission.getPriority();
    }

    public void deleteMission() {
        Mission m = this.mission;
        this.mission = null;
        m.onDelete();
    }

    public U getUnit() {
        return unit;
    }

    public void setUnit(U u) {
        unit = u;
    }

    public void update() {
        if (mission != null && mission.isCompleted()) {
            deleteMission();
        }
    }
}
