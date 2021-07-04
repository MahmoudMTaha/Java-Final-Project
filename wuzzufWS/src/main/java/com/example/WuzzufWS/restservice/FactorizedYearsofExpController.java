package com.example.WuzzufWS.restservice;

import com.example.WuzzufWS.LoadData.ReadingCSV;
import com.example.WuzzufWS.LoadData.WuzzufEmpolyee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smile.data.DataFrame;
import smile.data.vector.StringVector;

import java.util.Arrays;
import java.util.List;

@RestController
public class FactorizedYearsofExpController {
    @GetMapping("/yearsofexp")
    public String YearsofExp(@RequestParam(value= "limit", defaultValue = "5" )int limit){
        ReadingCSV employsFile = new ReadingCSV();
        DataFrame Wuzzufdf = employsFile.readCSV("src/main/resources/Wuzzuf_Jobs.csv");

        Wuzzufdf = Wuzzufdf.omitNullRows();
        String[] values =new String[Wuzzufdf.size()];
        int flag=0;
        String [] newcol = new String[Wuzzufdf.size()];
        for(int i=0;i<Wuzzufdf.size();i++)
        {
            flag=0;
            values[i] = Wuzzufdf.column("YearsExp").get(i).toString();
            int ind = values[i].indexOf("-");
            if (ind==-1){
                ind=values[i].indexOf("+");
                flag=1;
            }
            if (flag==1 & ind !=-1)
            {String x= values[i].substring(0,ind);
                newcol[i]=(x);
                continue;}

            if (ind!=-1 & flag==0){
                String x = values[i].substring(0,ind);
                if (flag!=1){
                    int indy=values[i].indexOf(" ");
                    String y = values[i].substring(ind+1,indy);

                    Double avg = (Double.parseDouble(x) + Double.parseDouble(y) )/ 2;
                    //System.out.println(avg);
                    newcol[i]=String.valueOf(avg);
                    flag=1;}


            }
            else{ newcol[i]="NULL";}
        }

        Wuzzufdf=Wuzzufdf.merge(StringVector.of("FactorizedYearsofExp", (newcol)));
        Object[] out = Wuzzufdf.stream().limit(limit).toArray();
        return  Arrays.deepToString(out);
    }

}
