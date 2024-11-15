package lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl;

import java.sql.Date;
import java.util.Set;

public class LogDTO {
    private String logCode;
    private String logDetails;
    private Date date;
    private String image2;

    // IDs of associated entities for a lightweight representation
    private Set<String> staffIds;   // IDs of staff members monitoring this log
    private Set<String> fieldIds;     // IDs of fields related to this log
    private Set<String> cropIds;
}
