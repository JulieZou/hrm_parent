package com.ibicd.employee.controller;

import com.ibicd.common.controller.BaseController;
import com.ibicd.common.entity.PageResult;
import com.ibicd.common.entity.Result;
import com.ibicd.common.entity.ResultCode;
import com.ibicd.common.poi.ExcelExportUtils;
import com.ibicd.common.utils.BeanMapUtils;
import com.ibicd.common.utils.DownloadUtils;
import com.ibicd.domain.employee.*;
import com.ibicd.domain.employee.response.EmployeeReportResult;
import com.ibicd.employee.service.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/employees")
public class EmployeeController extends BaseController {

    @Autowired
    private UserCompanyPersonalService userCompanyPersonalService;
    @Autowired
    private UserCompanyJobsService userCompanyJobsService;
    @Autowired
    private ResignationService resignationService;
    @Autowired
    private TransferPositionService transferPositionService;
    @Autowired
    private PositiveService positiveService;
    @Autowired
    private ArchiveService archiveService;


    /**
     * 员工个人信息保存
     */
    @RequestMapping(value = "/{id}/personalInfo", method = RequestMethod.PUT)
    public Result savePersonalInfo(@PathVariable(name = "id") String uid, @RequestBody Map map) throws Exception {
        UserCompanyPersonal sourceInfo = BeanMapUtils.mapToBean(map, UserCompanyPersonal.class);
        if (sourceInfo == null) {
            sourceInfo = new UserCompanyPersonal();
        }
        sourceInfo.setUserId(uid);
        sourceInfo.setCompanyId(super.companyId);
        userCompanyPersonalService.save(sourceInfo);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 员工个人信息读取
     */
    @RequestMapping(value = "/{id}/personalInfo", method = RequestMethod.GET)
    public Result findPersonalInfo(@PathVariable(name = "id") String uid) throws Exception {
        UserCompanyPersonal info = userCompanyPersonalService.findById(uid);
        if (info == null) {
            info = new UserCompanyPersonal();
            info.setUserId(uid);
        }
        return new Result(ResultCode.SUCCESS, info);
    }

    /**
     * 员工岗位信息保存
     */
    @RequestMapping(value = "/{id}/jobs", method = RequestMethod.PUT)
    public Result saveJobsInfo(@PathVariable(name = "id") String uid, @RequestBody UserCompanyJobs sourceInfo) throws Exception {
        //更新员工岗位信息
        if (sourceInfo == null) {
            sourceInfo = new UserCompanyJobs();
            sourceInfo.setUserId(uid);
            sourceInfo.setCompanyId(super.companyId);
        }
        userCompanyJobsService.save(sourceInfo);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 员工岗位信息读取
     */
    @RequestMapping(value = "/{id}/jobs", method = RequestMethod.GET)
    public Result findJobsInfo(@PathVariable(name = "id") String uid) throws Exception {
        UserCompanyJobs info = userCompanyJobsService.findById(uid);
        if (info == null) {
            info = new UserCompanyJobs();
            info.setUserId(uid);
            info.setCompanyId(companyId);
        }
        return new Result(ResultCode.SUCCESS, info);
    }

    /**
     * 离职表单保存
     */
    @RequestMapping(value = "/{id}/leave", method = RequestMethod.PUT)
    public Result saveLeave(@PathVariable(name = "id") String uid, @RequestBody EmployeeResignation resignation) throws Exception {
        resignation.setUserId(uid);
        resignationService.save(resignation);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 离职表单读取
     */
    @RequestMapping(value = "/{id}/leave", method = RequestMethod.GET)
    public Result findLeave(@PathVariable(name = "id") String uid) throws Exception {
        EmployeeResignation resignation = resignationService.findById(uid);
        if (resignation == null) {
            resignation = new EmployeeResignation();
            resignation.setUserId(uid);
        }
        return new Result(ResultCode.SUCCESS, resignation);
    }

    /**
     * 导入员工
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public Result importDatas(@RequestParam(name = "file") MultipartFile attachment) throws Exception {
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 调岗表单保存
     */
    @RequestMapping(value = "/{id}/transferPosition", method = RequestMethod.PUT)
    public Result saveTransferPosition(@PathVariable(name = "id") String uid, @RequestBody EmployeeTransferPosition transferPosition) throws Exception {
        transferPosition.setUserId(uid);
        transferPositionService.save(transferPosition);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 调岗表单读取
     */
    @RequestMapping(value = "/{id}/transferPosition", method = RequestMethod.GET)
    public Result findTransferPosition(@PathVariable(name = "id") String uid) throws Exception {
        UserCompanyJobs jobsInfo = userCompanyJobsService.findById(uid);
        if (jobsInfo == null) {
            jobsInfo = new UserCompanyJobs();
            jobsInfo.setUserId(uid);
        }
        return new Result(ResultCode.SUCCESS, jobsInfo);
    }

    /**
     * 转正表单保存
     */
    @RequestMapping(value = "/{id}/positive", method = RequestMethod.PUT)
    public Result savePositive(@PathVariable(name = "id") String uid, @RequestBody EmployeePositive positive) throws Exception {
        positiveService.save(positive);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 转正表单读取
     */
    @RequestMapping(value = "/{id}/positive", method = RequestMethod.GET)
    public Result findPositive(@PathVariable(name = "id") String uid) throws Exception {
        EmployeePositive positive = positiveService.findById(uid);
        if (positive == null) {
            positive = new EmployeePositive();
            positive.setUserId(uid);
        }
        return new Result(ResultCode.SUCCESS, positive);
    }

    /**
     * 历史归档详情列表
     */
    @RequestMapping(value = "/archives/{month}", method = RequestMethod.GET)
    public Result archives(@PathVariable(name = "month") String month, @RequestParam(name = "type") Integer type) throws Exception {
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 归档更新
     */
    @RequestMapping(value = "/archives/{month}", method = RequestMethod.PUT)
    public Result saveArchives(@PathVariable(name = "month") String month) throws Exception {
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 历史归档列表
     */
    @RequestMapping(value = "/archives", method = RequestMethod.GET)
    public Result findArchives(@RequestParam(name = "pagesize") Integer pagesize, @RequestParam(name = "page") Integer page, @RequestParam(name = "year") String year) throws Exception {
        Map map = new HashMap();
        map.put("year", year);
        map.put("companyId", companyId);
        Page<EmployeeArchive> searchPage = archiveService.findSearch(map, page, pagesize);
        PageResult<EmployeeArchive> pr = new PageResult(searchPage.getTotalElements(), searchPage.getContent());
        return new Result(ResultCode.SUCCESS, pr);
    }

    /**
     * 月报表导出
     *
     * @param month
     * @throws IOException
     */
    @RequestMapping(value = "/export/{month}", method = RequestMethod.GET)
    public void export(@PathVariable String month) throws IOException {
        //获取数据
        List<EmployeeReportResult> list = userCompanyPersonalService.export(companyId, month);

        //生成Excel

        //SXSSF：百万数据报表，不适合模板打印的方式

        SXSSFWorkbook workbook = new SXSSFWorkbook(100);  //内存中的对象数量最大值，剩下的存入磁盘文件
//        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet();
        Row titleRow = sheet.createRow(0);
        String[] titleArray = "编号,姓名,手机,最高学历,国家地区,护照号,籍贯,生日,属相,入职时间,离职类型,离职原因,离职时间".split(",");
        int titleIndex = 0;
        Cell cell = null;
        for (String s : titleArray) {
            cell = titleRow.createCell(titleIndex++);
            cell.setCellValue(s);
        }

        int rowIndex = 1;
        Row row = null;
        for (EmployeeReportResult result : list) {
            row = sheet.createRow(rowIndex++);
            cell = row.createCell(0);
            cell.setCellValue(result.getUserId());

            cell = row.createCell(1);
            cell.setCellValue(result.getUsername());

            cell = row.createCell(2);
            cell.setCellValue(result.getMobile());

            cell = row.createCell(3);
            cell.setCellValue(result.getTheHighestDegreeOfEducation());

            cell = row.createCell(4);
            cell.setCellValue(result.getNationalArea());

            cell = row.createCell(5);
            cell.setCellValue(result.getPassportNo());

            cell = row.createCell(6);
            cell.setCellValue(result.getNativePlace());

            cell = row.createCell(7);
            cell.setCellValue(result.getBirthday());

            cell = row.createCell(8);
            cell.setCellValue(result.getZodiac());

            cell = row.createCell(9);
            cell.setCellValue(result.getTimeOfEntry());

            cell = row.createCell(10);
            cell.setCellValue(result.getTypeOfTurnover());

            cell = row.createCell(11);
            cell.setCellValue(result.getReasonsForLeaving());

            cell = row.createCell(12);
            cell.setCellValue(result.getResignationTime());
        }

        //下载
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        DownloadUtils.download(os, response, month + "人事报表.xlsx");
    }

    @RequestMapping(value = "/exportByTemplate/{month}", method = RequestMethod.GET)
    public void exportByLoadTemplate(@PathVariable String month) throws Exception {
        //获取数据
        List<EmployeeReportResult> list = userCompanyPersonalService.export(companyId, month);

        //加载模板
        Resource resource = new ClassPathResource("excel-template/hr-demo.xlsx");
        FileInputStream fis = new FileInputStream(resource.getFile());

        //使用工具类导出
        ExcelExportUtils<EmployeeReportResult> exportUtils = new ExcelExportUtils<EmployeeReportResult>(2, 2, EmployeeReportResult.class);
        exportUtils.export(response, fis, list, month + "人事报表.xlsx");

    }
}
