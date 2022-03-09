package Server.Controller;

import Server.View.SouthPanel;

import java.io.*;
import java.util.ArrayList;

public class FileReader {
    private String startTime, endTime;
    private File[] files;
    private ArrayList<String> finalList;
    private int startSearch, intervalStartDate;
    private int endSearch, intervalEndDate;
    private SouthPanel sp;

    public FileReader(SouthPanel sp, String start, String end) {
        this.startTime = start;
        this.endTime = end;
        this.sp = sp;
        finalList = sp.getFinalList();

        //FORMATTERAR LITE STRÄNGAR FÖR ATT KUNNA HITTA MATCHNINGAR I LOGS BASERAT PÅ DATUM
        String sortStart = startTime.substring(0, startTime.indexOf(" "));
        String sortEnd = endTime.substring(0, endTime.indexOf(" "));
        sortStart = sortStart.replace("-", "");
        sortEnd = sortEnd.replace("-", "");

        try{
            intervalStartDate = Integer.parseInt(sortStart);
        } catch (NumberFormatException e){
            intervalStartDate = 20220101;
        }

        try{
            intervalEndDate = Integer.parseInt(sortEnd);
        } catch (NumberFormatException e){
            intervalEndDate = 20221231;
        }

        //FORMATTERAR LITE STRÄNGAR FÖR ATT KUNNA HITTA MATCHNINGAR I LOGS BASERAT PÅ TID
        String ts = startTime.substring(startTime.indexOf(" "));
        ts = ts.replace(":","");
        ts = ts.replace(" ","");

        String te = endTime.substring(endTime.indexOf(" "));
        te = te.replace(":","");
        te = te.replace(" ","");

        try{
            startSearch = Integer.parseInt(ts);
        }catch(NumberFormatException e){
            startSearch = 000000;
        }

        try{
            endSearch = Integer.parseInt(te);
        }catch(NumberFormatException e){
            endSearch = 235959;
        }

        //LITE GREJER FÖR ATT HÄMTA DIRECTORY
        File directory = new File("logs");
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println(String.format("Couldn't find directory called logs"));
            return;
        }

        //FILTRERAR BORT FILER SOM INTE ÄR AV .LOG TYP
        FileFilter logFilefilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.getName().endsWith(".log")) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        //SKAPAR ARRAY MED ALLA .LOG FILER I RÄTT DIRECTORY
        files = directory.listFiles(logFilefilter);

        //SKA LÄGGA TILL FILER I INTERVALLET I LISTAN;
        for (File f : files) {
            int filename = Integer.parseInt(f.getName().substring(0, f.getName().indexOf("-")));
            if (filename >= intervalStartDate && filename <= intervalEndDate) {
                showCorrectLogs(f.getPath());
            }
        }
        sp.setLogs(finalList);
        finalList.clear();
    }


    public void showCorrectLogs(String filename) {
        try {
            FileInputStream fstream = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {

                String currentLineTimeStamp = strLine.substring(strLine.indexOf(" "), strLine.indexOf(": "));
                currentLineTimeStamp = currentLineTimeStamp.replace(":","");
                currentLineTimeStamp = currentLineTimeStamp.replace(" ","");
                int lineTime = Integer.parseInt(currentLineTimeStamp);

                String currentLineDateStamp = strLine.substring(0, strLine.indexOf(" "));
                currentLineDateStamp = currentLineDateStamp.replace("-","");
                currentLineDateStamp = currentLineDateStamp.replace(" ","");
                int lineDate = Integer.parseInt(currentLineDateStamp);

                if(lineDate < intervalEndDate && lineTime >= startSearch){
                    finalList.add(strLine);
                    continue;
                }

                if(lineDate == intervalEndDate && lineTime <= endSearch){
                    finalList.add(strLine);
                    continue;
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
