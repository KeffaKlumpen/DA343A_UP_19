package Server.View;

import Server.Controller.ServerController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;


public class SouthPanel extends JPanel implements ActionListener {

    private JComboBox startYear, startMonth, startDay;
    private JComboBox endYear, endMonth, endDay;
    private JComboBox startHour, startMinute, startSeconds;
    private JComboBox endHour, endMinute, endSeconds;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private JButton go;
    private JPanel datesInnerPanel1, datesInnerPanel2;
    private ServerController controller;
    private CenterPanel c;
    private ArrayList <String> finalList;
    private String[] stringList;

    public SouthPanel(CenterPanel c,ServerController controller, int width, int height, int margin) {
        this.controller = controller;
        this.c = c;
        setBorder(BorderFactory.createTitledBorder("Choose interval to show server activity"));
        Border border = this.getBorder();
        Border emptyBorder = BorderFactory.createEmptyBorder(margin, margin, margin, margin);
        setBorder(new CompoundBorder(border, emptyBorder));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));
        createImportantStuff();
        finalList = new ArrayList<>();
    }
    private void createImportantStuff() {
        //SKAPAR TVÅ PANELER FÖR BOXAR OCH SÖKKNAPP
        datesInnerPanel1 = new JPanel(new GridLayout(4, 4, 10, 10));
        datesInnerPanel2 = new JPanel();

        //STRÄNGAR SOM SÄTTS SOM DEFAULTVÄRDEN I COMBOBOXARNA
        String hour = "Hour";
        String minute = "Minute";
        String second = "Second";
        String day = "Day";
        String month = "Month";
        String year = "Year";

        //LITE LABELS FÖR TYDLIGHET
        JLabel startDateLabel = new JLabel("Start Date:  ", SwingConstants.LEFT);
        JLabel endDateLabel = new JLabel("End Date:  ", SwingConstants.LEFT);
        JLabel startTimeLabel = new JLabel("Start Time:  ", SwingConstants.LEFT);
        JLabel endTimeLabel = new JLabel("End Time:  ", SwingConstants.LEFT);

        //BYGGER COMBOBOX FÖR ÅR(START)
        startYear = new JComboBox();
        startYear.setBackground(Color.WHITE);
        startYear.addItem(year);
        startYear.setSelectedItem(year);
        buildYearsList(startYear);

        //BYGGER COMBOBOX FÖR MÅNADER(START)
        startMonth = new JComboBox();
        startMonth.setBackground(Color.WHITE);
        startMonth.addItem(month);
        startMonth.setSelectedItem(month);
        buildMonthsList(startMonth);

        //BYGGER COMBOBOX FÖR DAGAR(START)
        startDay = new JComboBox();
        startDay.setBackground(Color.WHITE);
        startDay.addItem(day);
        startDay.setSelectedItem(day);
        buildDaysList(startDate, startDay, startMonth);

        //BYGGER COMBOBOX FÖR ÅR(SLUT)
        endYear = new JComboBox();
        endYear.setBackground(Color.WHITE);
        endYear.addItem(year);
        endYear.setSelectedItem(year);
        buildYearsList(endYear);

        //BYGGER COMBOBOX FÖR MÅNADER(SLUT)
        endMonth = new JComboBox();
        endMonth.setBackground(Color.WHITE);
        endMonth.addItem(month);
        endMonth.setSelectedItem(month);
        buildMonthsList(endMonth);

        //BYGGER COMBOBOX FÖR FÖR DAGAR(SLUT)
        endDay = new JComboBox();
        endDay.setBackground(Color.WHITE);
        endDay.addItem(day);
        endDay.setSelectedItem(day);
        buildDaysList(endDate, endDay, endMonth);

        //KNAPP FÖR ATT STARTA
        go = new JButton("SEARCH");
        go.setPreferredSize(new Dimension(160, 130));

        //BYGGER COMBOBOX FÖR STARTANDE TIDPUNKT (TIMME)
        startHour = new JComboBox();
        startHour.setBackground(Color.WHITE);
        startHour.addItem(hour);
        startHour.setSelectedItem(hour);
        buildHoursList(startHour);

        //BYGGER COMBOBOX FÖR STARTANDE TIDPUNKT (MINUT)
        startMinute = new JComboBox();
        startMinute.setBackground(Color.WHITE);
        startMinute.addItem(minute);
        startMinute.setSelectedItem(minute);
        buildMinutesList(startMinute);

        //BYGGER COMBOBOX FÖR STARTANDE TIDPUNKT (SEKUND)
        startSeconds = new JComboBox();
        startSeconds.setBackground(Color.WHITE);
        startSeconds.addItem(second);
        startSeconds.setSelectedItem(second);
        buildMinutesList(startSeconds);

        //BYGGER COMBOBOX FÖR SISTA TIDPUNKT (TIMME)
        endHour = new JComboBox();
        endHour.setBackground(Color.WHITE);
        endHour.addItem(hour);
        endHour.setSelectedItem(hour);
        buildHoursList(endHour);

        //BYGGER COMBOBOX FÖR SISTA TIDPUNKT (MINUT)
        endMinute = new JComboBox();
        endMinute.setBackground(Color.WHITE);
        endMinute.addItem(minute);
        endMinute.setSelectedItem(minute);
        buildMinutesList(endMinute);

        //BYGGER COMBOBOX FÖR SISTA TIDPUNKT (SEKUND)
        endSeconds = new JComboBox();
        endSeconds.setBackground(Color.WHITE);
        endSeconds.addItem(second);
        endSeconds.setSelectedItem(second);
        buildMinutesList(endSeconds);

        // LÄGGER TILL COMBOBOXAR OCH LABEL I PANEL
        datesInnerPanel1.add(startDateLabel);
        datesInnerPanel1.add(startYear);
        datesInnerPanel1.add(startMonth);
        datesInnerPanel1.add(startDay);
        datesInnerPanel1.add(startTimeLabel);
        datesInnerPanel1.add(startHour);
        datesInnerPanel1.add(startMinute);
        datesInnerPanel1.add(startSeconds);
        datesInnerPanel1.add(endDateLabel);
        datesInnerPanel1.add(endYear);
        datesInnerPanel1.add(endMonth);
        datesInnerPanel1.add(endDay);
        datesInnerPanel1.add(endTimeLabel);
        datesInnerPanel1.add(endHour);
        datesInnerPanel1.add(endMinute);
        datesInnerPanel1.add(endSeconds);

        //LÄGGER TILL KNAPP
        datesInnerPanel2.add(go);

        //LÄGGER TILL PANELERNA PÅ HUVUDPANELEN
        add(datesInnerPanel1, BorderLayout.WEST);
        add(datesInnerPanel2, BorderLayout.CENTER);

        //LÄGGER TILL LYSSNARE FÖR KNAPPEN
        go.addActionListener(this);
    }
    //LÄGGER TILL ÅRTAL I COMBOBOXARNA
    private void buildYearsList(JComboBox yearsList) {
        int currentYear = startDate.get(Calendar.YEAR);
        for (int yearCount = currentYear; yearCount >= currentYear - 10; yearCount--) {
            yearsList.addItem(Integer.toString(yearCount));
        }
    }
    //LÄGGER TILL MÅNADER I COMBOBOXARNA
    private void buildMonthsList(JComboBox monthsList) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (int monthCount = 0; monthCount < months.length; monthCount++) {
            monthsList.addItem(months[monthCount]);
        }
    }
    //LÄGGER TILL DAGAR I COMBOBOXARNA
    private void buildDaysList(Calendar dateIn, JComboBox daysList, JComboBox monthsList) {
        dateIn.set(Calendar.MONTH, monthsList.getSelectedIndex());
        int lastDay = startDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int dayCount = 1; dayCount <= lastDay; dayCount++) {
            if (dayCount < 10) {
                String day = String.format("%02d", dayCount);
                daysList.addItem(day);
            } else {
                daysList.addItem(Integer.toString(dayCount));
            }
        }
    }
    //LÄGGER TILL TIMMAR I COMBOBOXARNA
    private void buildHoursList(JComboBox hoursList) {
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                String date = String.format("%02d", i);
                hoursList.addItem(date);
            } else {
                hoursList.addItem(Integer.toString(i));
            }
        }
    }
    //LÄGGER TILL MINUTER I COMBOBOXARNA
    private void buildMinutesList(JComboBox minutesList) {
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                String minute = String.format("%02d", i);
                minutesList.addItem(minute);
            } else {
                minutesList.addItem(Integer.toString(i));
            }
        }
    }
    //OMVANDLAR MÅNAD TILL KORREKT SIFFRA
    private String getMonthAsNumber(Object o) {
        String monthNbr = o.toString();
        if (o.equals("January")) {
            monthNbr = "01";
        }
        if (o.equals("February")) {
            monthNbr = "02";
        }
        if (o.equals("March")) {
            monthNbr = "03";
        }
        if (o.equals("April")) {
            monthNbr = "04";
        }
        if (o.equals("May")) {
            monthNbr = "05";
        }
        if (o.equals("June")) {
            monthNbr = "06";
        }
        if (o.equals("July")) {
            monthNbr = "07";
        }
        if (o.equals("August")) {
            monthNbr = "08";
        }
        if (o.equals("September")) {
            monthNbr = "09";
        }
        if (o.equals("October")) {
            monthNbr = "10";
        }
        if (o.equals("November")) {
            monthNbr = "11";
        }
        if (o.equals("December")) {
            monthNbr = "12";
        }
        return monthNbr;
    }
    //LÄGGER TILL VALDA LOGGAR I EN ARRAY OCH PLACERAR I CENTERPANEL
    private void setLogs(ArrayList <String> list){
        stringList = new String [list.size()];
        for (int i = 0; i < list.size(); i++ ){
            stringList[i] = list.get(i);
        }
        c.showLogsInView(stringList);
    }

    //GER FELMEDDELANDE OM INGA LOGGAR HITTAS
    private void errorMessage(){
        JOptionPane.showMessageDialog(null, "No server activity was found during the given timespan", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == go) {
            //BYGGER STRÄNGAR BASERAT PÅ VAD SOM STÅR I COMBOBOXARNA
            String startMonthNbr = getMonthAsNumber(startMonth.getSelectedItem());
            String endMonthNbr = getMonthAsNumber(endMonth.getSelectedItem());
            String startTime = String.format("%s-%s-%s %s:%s:%s",
                    startYear.getSelectedItem(), startMonthNbr, startDay.getSelectedItem(),
                    startHour.getSelectedItem(), startMinute.getSelectedItem(), startSeconds.getSelectedItem());
            String endTime = String.format("%s-%s-%s %s:%s:%s",
                    endYear.getSelectedItem(), endMonthNbr, endDay.getSelectedItem(),
                    endHour.getSelectedItem(), endMinute.getSelectedItem(), endSeconds.getSelectedItem());
            new fileReader(startTime, endTime);
        }
    }

    private class fileReader {
        private String startTime, endTime;
        private File[] files;
        private int startSearch, intervalStartDate;
        private int endSearch, intervalEndDate;

        public fileReader(String start, String end) {
            this.startTime = start;
            this.endTime = end;

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
            setLogs(finalList);
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
}
