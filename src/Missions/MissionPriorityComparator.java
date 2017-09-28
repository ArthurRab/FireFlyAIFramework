package Missions;

import java.util.Comparator;

public class MissionPriorityComparator implements Comparator<Mission> {
    @Override
    public int compare(Mission o1, Mission o2) {
        float temp = (o1.getPriority() - o2.getPriority());

        if (temp > 0) {
            return -1;
        } else if (temp == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
