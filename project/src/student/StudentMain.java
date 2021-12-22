package student;

import java.math.BigDecimal;
import java.util.List;
import org.hamcrest.BaseDescription.*;
import org.junit.*;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;
import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.operations.DistrictOperations;
import rs.etf.sab.operations.CourierOperations;
import rs.etf.sab.operations.CourierRequestOperation;
import rs.etf.sab.operations.GeneralOperations;
import rs.etf.sab.operations.UserOperations;
import rs.etf.sab.operations.VehicleOperations;
import rs.etf.sab.operations.PackageOperations;

public class StudentMain {

    public static void main(String[] args) {
        CityOperations cityOperations = new vi160137_CityOperations(); // Change this to your implementation.
        DistrictOperations districtOperations = new vi160137_DistrictOperations(); // Do it for all classes.
        CourierOperations courierOperations = new vi160137_CourierOperations(); // e.g. = new MyDistrictOperations();
        CourierRequestOperation courierRequestOperation = new vi160137_CourierRequestOperation();
        GeneralOperations generalOperations = new vi160137_GeneralOperations();
        UserOperations userOperations = new vi160137_UserOperations();
        VehicleOperations vehicleOperations = new vi160137_VehicleOperations();
        PackageOperations packageOperations = new vi160137_PackageOperations();

        TestHandler.createInstance(
                cityOperations,
                courierOperations,
                courierRequestOperation,
                districtOperations,
                generalOperations,
                userOperations,
                vehicleOperations,
                packageOperations);

        TestRunner.runTests();
    }
}
