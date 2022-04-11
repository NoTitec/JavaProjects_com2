package persistence.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubjectDTO {
    private String subcode;
    private String subname;
    private String subinfo;
    private int subgrade;
    private String fmanid="master";
}
