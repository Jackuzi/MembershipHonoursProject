package com.membershipApp.ReportGenertor;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.membershipApp.MemberModel;
import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;

public class ReportGenerator {
  private static final Logger logger = LoggerFactory.getLogger(ReportGenerator.class);

  public void generatePdfReport(MemberModel item) {
    JasperReport jasperReport = null;
    try {
      jasperReport = JasperCompileManager.compileReport(ClassLoader.getSystemResourceAsStream("com/membershipApp/reports/ReminderReport.jrxml"));
      URL url = this.getClass().getClassLoader().getResource("/com/membershipApp/reports/indeks.png");
      HashMap para = new HashMap();
      para.put("logo", url);
      para.put("name", item.getName());
      para.put("surname", item.getSurname());
      para.put("validFrom", item.getdFrom().toString());
      para.put("ValidTo", item.getdTo().toString());
      para.put("daysLeft", item.getExpiration());
      para.put("address", item.getHouse() + " " + item.getStreet());
      para.put("postcode", item.getPostcode());
      para.put("city", item.getCity());
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, para, new JREmptyDataSource());
      //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap());
      JasperExportManager.exportReportToPdfFile(jasperPrint, "fromXml.pdf");
    } catch (JRException e) {
      e.printStackTrace();
    } finally {
      MobileApplication.getInstance().showMessage("Pdf reminder created in the same directory");
      MobileApplication.getInstance().getHostServices().showDocument("./fromXml.pdf");
    }
  }

}
