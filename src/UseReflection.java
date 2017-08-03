import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class UseReflection {
    //getting fill/sort classes into action by reflection
    public void getReflection() throws  IllegalAccessException,
            InstantiationException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {

        FillArray fillArray = new FillArray();
        SortArray sortArray = new SortArray();

        Class fillClass = fillArray.getClass();
        Class sortClass = sortArray.getClass();

        Method[] fillMethod = fillClass.getDeclaredMethods();
        Method[] sortMethod = sortClass.getDeclaredMethods();

        final int NUM_OF_ROWS = sortMethod.length+1;
        final int NUM_OF_COLUMNS = 10;

        //collection for holding sort results
        SortResultCollectionHolder collection = new SortResultCollectionHolder();
        collection.arrayList = new ArrayList();

        //loop for adding sort results to collection
        int testSize;
        int sizeMove;
        for (int k = 10; k < 100; k = k + 10) { // 'k' is array size
            for (int i = 0; i < fillMethod.length; i++) {
                int[] fillResult = ((int[]) fillMethod[i].invoke(fillClass.newInstance(), k));

                for (int j = 0; j < sortMethod.length; j++) {
                    long startTime = System.nanoTime();
                    int[] sortResult = ((int[]) sortMethod[j].invoke(sortClass.newInstance(), fillResult));
                    long sortTime = System.nanoTime() - startTime;//calculating sort time

                    //creating object for each fill/sort result and adding it to collection
                    SortResultObject sortResultObject = new SortResultObject(fillMethod[i].getName(),
                            sortMethod[j].getName(), k, sortTime);
                    collection.arrayList.add(sortResultObject);
                }
            }
        }
        // creating excel sheet with sort results
        Workbook workbook = new XSSFWorkbook();
        Row row=null;
        Cell cell;
        Sheet sheet=null;

        for (int i = 0; i < fillMethod.length; i++) {
            sheet = workbook.createSheet(fillMethod[i].getName());
            row = sheet.createRow(0);
            int countCell = 1;
            for (int j = 10; j <100 ; j=j+10) {
                cell = row.createCell(countCell);
                cell.setCellValue(j);
                countCell++;
            }

            int count = 1;
            for (int j = 0; j < sortMethod.length; j++) {
                row = sheet.createRow(count);
                cell = row.createCell(0);
                cell.setCellValue(sortMethod[j].getName());
                count++;

                Iterator iterator = collection.arrayList.iterator();
                while (iterator.hasNext()){
                    Object ob = iterator.next();
                    Class obClass = ob.getClass();

                    Field fillField = obClass.getDeclaredField("fillMethodName");
                    Field sizeField = obClass.getDeclaredField("collectionSize");
                    Field sortField = obClass.getDeclaredField("sortMethodName");
                    Field timeField = obClass.getDeclaredField("sortTime");

                    Object fillValue = fillField.get(ob);
                    Object sizeValue = sizeField.get(ob);
                    Object sortValue = sortField.get(ob);
                    Object timeValue = timeField.get(ob);
                    int countSize=10;
                    for (int k = 1; k < 10; k++) {

                        if (fillMethod[i].getName().equals(fillValue) && sortMethod[j].getName().equals(sortValue)&&
                                countSize==(int)sizeValue) {
                            cell = row.createCell(k);
                            cell.setCellValue((long) timeValue);

                        }
                        countSize = countSize+10;
                    }
                }
            }
            //drawing line chart

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 0,10,10, 20 );
            Chart chart = drawing.createChart(anchor);
            ChartLegend legend = chart.getOrCreateLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);

            LineChartData data = chart.getChartDataFactory().createLineChartData();

            // Use a category axis for the bottom axis.
            ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
            ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

            ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet,
                    new CellRangeAddress(0, 0, 0, NUM_OF_COLUMNS - 1));

            for (int j = 1; j <NUM_OF_ROWS ; j++) {
                ChartDataSource<Number> ys = DataSources.fromNumericCellRange(sheet,
                        new CellRangeAddress(j, j, 0, NUM_OF_COLUMNS - 1));
                data.addSeries(xs, ys);

            }
            chart.plot(data, bottomAxis, leftAxis);
        }

        try(FileOutputStream fo = new FileOutputStream("SortTime.xls")){
            workbook.write(fo);
        }catch (IOException e){
            System.out.println(e);
        }
    }
}





