package Missions;

import Unit.UnitWrapper;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class MissionManager {

    public MissionManager() {

    }

    public void clearAllMissions(Iterable<UnitWrapper> units) {
        for (UnitWrapper u : units) {
            u.deleteMission();
        }
        pendingMissions.clear();
        saveForLater.clear();
    }

    public void update() {
        pendingMissions = saveForLater;
        saveForLater = new PriorityQueue<Mission>(1, new MissionPriorityComparator());
    }

    public PriorityQueue<Mission> pendingMissions = new PriorityQueue<>(1, new MissionPriorityComparator());
    public PriorityQueue<Mission> saveForLater = new PriorityQueue<>(1, new MissionPriorityComparator());

    public void addMission(Mission m) {
        pendingMissions.add(m);
    }

    public void distributeMissions(Iterable<UnitWrapper> units) {
        while (!pendingMissions.isEmpty()) {
            Mission m = pendingMissions.poll();
            ArrayList<UnitWrapper> applicants = new ArrayList<>();
            for (UnitWrapper u : units) {
                if (m.isAllowedToAcceptThisMission((u)) && u.willTakeMission(m)) {
                    applicants.add(u);
                }
            }

            if (applicants.isEmpty()) {
                saveForLater.add(m);

                saveForLater.addAll(pendingMissions);
                pendingMissions.clear();
            } else {

                UnitWrapper chosenOne = m.chooseUnit(applicants);

                chosenOne.setMission(m);
                m.onStart();
            }
        }
    }

}
