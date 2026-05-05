package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManagerParallelExecution {

    // One shared ExtentReports object is enough for the whole parallel suite.
    // The listener will ask for this object whenever a test starts or finishes.
    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        // synchronized prevents two threads from creating two report objects at the same time
        // when the parallel suite starts.
        if (extent == null) {
            // This report path is intentionally separate from the existing framework report
            // so the original setup is not disturbed.
            String path = System.getProperty("user.dir")
                    + "/reports/parallel-execution/extent-parallel-execution.html";

            // Spark reporter generates the HTML file that we open in the browser later.
            ExtentSparkReporter spark = new ExtentSparkReporter(path);
            spark.config().setReportName("Parallel Execution Report");
            spark.config().setDocumentTitle("Parallel Execution Suite");

            // ExtentReports is the main engine. Spark is the actual output target.
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}
