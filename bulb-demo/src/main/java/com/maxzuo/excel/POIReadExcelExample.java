package com.maxzuo.excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * 使用POI克隆Excel表格
 * <p>
 * Created by zfh on 2019/05/09
 */
public class POIReadExcelExample {

    public static void main(String[] args) {
        try {
            URL templateResource = POIReadExcelExample.class.getResource("template/applyTableTemplate.xlsx");
            cloneExcelFile(templateResource.getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟填充数据
     */
    private static ApplyTableExcelVO mockFillData() {
        ApplyTableExcelVO excelVO = new ApplyTableExcelVO();
        excelVO.setCompany("智创工场");
        excelVO.setFillTableTime("2019年5月9日");
        excelVO.setApplicant("dazuo");
        excelVO.setActivityType(1);
        excelVO.setActivitySubject("winner winner clicken dinner");
        excelVO.setActivityGoal("clicken dinner");
        excelVO.setActivityTime("2019-5-9");
        excelVO.setActivityPlace("东沙大厦");
        excelVO.setPersonnel("dazuo、wang");
        excelVO.setActivityItems("ipad、MAC");
        excelVO.setActivityIntroduced("一起happy");
        excelVO.setTotalBudget("100000000000.00");
        excelVO.setReceiver("dazuo");
        excelVO.setActivityHead("dazuo");
        excelVO.setManager("dazuo");
        excelVO.setFinancial("wang");
        return excelVO;
    }

    /**
     * 进行模板的克隆，填充克隆后的模板
     */
    private static void cloneExcelFile(String filePath) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filePath));
        XSSFSheet sheet = wb.cloneSheet(0);
        wb.setSheetName(0, "Sheet1");

        replaceCellValue(sheet);

        // 输出新的Excel
        String fileName = System.currentTimeMillis() + ".xlsx";
        OutputStream out = new FileOutputStream("F:\\bulb\\bulb-demo\\" + fileName);
        // 移除workbook中的模板sheet
        wb.removeSheetAt(0);
        wb.write(out);
        out.flush();
        out.close();
    }

    /**
     * 替换单元格
     */
    private static void replaceCellValue(XSSFSheet sheet) {
        // 获取mock的填充数据
        ApplyTableExcelVO excelVO = mockFillData();
        sheet.getRow(1).getCell(1).setCellValue(excelVO.getCompany());
        sheet.getRow(1).getCell(2).setCellValue(excelVO.getFillTableTime());
        sheet.getRow(1).getCell(5).setCellValue(excelVO.getApplicant());

        // 活动类型（插入符号）
        String activityType;
        if (Integer.valueOf(1).equals(excelVO.getActivityType())) {
            activityType = (char) 0x2611 + " 小组活动 " + (char) 0x2610 + " 跨组活动";
        } else {
            activityType = (char) 0x2610 + " 小组活动 " + (char) 0x2611 + " 跨组活动";
        }
        sheet.getRow(2).getCell(1).setCellValue(activityType);
        sheet.getRow(3).getCell(1).setCellValue(excelVO.getActivitySubject());
        sheet.getRow(4).getCell(1).setCellValue(excelVO.getActivityGoal());
        sheet.getRow(5).getCell(1).setCellValue(excelVO.getActivityTime());
        sheet.getRow(5).getCell(3).setCellValue(excelVO.getActivityPlace());
        sheet.getRow(6).getCell(1).setCellValue(excelVO.getPersonnel());
        sheet.getRow(7).getCell(1).setCellValue(excelVO.getActivityItems());
        sheet.getRow(8).getCell(1).setCellValue(excelVO.getActivityIntroduced());
        sheet.getRow(9).getCell(1).setCellValue(excelVO.getTotalBudget());
        sheet.getRow(9).getCell(3).setCellValue(excelVO.getReceiver());
        sheet.getRow(10).getCell(1).setCellValue(excelVO.getActivityHead());
        sheet.getRow(10).getCell(3).setCellValue(excelVO.getManager());
        sheet.getRow(10).getCell(5).setCellValue(excelVO.getFinancial());
    }

    /**
     * 申请表视图VO
     */
    static class ApplyTableExcelVO {

        /**
         * 分公司
         */
        private String company;

        /**
         * 填表日期
         */
        private String fillTableTime;

        /**
         * 申请人
         */
        private String applicant;

        /**
         * 活动类型：1-小组活动 2-跨组活动
         */
        private Integer activityType;

        /**
         * 活动主题
         */
        private String activitySubject;

        /**
         * 活动目的
         */
        private String activityGoal;

        /**
         * 活动时间
         */
        private String activityTime;

        /**
         * 活动地点
         */
        private String activityPlace;

        /**
         * 参与人员
         */
        private String personnel;

        /**
         * 物品配备
         */
        private String activityItems;

        /**
         * 活动内容
         */
        private String activityIntroduced;

        /**
         * 费用总预算
         */
        private String totalBudget;

        /**
         * 费用领取人
         */
        private String receiver;

        /**
         * 活动负责人
         */
        private String activityHead;

        /**
         * 总经理
         */
        private String manager;

        /**
         * 财务
         */
        private String financial;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getFillTableTime() {
            return fillTableTime;
        }

        public void setFillTableTime(String fillTableTime) {
            this.fillTableTime = fillTableTime;
        }

        public String getApplicant() {
            return applicant;
        }

        public void setApplicant(String applicant) {
            this.applicant = applicant;
        }

        public String getActivitySubject() {
            return activitySubject;
        }

        public void setActivitySubject(String activitySubject) {
            this.activitySubject = activitySubject;
        }

        public String getActivityGoal() {
            return activityGoal;
        }

        public void setActivityGoal(String activityGoal) {
            this.activityGoal = activityGoal;
        }

        public String getActivityTime() {
            return activityTime;
        }

        public void setActivityTime(String activityTime) {
            this.activityTime = activityTime;
        }

        public String getActivityPlace() {
            return activityPlace;
        }

        public void setActivityPlace(String activityPlace) {
            this.activityPlace = activityPlace;
        }

        public String getPersonnel() {
            return personnel;
        }

        public void setPersonnel(String personnel) {
            this.personnel = personnel;
        }

        public String getActivityItems() {
            return activityItems;
        }

        public void setActivityItems(String activityItems) {
            this.activityItems = activityItems;
        }

        public String getActivityIntroduced() {
            return activityIntroduced;
        }

        public void setActivityIntroduced(String activityIntroduced) {
            this.activityIntroduced = activityIntroduced;
        }

        public String getTotalBudget() {
            return totalBudget;
        }

        public void setTotalBudget(String totalBudget) {
            this.totalBudget = totalBudget;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getActivityHead() {
            return activityHead;
        }

        public void setActivityHead(String activityHead) {
            this.activityHead = activityHead;
        }

        public String getManager() {
            return manager;
        }

        public void setManager(String manager) {
            this.manager = manager;
        }

        public String getFinancial() {
            return financial;
        }

        public void setFinancial(String financial) {
            this.financial = financial;
        }

        public Integer getActivityType() {
            return activityType;
        }

        public void setActivityType(Integer activityType) {
            this.activityType = activityType;
        }

        @Override
        public String toString() {
            return "ApplyTableExcelVO{" +
                    "company='" + company + '\'' +
                    ", fillTableTime='" + fillTableTime + '\'' +
                    ", applicant='" + applicant + '\'' +
                    ", activityType=" + activityType +
                    ", activitySubject='" + activitySubject + '\'' +
                    ", activityGoal='" + activityGoal + '\'' +
                    ", activityTime='" + activityTime + '\'' +
                    ", activityPlace='" + activityPlace + '\'' +
                    ", personnel='" + personnel + '\'' +
                    ", activityItems='" + activityItems + '\'' +
                    ", activityIntroduced='" + activityIntroduced + '\'' +
                    ", totalBudget='" + totalBudget + '\'' +
                    ", receiver='" + receiver + '\'' +
                    ", activityHead='" + activityHead + '\'' +
                    ", manager='" + manager + '\'' +
                    ", financial='" + financial + '\'' +
                    '}';
        }
    }
}
