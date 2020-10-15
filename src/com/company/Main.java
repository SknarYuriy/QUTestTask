package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        int linesCount = 0;
        int spaceCount = 0;

        String[] linesArray = new String[0];
        int lineNumber = 0;
        BufferedReader fileReader;
        try
        {
            fileReader = new BufferedReader(new FileReader("input.txt"));
            String line = fileReader.readLine();
            System.out.println("Input:");
            while (line != null)
            {
                System.out.println(line);
                if (lineNumber == 0) {
                    linesCount = Integer.parseInt(line);
                    linesArray = new String[linesCount];
                }
                else
                {
                    linesArray[lineNumber-1] = line;
                }
                lineNumber++;
                line = fileReader.readLine();
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<DLine> DLineArrayList = new ArrayList<DLine>();
        ArrayList<CLine> CLineArrayList = new ArrayList<CLine>();
        StringBuilder number = new StringBuilder();
        int j = 0;
        for (int i = 0; i < linesCount; i++) {
            if(linesArray[i].charAt(0) == 'D') {
                DLine DLine = new DLine();
                DLine.setCharacter('D');
                while(linesArray[i].length() > j) {
                    if (linesArray[i].charAt(j) == ' ') {
                        spaceCount++;
                        j++;
                        if (spaceCount == 1)
                        {
                            j = getPosAndNumber(linesArray, number, j, i);
                            DLine.setServiceId(Double.parseDouble(number.toString()));
                            number = new StringBuilder();
                        }
                        if (spaceCount == 2)
                        {
                            if (linesArray[i].charAt(j) == '*')
                                DLine.setQuestionTypeId(0);
                            else
                                {
                                    j = getPosAndNumber(linesArray, number, j, i);
                                    DLine.setQuestionTypeId(Double.parseDouble(number.toString()));
                                    number = new StringBuilder();
                                }
                        }
                        if (spaceCount == 3)
                        {
                            DLine.setResponseType(linesArray[i].charAt(j));
                        }
                        if (spaceCount == 4) {
                            while (linesArray[i].length() != j && linesArray[i].charAt(j) != '-')
                            {
                                number.append(linesArray[i].charAt(j));
                                j++;
                            }
                            String stringDate = String.valueOf(number);
                            SimpleDateFormat format = new SimpleDateFormat();
                            format.applyPattern("dd.MM.yyyy");
                            Date date = format.parse(stringDate);
                            DLine.setDateFrom(date);
                            if(linesArray[i].length() != j)
                            {
                                number = new StringBuilder();
                                j++;
                                while (linesArray[i].length() != j)
                                {
                                    number.append(linesArray[i].charAt(j));
                                    j++;
                                }
                                DLine.setDateTo(dateFromString(String.valueOf(number)));
                                number = new StringBuilder();
                            }
                        }
                    }
                    else
                        j++;
                }
                 DLineArrayList.add(DLine);
            }
            else if(linesArray[i].charAt(0) == 'C')
            {
                CLine CLine = new CLine();
                CLine.setCharacter('С');
                while(linesArray[i].length() > j) {
                    if (linesArray[i].charAt(j) == ' ') {
                        j++;
                        spaceCount++;
                        if (spaceCount == 1)
                        {
                            j = getPosAndNumber(linesArray, number, j, i);
                            CLine.setServiceId(Double.parseDouble(number.toString()));
                            number = new StringBuilder();
                        }
                        else if (spaceCount == 2)
                        {
                            while (linesArray[i].length() != j &&
                                    linesArray[i].charAt(j) != ' ' &&
                                    linesArray[i].charAt(j) != '.')
                            {
                                number.append(linesArray[i].charAt(j));
                                j++;
                            }
                            CLine.setQuestionTypeId(Double.parseDouble(number.toString()));
                            number = new StringBuilder();
                        }
                       else  if (spaceCount == 3)
                        {
                            CLine.setResponseType(linesArray[i].charAt(j));
                        }
                        else if (spaceCount == 4) {
                            while (linesArray[i].length() != j &&
                                    linesArray[i].charAt(j) != '-' &&
                                    linesArray[i].charAt(j) != ' ')
                            {
                                number.append(linesArray[i].charAt(j));
                                j++;
                            }
                            CLine.setDate(dateFromString(String.valueOf(number)));
                            number = new StringBuilder();
                        }
                        else if (spaceCount == 5) {
                            while (linesArray[i].length() > j)
                            {
                                    number.append(linesArray[i].charAt(j));
                                    j++;
                            }
                            CLine.setWaitingTime(Integer.parseInt(number.toString()));
                            number = new StringBuilder();
                        }
                    }

                    else
                        j++;
                }
                CLineArrayList.add(CLine);
            }
            else
            {
                System.out.println("Input file is not correct.");
            }
            spaceCount = 0;
            j = 0;
        }
        System.out.println();
        System.out.println("Output:");
        int count = 0;
        int result = 0;
        for(DLine DLine : DLineArrayList) {
            for(CLine CLine : CLineArrayList) {
                if(DLine.getQuestionTypeId() == 0)
                {
                    if (DLine.getServiceId() <= CLine.getServiceId() &&
                            DLine.getResponseType() == CLine.getResponseType() &&
                            ((DLine.getDateTo().after(CLine.getDate()) && DLine.getDateFrom().before(CLine.getDate()))
                                    || DLine.getDateTo().equals(CLine.getDate()))) {
                        count++;
                        result += CLine.getWaitingTime();
//                        System.out.print(CLine.getWaitingTime() + "+");
                    }
                }
                else if (DLine.getServiceId() <= CLine.getServiceId() &&
                        DLine.getQuestionTypeId() == CLine.getQuestionTypeId() &&
                        DLine.getResponseType() == CLine.getResponseType()&&
                        ((DLine.getDateTo().after(CLine.getDate()) && DLine.getDateFrom().before(CLine.getDate()))
                                || DLine.getDateTo().equals(CLine.getDate()))) {
                    count++;
                    result += CLine.getWaitingTime();
//                    System.out.print(CLine.getWaitingTime() + "+");
                }

            }
//            System.out.println();
            if(count == 0)
                System.out.println("-");
            else
                System.out.println(result+"/"+count+" = "+result/count);
            count = 0;
            result = 0;
        }
    }

    private static int getPosAndNumber(String[] line, StringBuilder number, int j, int i) {
        while (line[i].length() != j && line[i].charAt(j) != ' ')
        {
            if (number != null) {
                    number.append(line[i].charAt(j));
            }
            j++;
        }
        return j;
    }

    private static Date dateFromString (String stringDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        StringBuilder builder = new StringBuilder();
        format.applyPattern("dd.MM.yyyy");
        Date date = format.parse(stringDate);
        return date;
    }
}
