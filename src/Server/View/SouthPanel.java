package Server.View;

import Server.Controller.ServerController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;


public class SouthPanel extends JPanel implements ItemListener, ActionListener {

    private JComboBox startYear, startMonth, startDay;
    private JComboBox endYear, endMonth, endDay;
    private JComboBox startHour, startMinute;
    private JComboBox endHour, endMinute;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private JButton go;
    private JPanel datesInnerPanel1, datesInnerPanel2;
    private ServerController controller;

    public SouthPanel(ServerController controller, int width, int height, int margin){
        this.controller = controller;
        setBorder(BorderFactory.createTitledBorder("Choose interval to show server activity"));
        Border border = this.getBorder();
        Border emptyBorder = BorderFactory.createEmptyBorder(margin,margin,margin,margin);
        setBorder(new CompoundBorder(border, emptyBorder));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));
        createImportantStuff();
    }

    private void createImportantStuff(){
        //SKAPAR TVÅ PANELER FÖR BOXAR OCH SÖKKNAPP
        datesInnerPanel1 = new JPanel(new GridLayout(1, 8, 10, 10));
        datesInnerPanel2 = new JPanel(new GridLayout(1, 7, 10, 50));

        //LITE LABELS FÖR TYDLIGHET
        JLabel startDateLabel = new JLabel("Start Date:  ", SwingConstants.RIGHT);
        JLabel endDateLabel = new JLabel("End Date:  ", SwingConstants.RIGHT);
        JLabel startTimeLabel = new JLabel("Start Time:  ", SwingConstants.RIGHT);
        JLabel endTimeLabel = new JLabel("End Time:  ", SwingConstants.RIGHT);

        //BYGGER COMBOBOX FÖR ÅR(START)
        startYear = new JComboBox();
        startYear.setBackground(Color.WHITE);
        buildYearsList(startYear);
        startYear.setSelectedIndex(10);

        //BYGGER COMBOBOX FÖR MÅNADER(START)
        startMonth = new JComboBox();
        startMonth.setBackground(Color.WHITE);
        buildMonthsList(startMonth);
        startMonth.setSelectedIndex(startDate.get(Calendar.MONTH));

        //BYGGER COMBOBOX FÖR DAGAR(START)
        startDay = new JComboBox();
        startDay.setBackground(Color.WHITE);
        buildDaysList(startDate, startDay, startMonth);
        startDay.setSelectedItem(Integer.toString(startDate.get(Calendar.DATE)));

        //BYGGER COMBOBOX FÖR ÅR(SLUT)
        endYear = new JComboBox();
        endYear.setBackground(Color.WHITE);
        buildYearsList(endYear);
        endYear.setSelectedIndex(10);

        //BYGGER COMBOBOX FÖR MÅNADER(SLUT)
        endMonth = new JComboBox();
        endMonth.setBackground(Color.WHITE);
        buildMonthsList(endMonth);
        endMonth.setSelectedIndex(endDate.get(Calendar.MONTH));

        //BYGGER COMBOBOX FÖR FÖR DAGAR(SLUT)
        endDay = new JComboBox();
        endDay.setBackground(Color.WHITE);
        buildDaysList(endDate, endDay, endMonth);
        endDay.setSelectedItem(Integer.toString(endDate.get(Calendar.DATE)));

        // LÄGGER TILL COMBOBOXAR OCH LABEL TILL FÖRSTA PANELEN
        datesInnerPanel1.add(startDateLabel);
        datesInnerPanel1.add(startMonth);
        datesInnerPanel1.add(startDay);
        datesInnerPanel1.add(startYear);
        datesInnerPanel1.add(endDateLabel);
        datesInnerPanel1.add(endMonth);
        datesInnerPanel1.add(endDay);
        datesInnerPanel1.add(endYear);

        //KNAPP FÖR ATT STARTA
        go = new JButton("SEARCH");

        //BYGGER COMBOBOX FÖR STARTANDE TIDPUNKT (TIMME)
        startHour = new JComboBox();
        startHour.setBackground(Color.WHITE);
        buildHoursList(startHour);

        //BYGGER COMBOBOX FÖR STARTANDE TIDPUNKT (MINUT)
        startMinute = new JComboBox();
        startMinute.setBackground(Color.WHITE);
        buildMinutesList(startMinute);

        //BYGGER COMBOBOX FÖR SISTA TIDPUNKT (TIMME)
        endHour = new JComboBox();
        endHour.setBackground(Color.WHITE);
        buildHoursList(endHour);

        //BYGGER COMBOBOX FÖR SISTA TIDPUNKT (MINUT)
        endMinute = new JComboBox();
        endMinute.setBackground(Color.WHITE);
        endMinute.setSelectedItem(Integer.toString(endDate.get(Calendar.MINUTE)));
        buildMinutesList(endMinute);

        //LÄGGER TILL COMBOBOXAR FÖR TID OCH KNAPPEN FÖR SÖK
        datesInnerPanel2.add(startTimeLabel);
        datesInnerPanel2.add(startHour);
        datesInnerPanel2.add(startMinute);
        datesInnerPanel2.add(endTimeLabel);
        datesInnerPanel2.add(endHour);
        datesInnerPanel2.add(endMinute);
        datesInnerPanel2.add(go);


        //LÄGGER TILL PANELERNA PÅ HUVUDPANELEN
        add(datesInnerPanel1, BorderLayout.NORTH);
        add(datesInnerPanel2, BorderLayout.SOUTH);


        //LÄGGER TILL LYSSNARE FÖR COMBOBOXAR OCH KNAPP (OBS EJ FIXAT ÄN!!!!!)
        startYear.addItemListener(this);
        startMonth.addItemListener(this);
        startDay.addItemListener(this);
        endYear.addItemListener(this);
        endMonth.addItemListener(this);
        endDay.addItemListener(this);
        go.addActionListener(this);
    }

    //LÄGGER TILL ÅRTAL I COMBOBOXARNA
    private void buildYearsList(JComboBox yearsList) {
        int currentYear = startDate.get(Calendar.YEAR);
        for (int yearCount = currentYear - 10; yearCount <= currentYear; yearCount++){
            yearsList.addItem(Integer.toString(yearCount));
        }
    }

    //LÄGGER TILL MÅNADER I COMBOBOXARNA
    private void buildMonthsList(JComboBox monthsList) {
        monthsList.removeAllItems();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (int monthCount = 0; monthCount < months.length; monthCount++){
            monthsList.addItem(months[monthCount]);
        }
    }

    //LÄGGER TILL DAGAR I COMBOBOXARNA
    private void buildDaysList(Calendar dateIn, JComboBox daysList, JComboBox monthsList) {
        daysList.removeAllItems();
        dateIn.set(Calendar.MONTH, monthsList.getSelectedIndex());
        int lastDay = startDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int dayCount = 1; dayCount <= lastDay; dayCount++){
            daysList.addItem(Integer.toString(dayCount));
        }
    }

    //LÄGGER TILL TIMMAR I COMBOBOXARNA
    private void buildHoursList(JComboBox hoursList){
        for (int i = 0; i<24; i++){
            if(i<10){
                //String num = Integer.toString(i);
                String date = String.format("%02d", i);
                hoursList.addItem(date);
            } else{
                hoursList.addItem(Integer.toString(i));
            }
        }
    }

    //LÄGGER TILL MINUTER I COMBOBOXARNA
    private void buildMinutesList(JComboBox minutesList){
        for (int i = 0; i<60; i++){
            if(i<10){
                //String num = Integer.toString(i);
                String minute = String.format("%02d", i);
                minutesList.addItem(minute);
            } else{
                minutesList.addItem(Integer.toString(i));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
