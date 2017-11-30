package softuni.judgesystem.dtos.xml.participations;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "participations")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersParticipationsDto {

    @XmlElement(name = "participation")
    List<UserParticipation> participationList;

    public List<UserParticipation> getParticipationList() {
        return participationList;
    }

    public void setParticipationList(List<UserParticipation> participationList) {
        this.participationList = participationList;
    }
}
