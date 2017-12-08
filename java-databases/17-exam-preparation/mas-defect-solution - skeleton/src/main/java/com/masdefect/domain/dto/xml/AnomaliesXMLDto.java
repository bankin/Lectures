package com.masdefect.domain.dto.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "anomalies")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnomaliesXMLDto {

    @XmlElement(name = "anomaly")
    private List<AnomalyXMLDto> anomalyXMLDtos;

    public List<AnomalyXMLDto> getAnomalyXMLDtos() {
        return anomalyXMLDtos;
    }

    public void setAnomalyXMLDtos(List<AnomalyXMLDto> anomalyXMLDtos) {
        this.anomalyXMLDtos = anomalyXMLDtos;
    }
}
