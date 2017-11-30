package softuni.judgesystem.dtos.xml.participations;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "participation")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserParticipation {

    @XmlElement(name = "user")
    private IdDto userId;

    @XmlElement(name = "contest")
    private IdDto contestId;

    public IdDto getUserId() {
        return userId;
    }

    public void setUserId(IdDto userId) {
        this.userId = userId;
    }

    public IdDto getContestId() {
        return contestId;
    }

    public void setContestId(IdDto contestId) {
        this.contestId = contestId;
    }
}
