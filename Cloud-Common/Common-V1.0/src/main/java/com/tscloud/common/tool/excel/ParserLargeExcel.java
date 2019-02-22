package com.tscloud.common.tool.excel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 解析比较大的EXcel
 */
public class ParserLargeExcel{
    private static final Logger log = LogManager.getLogger(ExcelParser.class);
    private SSTRecord sstRecord = null;
    private Map<String,Object> map  = Maps.newLinkedHashMap();
    private List<List<Object>> dataLists = Lists.newArrayList();
    private List<Object> rowList = Lists.newArrayList();
    private int row = 0;


    public   ParserLargeExcel(InputStream inputStream) throws Exception {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(inputStream);
            ParserExcel my = new ParserExcel();
            HSSFEventFactory factory = new HSSFEventFactory();
            HSSFRequest request = new HSSFRequest();
            request.addListenerForAllRecords(my);
            factory.processWorkbookEvents(request, fs);
        }catch (Exception e){
            log.error("--------------------------Excel解析失败！");
            log.error("failed to parse excel." + e.getMessage());
            throw e;
        }

    }

    /**
     * 获取解析后的数据
     * @return
     */
    public List<List<Object>> getDataList(){
        return  this.dataLists;
    }


    class ParserExcel  implements HSSFListener{
        @Override
        public void processRecord(Record record) {
            switch (record.getSid()) {
                case BoundSheetRecord.sid:
                    break;
                case BOFRecord.sid:
                    break;
                case SSTRecord.sid:
                    sstRecord = (SSTRecord) record;
                    break;
                case BoolErrRecord.sid:
                    break;
                case FormulaRecord.sid:
                    break;
                case LabelSSTRecord.sid:
                    LabelSSTRecord lsrec = (LabelSSTRecord) record;
                    String value = "";
                    if (sstRecord != null) {
                        value = sstRecord.getString(lsrec.getSSTIndex()).toString();

                    }
                    if(row==lsrec.getRow()){
                        rowList.add(value);
                    }
                    if((lsrec.getRow()-row)==1){
                        row++;
                        dataLists.add(rowList);
                        rowList = Lists.newArrayList();
                        rowList.add(value);
                    }
                    break;
                case StringRecord.sid:
                    StringRecord srec = (StringRecord) record;
                    System.out.println("StringRecord:"+srec.toString());
                    break;
                case LabelRecord.sid:
                    break;
                case BlankRecord.sid:
                    break;
                case NoteRecord.sid:
                    break;
                case NumberRecord.sid:
                    NumberRecord nrec = (NumberRecord) record;
                    map.put(nrec.getRow()+"_"+nrec.getColumn(),nrec.getValue());
                    break;
                case RKRecord.sid:
                    break;
            }
        }
    }

    public static void main(String[] rg) {
        try {
            InputStream inputStream = new FileInputStream(new File("D:\\tmp\\excel\\11111.xls"));
            ParserLargeExcel my = new ParserLargeExcel(inputStream);
            my.map.size();
            my.dataLists.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
