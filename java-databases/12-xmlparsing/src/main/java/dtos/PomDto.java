package dtos;

import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class PomDto {
    @XmlAttribute(name = "xmlns")
    private String xmlns;

    @XmlAttribute(name = "xsi", namespace = "xmlns")
    private String xsi;

    @XmlAttribute(name = "schemaLocation", namespace = "xsi")
    private String schemaLocation;

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getXsi() {
        return xsi;
    }

    public void setXsi(String xsi) {
        this.xsi = xsi;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    @XmlElement
    private String modelVersion;

    @XmlElement
    private String groupId;

    @XmlElement
    private String artifactId;

    @XmlElement
    private String version;

    @XmlElementWrapper(name = "dependencies")
    @XmlElement(name = "dependency")
    private List<DependencyDto> dependencies;

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<DependencyDto> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyDto> dependencies) {
        this.dependencies = dependencies;
    }
}
