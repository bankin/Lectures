package softuni.judgesystem.dtos.xml.submissions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "submissions")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubmissionsDto {

    @XmlElement(name = "submission")
    private List<SubmissionDto> submissionDtoList;

    public List<SubmissionDto> getSubmissionDtoList() {
        return submissionDtoList;
    }

    public void setSubmissionDtoList(List<SubmissionDto> submissionDtoList) {
        this.submissionDtoList = submissionDtoList;
    }
}
