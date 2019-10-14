package hu.oe.nik.szfmv.automatedcar.visualization.dashboard;

public class LastRoadSign implements ILastRoadSign {

    public String getRoadSignName() {
        if(RoadSignName != null)
            return RoadSignName;
        else
            return "No Road Sign";
    }

    public void setRoadSignName(String roadSignName) {
        RoadSignName = roadSignName;
    }

    String RoadSignName;

    public LastRoadSign(String RoadSignName){
        this.RoadSignName = RoadSignName;
    }
}
