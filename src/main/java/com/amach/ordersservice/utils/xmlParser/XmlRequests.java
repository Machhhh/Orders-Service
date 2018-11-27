package com.amach.ordersservice.utils.xmlParser;

import com.amach.ordersservice.request.RequestDto;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;


@Builder(builderMethodName = "create")
@Getter
@Setter
@AllArgsConstructor
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "requests")
public final class XmlRequests {

    @XmlElement(name = "request")
    private List<RequestDto> xmlRequests;

    @XmlElement(name = "totalPrice")
    private BigDecimal totalPrice;

    @XmlElement(name = "avgPrice")
    private BigDecimal avgPrice;

    @XmlElement(name = "totalCounter")
    private Integer totalCounter;

    private XmlRequests() {
    }

    public static XmlRequests getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final XmlRequests INSTANCE = new XmlRequests();
    }
}
