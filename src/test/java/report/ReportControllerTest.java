package report;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.myspar.controller.ReportController;
import ru.myspar.dto.report.ComplianceReportDto;
import ru.myspar.dto.report.DailyReportDto;
import ru.myspar.dto.report.MealHistoryDto;
import ru.myspar.service.report.ReportService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @Test
    void getDailyReportTest() {
        Integer userId = 1;
        DailyReportDto expectedReport = new DailyReportDto();
        expectedReport.setUserId(userId);

        when(reportService.generateDailyReport(userId)).thenReturn(expectedReport);

        ResponseEntity<DailyReportDto> responseEntity = reportController.getDailyReport(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedReport, responseEntity.getBody());
        verify(reportService, times(1)).generateDailyReport(userId);
    }

    @Test
    void getComplianceReportTest() {
        Integer userId = 1;
        ComplianceReportDto expectedReport = new ComplianceReportDto();
        expectedReport.setUserId(userId);

        when(reportService.checkCompliance(userId)).thenReturn(expectedReport);

        ResponseEntity<ComplianceReportDto> responseEntity = reportController.getComplianceReport(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedReport, responseEntity.getBody());
        verify(reportService, times(1)).checkCompliance(userId);
    }

    @Test
    void getMealHistoryTest() {
        Integer userId = 1;
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();

        MealHistoryDto expectedMealHistory = new MealHistoryDto();
        expectedMealHistory.setUserId(userId);

        when(reportService.getMealHistory(userId, startDate, endDate)).thenReturn(expectedMealHistory);

        ResponseEntity<MealHistoryDto> responseEntity = reportController.getMealHistory(userId, startDate, endDate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedMealHistory, responseEntity.getBody());
        verify(reportService, times(1)).getMealHistory(userId, startDate, endDate);
    }
}


