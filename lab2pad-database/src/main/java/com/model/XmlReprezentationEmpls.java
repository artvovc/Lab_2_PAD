package com.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Artemie on 14.10.2016.
 */
@XmlRootElement(name = "empls")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlReprezentationEmpls {
    @XmlElement(name = "empl")
    private List<Empl> empls = null;

    public XmlReprezentationEmpls(){}

    public XmlReprezentationEmpls(List<Empl> empls){this.empls=empls;}

    public List<Empl> getEmpls() {
        return empls;
    }

    public void setEmpls(List<Empl> empls) {
        this.empls = empls;
    }

    @Override
    public String toString() {
        String str="";
        for (Empl empl : this.empls) {
            str+=empl.toString()+"\n";
        }
        return str;
    }
}
