package com.amach.coreServices.request;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Builder(builderMethodName = "create")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestDto {

    @CsvBindByName(column = "'Client_Id'")
    @XmlElement(name = "clientId")
    private Long clientId;
    @CsvBindByName(column = "'Client_Name'")
    @XmlElement(name = "clientName")
    private String clientName;
    @CsvBindByName(column = "'Request_Id'")
    @XmlElement(name = "requestId")
    private Long requestId;
    @CsvBindByName(column = "'Request_Name'")
    @XmlElement(name = "requestName")
    private String name;
    @CsvBindByName(column = "'Request_Uuid'")
    @XmlElement(name = "requestUuid")
    private String uuid;
    @CsvBindByName(column = "'Quantity'")
    @XmlElement(name = "quantity")
    private Integer quantity;
    @CsvBindByName(column = "'Price'")
    @XmlElement(name = "price")
    private BigDecimal price;
}
